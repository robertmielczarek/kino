package io.projekt.kino.dtos;

import io.projekt.kino.entities.Bilet;

import java.util.ArrayList;
import java.util.List;

public class RezerwacjaDTO {

	private Integer iDReservation;
	private String name;
	private String surname;
	private String email;
	private boolean idCancelled;

	private List<Integer> biletList;

	public Integer getiDReservation() {
		return iDReservation;
	}

	public void setiDReservation(Integer iDReservation) {
		this.iDReservation = iDReservation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isIdCancelled() {
		return idCancelled;
	}

	public void setIdCancelled(boolean idCancelled) {
		this.idCancelled = idCancelled;
	}

	public List<Integer> getBiletList() {
		if (biletList == null)
			biletList = new ArrayList<>();
		return biletList;
	}

	public void setBiletList(List<Integer> biletList) {
		this.biletList = biletList;
	}

}
