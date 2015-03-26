package io.projekt.kino.services;

import io.projekt.kino.dtos.UzytkownikDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Film;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.entities.Uzytkownik;
import io.projekt.kino.repositories.BiletRepository;
import io.projekt.kino.repositories.KinoRepository;
import io.projekt.kino.repositories.UzytkownikRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UzytkownikService
{
	@Autowired
	private UzytkownikRepository uzytkownikRepository;
	@Autowired
	private KinoRepository kinoRepository;
	@Autowired 
	private BiletRepository biletRepository;

	public List<UzytkownikDTO> findAll()
	{
			List<UzytkownikDTO> uzytkownicy = new ArrayList<UzytkownikDTO>();
			
			for(Uzytkownik uzytkownik: uzytkownikRepository.findAll()){
				UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
				uzytkownikDTO.setAddress(uzytkownik.getAddress());
				if(uzytkownik.getIDCinema() != null)
				uzytkownikDTO.setiDCinema(uzytkownik.getIDCinema().getIDCinema());
				uzytkownikDTO.setiDuser(uzytkownik.getIDuser());
				uzytkownikDTO.setLogin(uzytkownik.getLogin());
				uzytkownikDTO.setName(uzytkownik.getName());
				uzytkownikDTO.setPassword(uzytkownik.getPassword());
				uzytkownikDTO.setPhoneNumber(uzytkownik.getPhoneNumber());
				uzytkownikDTO.setRole(uzytkownik.getRole());
				uzytkownikDTO.setSurname(uzytkownik.getSurname());
				for(Bilet bilet: uzytkownik.getBiletList()){
					uzytkownikDTO.getBiletList().add(bilet.getIDTicket());
				}
				uzytkownicy.add(uzytkownikDTO);
			}
			return uzytkownicy;
    }
	
	// co zrobic jak sa dwa uzytkownicy o takim samym loginie i hasle ?
	public UzytkownikDTO login(String login, String password)
	{
		for(Uzytkownik u: uzytkownikRepository.findAll()){
			if(u.getLogin().equals(login) && u.getPassword().equals(password)){
				UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
				uzytkownikDTO.setAddress(u.getAddress());
				if(u.getIDCinema() != null)
				uzytkownikDTO.setiDCinema(u.getIDCinema().getIDCinema());
				uzytkownikDTO.setiDuser(u.getIDuser());
				uzytkownikDTO.setLogin(u.getLogin());
				uzytkownikDTO.setName(u.getName());
				uzytkownikDTO.setPassword(u.getPassword());
				uzytkownikDTO.setPhoneNumber(u.getPhoneNumber());
				uzytkownikDTO.setRole(u.getRole());
				uzytkownikDTO.setSurname(u.getSurname());
				for(Bilet bilet: u.getBiletList()){
					uzytkownikDTO.getBiletList().add(bilet.getIDTicket());
				}
				return uzytkownikDTO;
			}
		}
		
		return new UzytkownikDTO(); // zdefiniowane w wymaganiach grupy LOGIKA
	}
	
	public void deleteUser(Integer userId){
		uzytkownikRepository.delete(userId);
	}

	public Integer create(UzytkownikDTO uzytkownikDTO){
		Uzytkownik uzytkownik = new Uzytkownik();
		uzytkownik.setAddress(uzytkownikDTO.getAddress());
		uzytkownik.setLogin(uzytkownikDTO.getLogin());
		uzytkownik.setName(uzytkownikDTO.getName());
		uzytkownik.setPassword(uzytkownikDTO.getPassword());
		uzytkownik.setPhoneNumber(uzytkownikDTO.getPhoneNumber());
		uzytkownik.setRole(uzytkownikDTO.getRole());
		uzytkownik.setSurname(uzytkownikDTO.getSurname());
		uzytkownik.setIDCinema(kinoRepository.findOne(uzytkownikDTO.getiDCinema()));
		uzytkownik.setBiletList(biletRepository.findAll(uzytkownikDTO.getBiletList()));
		return uzytkownikRepository.save(uzytkownik).getIDuser();
	}
	public void update(UzytkownikDTO uzytkownikDTO){
		Uzytkownik uzytkownik = uzytkownikRepository.findOne(uzytkownikDTO.getiDuser());
		uzytkownik.setAddress(uzytkownikDTO.getAddress());
		uzytkownik.setLogin(uzytkownikDTO.getLogin());
		uzytkownik.setName(uzytkownikDTO.getName());
		uzytkownik.setPassword(uzytkownikDTO.getPassword());
		uzytkownik.setPhoneNumber(uzytkownikDTO.getPhoneNumber());
		uzytkownik.setRole(uzytkownikDTO.getRole());
		uzytkownik.setSurname(uzytkownikDTO.getSurname());
		uzytkownik.setIDCinema(kinoRepository.findOne(uzytkownikDTO.getiDCinema()));
		uzytkownik.setBiletList(biletRepository.findAll(uzytkownikDTO.getBiletList()));
		uzytkownikRepository.save(uzytkownik);
	}
	
	public UzytkownikDTO findUzytkownik(Integer id){
		UzytkownikDTO uzytkownikDTO = new UzytkownikDTO();
		Uzytkownik uzytkownik = uzytkownikRepository.findOne(id);
		uzytkownikDTO.setAddress(uzytkownik.getAddress());
		if(uzytkownik.getIDCinema() != null)
		uzytkownikDTO.setiDCinema(uzytkownik.getIDCinema().getIDCinema());
		uzytkownikDTO.setiDuser(uzytkownik.getIDuser());
		uzytkownikDTO.setLogin(uzytkownik.getLogin());
		uzytkownikDTO.setName(uzytkownik.getName());
		uzytkownikDTO.setPassword(uzytkownik.getPassword());
		uzytkownikDTO.setPhoneNumber(uzytkownik.getPhoneNumber());
		uzytkownikDTO.setRole(uzytkownik.getRole());
		uzytkownikDTO.setSurname(uzytkownik.getSurname());
		for(Bilet bilet: uzytkownik.getBiletList()){
			uzytkownikDTO.getBiletList().add(bilet.getIDTicket());
		}
		return uzytkownikDTO;
	}

	public List<Uzytkownik> findAll2() {
		return uzytkownikRepository.findAll();
	}

	public Uzytkownik findUzytkownik2(Integer userId) {
		return uzytkownikRepository.findOne(userId);
	}
}
