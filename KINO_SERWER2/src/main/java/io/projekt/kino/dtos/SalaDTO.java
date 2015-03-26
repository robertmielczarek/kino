package io.projekt.kino.dtos;

import io.projekt.kino.entities.Kino;
import io.projekt.kino.entities.Miejsce;
import io.projekt.kino.entities.Seans;

import java.util.LinkedList;
import java.util.List;

public class SalaDTO {
	private Integer iDhall;
	private String name;
	private List<Integer> miejsceList;
	private List<Integer> seansList;
	private Integer iDCinema;

	public Integer getiDhall() {
		return iDhall;
	}

	public void setiDhall(Integer iDhall) {
		this.iDhall = iDhall;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getMiejsceList() {
		if (miejsceList == null)
			miejsceList = new LinkedList<Integer>();
		return miejsceList;
	}

	public void setMiejsceList(List<Integer> miejsceList) {
		this.miejsceList = miejsceList;
	}

	public List<Integer> getSeansList() {
		if (seansList == null)
			seansList = new LinkedList<Integer>();
		return seansList;
	}

	public void setSeansList(List<Integer> seansList) {
		this.seansList = seansList;
	}

	public Integer getiDCinema() {
		return iDCinema;
	}

	public void setiDCinema(Integer iDCinema) {
		this.iDCinema = iDCinema;
	}

}
