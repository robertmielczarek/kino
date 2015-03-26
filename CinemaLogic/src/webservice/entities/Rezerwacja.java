/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Robson
 */
@Entity
@Table(name = "rezerwacja")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDReservation")
@NamedQueries({
		@NamedQuery(name = "Rezerwacja.findAll", query = "SELECT r FROM Rezerwacja r"),
		@NamedQuery(name = "Rezerwacja.findByIDReservation", query = "SELECT r FROM Rezerwacja r WHERE r.iDReservation = :iDReservation"),
		@NamedQuery(name = "Rezerwacja.findByName", query = "SELECT r FROM Rezerwacja r WHERE r.name = :name"),
		@NamedQuery(name = "Rezerwacja.findBySurname", query = "SELECT r FROM Rezerwacja r WHERE r.surname = :surname"),
		@NamedQuery(name = "Rezerwacja.findByEmail", query = "SELECT r FROM Rezerwacja r WHERE r.email = :email"),
		@NamedQuery(name = "Rezerwacja.findByIdCancelled", query = "SELECT r FROM Rezerwacja r WHERE r.idCancelled = :idCancelled") })
public class Rezerwacja implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_Reservation")
	private Integer iDReservation;
	@Basic(optional = false)
	@Column(name = "Name")
	private String name;
	@Basic(optional = false)
	@Column(name = "Surname")
	private String surname;
	@Basic(optional = false)
	@Column(name = "Email")
	private String email;
	@Basic(optional = false)
	@Column(name = "idCancelled")
	private boolean idCancelled;
	@OneToMany(mappedBy = "iDReservation")
	private List<Bilet> biletList;

	public Rezerwacja() {
	}

	public Rezerwacja(Integer iDReservation) {
		this.iDReservation = iDReservation;
	}

	public Rezerwacja(Integer iDReservation, String name, String surname,
			String email, boolean idCancelled) {
		this.iDReservation = iDReservation;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.idCancelled = idCancelled;
	}

	public Integer getIDReservation() {
		return iDReservation;
	}

	public void setIDReservation(Integer iDReservation) {
		this.iDReservation = iDReservation;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIdCancelled() {
		return idCancelled;
	}

	public void setIdCancelled(boolean idCancelled) {
		this.idCancelled = idCancelled;
	}

	public List<Bilet> getBiletList() {
		return biletList;
	}

	public void setBiletList(List<Bilet> biletList) {
		this.biletList = biletList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iDReservation != null ? iDReservation.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Rezerwacja)) {
			return false;
		}
		Rezerwacja other = (Rezerwacja) object;
		if ((this.iDReservation == null && other.iDReservation != null)
				|| (this.iDReservation != null && !this.iDReservation
						.equals(other.iDReservation))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Rezerwacja[ iDReservation=" + iDReservation + " ]";
	}

}
