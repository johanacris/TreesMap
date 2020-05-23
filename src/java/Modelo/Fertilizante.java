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
@Table(name = "fertilizante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fertilizante.findAll", query = "SELECT f FROM Fertilizante f"),
    @NamedQuery(name = "Fertilizante.findByIdFertilizante", query = "SELECT f FROM Fertilizante f WHERE f.idFertilizante = :idFertilizante"),
    @NamedQuery(name = "Fertilizante.findByNombreFertilizante", query = "SELECT f FROM Fertilizante f WHERE f.nombreFertilizante = :nombreFertilizante"),
    @NamedQuery(name = "Fertilizante.findByTipoFertilizante", query = "SELECT f FROM Fertilizante f WHERE f.tipoFertilizante = :tipoFertilizante")})
public class Fertilizante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fertilizante")
    private Integer idFertilizante;
    @Column(name = "nombre_fertilizante")
    private String nombreFertilizante;
    @Column(name = "tipo_fertilizante")
    private String tipoFertilizante;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fertilizante")
    private List<FertilizanteCultivo> fertilizanteCultivoList;

    public Fertilizante() {
    }

    public Fertilizante(Integer idFertilizante) {
        this.idFertilizante = idFertilizante;
    }

    public Fertilizante(String nombre, String tipo) {
        this.nombreFertilizante = nombre;
        this.tipoFertilizante = tipo;
    }

    public Integer getIdFertilizante() {
        return idFertilizante;
    }

    public void setIdFertilizante(Integer idFertilizante) {
        this.idFertilizante = idFertilizante;
    }

    public String getNombreFertilizante() {
        return nombreFertilizante;
    }

    public void setNombreFertilizante(String nombreFertilizante) {
        this.nombreFertilizante = nombreFertilizante;
    }

    public String getTipoFertilizante() {
        return tipoFertilizante;
    }

    public void setTipoFertilizante(String tipoFertilizante) {
        this.tipoFertilizante = tipoFertilizante;
    }

    @XmlTransient
    public List<FertilizanteCultivo> getFertilizanteCultivoList() {
        return fertilizanteCultivoList;
    }

    public void setFertilizanteCultivoList(List<FertilizanteCultivo> fertilizanteCultivoList) {
        this.fertilizanteCultivoList = fertilizanteCultivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFertilizante != null ? idFertilizante.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fertilizante)) {
            return false;
        }
        Fertilizante other = (Fertilizante) object;
        if ((this.idFertilizante == null && other.idFertilizante != null) || (this.idFertilizante != null && !this.idFertilizante.equals(other.idFertilizante))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Fertilizante[ idFertilizante=" + idFertilizante + " ]";
    }
    
}
