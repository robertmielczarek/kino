package model;

import interfaces.RestElement;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.DatabaseForeign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("miejscas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Seat implements RestElement {

	@JsonProperty("iDSeat")
	private Integer id;

	@JsonProperty("iDAuditorium")
	private Integer auditoriumId;

	@JsonProperty("number")
	private int number;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "auditoriumId")
	private Auditorium auditorium;

	public Seat() {
		super();
	}

	public Seat(int auditoriumId, int number) {
		super();
		this.auditoriumId = auditoriumId;
		this.number = number;
	}

	public Seat(Integer id, Integer auditoriumId, int number) {
		super();
		this.id = id;
		this.auditoriumId = auditoriumId;
		this.number = number;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}

	public Integer getId() {
		return id;
	}

	public Integer getAuditoriumId() {
		return auditoriumId;
	}

	public void setAuditoriumId(Integer auditoriumId) {
		this.auditoriumId = auditoriumId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditoriumId == null) ? 0 : auditoriumId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + number;
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
		Seat other = (Seat) obj;
		if (auditoriumId == null) {
			if (other.auditoriumId != null)
				return false;
		} else if (!auditoriumId.equals(other.auditoriumId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

}
