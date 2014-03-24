package proyectos.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import personal.modelo.Contrato;
import personal.modelo.Empleado;


@Entity
public class EmpleadoProyecto implements Serializable {


    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
    
    // <editor-fold defaultstate="collapsed" desc="fechaInicio">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    
    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaFin">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    
    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="proyecto (many EmpleadoProyecto toOne Proyecto)">
    @ManyToOne
    private Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="contrato (one EmpleadoProyecto toOne Contrato)">
    @OneToOne
    private Contrato contrato;

    public Contrato getContrato() {
        return contrato;
    }

    public void setContrato(Contrato contratoEmpleadoIACT) {
        this.contrato = contratoEmpleadoIACT;
        if (contratoEmpleadoIACT != null) {
            setEmpleadoIACT(contratoEmpleadoIACT.getEmpleado());
            
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="empleadoIACT (many EmpleadoProyecto toOne Empleado)">
    @ManyToOne
    private Empleado empleadoIACT;

    public Empleado getEmpleadoIACT() {
        return empleadoIACT;
    }

    public void setEmpleadoIACT(Empleado empleadoIACT) {
        this.empleadoIACT = empleadoIACT;
    }
    // </editor-fold>


    
    public boolean isIACT(){
        return getEmpleadoIACT()!=null;
    }

    public boolean isResponsable(){

        if(getProyecto()==null)
            return false;
        if(isIACT()){
            return this==getProyecto().getResponsable();
        }
        return false;
    }
    
    public String establecerResponsable(){
        if(this.proyecto!=null)
            this.proyecto.setResponsable(this);
        return null;
    }

    public String quitarDeProyecto(){
        if(isResponsable())
            getProyecto().setResponsable(null);

        setProyecto(null);
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoProyecto other = (EmpleadoProyecto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

  
}
