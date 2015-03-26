/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "miejsce")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDSeat")
@NamedQueries({
		@NamedQuery(name = "Miejsce.findAll", query = "SELECT m FROM Miejsce m"),
		@NamedQuery(name = "Miejsce.findByIDSeat", query = "SELECT m FROM Miejsce m WHERE m.iDSeat = :iDSeat"),
		@NamedQuery(name = "Miejsce.findByNumber", query = "SELECT m FROM Miejsce m WHERE m.number = :number") })
public class Miejsce implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_Seat")
	private Integer iDSeat;
	@Basic(optional = false)
	@Column(name = "Number")
	private int number;
	@JoinColumn(name = "ID_Auditorium", referencedColumnName = "ID_hall")
	@ManyToOne(optional = false)
	private Sala iDAuditorium;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDSeat")
	private List<Bilet> biletList;

	public Miejsce() {
	}

	public Miejsce(Integer iDSeat) {
		this.iDSeat = iDSeat;
	}

	public Miejsce(Integer iDSeat, int number) {
		this.iDSeat = iDSeat;
		this.number = number;
	}

	public Integer getIDSeat() {
		return iDSeat;
	}

	public void setIDSeat(Integer iDSeat) {
		this.iDSeat = iDSeat;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Sala getIDAuditorium() {
		return iDAuditorium;
	}

	public void setIDAuditorium(Sala iDAuditorium) {
		this.iDAuditorium = iDAuditorium;
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
		hash += (iDSeat != null ? iDSeat.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Miejsce)) {
			return false;
		}
		Miejsce other = (Miejsce) object;
		if ((this.iDSeat == null && other.iDSeat != null)
				|| (this.iDSeat != null && !this.iDSeat.equals(other.iDSeat))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Miejsce[ iDSeat=" + iDSeat + " ]";
	}

}
