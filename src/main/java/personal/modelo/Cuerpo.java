package personal.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Representa a un Cuerpo de trabajadores del Estado (funcionarios)
 * No existen dos cuerpos con mismo nombre.
 * @author edu
 */
@Entity
public class Cuerpo implements Serializable {

    @Id
    private String nombre;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Cuerpo)) {
            return false;
        }
        Cuerpo other = (Cuerpo) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return getNombre();
    }

    public void setId(String id) {
        setNombre(id);
    }
}
