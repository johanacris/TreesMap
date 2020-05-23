/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Romario
 */
@Entity
@Table(name = "veneno_cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VenenoCultivo.findAll", query = "SELECT v FROM VenenoCultivo v"),
    @NamedQuery(name = "VenenoCultivo.findByIdCultivo", query = "SELECT v FROM VenenoCultivo v WHERE v.venenoCultivoPK.idCultivo = :idCultivo"),
    @NamedQuery(name = "VenenoCultivo.findByIdVeneno", query = "SELECT v FROM VenenoCultivo v WHERE v.venenoCultivoPK.idVeneno = :idVeneno"),
    @NamedQuery(name = "VenenoCultivo.findByFechaAplicacion", query = "SELECT v FROM VenenoCultivo v WHERE v.fechaAplicacion = :fechaAplicacion"),
    @NamedQuery(name = "VenenoCultivo.findByAnotacion", query = "SELECT v FROM VenenoCultivo v WHERE v.anotacion = :anotacion")})
public class VenenoCultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected VenenoCultivoPK venenoCultivoPK;
    @Column(name = "fecha_aplicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAplicacion;
    @Column(name = "anotacion")
    private String anotacion;
    @JoinColumn(name = "id_cultivo", referencedColumnName = "id_cultivo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cultivo cultivo;
    @JoinColumn(name = "id_veneno", referencedColumnName = "id_veneno", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Veneno veneno;

    public VenenoCultivo() {
    }

    public VenenoCultivo(VenenoCultivoPK venenoCultivoPK) {
        this.venenoCultivoPK = venenoCultivoPK;
    }

    public VenenoCultivo(int idCultivo, int idVeneno) {
        this.venenoCultivoPK = new VenenoCultivoPK(idCultivo, idVeneno);
    }

    public VenenoCultivo(Veneno veneno, Cultivo cultivo, Date fecha, String notas) {
        this.veneno = veneno;
        this.cultivo = cultivo;
        this.fechaAplicacion = fecha;
        this.anotacion = notas;
    }

    public VenenoCultivoPK getVenenoCultivoPK() {
        return venenoCultivoPK;
    }

    public void setVenenoCultivoPK(VenenoCultivoPK venenoCultivoPK) {
        this.venenoCultivoPK = venenoCultivoPK;
    }

    public Date getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(Date fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public String getAnotacion() {
        return anotacion;
    }

    public void setAnotacion(String anotacion) {
        this.anotacion = anotacion;
    }

    public Cultivo getCultivo() {
        return cultivo;
    }

    public void setCultivo(Cultivo cultivo) {
        this.cultivo = cultivo;
    }

    public Veneno getVeneno() {
        return veneno;
    }

    public void setVeneno(Veneno veneno) {
        this.veneno = veneno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venenoCultivoPK != null ? venenoCultivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VenenoCultivo)) {
            return false;
        }
        VenenoCultivo other = (VenenoCultivo) object;
        if ((this.venenoCultivoPK == null && other.venenoCultivoPK != null) || (this.venenoCultivoPK != null && !this.venenoCultivoPK.equals(other.venenoCultivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.VenenoCultivo[ venenoCultivoPK=" + venenoCultivoPK + " ]";
    }
    
}
