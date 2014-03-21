package compras.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class EstadoPedido implements Serializable {

    @Id
    private String nombre;

    public EstadoPedido() {
    }


    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    private EstadoPedido(String nombre){
        setNombre(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoPedido other = (EstadoPedido) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    

    /**
     * @return the id
     */
    public String getId() {
        return getNombre();
    }

    @Override
    public String toString() {
        return getNombre();
    }



}
