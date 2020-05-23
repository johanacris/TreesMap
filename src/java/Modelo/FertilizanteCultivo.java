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
@Table(name = "fertilizante_cultivo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FertilizanteCultivo.findAll", query = "SELECT f FROM FertilizanteCultivo f"),
    @NamedQuery(name = "FertilizanteCultivo.findByIdCultivo", query = "SELECT f FROM FertilizanteCultivo f WHERE f.fertilizanteCultivoPK.idCultivo = :idCultivo"),
    @NamedQuery(name = "FertilizanteCultivo.findByIdFertilizante", query = "SELECT f FROM FertilizanteCultivo f WHERE f.fertilizanteCultivoPK.idFertilizante = :idFertilizante"),
    @NamedQuery(name = "FertilizanteCultivo.findByFechaAplicacion", query = "SELECT f FROM FertilizanteCultivo f WHERE f.fechaAplicacion = :fechaAplicacion"),
    @NamedQuery(name = "FertilizanteCultivo.findByAnotacion", query = "SELECT f FROM FertilizanteCultivo f WHERE f.anotacion = :anotacion")})
public class FertilizanteCultivo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected FertilizanteCultivoPK fertilizanteCultivoPK;
    @Column(name = "fecha_aplicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaAplicacion;
    @Column(name = "anotacion")
    private String anotacion;
    @JoinColumn(name = "id_cultivo", referencedColumnName = "id_cultivo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Cultivo cultivo;
    @JoinColumn(name = "id_fertilizante", referencedColumnName = "id_fertilizante", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Fertilizante fertilizante;

    public FertilizanteCultivo() {
    }

    public FertilizanteCultivo(FertilizanteCultivoPK fertilizanteCultivoPK) {
        this.fertilizanteCultivoPK = fertilizanteCultivoPK;
    }

    public FertilizanteCultivo(int idCultivo, int idFertilizante) {
        this.fertilizanteCultivoPK = new FertilizanteCultivoPK(idCultivo, idFertilizante);
    }

    public FertilizanteCultivo(Fertilizante fertilizante, Cultivo cultivo, Date fecha, String notas) {
        this.fertilizante = fertilizante;
        this.cultivo = cultivo;
        this.fechaAplicacion = fecha;
        this.anotacion = notas;
    }

    public FertilizanteCultivoPK getFertilizanteCultivoPK() {
        return fertilizanteCultivoPK;
    }

    public void setFertilizanteCultivoPK(FertilizanteCultivoPK fertilizanteCultivoPK) {
        this.fertilizanteCultivoPK = fertilizanteCultivoPK;
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

    public Fertilizante getFertilizante() {
        return fertilizante;
    }

    public void setFertilizante(Fertilizante fertilizante) {
        this.fertilizante = fertilizante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fertilizanteCultivoPK != null ? fertilizanteCultivoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FertilizanteCultivo)) {
            return false;
        }
        FertilizanteCultivo other = (FertilizanteCultivo) object;
        if ((this.fertilizanteCultivoPK == null && other.fertilizanteCultivoPK != null) || (this.fertilizanteCultivoPK != null && !this.fertilizanteCultivoPK.equals(other.fertilizanteCultivoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.FertilizanteCultivo[ fertilizanteCultivoPK=" + fertilizanteCultivoPK + " ]";
    }
    
}
