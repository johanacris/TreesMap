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
public class FertilizanteCultivoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "id_cultivo")
    private int idCultivo;
    @Basic(optional = false)
    @Column(name = "id_fertilizante")
    private int idFertilizante;

    public FertilizanteCultivoPK() {
    }

    public FertilizanteCultivoPK(int idCultivo, int idFertilizante) {
        this.idCultivo = idCultivo;
        this.idFertilizante = idFertilizante;
    }

    public int getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(int idCultivo) {
        this.idCultivo = idCultivo;
    }

    public int getIdFertilizante() {
        return idFertilizante;
    }

    public void setIdFertilizante(int idFertilizante) {
        this.idFertilizante = idFertilizante;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idCultivo;
        hash += (int) idFertilizante;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FertilizanteCultivoPK)) {
            return false;
        }
        FertilizanteCultivoPK other = (FertilizanteCultivoPK) object;
        if (this.idCultivo != other.idCultivo) {
            return false;
        }
        if (this.idFertilizante != other.idFertilizante) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.FertilizanteCultivoPK[ idCultivo=" + idCultivo + ", idFertilizante=" + idFertilizante + " ]";
    }
    
}
