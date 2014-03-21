package gasto.modelo;

import inventario.modelo.Asignacion;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
public class GastoRealizado implements Serializable {
    
    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="gasto (many GastoRealizado toOne Gasto">
    @ManyToOne
    private Gasto gasto;

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="asignadoA (many GastoRealizado toOne AsignacionGasto">
    @ManyToOne
    private AsignacionGasto asignadoA;

    public AsignacionGasto getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(AsignacionGasto asignadoA) {
        this.asignadoA = asignadoA;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fecha">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="total">
    private long total;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="observaciones">
    @Column(length = 400)
    private String observaciones;

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }// </editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GastoRealizado other = (GastoRealizado) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    
    
}
