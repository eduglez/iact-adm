package personal.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class EspecialidadLaboral implements Serializable {

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
        
        if (!(object instanceof EspecialidadLaboral)) {
            return false;
        }
        EspecialidadLaboral other = (EspecialidadLaboral) object;
        if ((getId() == null && getId() != null) || (getId() != null && !getId().equals(other.getId()))) {
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
