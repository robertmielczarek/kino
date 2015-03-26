package model;

import interfaces.RestElement;

import java.util.List;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("rezerwacjas")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking implements RestElement {

	@JsonProperty("iDReservation")
	private Integer id;

	@JsonProperty("name")
	private String customerName;

	@JsonProperty("surname")
	private String customerLastName;

	@JsonProperty("email")
	private String customerEmail;

	@JsonProperty("idCancelled")
	private boolean isCancelled;

	@JsonIgnore
	private List<Integer> tickets;

	public Booking() {
		super();
	}

	public Booking(boolean isCancelled, String customerName,
			String customerLastName, String customerEmail) {
		super();
		this.isCancelled = isCancelled;
		this.customerName = customerName;
		this.customerLastName = customerLastName;
		this.customerEmail = customerEmail;
	}

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer iDReservation) {
		this.id = iDReservation;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public boolean getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public List<Integer> getTickets() {
		return tickets;
	}

	public void setTickets(List<Integer> tickets) {
		this.tickets = tickets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customerEmail == null) ? 0 : customerEmail.hashCode());
		result = prime
				* result
				+ ((customerLastName == null) ? 0 : customerLastName.hashCode());
		result = prime * result
				+ ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (isCancelled ? 1231 : 1237);
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
		Booking other = (Booking) obj;
		if (customerEmail == null) {
			if (other.customerEmail != null)
				return false;
		} else if (!customerEmail.equals(other.customerEmail))
			return false;
		if (customerLastName == null) {
			if (other.customerLastName != null)
				return false;
		} else if (!customerLastName.equals(other.customerLastName))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isCancelled != other.isCancelled)
			return false;
		return true;
	}

}
