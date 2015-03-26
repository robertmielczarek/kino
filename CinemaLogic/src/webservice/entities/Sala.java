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
@Table(name = "sala")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDhall")
@NamedQueries({
		@NamedQuery(name = "Sala.findAll", query = "SELECT s FROM Sala s"),
		@NamedQuery(name = "Sala.findByIDhall", query = "SELECT s FROM Sala s WHERE s.iDhall = :iDhall"),
		@NamedQuery(name = "Sala.findByName", query = "SELECT s FROM Sala s WHERE s.name = :name") })
public class Sala implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_hall")
	private Integer iDhall;
	@Basic(optional = false)
	@Column(name = "Name")
	private String name;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDAuditorium")
	private List<Miejsce> miejsceList;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDAuditorium")
	private List<Seans> seansList;
	@JoinColumn(name = "ID_Cinema", referencedColumnName = "ID_Cinema")
	@ManyToOne(optional = false)
	private Kino iDCinema;

	public Sala() {
	}

	public Sala(Integer iDhall) {
		this.iDhall = iDhall;
	}

	public Sala(Integer iDhall, String name) {
		this.iDhall = iDhall;
		this.name = name;
	}

	public Integer getIDhall() {
		return iDhall;
	}

	public void setIDhall(Integer iDhall) {
		this.iDhall = iDhall;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Miejsce> getMiejsceList() {
		return miejsceList;
	}

	public void setMiejsceList(List<Miejsce> miejsceList) {
		this.miejsceList = miejsceList;
	}

	public List<Seans> getSeansList() {
		return seansList;
	}

	public void setSeansList(List<Seans> seansList) {
		this.seansList = seansList;
	}

	public Kino getIDCinema() {
		return iDCinema;
	}

	public void setIDCinema(Kino iDCinema) {
		this.iDCinema = iDCinema;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iDhall != null ? iDhall.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Sala)) {
			return false;
		}
		Sala other = (Sala) object;
		if ((this.iDhall == null && other.iDhall != null)
				|| (this.iDhall != null && !this.iDhall.equals(other.iDhall))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Sala[ iDhall=" + iDhall + " ]";
	}

}
