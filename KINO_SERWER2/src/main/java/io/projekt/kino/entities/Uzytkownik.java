/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.projekt.kino.entities;

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
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/**
 *
 * @author Robson
 */
@Entity
@Table(name = "uzytkownik")
@XmlRootElement
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="iDuser")
@NamedQueries({
    @NamedQuery(name = "Uzytkownik.findAll", query = "SELECT u FROM Uzytkownik u"),
    @NamedQuery(name = "Uzytkownik.findByIDuser", query = "SELECT u FROM Uzytkownik u WHERE u.iDuser = :iDuser"),
    @NamedQuery(name = "Uzytkownik.findByName", query = "SELECT u FROM Uzytkownik u WHERE u.name = :name"),
    @NamedQuery(name = "Uzytkownik.findBySurname", query = "SELECT u FROM Uzytkownik u WHERE u.surname = :surname"),
    @NamedQuery(name = "Uzytkownik.findByPhoneNumber", query = "SELECT u FROM Uzytkownik u WHERE u.phoneNumber = :phoneNumber"),
    @NamedQuery(name = "Uzytkownik.findByAddress", query = "SELECT u FROM Uzytkownik u WHERE u.address = :address"),
    @NamedQuery(name = "Uzytkownik.findByLogin", query = "SELECT u FROM Uzytkownik u WHERE u.login = :login"),
    @NamedQuery(name = "Uzytkownik.findByPassword", query = "SELECT u FROM Uzytkownik u WHERE u.password = :password"),
    @NamedQuery(name = "Uzytkownik.findByRole", query = "SELECT u FROM Uzytkownik u WHERE u.role = :role")})
public class Uzytkownik implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @Column(name = "ID_user")
    private Integer iDuser;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Surname")
    private String surname;
    @Basic(optional = false)
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Basic(optional = false)
    @Column(name = "Address")
    private String address;
    @Basic(optional = false)
    @Column(name = "Login")
    private String login;
    @Basic(optional = false)
    @Column(name = "Password")
    private String password;
    @Basic(optional = false)
    @Column(name = "Role")
    private String role;
    @JoinColumn(name = "ID_Cinema", referencedColumnName = "ID_Cinema")
    @ManyToOne
    private Kino iDCinema;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iDCashier")
    private List<Bilet> biletList;

    public Uzytkownik() {
    }

    public Uzytkownik(Integer iDuser) {
        this.iDuser = iDuser;
    }

    public Uzytkownik(Integer iDuser, String name, String surname, String phoneNumber, String address, String login, String password, String role) {
        this.iDuser = iDuser;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public Integer getIDuser() {
        return iDuser;
    }

    public void setIDuser(Integer iDuser) {
        this.iDuser = iDuser;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Kino getIDCinema() {
        return iDCinema;
    }

    public void setIDCinema(Kino iDCinema) {
        this.iDCinema = iDCinema;
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
        hash += (iDuser != null ? iDuser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Uzytkownik)) {
            return false;
        }
        Uzytkownik other = (Uzytkownik) object;
        if ((this.iDuser == null && other.iDuser != null) || (this.iDuser != null && !this.iDuser.equals(other.iDuser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Uzytkownik[ iDuser=" + iDuser + " ]";
    }
    
}
