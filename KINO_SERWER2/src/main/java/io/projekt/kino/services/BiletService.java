package io.projekt.kino.services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.dtos.MiejsceDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.MiejsceRepository;
import io.projekt.kino.repositories.RezerwacjaRepostiory;
import io.projekt.kino.repositories.SeansRepository;
import io.projekt.kino.repositories.UzytkownikRepository;

@Service
@Transactional
public class BiletService {

	@Autowired
	private BiletRepository biletRepository;

	@Autowired
	private MiejsceRepository miejsceRepository;

	@Autowired
	private RezerwacjaRepostiory rezerwacjaRepository;

	@Autowired
	private UzytkownikRepository userRepository;

	@Autowired
	private SeansRepository seansRepository;

	public List<BiletDTO> findAllBiletsDTO() {
		List<BiletDTO> listDTO = new LinkedList<BiletDTO>();
		for (Bilet bilet : biletRepository.findAll()) {
			BiletDTO biletDTO = new BiletDTO();
			biletDTO.setTicketStatus(bilet.getTicketStatus());
			biletDTO.setPrice(bilet.getPrice());
			biletDTO.setSellingDate(bilet.getSellingDate());
			biletDTO.setiDTicket(bilet.getIDTicket());
			if (bilet.getIDCashier() != null)
				biletDTO.setiDCashier(bilet.getIDCashier().getIDuser());
			if (bilet.getIDReservation() != null)
				biletDTO.setiDReservation(bilet.getIDReservation()
						.getIDReservation());
			if (bilet.getIDSeat() != null)
				biletDTO.setiDSeat(bilet.getIDSeat().getIDSeat());
			if (bilet.getIDShow() != null)
				biletDTO.setiDShow(bilet.getIDShow().getIDShow());
			listDTO.add(biletDTO);
		}
		return listDTO;
	}

	public Integer create(BiletDTO biletDTO) {
		Bilet bilet = new Bilet();
		bilet.setTicketStatus(biletDTO.getTicketStatus());
		bilet.setPrice(biletDTO.getPrice());
		bilet.setSellingDate(biletDTO.getSellingDate());
		bilet.setIDCashier(userRepository.findOne(biletDTO.getiDCashier()));
		if (biletDTO.getiDReservation() != null)
			bilet.setIDReservation(rezerwacjaRepository.findOne(biletDTO
					.getiDReservation()));
		bilet.setIDSeat(miejsceRepository.findOne(biletDTO.getiDSeat()));
		bilet.setIDShow(seansRepository.findOne(biletDTO.getiDShow()));
		return biletRepository.save(bilet).getIDTicket();
	}


	public BiletDTO findBilet(Integer biletId) {
		BiletDTO biletDTO = new BiletDTO();
		Bilet bilet = biletRepository.findOne(biletId);
		biletDTO.setTicketStatus(bilet.getTicketStatus());
		biletDTO.setPrice(bilet.getPrice());
		biletDTO.setSellingDate(bilet.getSellingDate());
		biletDTO.setiDTicket(bilet.getIDTicket());
		if (bilet.getIDCashier() != null)
			biletDTO.setiDCashier(bilet.getIDCashier().getIDuser());
		if (bilet.getIDReservation() != null)
			biletDTO.setiDReservation(bilet.getIDReservation()
					.getIDReservation());
		if (bilet.getIDSeat() != null)
			biletDTO.setiDSeat(bilet.getIDSeat().getIDSeat());
		if (bilet.getIDShow() != null)
			biletDTO.setiDShow(bilet.getIDShow().getIDShow());
		return biletDTO;
	}

	public void update(BiletDTO biletDTO) {
		Bilet bilet = biletRepository.findOne(biletDTO.getiDTicket());
		bilet.setTicketStatus(biletDTO.getTicketStatus());
		bilet.setPrice(biletDTO.getPrice());
		bilet.setSellingDate(biletDTO.getSellingDate());
		bilet.setIDCashier(userRepository.findOne(biletDTO.getiDCashier()));
		if (biletDTO.getiDReservation() != null)
			bilet.setIDReservation(rezerwacjaRepository.findOne(biletDTO
					.getiDReservation()));
		bilet.setIDSeat(miejsceRepository.findOne(biletDTO.getiDSeat()));
		bilet.setIDShow(seansRepository.findOne(biletDTO.getiDShow()));
	}

	public void deleteBilet(int biletId) {
		biletRepository.delete(biletId);
	}

	public BiletDTO convertBiletToDTO(Bilet bilet) {
		BiletDTO biletDTO = new BiletDTO();
		biletDTO.setTicketStatus(bilet.getTicketStatus());
		biletDTO.setPrice(bilet.getPrice());
		biletDTO.setSellingDate(bilet.getSellingDate());
		biletDTO.setiDTicket(bilet.getIDTicket());
		if (bilet.getIDCashier() != null)
			biletDTO.setiDCashier(bilet.getIDCashier().getIDuser());
		if (bilet.getIDReservation() != null)
			biletDTO.setiDReservation(bilet.getIDReservation()
					.getIDReservation());
		if (bilet.getIDSeat() != null)
			biletDTO.setiDSeat(bilet.getIDSeat().getIDSeat());
		if (bilet.getIDShow() != null)
			biletDTO.setiDShow(bilet.getIDShow().getIDShow());
		return biletDTO;
	}

}
