package gasto.modelo;

import inventario.modelo.*;
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
public class AsignacionGasto implements Serializable{

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



    // <editor-fold defaultstate="collapsed" desc="gastos (one AsignacionGasto toMany GastoRealizado)">
    @OneToMany(mappedBy = "asignadoA")
    private Collection<GastoRealizado> gastos;

    public Collection<GastoRealizado> getGastos() {
        return gastos;
    }

    public void setGastos(Collection<GastoRealizado> gastos) {
        this.gastos = gastos;
    }


    // </editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AsignacionGasto other = (AsignacionGasto) obj;
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
