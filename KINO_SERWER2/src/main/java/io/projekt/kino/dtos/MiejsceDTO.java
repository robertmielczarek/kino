package io.projekt.kino.dtos;

import java.util.LinkedList;
import java.util.List;

public class MiejsceDTO {

	private Integer iDSeat;
	private int number;

	private Integer iDAuditorium;
	private List<Integer> biletList;

	public Integer getiDSeat() {
		return iDSeat;
	}

	public void setiDSeat(Integer iDSeat) {
		this.iDSeat = iDSeat;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Integer getiDAuditorium() {
		return iDAuditorium;
	}

	public void setiDAuditorium(Integer iDAuditorium) {
		this.iDAuditorium = iDAuditorium;
	}

	public List<Integer> getBiletList() {
		if(biletList==null)
			biletList=new LinkedList<Integer>();
		return biletList;
	}

	public void setBiletList(List<Integer> biletList) {
		this.biletList = biletList;
	}

}
