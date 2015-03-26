package io.projekt.kino.services;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import io.projekt.kino.dtos.SalaDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Miejsce;
import io.projekt.kino.entities.Sala;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.repositories.KinoRepository;
import io.projekt.kino.repositories.MiejsceRepository;
import io.projekt.kino.repositories.SalaRepository;
import io.projekt.kino.repositories.SeansRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SalaService {

	@Autowired
	private SalaRepository salaRepository;
	@Autowired
	private KinoRepository kinoRepository;
	@Autowired
	private SeansRepository seansRepository;
	@Autowired
	private MiejsceRepository miejsceRepository;

	
	public List<SalaDTO> findAllSalasDTO() {
		List<Sala> sale = salaRepository.findAll();
		List<SalaDTO> saleDTO = new LinkedList<SalaDTO>();
		for(Sala sala: sale){
			SalaDTO salaDTO = new SalaDTO();
			salaDTO.setName(sala.getName());
			if(sala.getIDCinema()!=null)
			salaDTO.setiDCinema(sala.getIDCinema().getIDCinema());
			salaDTO.setiDhall(sala.getIDhall());
			for(Miejsce m: sala.getMiejsceList()){
				salaDTO.getMiejsceList().add(m.getIDSeat());
			}
			for(Seans s: sala.getSeansList()){
				salaDTO.getSeansList().add(s.getIDShow());
			}
			saleDTO.add(salaDTO);
		}
		return saleDTO;
	}

	public void deleteSala(int salaId) {
		salaRepository.delete(salaId);
	}

	public Integer create(SalaDTO salaDTO) {
		Sala sala = new Sala();
		sala.setName(salaDTO.getName());
		sala.setIDCinema(kinoRepository.findOne(salaDTO.getiDCinema()));
		sala.setSeansList(seansRepository.findAll(salaDTO.getSeansList()));
		sala.setMiejsceList(miejsceRepository.findAll(salaDTO.getMiejsceList()));
		return salaRepository.save(sala).getIDhall();
	}

	public void update(SalaDTO salaDTO) {
		Sala sala = salaRepository.findOne(salaDTO.getiDhall());
		sala.setName(salaDTO.getName());
		sala.setIDCinema(kinoRepository.findOne(salaDTO.getiDCinema()));
		sala.setSeansList(seansRepository.findAll(salaDTO.getSeansList()));
		sala.setMiejsceList(miejsceRepository.findAll(salaDTO.getMiejsceList()));
		salaRepository.save(sala);	
	}

	public SalaDTO findSala(Integer salaId) {
		SalaDTO salaDTO = new SalaDTO();
		Sala sala = salaRepository.findOne(salaId);
		salaDTO.setName(sala.getName());
		if(sala.getIDCinema()!=null)
		salaDTO.setiDCinema(sala.getIDCinema().getIDCinema());
		salaDTO.setiDhall(sala.getIDhall());
		for(Miejsce m: sala.getMiejsceList()){
			salaDTO.getMiejsceList().add(m.getIDSeat());
		}
		for(Seans s: sala.getSeansList()){
			salaDTO.getSeansList().add(s.getIDShow());
		}
		return salaDTO;
	}
	
	
	
}
