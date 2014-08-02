/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aaguerra
 */
@Entity
@Table(name = "factura_desktop")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaDesktop.findAll", query = "SELECT f FROM FacturaDesktop f"),
    @NamedQuery(name = "FacturaDesktop.findById", query = "SELECT f FROM FacturaDesktop f WHERE f.id = :id"),
    @NamedQuery(name = "FacturaDesktop.findByProveedorId", query = "SELECT f FROM FacturaDesktop f WHERE f.proveedorId = :proveedorId"),
    @NamedQuery(name = "FacturaDesktop.findByFechaFacturado", query = "SELECT f FROM FacturaDesktop f WHERE f.fechaFacturado = :fechaFacturado"),
    @NamedQuery(name = "FacturaDesktop.findByNumeroFactura", query = "SELECT f FROM FacturaDesktop f WHERE f.numeroFactura = :numeroFactura"),
    @NamedQuery(name = "FacturaDesktop.findByClaveAcceso", query = "SELECT f FROM FacturaDesktop f WHERE f.claveAcceso = :claveAcceso"),
    @NamedQuery(name = "FacturaDesktop.findByNumeroAutorizacion", query = "SELECT f FROM FacturaDesktop f WHERE f.numeroAutorizacion = :numeroAutorizacion"),
    @NamedQuery(name = "FacturaDesktop.findByTipoAmbiente", query = "SELECT f FROM FacturaDesktop f WHERE f.tipoAmbiente = :tipoAmbiente")})
public class FacturaDesktop implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 10)
    @Column(name = "proveedor_id")
    private String proveedorId;
    @Column(name = "fecha_facturado")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFacturado;
    @Size(max = 20)
    @Column(name = "numero_factura")
    private String numeroFactura;
    @Size(max = 60)
    @Column(name = "clave_acceso")
    private String claveAcceso;
    @Size(max = 60)
    @Column(name = "numero_autorizacion")
    private String numeroAutorizacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo_ambiente")
    private int tipoAmbiente;
    @JoinColumn(name = "cliente_id", referencedColumnName = "ci_ruc")
    @ManyToOne
    private PersonaEmpresaUsuario clienteId;

    public FacturaDesktop() {
    }

    public FacturaDesktop(Integer id) {
        this.id = id;
    }

    public FacturaDesktop(Integer id, int tipoAmbiente) {
        this.id = id;
        this.tipoAmbiente = tipoAmbiente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(String proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Date getFechaFacturado() {
        return fechaFacturado;
    }

    public void setFechaFacturado(Date fechaFacturado) {
        this.fechaFacturado = fechaFacturado;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public int getTipoAmbiente() {
        return tipoAmbiente;
    }

    public void setTipoAmbiente(int tipoAmbiente) {
        this.tipoAmbiente = tipoAmbiente;
    }

    public PersonaEmpresaUsuario getClienteId() {
        return clienteId;
    }

    public void setClienteId(PersonaEmpresaUsuario clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDesktop)) {
            return false;
        }
        FacturaDesktop other = (FacturaDesktop) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.FacturaDesktop[ id=" + id + " ]";
    }
    
}
