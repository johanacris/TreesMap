/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

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
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cultivo.findAll", query = "SELECT c FROM Cultivo c"),
    @NamedQuery(name = "Cultivo.findByIdCultivo", query = "SELECT c FROM Cultivo c WHERE c.idCultivo = :idCultivo"),
    @NamedQuery(name = "Cultivo.findByNombreCultivo", query = "SELECT c FROM Cultivo c WHERE c.nombreCultivo = :nombreCultivo"),
    @NamedQuery(name = "Cultivo.findByFechaSembtado", query = "SELECT c FROM Cultivo c WHERE c.fechaSembtado = :fechaSembtado"),
    @NamedQuery(name = "Cultivo.findByEspecie", query = "SELECT c FROM Cultivo c WHERE c.especie = :especie"),
    @NamedQuery(name = "Cultivo.findByGenero", query = "SELECT c FROM Cultivo c WHERE c.genero = :genero"),
    @NamedQuery(name = "Cultivo.findByFamilia", query = "SELECT c FROM Cultivo c WHERE c.familia = :familia"),
    @NamedQuery(name = "Cultivo.findByOrden", query = "SELECT c FROM Cultivo c WHERE c.orden = :orden"),
    @NamedQuery(name = "Cultivo.findByClase", query = "SELECT c FROM Cultivo c WHERE c.clase = :clase"),
    @NamedQuery(name = "Cultivo.findByDivision", query = "SELECT c FROM Cultivo c WHERE c.division = :division"),
    @NamedQuery(name = "Cultivo.findByLatitud", query = "SELECT c FROM Cultivo c WHERE c.latitud = :latitud"),
    @NamedQuery(name = "Cultivo.findByLongitud", query = "SELECT c FROM Cultivo c WHERE c.longitud = :longitud")})
public class Cultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cultivo")
    private Integer idCultivo;
    @Column(name = "nombre_cultivo")
    private String nombreCultivo;
    @Column(name = "fecha_sembtado")
    @Temporal(TemporalType.DATE)
    private Date fechaSembtado;
    @Column(name = "especie")
    private String especie;
    @Column(name = "genero")
    private String genero;
    @Column(name = "familia")
    private String familia;
    @Column(name = "orden")
    private String orden;
    @Column(name = "clase")
    private String clase;
    @Column(name = "division")
    private String division;
    @Column(name = "latitud")
    private String latitud;
    @Column(name = "longitud")
    private String longitud;
    @JoinColumn(name = "usuario", referencedColumnName = "email")
    @ManyToOne
    private Usuario usuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cultivo")
    private List<PlagaCultivo> plagaCultivoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cultivo")
    private List<FertilizanteCultivo> fertilizanteCultivoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cultivo")
    private List<VenenoCultivo> venenoCultivoList;

    public Cultivo() {
    }

    public Cultivo(Integer idCultivo) {
        this.idCultivo = idCultivo;
    }

    public Cultivo(String nombre, Date fecha, String latitud, String longitud, String especie, String genero, String familia, String orden, String clase, String division, Usuario usuario) {
        this.nombreCultivo = nombre;
        this.fechaSembtado = fecha;
        this.latitud = latitud;
        this.longitud = longitud;
        this.especie = especie;
        this.genero = genero;
        this.familia = familia;
        this.orden = orden;
        this.clase = clase;
        this.division = division;
        this.usuario = usuario;
    }

    public Integer getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(Integer idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getNombreCultivo() {
        return nombreCultivo;
    }

    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }

    public Date getFechaSembtado() {
        return fechaSembtado;
    }

    public void setFechaSembtado(Date fechaSembtado) {
        this.fechaSembtado = fechaSembtado;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public String getOrden() {
        return orden;
    }

    public void setOrden(String orden) {
        this.orden = orden;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @XmlTransient
    public List<PlagaCultivo> getPlagaCultivoList() {
        return plagaCultivoList;
    }

    public void setPlagaCultivoList(List<PlagaCultivo> plagaCultivoList) {
        this.plagaCultivoList = plagaCultivoList;
    }

    @XmlTransient
    public List<FertilizanteCultivo> getFertilizanteCultivoList() {
        return fertilizanteCultivoList;
    }

    public void setFertilizanteCultivoList(List<FertilizanteCultivo> fertilizanteCultivoList) {
        this.fertilizanteCultivoList = fertilizanteCultivoList;
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
        hash += (idCultivo != null ? idCultivo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cultivo)) {
            return false;
        }
        Cultivo other = (Cultivo) object;
        if ((this.idCultivo == null && other.idCultivo != null) || (this.idCultivo != null && !this.idCultivo.equals(other.idCultivo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Cultivo[ idCultivo=" + idCultivo + " ]";
    }
    
}
