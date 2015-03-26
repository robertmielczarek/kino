package model;

import interfaces.RestElement;

import java.util.Date;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.DatabaseForeign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("bilets")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Ticket implements RestElement {

	@JsonProperty("iDTicket")
	Integer id;

	@JsonProperty("price")
	double price;

	@JsonProperty("ticketStatus")
	TicketStatus ticketStatus;

	@JsonProperty("sellingDate")
	Date sellingDate;

	@JsonProperty("iDCashier")
	Integer cashierId;

	@JsonProperty("iDShow")
	Integer showId;

	@JsonProperty("iDSeat")
	Integer seatId;

	@JsonProperty("iDReservation")
	Integer bookingId;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "cashierId")
	User cashier;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "showId")
	Show show;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "seatId")
	Seat seat;

	@JsonIgnore
	// @DatabaseForeign(referenceFieldId = "bookingId")
	Booking booking;

	public Ticket() {
		super();
	}

	public Ticket(double price, Integer cashierId, Integer showId,
			Integer seatId) {
		super();
		this.price = price;
		this.cashierId = cashierId;
		this.showId = showId;
		this.seatId = seatId;
	}

	public Ticket(double price, TicketStatus ticketStatus, Date sellingDate,
			Integer cashierId, Integer showId, Integer seatId) {
		super();
		this.price = price;
		this.ticketStatus = ticketStatus;
		this.sellingDate = sellingDate;
		this.cashierId = cashierId;
		this.showId = showId;
		this.seatId = seatId;
	}

	public Ticket(double price, TicketStatus ticketStatus, Date sellingDate,
			Integer cashierId, Integer showId, Integer seatId, Integer bookingId) {
		super();
		this.price = price;
		this.ticketStatus = ticketStatus;
		this.sellingDate = sellingDate;
		this.cashierId = cashierId;
		this.showId = showId;
		this.seatId = seatId;
		this.bookingId = bookingId;
	}

	public Integer getCashierId() {
		return cashierId;
	}

	public void setCashierId(Integer cashierId) {
		this.cashierId = cashierId;
	}

	public Integer getShowId() {
		return showId;
	}

	public void setShowId(Integer showId) {
		this.showId = showId;
	}

	public Integer getSeatId() {
		return seatId;
	}

	public void setSeatId(Integer seatId) {
		this.seatId = seatId;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Date getSellingDate() {
		return sellingDate;
	}

	public void setSellingDate(Date sellingDate) {
		this.sellingDate = sellingDate;
	}

	public User getCashier() {
		return cashier;
	}

	public void setCashier(User cashier) {
		this.cashier = cashier;
	}

	public TicketStatus getTicketStatus() {
		return ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	// public int getShowId() {
	// return showId;
	// }
	//
	// public void setShowId(int showId) {
	// this.showId = showId;
	// }
	//
	// public int getSeatId() {
	// return seatId;
	// }
	//
	// public void setSeatId(int seatId) {
	// this.seatId = seatId;
	// }

	public Show getShow() {
		return show;
	}

	public void setShow(Show show) {
		this.show = show;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bookingId == null) ? 0 : bookingId.hashCode());
		result = prime * result
				+ ((cashierId == null) ? 0 : cashierId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((seatId == null) ? 0 : seatId.hashCode());
		result = prime * result
				+ ((sellingDate == null) ? 0 : sellingDate.hashCode());
		result = prime * result + ((showId == null) ? 0 : showId.hashCode());
		result = prime * result
				+ ((ticketStatus == null) ? 0 : ticketStatus.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (bookingId == null) {
			if (other.bookingId != null)
				return false;
		} else if (!bookingId.equals(other.bookingId))
			return false;
		if (cashierId == null) {
			if (other.cashierId != null)
				return false;
		} else if (!cashierId.equals(other.cashierId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (seatId == null) {
			if (other.seatId != null)
				return false;
		} else if (!seatId.equals(other.seatId))
			return false;
		if (sellingDate == null) {
			if (other.sellingDate != null)
				return false;
		} else if (!sellingDate.equals(other.sellingDate))
			return false;
		if (showId == null) {
			if (other.showId != null)
				return false;
		} else if (!showId.equals(other.showId))
			return false;
		if (ticketStatus != other.ticketStatus)
			return false;
		return true;
	}

}
