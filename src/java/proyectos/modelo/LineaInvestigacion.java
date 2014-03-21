package proyectos.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class LineaInvestigacion implements Serializable {
    @Id
    private String nombre;

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @OneToMany(mappedBy="lineaInvestigacionPadre", cascade=CascadeType.ALL)
    private Collection<LineaInvestigacion> subLineasInvestigacion;

    public Collection<LineaInvestigacion> getSubLineasInvestigacion() {
        return subLineasInvestigacion;
    }

    public void setSubLineasInvestigacion(Collection<LineaInvestigacion> subLineasInvestigacion) {
        this.subLineasInvestigacion = subLineasInvestigacion;
    }

    @ManyToOne
    private LineaInvestigacion lineaInvestigacionPadre;

    public LineaInvestigacion getLineaInvestigacionPadre() {
        return lineaInvestigacionPadre;
    }

    public void setLineaInvestigacionPadre(LineaInvestigacion lineaInvestigacionPadre) {
        this.lineaInvestigacionPadre = lineaInvestigacionPadre;
    }
    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LineaInvestigacion other = (LineaInvestigacion) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    public String getId(){
        return getNombre();
    }

    @Override
    public String toString(){
        return getLineaInvestigacionPadre()!=null?getLineaInvestigacionPadre().getNombre()+" -> "+getNombre():getNombre();
    }

    public boolean isLineaInvestigacionRaiz(){
        return getLineaInvestigacionPadre()==null;
    }



}
