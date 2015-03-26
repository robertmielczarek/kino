package io.projekt.kino.services;

import io.projekt.kino.dtos.MiejsceBooleanDTO;
import io.projekt.kino.dtos.MiejsceDTO;
import io.projekt.kino.dtos.SeansDTO;
import io.projekt.kino.entities.Bilet;
import io.projekt.kino.entities.Kino;
import io.projekt.kino.entities.Miejsce;
import io.projekt.kino.entities.Sala;
import io.projekt.kino.entities.Seans;
import io.projekt.kino.repositories.KinoRepository;
import io.projekt.kino.repositories.SalaRepository;
import io.projekt.kino.repositories.SeansRepository;
import io.projekt.kino.repositories.UzytkownikRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdditionalService {

	@Autowired
	private KinoRepository kinoRepository;

	@Autowired
	private SeansService seansService;

	@Autowired
	private UzytkownikRepository uzytkownikRepository;

	@Autowired
	private SalaRepository salaRepository;

	@Autowired
	private SeansRepository seansRepository;

	@Autowired
	private MiejsceService miejsceService;

	public List<String> getCityAndCinemas() {
		List<String> kinaimiasta = new LinkedList<>();
		for (Kino k : kinoRepository.findAll()) {
			kinaimiasta.add(k.getCity() + "|" + k.getName());
		}
		return kinaimiasta;
	}

	public List<SeansDTO> getShowList(String nazwa_miasta, String nazwa_kina,
			Date date, int iD_Movie) {
		Kino kino = null;
		List<SeansDTO> seanse = new LinkedList<SeansDTO>();
		for (Kino k : kinoRepository.findAll()) {
			if (k.getCity().equals(nazwa_miasta)
					&& k.getName().equals(nazwa_kina)) {
				kino = k;
				break;
			}
		}
		if (kino == null) {
			return seanse;
		}
		for (Sala s : kino.getSalaList()) {
			for (Seans seans : s.getSeansList()) {
				if (DateUtils.isSameDay(seans.getDate(), date)) {
					if (seans.getIDMovie().getIDMovie().equals(iD_Movie))
						seanse.add(seansService.convertSeansToDTO(seans));
				}
			}
		}

		return seanse;
	}

	public List<SeansDTO> getShowList(String nazwa_miasta, String nazwa_kina,
			Date date) {
		Kino kino = null;
		List<SeansDTO> seanse = new LinkedList<SeansDTO>();
		for (Kino k : kinoRepository.findAll()) {
			if (k.getCity().equals(nazwa_miasta)
					&& k.getName().equals(nazwa_kina)) {
				kino = k;
				break;
			}
		}
		if (kino == null) {
			return seanse; // wie co tutaj zrobiæ czy zwróciæ pust¹ listê czy
		}
		for (Sala s : kino.getSalaList()) {
			for (Seans seans : s.getSeansList()) {
				if (DateUtils.isSameDay(seans.getDate(), date)) { // tak kazali
					seanse.add(seansService.convertSeansToDTO(seans));
				}
			}
		}

		return seanse;
	}

	public List<String> getSeatList(int showId) {
		Seans seans = seansRepository.findOne(showId);
		List<Bilet> bilety = seans.getBiletList();
		List<Miejsce> miejsca = seans.getIDAuditorium().getMiejsceList();
		List<String> lista = new LinkedList<String>();
		for (Miejsce miejsce : miejsca) {
			boolean zajete = false;
			for (Bilet bilet : bilety) {
				String status = bilet.getTicketStatus();
				if (bilet.getIDSeat().equals(miejsce)
						&& ("Sold".equalsIgnoreCase(status) || "Booked"
								.equalsIgnoreCase(status))) {
					zajete = true;
				}
			}
			if (zajete)
				lista.add(miejsce.getIDSeat() + "|" + miejsce.getNumber() + "|"
						+ "0");
			else
				lista.add(miejsce.getIDSeat() + "|" + miejsce.getNumber() + "|"
						+ "1");
		}
		return lista;
	}

	public List<MiejsceBooleanDTO> getSeatMap(int showId) {
		Seans seans = seansRepository.findOne(showId);
		List<MiejsceBooleanDTO> lista = new LinkedList<MiejsceBooleanDTO>();
		for (Miejsce miejsce : seans.getIDAuditorium().getMiejsceList()) {
			boolean zajete = false;
			for (Bilet bilet : seans.getBiletList()) {
				String status = bilet.getTicketStatus();
				if (bilet.getIDSeat().equals(miejsce)
						&& ("Sold".equalsIgnoreCase(status) || "Booked"
								.equalsIgnoreCase(status))) {
					zajete = true;
				}
			}
			lista.add(miejsceService
					.convertMiejsceToBooleanDTO(miejsce, zajete));
		}
		return lista;
	}

}
