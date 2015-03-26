package io.projekt.kino.services;

import java.util.LinkedList;
import java.util.List;

import io.projekt.kino.dtos.BiletDTO;
import io.projekt.kino.dtos.RezerwacjaDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Rezerwacja;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.RezerwacjaRepostiory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RezerwacjaService {

	@Autowired
	private RezerwacjaRepostiory rezerwacjaRepository;
	@Autowired
	private BiletRepository biletRepository;

	public void update(RezerwacjaDTO rezerwacjaDTO) {
		Rezerwacja rezerwacja = rezerwacjaRepository.findOne(rezerwacjaDTO
				.getiDReservation());
		rezerwacja.setEmail(rezerwacjaDTO.getEmail());
		rezerwacja.setIdCancelled(rezerwacjaDTO.isIdCancelled());
		rezerwacja.setName(rezerwacjaDTO.getName());
		rezerwacja.setSurname(rezerwacjaDTO.getSurname());
		rezerwacja.setBiletList(biletRepository.findAll(rezerwacjaDTO
				.getBiletList()));
		rezerwacjaRepository.save(rezerwacja);
	}

	public RezerwacjaDTO findRezerwacja(Integer rezerwacjaId) {
		RezerwacjaDTO rezerwacjaDTO = new RezerwacjaDTO();
		Rezerwacja rezerwacja = rezerwacjaRepository.findOne(rezerwacjaId);
		rezerwacjaDTO.setEmail(rezerwacja.getEmail());
		rezerwacjaDTO.setIdCancelled(rezerwacja.getIdCancelled());
		rezerwacjaDTO.setName(rezerwacja.getName());
		rezerwacjaDTO.setSurname(rezerwacja.getSurname());
		rezerwacjaDTO.setiDReservation(rezerwacja.getIDReservation());
		for (Bilet bilet : rezerwacja.getBiletList()) {
			rezerwacjaDTO.getBiletList().add(bilet.getIDTicket());
		}
		return rezerwacjaDTO;
	}

	public Integer create(RezerwacjaDTO rezerwacjaDTO) {
		Rezerwacja rezerwacja = new Rezerwacja();
		rezerwacja.setEmail(rezerwacjaDTO.getEmail());
		rezerwacja.setIdCancelled(rezerwacjaDTO.isIdCancelled());
		rezerwacja.setName(rezerwacjaDTO.getName());
		rezerwacja.setSurname(rezerwacjaDTO.getSurname());
		rezerwacja.setBiletList(biletRepository.findAll(rezerwacjaDTO
				.getBiletList()));
		return rezerwacjaRepository.save(rezerwacja).getIDReservation();
	}

	public void deleteRezerwacja(int rezerwacjaId) {
		rezerwacjaRepository.delete(rezerwacjaId);
	}

	public List<RezerwacjaDTO> findAllRezerwacjasDTO() {
		List<RezerwacjaDTO> listDTO = new LinkedList<RezerwacjaDTO>();
		for (Rezerwacja rezerwacja : rezerwacjaRepository.findAll()) {
			RezerwacjaDTO rezerwacjaDTO = new RezerwacjaDTO();
			rezerwacjaDTO.setEmail(rezerwacja.getEmail());
			rezerwacjaDTO.setIdCancelled(rezerwacja.getIdCancelled());
			rezerwacjaDTO.setName(rezerwacja.getName());
			rezerwacjaDTO.setSurname(rezerwacja.getSurname());
			rezerwacjaDTO.setiDReservation(rezerwacja.getIDReservation());
			for (Bilet bilet : rezerwacja.getBiletList()) {
				rezerwacjaDTO.getBiletList().add(bilet.getIDTicket());
			}
			listDTO.add(rezerwacjaDTO);
		}
		return listDTO;
	}

}
