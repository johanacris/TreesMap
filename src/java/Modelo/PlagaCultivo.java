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
@Table(name = "plaga_cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlagaCultivo.findAll", query = "SELECT p FROM PlagaCultivo p"),
    @NamedQuery(name = "PlagaCultivo.findByIdCultivo", query = "SELECT p FROM PlagaCultivo p WHERE p.plagaCultivoPK.idCultivo = :idCultivo"),
    @NamedQuery(name = "PlagaCultivo.findByIdPlaga", query = "SELECT p FROM PlagaCultivo p WHERE p.plagaCultivoPK.idPlaga = :idPlaga"),
    @NamedQuery(name = "PlagaCultivo.findByFechaAplicacion", query = "SELECT p FROM PlagaCultivo p WHERE p.fechaAplicacion = :fechaAplicacion"),
    @NamedQuery(name = "PlagaCultivo.findByAnotacion", query = "SELECT p FROM PlagaCultivo p WHERE p.anotacion = :anotacion")})
public class PlagaCultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PlagaCultivoPK plagaCultivoPK;
    @Column(name = "fecha_aplicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAplicacion;
    @Column(name = "anotacion")
    private String anotacion;
    @JoinColumn(name = "id_cultivo", referencedColumnName = "id_cultivo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cultivo cultivo;
    @JoinColumn(name = "id_plaga", referencedColumnName = "id_plaga", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Plaga plaga;

    public PlagaCultivo() {
    }

    public PlagaCultivo(PlagaCultivoPK plagaCultivoPK) {
        this.plagaCultivoPK = plagaCultivoPK;
    }

    public PlagaCultivo(int idCultivo, int idPlaga) {
        this.plagaCultivoPK = new PlagaCultivoPK(idCultivo, idPlaga);
    }

    public PlagaCultivo(Date fecha, String notas, Cultivo cultivo, Plaga plaga) {
        this.fechaAplicacion = fecha;
        this.anotacion = notas;
        this.cultivo = cultivo;
        this.plaga = plaga;
    }

    public PlagaCultivoPK getPlagaCultivoPK() {
        return plagaCultivoPK;
    }

    public void setPlagaCultivoPK(PlagaCultivoPK plagaCultivoPK) {
        this.plagaCultivoPK = plagaCultivoPK;
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

    public Plaga getPlaga() {
        return plaga;
    }

    public void setPlaga(Plaga plaga) {
        this.plaga = plaga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (plagaCultivoPK != null ? plagaCultivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlagaCultivo)) {
            return false;
        }
        PlagaCultivo other = (PlagaCultivo) object;
        if ((this.plagaCultivoPK == null && other.plagaCultivoPK != null) || (this.plagaCultivoPK != null && !this.plagaCultivoPK.equals(other.plagaCultivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.PlagaCultivo[ plagaCultivoPK=" + plagaCultivoPK + " ]";
    }
    
}
