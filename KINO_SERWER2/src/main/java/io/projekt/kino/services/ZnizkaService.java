package io.projekt.kino.services;

import java.util.LinkedList;
import java.util.List;

import io.projekt.kino.dtos.ZnizkaDTO;
import io.projekt.kino.entities.Znizka;
import io.projekt.kino.repositories.ZnizkaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ZnizkaService {

	@Autowired
	private ZnizkaRepository znizkaRepository;

	public void deleteZnizka(int znizkaId) {
		znizkaRepository.delete(znizkaId);
	}

	public List<ZnizkaDTO> findAllZnizkasDTO() {
		List<ZnizkaDTO> listDTO = new LinkedList<ZnizkaDTO>();
		for(Znizka znizka: znizkaRepository.findAll()){
			ZnizkaDTO znizkaDTO = new ZnizkaDTO();
			znizkaDTO.setName(znizka.getName());
			znizkaDTO.setValue(znizka.getValue());
			znizkaDTO.setiDDiscount(znizka.getIDDiscount());
			listDTO.add(znizkaDTO);
		}
		return listDTO;
	}

	public Integer create(ZnizkaDTO znizkaDTO) {
		Znizka znizka = new Znizka();
		znizka.setName(znizkaDTO.getName());
		znizka.setValue(znizkaDTO.getValue());
		return znizkaRepository.save(znizka).getIDDiscount();
	}

	public void update(ZnizkaDTO znizkaDTO) {
		Znizka znizka = znizkaRepository.findOne(znizkaDTO.getiDDiscount());
		znizka.setName(znizkaDTO.getName());
		znizka.setValue(znizkaDTO.getValue());
		znizkaRepository.save(znizka);
	}

	public ZnizkaDTO findZnizka(Integer znizkaId) {
		ZnizkaDTO znizkaDTO = new ZnizkaDTO();
		Znizka znizka = znizkaRepository.findOne(znizkaId);
		znizkaDTO.setName(znizka.getName());
		znizkaDTO.setValue(znizka.getValue());
		znizkaDTO.setiDDiscount(znizka.getIDDiscount());
		return znizkaDTO;
	}
}

