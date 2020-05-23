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
public class PlagaCultivoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_cultivo")
    private int idCultivo;
    @Basic(optional = false)
    @Column(name = "id_plaga")
    private int idPlaga;

    public PlagaCultivoPK() {
    }

    public PlagaCultivoPK(int idCultivo, int idPlaga) {
        this.idCultivo = idCultivo;
        this.idPlaga = idPlaga;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public int getIdPlaga() {
        return idPlaga;
    }

    public void setIdPlaga(int idPlaga) {
        this.idPlaga = idPlaga;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCultivo;
        hash += (int) idPlaga;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlagaCultivoPK)) {
            return false;
        }
        PlagaCultivoPK other = (PlagaCultivoPK) object;
        if (this.idCultivo != other.idCultivo) {
            return false;
        }
        if (this.idPlaga != other.idPlaga) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.PlagaCultivoPK[ idCultivo=" + idCultivo + ", idPlaga=" + idPlaga + " ]";
    }
    
}
