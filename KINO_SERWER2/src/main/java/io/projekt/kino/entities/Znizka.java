/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.projekt.kino.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Robson
 */
@Entity
@Table(name = "znizka")
@XmlRootElement
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="iDDiscount")
@NamedQueries({
    @NamedQuery(name = "Znizka.findAll", query = "SELECT z FROM Znizka z"),
    @NamedQuery(name = "Znizka.findByIDDiscount", query = "SELECT z FROM Znizka z WHERE z.iDDiscount = :iDDiscount"),
    @NamedQuery(name = "Znizka.findByName", query = "SELECT z FROM Znizka z WHERE z.name = :name"),
    @NamedQuery(name = "Znizka.findByValue", query = "SELECT z FROM Znizka z WHERE z.value = :value")})
public class Znizka implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Discount")
    private Integer iDDiscount;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Value")
    private double value;

    public Znizka() {
    }

    public Znizka(Integer iDDiscount) {
        this.iDDiscount = iDDiscount;
    }

    public Znizka(Integer iDDiscount, String name, double value) {
        this.iDDiscount = iDDiscount;
        this.name = name;
        this.value = value;
    }

    public Integer getIDDiscount() {
        return iDDiscount;
    }

    public void setIDDiscount(Integer iDDiscount) {
        this.iDDiscount = iDDiscount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDDiscount != null ? iDDiscount.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Znizka)) {
            return false;
        }
        Znizka other = (Znizka) object;
        if ((this.iDDiscount == null && other.iDDiscount != null) || (this.iDDiscount != null && !this.iDDiscount.equals(other.iDDiscount))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Znizka[ iDDiscount=" + iDDiscount + " ]";
    }
    
}
