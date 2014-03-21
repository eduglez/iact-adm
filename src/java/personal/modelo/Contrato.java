package personal.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import proyectos.modelo.EmpleadoProyecto;
import proyectos.modelo.LineaInvestigacion;
import proyectos.modelo.Organismo;


@Entity
public class Contrato implements Serializable {

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

    // <editor-fold defaultstate="collapsed" desc="registroPersonal">
    private String registroPersonal;

    public String getRegistroPersonal() {
        return registroPersonal;
    }

    public void setRegistroPersonal(String registroPersonal) {
        this.registroPersonal = registroPersonal;
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
    
    // <editor-fold defaultstate="collapsed" desc="eMail">
    private String eMail;

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="eMail2">
    private String eMailsec;

    public String getEMailsec() {
        return eMailsec;
    }

    public void setEMailsec(String eMailsec) {
        this.eMailsec = eMailsec;
    }

    
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="localizacion">
    private String localizacion;

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="observaciones">
    @Column(length = 500)
    private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaInicioContrato">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioContrato;
    
    public Date getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(Date fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaFinContrato">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinContrato;

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaInicioIACT">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicioIACT;

    public Date getFechaInicioIACT() {
        return fechaInicioIACT;
    }

    public void setFechaInicioIACT(Date fechaInicioIACT) {
        this.fechaInicioIACT = fechaInicioIACT;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fechaFinIACT">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinIACT;

    public Date getFechaFinIACT() {
        return fechaFinIACT;
    }

    public void setFechaFinIACT(Date fechaFinIACT) {
        this.fechaFinIACT = fechaFinIACT;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="jornadaLaboral (many Contrato toOne JornadaLaboral)">
    @ManyToOne
    private JornadaLaboral jornadaLaboral;

    public JornadaLaboral getJornadaLaboral() {
        return jornadaLaboral;
    }

    public void setJornadaLaboral(JornadaLaboral jornadaLaboral) {
        this.jornadaLaboral = jornadaLaboral;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="departamento (many Contrato toOne Departamento)">
    @ManyToOne
    private Departamento departamento;
    
    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="servicio (many Contrato toOne Servicio)">
    @ManyToOne
    private Servicio servicio;

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="lineaInvestigacion (many Contrato toOne LineaInvestigacion)">
    @ManyToOne
    private LineaInvestigacion lineaInvestigacion;

    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="organismo (many Contrato toOne Organismo)">
    @ManyToOne
    private Organismo organismo;

    public Organismo getOrganismo() {
        return organismo;
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="empleado (many Contrato toOne Empleado)">
    @ManyToOne
    private Empleado empleado;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="proyecto (one Contrato toOne EmpleadoProyecto)">
    @OneToOne(mappedBy = "contrato", cascade = (CascadeType.MERGE))
    private EmpleadoProyecto proyecto;

    public EmpleadoProyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(EmpleadoProyecto proyecto) {
        this.proyecto = proyecto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="tipoEmpleado (one Contrato toOne TipoEmpleado)">
    @OneToOne(mappedBy = "contratoEmpleado", cascade = (CascadeType.ALL))
    private TipoEmpleado tipoEmpleado;

    public TipoEmpleado getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(TipoEmpleado tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="documento (one Contrato toOne DocumentoContrato)">
    @OneToOne(mappedBy = "contrato", cascade = (CascadeType.ALL))
    private DocumentoContrato documento;

    public DocumentoContrato getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoContrato documento) {
        this.documento = documento;
    }
    // </editor-fold>
    

    //CONSTRUCTORES

    public Contrato() {
        TipoEmpleado t=new TipoEmpleado();
        t.setTipoEmpleado(TipoEmpleado.ESTANCIA);
        setTipoEmpleado(t);
        t.setContratoEmpleado(this);
    }


    public String getFechaPrueba(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaInicioIACT);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Contrato)) {
            return false;
        }
        Contrato other = (Contrato) object;
        if ((getId() == null && other.getId() != null) || (getId() != null && !getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return registroPersonal;
    }

    public boolean isVacio(){
        return (getRegistroPersonal()==null||getRegistroPersonal().equals(""))&&
                (getFechaFinContrato()==null)&&
                (getFechaFinIACT()==null)&&
                (getFechaInicioContrato()==null)&&
                (getFechaInicioIACT()==null)&&
                (getTelefono()==null||getTelefono().equals(""))&&
                (getObservaciones()==null||getObservaciones().equals(""))&&
                (getEMail()==null||getEMail().equals(""))&&
                (getDepartamento()==null)&&
                (getServicio()==null)&&
                (getLineaInvestigacion()==null)&&
                (getOrganismo()==null)&&
                (getTipoEmpleado()==null||getTipoEmpleado().getAdscripcion()==null)&&
                (getProyecto()==null);
    }

    public boolean isFinalizadoEnUnMes(){

        if(isVacio())
            return false;

        if(getFechaFinContrato()==null)
            return false;
        
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 1);

        return getFechaFinContrato().compareTo(c1.getTime())<=0;

    }

    public boolean isContratoActual(){
        if(empleado==null)
            return false;

        return empleado.getContratoActual() == this;
    }
    
}
