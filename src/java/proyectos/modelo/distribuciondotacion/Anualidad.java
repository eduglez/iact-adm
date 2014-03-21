package proyectos.modelo.distribuciondotacion;

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Anualidad implements Comparable<Anualidad>{

    @Id
    private String anio;

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Anualidad other = (Anualidad) obj;
        if ((this.anio == null) ? (other.anio != null) : !this.anio.equals(other.anio)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.anio != null ? this.anio.hashCode() : 0);
        return hash;
    }

    public String getId(){
        return getAnio();
    }

    @Override
    public String toString(){
        return getAnio();
    }

    public int compareTo(Anualidad o) {
        return Integer.parseInt(getAnio())-Integer.parseInt(o.getAnio());
    }


    public boolean isEnAnualidad(Date fecha){
        if(fecha==null)
            return false;

        Calendar c=Calendar.getInstance();

        c.setTime(fecha);

        return c.get(Calendar.YEAR)==Integer.parseInt(getAnio());
    }
}
