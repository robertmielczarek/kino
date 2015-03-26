package io.projekt.kino.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.FilmRepository;
import io.projekt.kino.repositories.SalaRepository;
import io.projekt.kino.repositories.SeansRepository;

@Service
@Transactional
public class SeansService {
	
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private SalaRepository salaRepository;
	@Autowired
	private BiletRepository biletRepository;
	
	public SeansDTO convertSeansToDTO(Seans seans){
		SeansDTO seansDTO = new SeansDTO();
		seansDTO.setiDShow(seans.getIDShow());
		seansDTO.setDate(seans.getDate());
		seansDTO.setPrice(seans.getPrice());
		if(seans.getIDMovie() != null)
		seansDTO.setiDMovie(seans.getIDMovie().getIDMovie());
		if(seans.getIDAuditorium() != null)
		seansDTO.setiDAuditorium(seans.getIDAuditorium().getIDhall());
		for(Bilet bilet: seans.getBiletList()){
			seansDTO.getBiletList().add(bilet.getIDTicket());
		}
		return seansDTO;
	}
	
	@Autowired 
	private SeansRepository seansRepository;
	
	@Transactional
	public List<Seans> getSeansByIds(List<Integer> ids){
		List<Seans> lista = new LinkedList<Seans>();
		for(Integer id: ids){
			lista.add(seansRepository.findOne(id));
		}
		return lista;
	}

	public List<SeansDTO> findAllSeansDTO() {
		List<Seans> seanse = seansRepository.findAll();
		List<SeansDTO> seanseDTO = new LinkedList<SeansDTO>();
		for(Seans s: seanse){
			seanseDTO.add(convertSeansToDTO(s));
		}
		return seanseDTO;
	}


	public Integer create(SeansDTO seansDTO) {
		Seans seans = new Seans();
		seans.setDate(seansDTO.getDate());
		seans.setPrice(seansDTO.getPrice());
		seans.setIDMovie(filmRepository.findOne(seansDTO.getiDMovie()));
		seans.setIDAuditorium(salaRepository.findOne(seansDTO.getiDAuditorium()));
		seans.setBiletList(biletRepository.findAll(seansDTO.getBiletList()));
		return seansRepository.save(seans).getIDShow();
	}

	public void update(SeansDTO seansDTO) {
		Seans seans = seansRepository.findOne(seansDTO.getiDShow());
		seans.setDate(seansDTO.getDate());
		seans.setPrice(seansDTO.getPrice());
		seans.setIDMovie(filmRepository.findOne(seansDTO.getiDMovie()));
		seans.setIDAuditorium(salaRepository.findOne(seansDTO.getiDAuditorium()));
		seans.setBiletList(biletRepository.findAll(seansDTO.getBiletList()));
		seansRepository.save(seans);
	}

	public SeansDTO findSeans(Integer seansId) {
		return convertSeansToDTO(seansRepository.findOne(seansId));
	}

	public void deleteSeans(int seansId) {
		seansRepository.delete(seansId);
	}
}
