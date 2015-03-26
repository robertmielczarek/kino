package model;

import interfaces.RestElement;

import java.util.Date;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.DatabaseForeign;
import annotations.WebServiceIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("seans")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Show implements RestElement {

	@JsonProperty("iDShow")
	private Integer id;

	@JsonProperty("iDAuditorium")
	private Integer auditoriumId;

	@JsonProperty("iDMovie")
	private Integer movieId;

	@JsonProperty("date")
	private Date date;

	@JsonProperty("price")
	private double price;

	@JsonProperty("idmovie")
	@WebServiceIgnore
	@DatabaseForeign(referenceFieldId = "movieId")
	private Movie movie;

	@JsonProperty("idauditorium")
	@WebServiceIgnore
	@DatabaseForeign(referenceFieldId = "auditoriumId")
	private Auditorium auditorium;

	public Show() {
		super();
	}

	public Show(int auditoriumId, int movieId, Date date, double price) {
		super();
		this.auditoriumId = auditoriumId;
		this.movieId = movieId;
		this.date = date;
		this.price = price;
	}

	public Integer getAuditoriumId() {
		return auditoriumId;
	}

	public void setAuditoriumId(Integer auditoriumId) {
		this.auditoriumId = auditoriumId;
	}

	public Integer getMovieId() {
		return movieId;
	}

	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public Auditorium getAuditorium() {
		return auditorium;
	}

	public void setAuditorium(Auditorium auditorium) {
		this.auditorium = auditorium;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditoriumId == null) ? 0 : auditoriumId.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Show other = (Show) obj;
		if (auditoriumId == null) {
			if (other.auditoriumId != null)
				return false;
		} else if (!auditoriumId.equals(other.auditoriumId))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (movieId == null) {
			if (other.movieId != null)
				return false;
		} else if (!movieId.equals(other.movieId))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		return true;
	}

}
