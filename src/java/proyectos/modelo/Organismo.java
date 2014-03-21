package proyectos.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Organismo implements Serializable {
    @Id
    private String nombre;

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
        final Organismo other = (Organismo) obj;
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
        return getNombre();
    }


}
