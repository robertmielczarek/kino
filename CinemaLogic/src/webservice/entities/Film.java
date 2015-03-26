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
@Table(name = "film")
@XmlRootElement
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "iDMovie")
@NamedQueries({
		@NamedQuery(name = "Film.findAll", query = "SELECT f FROM Film f"),
		@NamedQuery(name = "Film.findByIDMovie", query = "SELECT f FROM Film f WHERE f.iDMovie = :iDMovie"),
		@NamedQuery(name = "Film.findByTitle", query = "SELECT f FROM Film f WHERE f.title = :title"),
		@NamedQuery(name = "Film.findByGenre", query = "SELECT f FROM Film f WHERE f.genre = :genre"),
		@NamedQuery(name = "Film.findByLenght", query = "SELECT f FROM Film f WHERE f.lenght = :lenght"),
		@NamedQuery(name = "Film.findByDescription", query = "SELECT f FROM Film f WHERE f.description = :description") })
public class Film implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ID_Movie")
	private Integer iDMovie;
	@Basic(optional = false)
	@Column(name = "Title")
	private String title;
	@Basic(optional = false)
	@Column(name = "Genre")
	private String genre;
	@Basic(optional = false)
	@Column(name = "Lenght")
	private String lenght;
	@Basic(optional = false)
	@Column(name = "Description")
	private String description;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "iDMovie")
	private List<Seans> seansList;

	public Film() {
	}

	public Film(Integer iDMovie) {
		this.iDMovie = iDMovie;
	}

	public Film(Integer iDMovie, String title, String genre, String lenght,
			String description) {
		this.iDMovie = iDMovie;
		this.title = title;
		this.genre = genre;
		this.lenght = lenght;
		this.description = description;
	}

	public Integer getIDMovie() {
		return iDMovie;
	}

	public void setIDMovie(Integer iDMovie) {
		this.iDMovie = iDMovie;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getLenght() {
		return lenght;
	}

	public void setLenght(String lenght) {
		this.lenght = lenght;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Seans> getSeansList() {
		return seansList;
	}

	public void setSeansList(List<Seans> seansList) {
		this.seansList = seansList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (iDMovie != null ? iDMovie.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Film)) {
			return false;
		}
		Film other = (Film) object;
		if ((this.iDMovie == null && other.iDMovie != null)
				|| (this.iDMovie != null && !this.iDMovie.equals(other.iDMovie))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entities.Film[ iDMovie=" + iDMovie + " ]";
	}

}
