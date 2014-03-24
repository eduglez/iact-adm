package proyectos.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;


@Entity
public class Justificacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Proyecto proyecto;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinPlazoProximaJustificacion;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaJustificacion;

    private long gastosPersonal;

    private long gastosEjecucion;

    private long dotacionAdicional;

    private long costesIndirectos;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicialPeriodoJustificacion;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFinalPeriodoJustificacion;

    //ATRIBUTOS NO PERSISTENTES
    @Transient
    private boolean marcaBorrado;

    public boolean isMarcaBorrado() {
        return marcaBorrado;
    }

    public void setMarcaBorrado(boolean marcaBorrado) {
        this.marcaBorrado = marcaBorrado;
    }


    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the proyecto
     */
    public Proyecto getProyecto() {
        return proyecto;
    }

    /**
     * @param proyecto the proyecto to set
     */
    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }


    /**
     * @return the fechaJustificacion
     */
    public Date getFechaJustificacion() {
        return fechaJustificacion;
    }

    /**
     * @param fechaJustificacion the fechaJustificacion to set
     */
    public void setFechaJustificacion(Date fechaJustificacion) {
        this.fechaJustificacion = fechaJustificacion;
    }

    public Date getFechaFinPlazoProximaJustificacion() {
        return fechaFinPlazoProximaJustificacion;
    }

    public void setFechaFinPlazoProximaJustificacion(Date fechaFinPlazoProximaJustificacion) {
        this.fechaFinPlazoProximaJustificacion = fechaFinPlazoProximaJustificacion;
    }

    public Date getFechaFinalPeriodoJustificacion() {
        return fechaFinalPeriodoJustificacion;
    }

    public void setFechaFinalPeriodoJustificacion(Date fechaFinalPeriodoJustificacion) {
        this.fechaFinalPeriodoJustificacion = fechaFinalPeriodoJustificacion;
    }

    public Date getFechaInicialPeriodoJustificacion() {
        return fechaInicialPeriodoJustificacion;
    }

    public void setFechaInicialPeriodoJustificacion(Date fechaInicialPeriodoJustificacion) {
        this.fechaInicialPeriodoJustificacion = fechaInicialPeriodoJustificacion;
    }

    public long getCostesIndirectos() {
        return costesIndirectos;
    }

    public void setCostesIndirectos(long costesIndirectos) {
        this.costesIndirectos = costesIndirectos;
    }

    public long getDotacionAdicional() {
        return dotacionAdicional;
    }

    public void setDotacionAdicional(long dotacionAdicional) {
        this.dotacionAdicional = dotacionAdicional;
    }

    public long getGastosEjecucion() {
        return gastosEjecucion;
    }

    public void setGastosEjecucion(long gastosEjecucion) {
        this.gastosEjecucion = gastosEjecucion;
    }

    public long getGastosPersonal() {
        return gastosPersonal;
    }

    public void setGastosPersonal(long gastosPersonal) {
        this.gastosPersonal = gastosPersonal;
    }

    public Justificacion() {
        gastosPersonal=0;
        gastosEjecucion=0;
        dotacionAdicional=0;
        costesIndirectos=0;
        marcaBorrado=false;
    }

    public long getTotal(){
        return gastosPersonal+gastosEjecucion+dotacionAdicional+costesIndirectos;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Justificacion other = (Justificacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


}
