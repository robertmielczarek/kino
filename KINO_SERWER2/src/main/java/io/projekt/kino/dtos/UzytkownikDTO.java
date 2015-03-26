package io.projekt.kino.dtos;

import java.util.ArrayList;
import java.util.List;

public class UzytkownikDTO {
	
    private Integer iDuser;
    private String name;
    private String surname;
    private String phoneNumber;
    private String address;
	private String login;
    private String password;
    private String role;
    private Integer iDCinema;
    private List<Integer> biletList;
    
    public Integer getiDuser() {
		return iDuser;
	}
	public void setiDuser(Integer iDuser) {
		this.iDuser = iDuser;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Integer getiDCinema() {
		return iDCinema;
	}
	public void setiDCinema(Integer iDCinema) {
		this.iDCinema = iDCinema;
	}
	public List<Integer> getBiletList() {
		if(biletList==null)
	    biletList=new ArrayList<>();
		return biletList;
	}
	public void setBiletList(List<Integer> biletList) {
		this.biletList = biletList;
	}

}
