/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "plaga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Plaga.findAll", query = "SELECT p FROM Plaga p"),
    @NamedQuery(name = "Plaga.findByIdPlaga", query = "SELECT p FROM Plaga p WHERE p.idPlaga = :idPlaga"),
    @NamedQuery(name = "Plaga.findByNombrePlaga", query = "SELECT p FROM Plaga p WHERE p.nombrePlaga = :nombrePlaga"),
    @NamedQuery(name = "Plaga.findByTipoPlaga", query = "SELECT p FROM Plaga p WHERE p.tipoPlaga = :tipoPlaga")})
public class Plaga implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plaga")
    private Integer idPlaga;
    @Column(name = "nombre_plaga")
    private String nombrePlaga;
    @Column(name = "tipo_plaga")
    private String tipoPlaga;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "plaga")
    private List<PlagaCultivo> plagaCultivoList;

    public Plaga() {
    }

    public Plaga(Integer idPlaga) {
        this.idPlaga = idPlaga;
    }

    public Plaga(String nombre, String tipo) {
        this.nombrePlaga = nombre;
        this.tipoPlaga = tipo;
    }

    public Integer getIdPlaga() {
        return idPlaga;
    }

    public void setIdPlaga(Integer idPlaga) {
        this.idPlaga = idPlaga;
    }

    public String getNombrePlaga() {
        return nombrePlaga;
    }

    public void setNombrePlaga(String nombrePlaga) {
        this.nombrePlaga = nombrePlaga;
    }

    public String getTipoPlaga() {
        return tipoPlaga;
    }

    public void setTipoPlaga(String tipoPlaga) {
        this.tipoPlaga = tipoPlaga;
    }

    @XmlTransient
    public List<PlagaCultivo> getPlagaCultivoList() {
        return plagaCultivoList;
    }

    public void setPlagaCultivoList(List<PlagaCultivo> plagaCultivoList) {
        this.plagaCultivoList = plagaCultivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlaga != null ? idPlaga.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Plaga)) {
            return false;
        }
        Plaga other = (Plaga) object;
        if ((this.idPlaga == null && other.idPlaga != null) || (this.idPlaga != null && !this.idPlaga.equals(other.idPlaga))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Plaga[ idPlaga=" + idPlaga + " ]";
    }
    
}
