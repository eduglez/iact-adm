package inventario.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ProductoAlmacenado implements Serializable {

    
    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ean">
    private String ean;
    
    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="nombre">
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="descripcion">
    private String descripcion;

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    // </editor-fold>


    // <editor-fold defaultstate="collapsed" desc="asignaciones (one ProductoAlmacenado toMany Asignacion)">
    @OneToMany(mappedBy="producto")
    private Collection<Asignacion> asignaciones;

    public Collection<Asignacion> getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Collection<Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="tipoCantidad (many ProductoAlmacenado toOne TipoCantidad)">
    @ManyToOne
    private TipoCantidad tipoCantidad;

    public TipoCantidad getTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(TipoCantidad tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    // </editor-fold>


    public ProductoAlmacenado(){
        asignaciones=new ArrayList<Asignacion>();
    }

    public int getTotalEntero(){
        int total=0;
        if(asignaciones!=null){
            for(Asignacion a:asignaciones){
                for(MovimientoEntrada me : a.getEntradas()){
                    total+=me.getCantidadEntera();
                }
                
                for(MovimientoSalida ms:a.getSalidas()){
                    total-=ms.getCantidadEntera();
                }
            }
        }

        return total;

    }

    public float getTotalFlotante(){
        float total=0;
        if(asignaciones!=null){
            for(Asignacion a:asignaciones){
                for(MovimientoEntrada me : a.getEntradas()){
                    total+=me.getCantidadFlotante();
                }

                for(MovimientoSalida ms:a.getSalidas()){
                    total-=ms.getCantidadFlotante();
                }
            }
        }

        return total;
    }

   
    // <editor-fold defaultstate="collapsed" desc="toString">
    @Override
    public String toString() {
        return getNombre();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="equals">
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoAlmacenado other = (ProductoAlmacenado) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="hashCode">
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    // </editor-fold>
    


    
}
