package io.projekt.kino.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import io.projekt.kino.dtos.FilmDTO;
import io.projekt.kino.dtos.KinoDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Kino;
import io.projekt.kino.entities.Sala;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.entities.Uzytkownik;
import io.projekt.kino.repositories.KinoRepository;
import io.projekt.kino.repositories.SalaRepository;
import io.projekt.kino.repositories.UzytkownikRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class KinoService {

	@Autowired
	private KinoRepository kinoRepository;

	@Autowired
	private UzytkownikRepository uzytkownikRepository;
	
	@Autowired
	private SalaRepository salaRepository;
	

	public List<KinoDTO> findAllCinemasDTO() {
		List<Kino> kina = kinoRepository.findAll();
		List<KinoDTO> kinaDTOs = new LinkedList<KinoDTO>();
		for(Kino kino: kina){
			KinoDTO kinoDTO = new KinoDTO();
			kinoDTO.setCity(kino.getCity());
			kinoDTO.setName(kino.getName());
			kinoDTO.setiDCinema(kino.getIDCinema());
			for(Sala s: kino.getSalaList()){
				kinoDTO.getSalaList().add(s.getIDhall());
			}
			for(Uzytkownik u: kino.getUzytkownikList()){
				kinoDTO.getUzytkownikList().add(u.getIDuser());
			}
			kinaDTOs.add(kinoDTO);
		}
		return kinaDTOs;
	}

	public void deleteCinema(int cinemaId) {
		kinoRepository.delete(cinemaId);
	}

	public Integer create(KinoDTO kinoDTO) {
		Kino kino = new Kino();
		kino.setCity(kinoDTO.getCity());
		kino.setName(kinoDTO.getName());
		for(Integer salaId: kinoDTO.getSalaList()){
			kino.getSalaList().add(salaRepository.findOne(salaId));
		}
		for(Integer uzytkownikId: kinoDTO.getUzytkownikList()){
			kino.getUzytkownikList().add(uzytkownikRepository.findOne(uzytkownikId));
		}
		return kinoRepository.save(kino).getIDCinema();
	}

	public KinoDTO findCinema(Integer cinemaId) {
		Kino kino = kinoRepository.findOne(cinemaId);
		KinoDTO kinoDTO = new KinoDTO();
		kinoDTO.setCity(kino.getCity());
		kinoDTO.setName(kino.getName());
		kinoDTO.setiDCinema(kino.getIDCinema());
		for(Sala s: kino.getSalaList()){
			kinoDTO.getSalaList().add(s.getIDhall());
		}
		for(Uzytkownik u: kino.getUzytkownikList()){
			kinoDTO.getUzytkownikList().add(u.getIDuser());
		}
		return kinoDTO;
	}

	public void update(KinoDTO kinoDTO) {
		Kino kino = kinoRepository.findOne(kinoDTO.getiDCinema());
		kino.setCity(kinoDTO.getCity());
		kino.setName(kinoDTO.getName());
	    kino.setSalaList(salaRepository.findAll(kinoDTO.getSalaList()));
	    kino.setUzytkownikList(uzytkownikRepository.findAll(kinoDTO.getUzytkownikList()));
	    kinoRepository.save(kino);
	}

	public SeansDTO findSeans(Integer seansId) {
		// TODO Auto-generated method stub
		return null;
	}
}
