package personal.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Adscripción es un etiqueta asignada de forma arbitraria, que lleva cada
 * empleado del IACT para ser agrupado en el buscador dentro de la Web Institucional.
 * Se usa para ocultar el enorme desajuste en el número de Informáticos, Técnicos y
 * Personal administrativo, y personal Investigador.
 * @author edu
 */

@Entity
public class Adscripcion implements Serializable{

    @Id
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId(){
        return getNombre();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Adscripcion other = (Adscripcion) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
    

}
