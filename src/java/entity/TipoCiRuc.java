/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aaguerra
 */
@Entity
@Table(name = "tipo_ci_ruc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoCiRuc.findAll", query = "SELECT t FROM TipoCiRuc t"),
    @NamedQuery(name = "TipoCiRuc.findById", query = "SELECT t FROM TipoCiRuc t WHERE t.id = :id"),
    @NamedQuery(name = "TipoCiRuc.findByDescripcion", query = "SELECT t FROM TipoCiRuc t WHERE t.descripcion = :descripcion")})
public class TipoCiRuc implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "tipoCiRucId")
    private Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection;

    public TipoCiRuc() {
    }

    public TipoCiRuc(Integer id) {
        this.id = id;
    }

    public TipoCiRuc(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @XmlTransient
    public Collection<PersonaEmpresaUsuario> getPersonaEmpresaUsuarioCollection() {
        return personaEmpresaUsuarioCollection;
    }

    public void setPersonaEmpresaUsuarioCollection(Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection) {
        this.personaEmpresaUsuarioCollection = personaEmpresaUsuarioCollection;
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
        if (!(object instanceof TipoCiRuc)) {
            return false;
        }
        TipoCiRuc other = (TipoCiRuc) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TipoCiRuc[ id=" + id + " ]";
    }
    
}
