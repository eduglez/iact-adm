package proyectos.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class EstadoProyecto implements Serializable {
    @Id
    private String nombre;

    private String styleColor;

    public String getStyleColor() {
        return styleColor;
    }

    public void setStyleColor(String styleColor) {
        this.styleColor = styleColor;
    }

    
    public String getNombre() {
        return nombre;
    }

    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoProyecto other = (EstadoProyecto) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public String getId(){
        return getNombre();
    }

    
}
