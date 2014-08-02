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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "persona_empresa_usuario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PersonaEmpresaUsuario.findAll", query = "SELECT p FROM PersonaEmpresaUsuario p"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByUserId", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.userId = :userId"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByCiRuc", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.ciRuc = :ciRuc"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByApellido", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.apellido = :apellido"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByNombre", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByEmail", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.email = :email"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByNombreUsuario", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.nombreUsuario = :nombreUsuario"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByPassword", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.password = :password"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByRazonSocial", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.razonSocial = :razonSocial"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByDireccion", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.direccion = :direccion"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByCiudad", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.ciudad = :ciudad"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByNombreComercial", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.nombreComercial = :nombreComercial"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByNumeroResolucion", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.numeroResolucion = :numeroResolucion"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByTipo", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByAsignado", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.asignado = :asignado"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByRol", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.rol = :rol"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByTelCelular", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.telCelular = :telCelular"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByTelConvencional", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.telConvencional = :telConvencional"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByTokenPassword", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.tokenPassword = :tokenPassword"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByRepresentante", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.representante = :representante"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByContador", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.contador = :contador"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByWebSite", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.webSite = :webSite"),
    @NamedQuery(name = "PersonaEmpresaUsuario.findByDescuentoServicio", query = "SELECT p FROM PersonaEmpresaUsuario p WHERE p.descuentoServicio = :descuentoServicio")})
public class PersonaEmpresaUsuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 10)
    @Column(name = "user_id")
    private String userId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "ci_ruc")
    private String ciRuc;
    @Size(max = 30)
    @Column(name = "apellido")
    private String apellido;
    @Size(max = 30)
    @Column(name = "nombre")
    private String nombre;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 70)
    @Column(name = "email")
    private String email;
    @Size(max = 20)
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Size(max = 130)
    @Column(name = "password")
    private String password;
    @Size(max = 80)
    @Column(name = "razon_social")
    private String razonSocial;
    @Size(max = 300)
    @Column(name = "direccion")
    private String direccion;
    @Size(max = 45)
    @Column(name = "ciudad")
    private String ciudad;
    @Size(max = 80)
    @Column(name = "nombre_comercial")
    private String nombreComercial;
    @Size(max = 5)
    @Column(name = "numero_resolucion")
    private String numeroResolucion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipo")
    private int tipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asignado")
    private int asignado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "rol")
    private int rol;
    @Size(max = 15)
    @Column(name = "tel_celular")
    private String telCelular;
    @Size(max = 15)
    @Column(name = "tel_convencional")
    private String telConvencional;
    @Size(max = 200)
    @Column(name = "token_password")
    private String tokenPassword;
    @Size(max = 20)
    @Column(name = "representante")
    private String representante;
    @Size(max = 20)
    @Column(name = "contador")
    private String contador;
    @Size(max = 45)
    @Column(name = "web_site")
    private String webSite;
    @Column(name = "descuento_servicio")
    private Integer descuentoServicio;
    @JoinColumn(name = "tipo_ci_ruc_id", referencedColumnName = "id")
    @ManyToOne
    private TipoCiRuc tipoCiRucId;
    @JoinColumn(name = "ir_id", referencedColumnName = "id")
    @ManyToOne
    private Ir irId;
    @JoinColumn(name = "estado_id", referencedColumnName = "id")
    @ManyToOne
    private Estado estadoId;
    @JoinColumn(name = "contribuyente_id", referencedColumnName = "id")
    @ManyToOne
    private Contribuyente contribuyenteId;
    @OneToMany(mappedBy = "clienteId")
    private Collection<FacturaDesktop> facturaDesktopCollection;

    public PersonaEmpresaUsuario() {
    }

    public PersonaEmpresaUsuario(String ciRuc) {
        this.ciRuc = ciRuc;
    }

    public PersonaEmpresaUsuario(String ciRuc, int tipo, int asignado, int rol) {
        this.ciRuc = ciRuc;
        this.tipo = tipo;
        this.asignado = asignado;
        this.rol = rol;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCiRuc() {
        return ciRuc;
    }

    public void setCiRuc(String ciRuc) {
        this.ciRuc = ciRuc;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getNombreComercial() {
        return nombreComercial;
    }

    public void setNombreComercial(String nombreComercial) {
        this.nombreComercial = nombreComercial;
    }

    public String getNumeroResolucion() {
        return numeroResolucion;
    }

    public void setNumeroResolucion(String numeroResolucion) {
        this.numeroResolucion = numeroResolucion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getAsignado() {
        return asignado;
    }

    public void setAsignado(int asignado) {
        this.asignado = asignado;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getTelCelular() {
        return telCelular;
    }

    public void setTelCelular(String telCelular) {
        this.telCelular = telCelular;
    }

    public String getTelConvencional() {
        return telConvencional;
    }

    public void setTelConvencional(String telConvencional) {
        this.telConvencional = telConvencional;
    }

    public String getTokenPassword() {
        return tokenPassword;
    }

    public void setTokenPassword(String tokenPassword) {
        this.tokenPassword = tokenPassword;
    }

    public String getRepresentante() {
        return representante;
    }

    public void setRepresentante(String representante) {
        this.representante = representante;
    }

    public String getContador() {
        return contador;
    }

    public void setContador(String contador) {
        this.contador = contador;
    }

    public String getWebSite() {
        return webSite;
    }

    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    public Integer getDescuentoServicio() {
        return descuentoServicio;
    }

    public void setDescuentoServicio(Integer descuentoServicio) {
        this.descuentoServicio = descuentoServicio;
    }

    public TipoCiRuc getTipoCiRucId() {
        return tipoCiRucId;
    }

    public void setTipoCiRucId(TipoCiRuc tipoCiRucId) {
        this.tipoCiRucId = tipoCiRucId;
    }

    public Ir getIrId() {
        return irId;
    }

    public void setIrId(Ir irId) {
        this.irId = irId;
    }

    public Estado getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Estado estadoId) {
        this.estadoId = estadoId;
    }

    public Contribuyente getContribuyenteId() {
        return contribuyenteId;
    }

    public void setContribuyenteId(Contribuyente contribuyenteId) {
        this.contribuyenteId = contribuyenteId;
    }

    @XmlTransient
    public Collection<FacturaDesktop> getFacturaDesktopCollection() {
        return facturaDesktopCollection;
    }

    public void setFacturaDesktopCollection(Collection<FacturaDesktop> facturaDesktopCollection) {
        this.facturaDesktopCollection = facturaDesktopCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciRuc != null ? ciRuc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonaEmpresaUsuario)) {
            return false;
        }
        PersonaEmpresaUsuario other = (PersonaEmpresaUsuario) object;
        if ((this.ciRuc == null && other.ciRuc != null) || (this.ciRuc != null && !this.ciRuc.equals(other.ciRuc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.PersonaEmpresaUsuario[ ciRuc=" + ciRuc + " ]";
    }
    
}
