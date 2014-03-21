package inventario.modelo;

import compras.modelo.Pedido;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

@Entity
public class MovimientoEntrada implements Serializable {

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

    // <editor-fold defaultstate="collapsed" desc="fechaEntrada">
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEntrada;

    public Date getFechaEntrada() {
        return fechaEntrada;
    }

    public void setFechaEntrada(Date fechaEntrada) {
        this.fechaEntrada = fechaEntrada;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="productoAsignado (many MovimientoEntrada toOne Asignacion)">
    @ManyToOne
    private Asignacion productoAsignado;

    public Asignacion getProductoAsignado() {
        return productoAsignado;
    }

    public void setProductoAsignado(Asignacion productoAsignado) {
        this.productoAsignado = productoAsignado;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="pedido (many MovimientoEntrada toOne Pedido)">
    @ManyToOne
    private Pedido pedido;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cantidadEntera">
    private int cantidadEntera;

    public int getCantidadEntera() {
        return cantidadEntera;
    }

    public void setCantidadEntera(int cantidadEntera) {
        this.cantidadEntera = cantidadEntera;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="cantidadFlotante">
    private int cantidadFlotante;

    public int getCantidadFlotante() {
        return cantidadFlotante;
    }

    public void setCantidadFlotante(int cantidadFlotante) {
        this.cantidadFlotante = cantidadFlotante;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="subCantidadEntera">
    private int subCantidadEntera;

    public int getSubCantidadEntera() {
        return subCantidadEntera;
    }

    public void setSubCantidadEntera(int subCantidadEntera) {
        this.subCantidadEntera = subCantidadEntera;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="subCantidadFlotante">
    private int subCantidadFlotante;

    public int getSubCantidadFlotante() {
        return subCantidadFlotante;
    }

    public void setSubCantidadFlotante(int subCantidadFlotante) {
        this.subCantidadFlotante = subCantidadFlotante;
    }// </editor-fold>
  

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MovimientoEntrada other = (MovimientoEntrada) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormatter= new SimpleDateFormat("dd/MM/yyyy");
        return dateFormatter.format(getFechaEntrada())+" - "+getProductoAsignado();
    }


}
