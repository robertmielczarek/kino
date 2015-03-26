package io.projekt.kino.services;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.LockModeType;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Miejsce;
import io.projekt.kino.entities.Rezerwacja;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.exceptions.InvalidCredentialsException;
import io.projekt.kino.exceptions.RezerwacjaCancelledException;
import io.projekt.kino.exceptions.SeatTakenException;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.MiejsceRepository;
import io.projekt.kino.repositories.RezerwacjaRepostiory;
import io.projekt.kino.repositories.SeansRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RezerwowanieService {
	@Autowired
	private SeansRepository seansRepository;
	@Autowired
	private MiejsceRepository miejsceRepository;
	@Autowired
	private BiletRepository biletRepository;
	@Autowired
	private RezerwacjaRepostiory rezerwacjaRepository;
	@Autowired
	private BiletService biletService;

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public boolean reserveTemporary(int id_Seat, int id_Show) {
		Seans seans = seansRepository.findOne(id_Show);
		List<Bilet> bilety = seans.getBiletList();
		Miejsce miejsce = miejsceRepository.findOne(id_Seat);
		boolean zajete = false;
		for (Bilet bilet : bilety) {
			String status = bilet.getTicketStatus();
			if (bilet.getIDSeat().equals(miejsce)
					&& ("Sold".equalsIgnoreCase(status) || "Booked"
							.equalsIgnoreCase(status))) {
				zajete = true;
			}
		}
		if (!zajete) {
			Bilet bilet = new Bilet();
			bilet.setSellingDate(new Date());
			bilet.setIDSeat(miejsce);
			bilet.setIDShow(seans);
			bilet.setPrice(seans.getPrice());
			bilet.setTicketStatus("Booked");
			biletRepository.save(bilet);
		}
		return zajete;
	}

	@Transactional
	public void unreserveTemporary(int id_Seat, int id_Show) {
		Miejsce miejsce = miejsceRepository.findOne(id_Seat);
		Seans seans = seansRepository.findOne(id_Show);
		for (Bilet b : miejsce.getBiletList()) {
			if (b.getIDShow().equals(seans)) {
				b.setTicketStatus("Cancelled");
			}
		}
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public Integer bookTickets(List<BiletDTO> biletyDTO, String customerName,
			String customerLastName, String customerEmail) throws SeatTakenException {
		for (BiletDTO biletDTO : biletyDTO) {
			Seans s = seansRepository.findOne(biletDTO.getiDShow());
			Miejsce m = miejsceRepository.findOne(biletDTO.getiDSeat());
			List<Bilet> bilety = s.getBiletList();
			for (Bilet b : bilety) {
				String status = b.getTicketStatus();
				if (b.getIDSeat().equals(m)
						&& ("Sold".equalsIgnoreCase(status) || "Booked"
								.equalsIgnoreCase(status))) {
					throw new SeatTakenException();
				}
			}
		}

		Rezerwacja rezerwacja = new Rezerwacja();
		rezerwacja.setEmail(customerEmail);
		rezerwacja.setName(customerName);
		rezerwacja.setSurname(customerLastName);
		Integer iDReservation = rezerwacjaRepository.save(rezerwacja)
				.getIDReservation();
		for (BiletDTO biletDTO : biletyDTO) {
			biletDTO.setiDReservation(iDReservation);
			biletDTO.setTicketStatus("Booked");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			Date date = new Date(calendar.getTimeInMillis());
			biletDTO.setSellingDate(date);
			biletService.create(biletDTO);
		}
		return iDReservation;
	}

	@Transactional(isolation = Isolation.SERIALIZABLE)
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	public List<BiletDTO> sellTickets(List<BiletDTO> biletyDTO)
			throws Exception {
		for (BiletDTO biletDTO : biletyDTO) {
			Seans s = seansRepository.findOne(biletDTO.getiDShow());
			Miejsce m = miejsceRepository.findOne(biletDTO.getiDSeat());
			List<Bilet> bilety = s.getBiletList();
			for (Bilet b : bilety) {
				String status = b.getTicketStatus();
				if (b.getIDSeat().equals(m)
						&& ("Sold".equalsIgnoreCase(status) || "Booked"
								.equalsIgnoreCase(status))) {
					throw new SeatTakenException();
				}
			}
		}
		List<BiletDTO> lista = new LinkedList<BiletDTO>();
		for (BiletDTO biletDTO : biletyDTO) {
			biletDTO.setTicketStatus("Sold");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			Date date = new Date(calendar.getTimeInMillis());
			biletDTO.setSellingDate(date);
			biletDTO.setiDTicket(biletService.create(biletDTO));
			lista.add(biletDTO);
		}
		return lista;
	}

	@Transactional
	public boolean cancelBooking(String customerLastName, int bookingId)
			throws InvalidCredentialsException, RezerwacjaCancelledException, SeatTakenException {
		Rezerwacja rezerwacja = rezerwacjaRepository.findOne(bookingId);
		if (!customerLastName.equals(rezerwacja.getSurname()))
			return false;
		if(rezerwacja.getIdCancelled())
			throw new RezerwacjaCancelledException();
		
		for (Bilet b : rezerwacja.getBiletList()) {
			if(!"Booked".equalsIgnoreCase(b.getTicketStatus())){
				throw new SeatTakenException();
			}
			b.setTicketStatus("Cancelled");
		}
		rezerwacja.setIdCancelled(true);
		return true;
	}

	@Transactional
	public List<BiletDTO> realizeBooking(String customerLastName, int bookingId)
			throws InvalidCredentialsException, RezerwacjaCancelledException, SeatTakenException {
		Rezerwacja rezerwacja = rezerwacjaRepository.findOne(bookingId);
		if (!customerLastName.equals(rezerwacja.getSurname()))
			throw new InvalidCredentialsException();
		if(rezerwacja.getIdCancelled())
			throw new RezerwacjaCancelledException();
		
		List<BiletDTO> lista = new LinkedList<BiletDTO>();
		for (Bilet b : rezerwacja.getBiletList()) {
			if(!"Booked".equalsIgnoreCase(b.getTicketStatus())){
					throw new SeatTakenException();
			}
			b.setTicketStatus("Sold");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			Date date = new Date(calendar.getTimeInMillis());
			b.setSellingDate(date);
			lista.add(biletService.convertBiletToDTO(b));
		}
		return lista;
	}

}
