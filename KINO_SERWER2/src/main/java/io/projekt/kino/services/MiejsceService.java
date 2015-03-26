package io.projekt.kino.services;

import java.util.LinkedList;
import java.util.List;

import io.projekt.kino.dtos.MiejsceBooleanDTO;
import io.projekt.kino.dtos.MiejsceDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Miejsce;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.MiejsceRepository;
import io.projekt.kino.repositories.SalaRepository;
import io.projekt.kino.repositories.SeansRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MiejsceService {

	@Autowired 
	private MiejsceRepository miejsceRepository;
	@Autowired
	private SalaRepository salaRepository;
	@Autowired
	private BiletRepository biletRepository;

	public List<MiejsceDTO> findAllMiejscasDTO() {
		List<MiejsceDTO> lista = new LinkedList<MiejsceDTO>();
		for(Miejsce m: miejsceRepository.findAll()){
			MiejsceDTO miejsceDTO = new MiejsceDTO();
			miejsceDTO.setNumber(m.getNumber());
			miejsceDTO.setiDSeat(m.getIDSeat());
			if(m.getIDAuditorium()!=null)
			miejsceDTO.setiDAuditorium(m.getIDAuditorium().getIDhall());
			for(Bilet b: m.getBiletList()){
				miejsceDTO.getBiletList().add(b.getIDTicket());
			}
			lista.add(miejsceDTO);
		}
		return lista;
	}

	public void deleteMiejsce(int miejsceId) {
		miejsceRepository.delete(miejsceId);
	}

	public MiejsceDTO findMiejsce(Integer miejsceId) {
		Miejsce miejsce = miejsceRepository.findOne(miejsceId);
		return convertMiejsceToDTO(miejsce);
	}

	public void update(MiejsceDTO miejsceDTO) {
		Miejsce miejsce = miejsceRepository.findOne(miejsceDTO.getiDSeat());
		miejsce.setNumber(miejsceDTO.getNumber());
		miejsce.setIDAuditorium(salaRepository.findOne(miejsceDTO.getiDAuditorium()));
		miejsce.setBiletList(biletRepository.findAll(miejsceDTO.getBiletList()));
		miejsceRepository.save(miejsce);
	}

	public Integer create(MiejsceDTO miejsceDTO) {
		Miejsce miejsce = new Miejsce();
		miejsce.setNumber(miejsceDTO.getNumber());
		miejsce.setIDAuditorium(salaRepository.findOne(miejsceDTO.getiDAuditorium()));
		miejsce.setBiletList(biletRepository.findAll(miejsceDTO.getBiletList()));
		return miejsceRepository.save(miejsce).getIDSeat();
	}
	
	public MiejsceDTO convertMiejsceToDTO(Miejsce miejsce){
		MiejsceDTO miejsceDTO = new MiejsceDTO();
		miejsceDTO.setNumber(miejsce.getNumber());
		miejsceDTO.setiDSeat(miejsce.getIDSeat());
		if(miejsce.getIDAuditorium()!=null)
		miejsceDTO.setiDAuditorium(miejsce.getIDAuditorium().getIDhall());
		for(Bilet b: miejsce.getBiletList()){
			miejsceDTO.getBiletList().add(b.getIDTicket());
		}
		return miejsceDTO;
	}
	
	
	public MiejsceBooleanDTO convertMiejsceToBooleanDTO(Miejsce miejsce,boolean isBooked){
		MiejsceBooleanDTO miejsceBooleanDTO = new MiejsceBooleanDTO();
		MiejsceDTO miejsceDTO = new MiejsceDTO();
		miejsceDTO.setNumber(miejsce.getNumber());
		miejsceDTO.setiDSeat(miejsce.getIDSeat());
		if(miejsce.getIDAuditorium()!=null)
		miejsceDTO.setiDAuditorium(miejsce.getIDAuditorium().getIDhall());
		for(Bilet b: miejsce.getBiletList()){
			miejsceDTO.getBiletList().add(b.getIDTicket());
		}
		miejsceBooleanDTO.setMiejsceDTO(miejsceDTO);
		miejsceBooleanDTO.setIsBooked(isBooked);
		return miejsceBooleanDTO;
	}
}
