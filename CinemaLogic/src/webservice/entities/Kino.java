/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "kino")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDCinema")
@NamedQueries({
		@NamedQuery(name = "Kino.findAll", query = "SELECT k FROM Kino k"),
		@NamedQuery(name = "Kino.findByIDCinema", query = "SELECT k FROM Kino k WHERE k.iDCinema = :iDCinema"),
		@NamedQuery(name = "Kino.findByCity", query = "SELECT k FROM Kino k WHERE k.city = :city"),
		@NamedQuery(name = "Kino.findByName", query = "SELECT k FROM Kino k WHERE k.name = :name") })
public class Kino implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_Cinema")
	private Integer iDCinema;
	@Basic(optional = false)
	@Column(name = "City")
	private String city;
	@Basic(optional = false)
	@Column(name = "Name")
	private String name;
	@OneToMany(mappedBy = "iDCinema")
	private List<Uzytkownik> uzytkownikList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDCinema")
	private List<Sala> salaList;

	public Kino() {
	}

	public Kino(Integer iDCinema) {
		this.iDCinema = iDCinema;
	}

	public Kino(Integer iDCinema, String city, String name) {
		this.iDCinema = iDCinema;
		this.city = city;
		this.name = name;
	}

	public Integer getIDCinema() {
		return iDCinema;
	}

	public void setIDCinema(Integer iDCinema) {
		this.iDCinema = iDCinema;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Uzytkownik> getUzytkownikList() {
		if (uzytkownikList == null)
			uzytkownikList = new LinkedList<Uzytkownik>();
		return uzytkownikList;
	}

	public void setUzytkownikList(List<Uzytkownik> uzytkownikList) {
		this.uzytkownikList = uzytkownikList;
	}

	public List<Sala> getSalaList() {
		if (salaList == null) {
			salaList = new LinkedList<Sala>();
		}
		return salaList;
	}

	public void setSalaList(List<Sala> salaList) {
		this.salaList = salaList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iDCinema != null ? iDCinema.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Kino)) {
			return false;
		}
		Kino other = (Kino) object;
		if ((this.iDCinema == null && other.iDCinema != null)
				|| (this.iDCinema != null && !this.iDCinema
						.equals(other.iDCinema))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Kino[ iDCinema=" + iDCinema + " ]";
	}

}
