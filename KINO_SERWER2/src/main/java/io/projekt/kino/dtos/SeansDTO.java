package io.projekt.kino.dtos;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeansDTO {
	
    private Integer iDShow;
	private Date date;
    private double price;
    private List<Integer> biletList;
    private Integer iDAuditorium;
    private Integer iDMovie;
    
    public Integer getiDShow() {
		return iDShow;
	}
	public void setiDShow(Integer iDShow) {
		this.iDShow = iDShow;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public List<Integer> getBiletList() {
		if(biletList == null)
	    biletList=new ArrayList<Integer>();
		return biletList;
	}
	public void setBiletList(List<Integer> biletList) {
		this.biletList = biletList;
	}
	public Integer getiDAuditorium() {
		return iDAuditorium;
	}
	public void setiDAuditorium(Integer iDAuditorium) {
		this.iDAuditorium = iDAuditorium;
	}
	public Integer getiDMovie() {
		return iDMovie;
	}
	public void setiDMovie(Integer iDMovie) {
		this.iDMovie = iDMovie;
	}
}
