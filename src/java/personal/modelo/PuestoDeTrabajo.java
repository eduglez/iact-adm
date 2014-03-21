package personal.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class PuestoDeTrabajo implements Serializable {

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

        if (!(object instanceof PuestoDeTrabajo)) {
            return false;
        }
        PuestoDeTrabajo other = (PuestoDeTrabajo) object;
        if ((getId() == null && other.getId() != null) || (getId() != null && !getId().equals(other.getId()))) {
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
