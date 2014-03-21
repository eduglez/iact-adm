package personal.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import proyectos.modelo.EmpleadoProyecto;

@Entity
public class Empleado implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nombre">
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="apellidos">
    private String apellidos;

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="fechaNacimiento">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaNacimiento;

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="direccion">
    private String direccion;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="telefono">
    private String telefono;

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="numSeguridadSocial">
    private String numSeguridadSocial;

    public String getNumSeguridadSocial() {
        return numSeguridadSocial;
    }

    public void setNumSeguridadSocial(String numSeguridadSocial) {
        this.numSeguridadSocial = numSeguridadSocial;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="numCuentaBancaria">
    private String numCuentaBancaria;
    
    public String getNumCuentaBancaria() {
        return numCuentaBancaria;
    }

    public void setNumCuentaBancaria(String numCuentaBancaria) {
        this.numCuentaBancaria = numCuentaBancaria;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="web">
    private String web;
    
    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="dni">
    private String dni;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="sexo">
    private String sexo;

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="borrado">
    private boolean borrado;

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
    // </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="contratos (one Empleado toMany Contrato">
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.ALL)
    private Collection<Contrato> contratos;

    public Collection<Contrato> getContratos() {
        return contratos;
    }

    public void setContratos(Collection<Contrato> contratos) {
        this.contratos = contratos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="proyectos (one Empleado toMany EmpleadoProyecto)">
    @OneToMany(mappedBy = "empleadoIACT", cascade = CascadeType.ALL)
    private Collection<EmpleadoProyecto> proyectos;
    
    public Collection<EmpleadoProyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(Collection<EmpleadoProyecto> proyectos) {
        this.proyectos = proyectos;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="faltas (one Empleado toMany Falta)">
    @OneToMany(mappedBy = "empleado", cascade = CascadeType.MERGE)
    private Collection<Falta> faltas;

    public Collection<Falta> getFaltas() {
        return faltas;
    }

    public void setFaltas(Collection<Falta> faltas) {
        this.faltas = faltas;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="contratoActual (one Empleado toOne Contrato)">
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private Contrato contratoActual;
    
    public Contrato getContratoActual() {
        return contratoActual;
    }
    
    public void setContratoActual(Contrato contratoActual) {
        this.contratoActual = contratoActual;
    }
    // </editor-fold>



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Empleado)) {
            return false;
        }
        Empleado other = (Empleado) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getApellidos()+", "+getNombre();
    }
    
    public Empleado(){
        contratos=new ArrayList<Contrato>();
        faltas=new ArrayList<Falta>();
    }

    public List<Contrato> getContratosAnteriores() {
        ArrayList<Contrato> contratosAnteriores=new ArrayList<Contrato>(getContratos());
        if(getContratoActual()!=null){
            contratosAnteriores.remove(getContratoActual());
            
        }
        return contratosAnteriores;
    }



    public final static String HOMBRE="Hombre";

    public final static String MUJER="Mujer";

}
