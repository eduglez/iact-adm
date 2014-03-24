package compras.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import javax.persistence.Transient;
@Entity
public class LineaPedido implements Serializable {

    //CAMPOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int cantidad;

    @Column(length=400)
    private String descripcionAlternativa;

    private long precioUnitario;

    private long ivaAplicado;

    private String numeroInventario;


    //RELACIONES
    @ManyToOne
    private Pedido pedido;

    @ManyToOne
    private Producto producto;

    
    //ATRIBUTOS NO PERSISTENTES
    @Transient
    private boolean marcaBorrado;


    //CONSTRUCTORES

    public LineaPedido(){
        marcaBorrado=false;
    }

    
    //METODOS

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LineaPedido other = (LineaPedido) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    

    //ACCESORES

    public long getSubtotal(){
        return precioUnitario*cantidad+ivaAplicado;
    }

    public float getSubtotalFloat(){
        return (float)getSubtotal()/1000;
    }

    
    public String getDescripcionAlternativa() {
        return descripcionAlternativa;
    }

    public void setDescripcionAlternativa(String descripcionAlternativa) {
        this.descripcionAlternativa = descripcionAlternativa;
    }
    
    public int getCantidad() {
        return cantidad;
    }

  
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

   
    public Producto getProducto() {
        return producto;
    }

    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

 
    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

 
    public Pedido getPedido() {
        return pedido;
    }


    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public long getIvaAplicado() {
        return ivaAplicado;
    }

    public float getIvaAplicadoFloat(){
        return (float)getIvaAplicado()/1000;
    }

    public void setIvaAplicado(long ivaAplicado) {
        this.ivaAplicado = ivaAplicado;
    }

    public String getNumeroInventario() {
        return numeroInventario;
    }

    public void setNumeroInventario(String numeroInventario) {
        this.numeroInventario = numeroInventario;
    }

    public long getPrecioUnitario() {
        return precioUnitario;
    }

    public float getPrecioUnitarioFloat(){
        return (float)getPrecioUnitario()/1000;
    }

    public void setPrecioUnitario(long precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public boolean isMarcaBorrado() {
        return marcaBorrado;
    }

    public void setMarcaBorrado(boolean marcaBorrado) {
        this.marcaBorrado = marcaBorrado;
    }

    
}
