package compras.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Factura implements Serializable {

    //CAMPOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long importe;
    private String numFactura;


    //RELACIONES
//    @ManyToOne
//    private Pedido pedido;
    @OneToMany(mappedBy="factura")
    private Collection<Albaran> albaranes;
    
    @OneToOne(mappedBy = "factura", cascade = CascadeType.ALL)
    private DocumentoFactura documento;

    //ATRIBUTOS NO PERSISTENTES
    @Transient
    private boolean marcaBorrado;
    @ManyToMany(mappedBy="facturas",cascade = {CascadeType.MERGE})
    private Collection<Pedido> pedidos;
    
    //CONSTRUCTORES
    public Factura() {
        marcaBorrado = false;
        albaranes=new ArrayList<Albaran>();
        pedidos=new ArrayList<Pedido>();
    }

    //METODOS
    @Override
    public String toString() {
        return getNumFactura();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Factura other = (Factura) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }


    public long getTotal(){
        if(getPedidos().isEmpty())
            if(getPedido()!=null)
                return getPedido().getTotal();
            else
                return 0;

        long total=0;
        for(Pedido p: getPedidos())
            total+=p.getTotal();

        return total;
    }

    public long getBaseImponible(){
        if(getPedidos().isEmpty())
            if(getPedido()!=null)
                return getPedido().getBaseImponible();
            else
                return 0;

        long total=0;
        for(Pedido p: getPedidos())
            total+=p.getBaseImponible();

        return total;
    }

    public String getNumeroPedidos(){
        if(getPedidos().isEmpty()){
            if(getPedido()!=null)
                return getPedido().getNumPedido();
            else
                return "";
        }

        String numPedidos="";
        for(Pedido p: getPedidos()){
            numPedidos+=p.getNumPedido()+"; ";
        }
        if(!numPedidos.isEmpty() && numPedidos.length()>2){
            numPedidos=numPedidos.substring(0,numPedidos.length()-2);
        }
        return numPedidos;
    }

    public Pedido getPedidoMasReciente(){
        if(getPedidos().isEmpty()){
            return getPedido();
        }

        Pedido pedidoMasReciente=null;

        for(Pedido p: getPedidos()){
            if(pedidoMasReciente==null)
                pedidoMasReciente=p;
            else{
                if(pedidoMasReciente.getFecha().before(p.getFecha()))
                    pedidoMasReciente=p;
            }
        }

        return pedidoMasReciente;
    }


    //ACCESORES
    public boolean isMarcaBorrado() {
        return marcaBorrado;
    }

    public void setMarcaBorrado(boolean marcaBorrado) {
        this.marcaBorrado = marcaBorrado;
    }

    public DocumentoFactura getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoFactura documento) {
        this.documento = documento;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public String getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(String numFactura) {
        this.numFactura = numFactura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return null;
    }

    public void setPedido(Pedido pedido) {
        ;
    }

    public Collection<Albaran> getAlbaranes() {
        return albaranes;
    }

    public void setAlbaranes(Collection<Albaran> albaranes) {
        this.albaranes = albaranes;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Collection<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

}
