package model;

import interfaces.RestElement;

import javax.ws.rs.Path;
import javax.xml.bind.annotation.XmlRootElement;

import annotations.DatabaseForeign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement
@Path("users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements RestElement {

	@JsonProperty("iDuser")
	private Integer id;

	@JsonProperty("iDCinema")
	private Integer cinemaId;

	@JsonProperty("name")
	private String name;

	@JsonProperty("surname")
	private String lastName;

	@JsonProperty("phoneNumber")
	private String phoneNumber;

	@JsonProperty("address")
	private String address;

	@JsonProperty("role")
	private Role role;

	@JsonProperty("login")
	private String login;

	@JsonProperty("password")
	private String password;

	@JsonIgnore
	@DatabaseForeign(referenceFieldId = "cinemaId")
	private Cinema cinema;

	public User() {
		super();
	}

	public User(Integer id, Integer cinemaId, String name, String lastName,
			String phoneNumber, String address, Role role, String login,
			String password) {
		super();
		this.id = id;
		this.cinemaId = cinemaId;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.login = login;
		this.password = password;
	}

	public User(Integer cinemaId, String name, String lastName,
			String phoneNumber, String address, Role role, String login,
			String password) {
		super();
		this.cinemaId = cinemaId;
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.login = login;
		this.password = password;
	}

	public User(String name, String lastName, String phoneNumber,
			String address, Role role, String login, String password) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.role = role;
		this.login = login;
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCinemaId() {
		return cinemaId;
	}

	public void setCinemaId(Integer cinemaId) {
		this.cinemaId = cinemaId;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result
				+ ((cinemaId == null) ? 0 : cinemaId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
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
		User other = (User) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
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
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

}
