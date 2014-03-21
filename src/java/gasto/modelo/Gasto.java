package gasto.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Gasto implements Serializable {

    // <editor-fold defaultstate="collapsed" desc="TIPOS_GASTO">
    public final static String COMPLEMENTOS_SALARIALES = "Complementos salariales";
    public final static String CONTRATACION_PERSONAL = "Contratación personal";
    public final static String SEGURIDAD_SOCIAL = "Seguridad social";
    public final static String INVENTARIABLE = "Inventariable";
    public final static String FUNGIBLE = "Fungible";
    public final static String VIAJES_DIETAS = "Viajes y dietas";
    public final static String OTROS_GASTOS = "Otros gastos";
    public final static String COSTES_INDIRECTOS = "Costes indirectos";
    public final static String[] TIPOS_GASTO = {COMPLEMENTOS_SALARIALES,
        CONTRATACION_PERSONAL,
        SEGURIDAD_SOCIAL,
        INVENTARIABLE,
        FUNGIBLE,
        VIAJES_DIETAS,
        OTROS_GASTOS,
        COSTES_INDIRECTOS};// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="tipoGasto">
    private String tipoGasto;

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="descripcion">
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }// </editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gasto other = (Gasto) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getDescripcion()+" - "+tipoGasto;
    }



}
