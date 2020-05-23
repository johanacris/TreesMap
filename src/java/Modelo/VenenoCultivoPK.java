/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Romario
 */
@Embeddable
public class VenenoCultivoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_cultivo")
    private int idCultivo;
    @Basic(optional = false)
    @Column(name = "id_veneno")
    private int idVeneno;

    public VenenoCultivoPK() {
    }

    public VenenoCultivoPK(int idCultivo, int idVeneno) {
        this.idCultivo = idCultivo;
        this.idVeneno = idVeneno;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public int getIdVeneno() {
        return idVeneno;
    }

    public void setIdVeneno(int idVeneno) {
        this.idVeneno = idVeneno;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCultivo;
        hash += (int) idVeneno;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VenenoCultivoPK)) {
            return false;
        }
        VenenoCultivoPK other = (VenenoCultivoPK) object;
        if (this.idCultivo != other.idCultivo) {
            return false;
        }
        if (this.idVeneno != other.idVeneno) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.VenenoCultivoPK[ idCultivo=" + idCultivo + ", idVeneno=" + idVeneno + " ]";
    }
    
}
