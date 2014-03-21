
package compras.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Producto implements Serializable {

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
        final Producto other = (Producto) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getNombre();
    }

   
    public String getId() {
        return getNombre();
    }


}
