package io.projekt.kino.dtos;

import java.util.Date;

public class BiletDTO {
	private Integer iDTicket;
	private double price;
	private Date sellingDate;
	private String ticketStatus;

	private Integer iDShow;
	private Integer iDSeat;
	private Integer iDCashier;
	private Integer iDReservation;

	public BiletDTO(Integer idTicket) {
		this.iDTicket=idTicket;
	}

	public BiletDTO() {
	}

	public Integer getiDTicket() {
		return iDTicket;
	}

	public void setiDTicket(Integer iDTicket) {
		this.iDTicket = iDTicket;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getSellingDate() {
		return sellingDate;
	}

	public void setSellingDate(Date sellingDate) {
		this.sellingDate = sellingDate;
	}

	public String getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(String ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Integer getiDShow() {
		return iDShow;
	}

	public void setiDShow(Integer iDShow) {
		this.iDShow = iDShow;
	}

	public Integer getiDSeat() {
		return iDSeat;
	}

	public void setiDSeat(Integer iDSeat) {
		this.iDSeat = iDSeat;
	}

	public Integer getiDCashier() {
		return iDCashier;
	}

	public void setiDCashier(Integer iDCashier) {
		this.iDCashier = iDCashier;
	}

	public Integer getiDReservation() {
		return iDReservation;
	}

	public void setiDReservation(Integer iDReservation) {
		this.iDReservation = iDReservation;
	}

}
