package model;

import interfaces.RestElement;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.DatabaseForeign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("salas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auditorium implements RestElement {

	@JsonProperty("iDhall")
	private Integer id;

	@JsonProperty("iDCinema")
	private Integer cinemaId;

	@JsonProperty("name")
	private String name;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "cinemaId")
	private Cinema cinema;

	// @ForeignCollectionField(eager = false, foreignFieldName = "auditorium")
	// @JsonIgnore
	// ForeignCollection<Seat> seats;

	// @ForeignCollectionField(eager = false, foreignFieldName = "auditorium")
	// @JsonIgnore
	// ForeignCollection<Show> shows;

	public Auditorium() {
		super();
	}

	public Auditorium(Integer cinemaId, String name) {
		super();
		this.cinemaId = cinemaId;
		this.name = name;
	}

	public Auditorium(String auditoriumName) {
		super();
		this.name = auditoriumName;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	// public ForeignCollection<Seat> getSeats() {
	// return seats;
	// }
	//
	// public void setSeats(ForeignCollection<Seat> seats) {
	// this.seats = seats;
	// }

	// public ForeignCollection<Show> getShows() {
	// return shows;
	// }
	//
	// public void setShows(ForeignCollection<Show> shows) {
	// this.shows = shows;
	// }

	public Integer getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Integer cinemaId) {
		this.cinemaId = cinemaId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cinemaId == null) ? 0 : cinemaId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Auditorium other = (Auditorium) obj;
		if (cinemaId == null) {
			if (other.cinemaId != null)
				return false;
		} else if (!cinemaId.equals(other.cinemaId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
