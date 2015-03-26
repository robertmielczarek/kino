/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.projekt.kino.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "bilet")
@XmlRootElement
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="iDTicket")
@NamedQueries({
    @NamedQuery(name = "Bilet.findAll", query = "SELECT b FROM Bilet b"),
    @NamedQuery(name = "Bilet.findByIDTicket", query = "SELECT b FROM Bilet b WHERE b.iDTicket = :iDTicket"),
    @NamedQuery(name = "Bilet.findByPrice", query = "SELECT b FROM Bilet b WHERE b.price = :price"),
    @NamedQuery(name = "Bilet.findBySellingDate", query = "SELECT b FROM Bilet b WHERE b.sellingDate = :sellingDate"),
    @NamedQuery(name = "Bilet.findByTicketStatus", query = "SELECT b FROM Bilet b WHERE b.ticketStatus = :ticketStatus")})
public class Bilet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_Ticket")
    private Integer iDTicket;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @Column(name = "sellingDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sellingDate;
    @Basic(optional = false)
    @Column(name = "ticketStatus")
    private String ticketStatus;
    @JoinColumn(name = "ID_Show", referencedColumnName = "ID_Show")
    @ManyToOne(optional = false)
    private Seans iDShow;
    @JoinColumn(name = "ID_Seat", referencedColumnName = "ID_Seat")
    @ManyToOne(optional = false)
    private Miejsce iDSeat;
    @JoinColumn(name = "ID_Cashier", referencedColumnName = "ID_user")
    @ManyToOne(optional = false)
    private Uzytkownik iDCashier;
    @JoinColumn(name = "ID_Reservation", referencedColumnName = "ID_Reservation")
    @ManyToOne
    private Rezerwacja iDReservation;

    public Bilet() {
    }

    public Bilet(Integer iDTicket) {
        this.iDTicket = iDTicket;
    }

    public Bilet(Integer iDTicket, double price, Date sellingDate, String ticketStatus) {
        this.iDTicket = iDTicket;
        this.price = price;
        this.sellingDate = sellingDate;
        this.ticketStatus = ticketStatus;
    }

    public Integer getIDTicket() {
        return iDTicket;
    }

    public void setIDTicket(Integer iDTicket) {
        this.iDTicket = iDTicket;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(Date sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }

    public Seans getIDShow() {
        return iDShow;
    }

    public void setIDShow(Seans iDShow) {
        this.iDShow = iDShow;
    }

    public Miejsce getIDSeat() {
        return iDSeat;
    }

    public void setIDSeat(Miejsce iDSeat) {
        this.iDSeat = iDSeat;
    }

    public Uzytkownik getIDCashier() {
        return iDCashier;
    }

    public void setIDCashier(Uzytkownik iDCashier) {
        this.iDCashier = iDCashier;
    }

    public Rezerwacja getIDReservation() {
        return iDReservation;
    }

    public void setIDReservation(Rezerwacja iDReservation) {
        this.iDReservation = iDReservation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iDTicket != null ? iDTicket.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bilet)) {
            return false;
        }
        Bilet other = (Bilet) object;
        if ((this.iDTicket == null && other.iDTicket != null) || (this.iDTicket != null && !this.iDTicket.equals(other.iDTicket))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Bilet[ iDTicket=" + iDTicket + " ]";
    }
    
}
