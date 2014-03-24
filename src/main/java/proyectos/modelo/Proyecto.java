package proyectos.modelo;

import gasto.modelo.Gasto;
import compras.modelo.Pedido;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import personal.modelo.Departamento;
import proyectos.modelo.distribuciondotacion.AnualidadPartida;

@Entity
public class Proyecto implements Serializable {

    //CAMPOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    private boolean borrado;

    private String cuentaInterna;
    private String referencia;
    private String subtipoProyecto;
    private String convocatoria;
    private String web;
    private long dotacion;
    private boolean contrato;
    private boolean publicar;
    
    @Column(length = 300)
    private String titulo;
    @Column(length = 500)
    private String observaciones;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSolicitud;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaResolucion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAceptacion;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAmpliacionPlazo;
    

    //RELACIONES
//    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.MERGE)
//    private List<Gasto> gastos;
    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<AnualidadPartida> datosEconomicos;
    @OneToMany(mappedBy = "proyecto")
    private List<Pedido> pedidos;
    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.MERGE})
    private Collection<Justificacion> justificaciones;
    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.MERGE})
    private Collection<DocumentoProyecto> documentos;
    @OneToMany(mappedBy = "proyecto", cascade = {CascadeType.MERGE})
    private Collection<EmpleadoProyecto> personal;
    
    @ManyToOne
    private EstadoProyecto estadoProyecto;
    @ManyToOne
    private EmpleadoProyecto responsable;
    @ManyToOne
    private EntidadFinanciadora entidadFinanciadora;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private TipoProyecto tipoProyecto;
    @ManyToOne
    private Organismo organismo;

    //CONSTRUCTORES
    public Proyecto() {
        justificaciones = new ArrayList<Justificacion>();
        personal = new ArrayList<EmpleadoProyecto>();
        documentos = new ArrayList<DocumentoProyecto>();
        datosEconomicos = new ArrayList<AnualidadPartida>();
//        gastos = new ArrayList<Gasto>();
    }

    //METODOS
    public Date getFechaJustificacion() {
        Date fechaSeleccionada = null;

        if ((getJustificaciones() != null) && (!getJustificaciones().isEmpty())) {
            for (Justificacion j : getJustificaciones()) {
                if (j.getFechaJustificacion() == null) {
                    if (fechaSeleccionada == null || fechaSeleccionada.after(j.getFechaFinPlazoProximaJustificacion())) {
                        fechaSeleccionada = j.getFechaFinPlazoProximaJustificacion();
                    }
                }
            }
            return fechaSeleccionada;

        } else {
            return null;
        }

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Proyecto other = (Proyecto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getReferencia();
    }

    public boolean isJustificarEnMenosUnMes() {
        Calendar c1 = Calendar.getInstance();
        c1.add(Calendar.MONTH, 1);

        if (getFechaJustificacion() == null) {
            return false;
        }

        if (getFechaJustificacion().before(c1.getTime())) {
            return true;
        } else {
            return false;
        }
    }

    public Date getFechaFinReal() {
        if (getFechaAmpliacionPlazo() != null) {
            if (getFechaFin() != null) {
                Calendar cFin = Calendar.getInstance();
                Calendar cAmpliacion = Calendar.getInstance();

                cFin.setTime(getFechaFin());
                cAmpliacion.setTime(getFechaAmpliacionPlazo());

                if (cAmpliacion.after(cFin)) {
                    return getFechaAmpliacionPlazo();
                } else {
                    return getFechaFin();
                }

            } else {
                return getFechaAmpliacionPlazo();
            }
        } else {
            return getFechaFin();
        }

    }

    public boolean isJA() {
        if (getEntidadFinanciadora() != null) {
            return getEntidadFinanciadora().getNombre().equals("Consejería de Economía, Innovación y Empresa. Junta de Andalucía");
        } else {
            return false;
        }
    }

    public boolean isAyudaGruposInvestigacion() {
        if (getTipoProyecto() != null) {
            return getTipoProyecto().getNombre().equals("Ayuda a Grupos de Investigación");
        } else {
            return false;
        }
    }

    public boolean isProyectoExcelencia() {
        if (getTipoProyecto() != null) {
            return getTipoProyecto().getNombre().equals("Excelencia");
        } else {
            return false;
        }
    }

    //ACCESORES
//    public List<Gasto> getGastos() {
//        return gastos;
//    }
//
//    public void setGastos(List<Gasto> gastos) {
//        this.gastos = gastos;
//    }

    public List<AnualidadPartida> getDatosEconomicos() {
        return datosEconomicos;
    }

    public void setDatosEconomicos(List<AnualidadPartida> datosEconomicos) {
        this.datosEconomicos = datosEconomicos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    private boolean financiadoFondosFEDER;

    public boolean isFinanciadoFondosFEDER() {
        return financiadoFondosFEDER;
    }

    public void setFinanciadoFondosFEDER(boolean financiadoFondosFEDER) {
        this.financiadoFondosFEDER = financiadoFondosFEDER;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Collection<DocumentoProyecto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Collection<DocumentoProyecto> documentos) {
        this.documentos = documentos;
    }

    public boolean isContrato() {
        return contrato;
    }

    public void setContrato(boolean contrato) {
        this.contrato = contrato;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCuentaInterna() {
        return cuentaInterna;
    }

    public void setCuentaInterna(String cuentaInterna) {
        this.cuentaInterna = cuentaInterna;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getSubtipoProyecto() {
        return subtipoProyecto;
    }

    public void setSubtipoProyecto(String subtipoProyecto) {
        this.subtipoProyecto = subtipoProyecto;
    }

    public String getConvocatoria() {
        return convocatoria;
    }

    public void setConvocatoria(String convocatoria) {
        this.convocatoria = convocatoria;
    }

    public EstadoProyecto getEstadoProyecto() {
        return estadoProyecto;
    }

    public void setEstadoProyecto(EstadoProyecto estadoProyecto) {
        this.estadoProyecto = estadoProyecto;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaResolucion() {
        return fechaResolucion;
    }

    public void setFechaResolucion(Date fechaResolucion) {
        this.fechaResolucion = fechaResolucion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public long getDotacion() {
        return dotacion;
    }

    public void setDotacion(long dotacion) {
        this.dotacion = dotacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFechaAceptacion() {
        return fechaAceptacion;
    }

    public void setFechaAceptacion(Date fechaAceptacion) {
        this.fechaAceptacion = fechaAceptacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFechaAmpliacionPlazo() {
        return fechaAmpliacionPlazo;
    }

    public void setFechaAmpliacionPlazo(Date fechaAmpliacionPlazo) {
        this.fechaAmpliacionPlazo = fechaAmpliacionPlazo;
    }

    public Collection<Justificacion> getJustificaciones() {
        return justificaciones;
    }

    public void setJustificaciones(Collection<Justificacion> justificaciones) {
        this.justificaciones = justificaciones;
    }

    public EmpleadoProyecto getResponsable() {
        return responsable;
    }

    public void setResponsable(EmpleadoProyecto responsable) {
        this.responsable = responsable;
    }

    public Collection<EmpleadoProyecto> getPersonal() {
        return personal;
    }

    public void setPersonal(Collection<EmpleadoProyecto> personal) {
        this.personal = personal;
    }

    public EntidadFinanciadora getEntidadFinanciadora() {
        return entidadFinanciadora;
    }

    public void setEntidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public TipoProyecto getTipoProyecto() {
        return tipoProyecto;
    }

    public void setTipoProyecto(TipoProyecto tipoProyecto) {
        this.tipoProyecto = tipoProyecto;
    }

    public Organismo getOrganismo() {
        return organismo;
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public boolean isPublicar() {
        return publicar;
    }

    public void setPublicar(boolean publicarEnWeb) {
        this.publicar = publicarEnWeb;
    }

    
}
