/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "codigo_recuperacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoRecuperacion.findAll", query = "SELECT c FROM CodigoRecuperacion c"),
    @NamedQuery(name = "CodigoRecuperacion.findByCodigo", query = "SELECT c FROM CodigoRecuperacion c WHERE c.codigo = :codigo"),
    @NamedQuery(name = "CodigoRecuperacion.findByHora", query = "SELECT c FROM CodigoRecuperacion c WHERE c.hora = :hora"),
    @NamedQuery(name = "CodigoRecuperacion.findByUsuario", query = "SELECT c FROM CodigoRecuperacion c WHERE c.usuario = :usuario"),
    @NamedQuery(name = "CodigoRecuperacion.findByFecha", query = "SELECT c FROM CodigoRecuperacion c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "CodigoRecuperacion.findByUsado", query = "SELECT c FROM CodigoRecuperacion c WHERE c.usado = :usado")})
public class CodigoRecuperacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "usado")
    private Boolean usado;

    public CodigoRecuperacion() {
    }

    public CodigoRecuperacion(String codigo) {
        this.codigo = codigo;
    }

    public CodigoRecuperacion(String codigo, String email, Date fecha, Date hora, boolean valido) {
        this.codigo = codigo;
        this.usuario = email;
        this.fecha = fecha;
        this.hora = hora;
        this.usado = valido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getUsado() {
        return usado;
    }

    public void setUsado(Boolean usado) {
        this.usado = usado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoRecuperacion)) {
            return false;
        }
        CodigoRecuperacion other = (CodigoRecuperacion) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.CodigoRecuperacion[ codigo=" + codigo + " ]";
    }
    
}
