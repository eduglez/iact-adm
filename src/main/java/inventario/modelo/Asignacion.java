package inventario.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import personal.modelo.Empleado;
import proyectos.modelo.LineaInvestigacion;
import proyectos.modelo.Proyecto;

@Entity
public class Asignacion implements Serializable{

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

    
    // <editor-fold defaultstate="collapsed" desc="producto (many Asignacion toOne ProductoAlmacenado)">
    @ManyToOne
    private ProductoAlmacenado producto;
    
    public ProductoAlmacenado getProducto() {
        return producto;
    }

    public void setProducto(ProductoAlmacenado producto) {
        this.producto = producto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="proyecto (many Asignacion toOne Proyecto)">
    @ManyToOne
    private Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="empleado (many Asignacion toOne Empleado)">
    @ManyToOne
    private Empleado empleado;
    
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="lineaInvestigacion (many Asignacion toOne LineaInvestigacion)">
    @ManyToOne
    private LineaInvestigacion lineaInvestigacion;

    public LineaInvestigacion getLineaInvestigacion() {
        return lineaInvestigacion;
    }

    public void setLineaInvestigacion(LineaInvestigacion lineaInvestigacion) {
        this.lineaInvestigacion = lineaInvestigacion;
    }// </editor-fold>



    // <editor-fold defaultstate="collapsed" desc="entradas (one Asignacion toMany MovimientoEntrada)">
    @OneToMany(mappedBy = "productoAsignado")
    private Collection<MovimientoEntrada> entradas;

    public Collection<MovimientoEntrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(Collection<MovimientoEntrada> entradas) {
        this.entradas = entradas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="salidas (one Asignacion toMany MovimientoSalida)">
    @OneToMany(mappedBy = "productoAsignado")
    private Collection<MovimientoSalida> salidas;

    public Collection<MovimientoSalida> getSalidas() {
        return salidas;
    }

    public void setSalidas(Collection<MovimientoSalida> salidas) {
        this.salidas = salidas;
    }// </editor-fold>

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Asignacion other = (Asignacion) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        if(isNoAsignado())
            return "NO ASIGNADO";

        return (getProyecto()!=null
                     ?"Proyecto: "+getProyecto()+" "
                     :"")
                    +(getEmpleado()!=null
                     ?"Empleado: "+getEmpleado()+" "
                     :"")
                    +(getLineaInvestigacion()!=null
                     ?"Linea Investigacion: "+getLineaInvestigacion()
                     :"");
    }



    public boolean isNoAsignado(){
        return getProyecto()==null&&getLineaInvestigacion()==null&&getEmpleado()==null;
    }



    
}
