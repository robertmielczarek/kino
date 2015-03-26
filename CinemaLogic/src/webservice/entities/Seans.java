/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 * 
 * @author Robson
 */
@Entity
@Table(name = "seans")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDShow")
@NamedQueries({
		@NamedQuery(name = "Seans.findAll", query = "SELECT s FROM Seans s"),
		@NamedQuery(name = "Seans.findByIDShow", query = "SELECT s FROM Seans s WHERE s.iDShow = :iDShow"),
		@NamedQuery(name = "Seans.findByDate", query = "SELECT s FROM Seans s WHERE s.date = :date"),
		@NamedQuery(name = "Seans.findByPrice", query = "SELECT s FROM Seans s WHERE s.price = :price") })
public class Seans implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_Show")
	private Integer iDShow;
	@Basic(optional = false)
	@Column(name = "Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date date;
	@Basic(optional = false)
	@Column(name = "Price")
	private double price;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDShow")
	private List<Bilet> biletList;
	@JoinColumn(name = "ID_Auditorium", referencedColumnName = "ID_hall")
	@ManyToOne(optional = false)
	private Sala iDAuditorium;
	@JoinColumn(name = "ID_Movie", referencedColumnName = "ID_Movie")
	@ManyToOne(optional = false)
	private Film iDMovie;

	public Seans() {
	}

	public Seans(Integer iDShow) {
		this.iDShow = iDShow;
	}

	public Seans(Integer iDShow, Date date, double price) {
		this.iDShow = iDShow;
		this.date = date;
		this.price = price;
	}

	public Integer getIDShow() {
		return iDShow;
	}

	public void setIDShow(Integer iDShow) {
		this.iDShow = iDShow;
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

	public List<Bilet> getBiletList() {
		return biletList;
	}

	public void setBiletList(List<Bilet> biletList) {
		this.biletList = biletList;
	}

	public Sala getIDAuditorium() {
		return iDAuditorium;
	}

	public void setIDAuditorium(Sala iDAuditorium) {
		this.iDAuditorium = iDAuditorium;
	}

	public Film getIDMovie() {
		return iDMovie;
	}

	public void setIDMovie(Film iDMovie) {
		this.iDMovie = iDMovie;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iDShow != null ? iDShow.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Seans)) {
			return false;
		}
		Seans other = (Seans) object;
		if ((this.iDShow == null && other.iDShow != null)
				|| (this.iDShow != null && !this.iDShow.equals(other.iDShow))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Seans[ iDShow=" + iDShow + " ]";
	}

}
