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
@Table(name = "veneno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Veneno.findAll", query = "SELECT v FROM Veneno v"),
    @NamedQuery(name = "Veneno.findByIdVeneno", query = "SELECT v FROM Veneno v WHERE v.idVeneno = :idVeneno"),
    @NamedQuery(name = "Veneno.findByNombreVeneno", query = "SELECT v FROM Veneno v WHERE v.nombreVeneno = :nombreVeneno"),
    @NamedQuery(name = "Veneno.findByTipoVeneno", query = "SELECT v FROM Veneno v WHERE v.tipoVeneno = :tipoVeneno")})
public class Veneno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_veneno")
    private Integer idVeneno;
    @Column(name = "nombre_veneno")
    private String nombreVeneno;
    @Column(name = "tipo_veneno")
    private String tipoVeneno;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "veneno")
    private List<VenenoCultivo> venenoCultivoList;

    public Veneno() {
    }

    public Veneno(Integer idVeneno) {
        this.idVeneno = idVeneno;
    }

    public Veneno(String nombre, String tipo) {
        this.nombreVeneno = nombre;
        this.tipoVeneno = tipo;
    }

    public Integer getIdVeneno() {
        return idVeneno;
    }

    public void setIdVeneno(Integer idVeneno) {
        this.idVeneno = idVeneno;
    }

    public String getNombreVeneno() {
        return nombreVeneno;
    }

    public void setNombreVeneno(String nombreVeneno) {
        this.nombreVeneno = nombreVeneno;
    }

    public String getTipoVeneno() {
        return tipoVeneno;
    }

    public void setTipoVeneno(String tipoVeneno) {
        this.tipoVeneno = tipoVeneno;
    }

    @XmlTransient
    public List<VenenoCultivo> getVenenoCultivoList() {
        return venenoCultivoList;
    }

    public void setVenenoCultivoList(List<VenenoCultivo> venenoCultivoList) {
        this.venenoCultivoList = venenoCultivoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVeneno != null ? idVeneno.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Veneno)) {
            return false;
        }
        Veneno other = (Veneno) object;
        if ((this.idVeneno == null && other.idVeneno != null) || (this.idVeneno != null && !this.idVeneno.equals(other.idVeneno))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Veneno[ idVeneno=" + idVeneno + " ]";
    }
    
}
