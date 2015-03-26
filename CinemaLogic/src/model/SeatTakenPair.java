package model;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
public class SeatTakenPair {

	@JsonProperty("miejsceDTO")
	private Seat seat;

	@JsonProperty("isBooked")
	private Boolean isTaken;

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public Boolean getTaken() {
		return isTaken;
	}

	public void setTaken(Boolean taken) {
		this.isTaken = taken;
	}

}
