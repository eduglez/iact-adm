package inventario.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import personal.modelo.Empleado;
import proyectos.modelo.LineaInvestigacion;
import proyectos.modelo.Proyecto;

@Entity
public class MovimientoSalida implements Serializable{

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


    // <editor-fold defaultstate="collapsed" desc="fechaSalida">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaSalida;

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="productoAsignado (many MovimientoSalida toOne Asignacion)">
    @ManyToOne
    private Asignacion productoAsignado;
    
    public Asignacion getProductoAsignado() {
        return productoAsignado;
    }

    public void setProductoAsignado(Asignacion productoAsignado) {
        this.productoAsignado = productoAsignado;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="cantidadEntera">
    private int cantidadEntera;

    public int getCantidadEntera() {
        return cantidadEntera;
    }

    public void setCantidadEntera(int cantidadEntera) {
        this.cantidadEntera = cantidadEntera;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="cantidadFlotante">
    private int cantidadFlotante;

    public int getCantidadFlotante() {
        return cantidadFlotante;
    }

    public void setCantidadFlotante(int cantidadFlotante) {
        this.cantidadFlotante = cantidadFlotante;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="subCantidadEntera">
    private int subCantidadEntera;

    public int getSubCantidadEntera() {
        return subCantidadEntera;
    }

    public void setSubCantidadEntera(int subCantidadEntera) {
        this.subCantidadEntera = subCantidadEntera;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="subCantidadFlotante">
    private int subCantidadFlotante;

    public int getSubCantidadFlotante() {
        return subCantidadFlotante;
    }

    public void setSubCantidadFlotante(int subCantidadFlotante) {
        this.subCantidadFlotante = subCantidadFlotante;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="recogidoPor (many MovimientoSalida toOne Empleado)">
    @ManyToOne
    private Empleado recogidoPor;

    public Empleado getRecogidoPor() {
        return recogidoPor;
    }

    public void setRecogidoPor(Empleado recogidoPor) {
        this.recogidoPor = recogidoPor;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="paraEmpleado (many MovimientoSalida toOne Empleado)">
    @ManyToOne
    private Empleado paraEmpleado;

    public Empleado getParaEmpleado() {
        return paraEmpleado;
    }

    public void setParaEmpleado(Empleado paraEmpleado) {
        this.paraEmpleado = paraEmpleado;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="paraProyecto (many MovimientoSalida toOne Proyecto)">
    @ManyToOne
    private Proyecto paraProyecto;

    public Proyecto getParaProyecto() {
        return paraProyecto;
    }

    public void setParaProyecto(Proyecto paraProyecto) {
        this.paraProyecto = paraProyecto;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="paraLineaInvestigacion (many MovimientoSalida toOne LineInvestigacion)">
    @ManyToOne
    private LineaInvestigacion paraLineaInvestigacion;

    public LineaInvestigacion getParaLineaInvestigacion() {
        return paraLineaInvestigacion;
    }

    public void setParaLineaInvestigacion(LineaInvestigacion paraLineaInvestigacion) {
        this.paraLineaInvestigacion = paraLineaInvestigacion;
    }
    // </editor-fold>



    
    // <editor-fold defaultstate="collapsed" desc="equals">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovimientoSalida other = (MovimientoSalida) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="hashCode">
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }// </editor-fold>
    

}
