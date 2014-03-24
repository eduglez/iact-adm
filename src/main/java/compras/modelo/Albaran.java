package compras.modelo;

import com.icesoft.faces.context.Resource;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.Transient;

@Entity
public class Albaran implements Serializable {

    //CAMPOS
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String numAlbaran;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaLlegada;

    //RELACIONES
    @ManyToOne
    private Pedido pedido;
    @ManyToOne
    private Factura factura;
    @OneToOne(mappedBy = "albaran", cascade = CascadeType.ALL)
    private DocumentoAlbaran documento;

    //ATRIBUTOS NO PERSISTENTES
    @Transient
    private boolean marcaBorrado;


    
    //METODOS
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Albaran other = (Albaran) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    //ACCESORES
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public DocumentoAlbaran getDocumento() {
        return documento;
    }

    public void setDocumento(DocumentoAlbaran documento) {
        this.documento = documento;
    }

    public String getNumAlbaran() {
        return numAlbaran;
    }

    public void setNumAlbaran(String numAlbaran) {
        this.numAlbaran = numAlbaran;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
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

    public boolean isMarcaBorrado() {
        return marcaBorrado;
    }

    public void setMarcaBorrado(boolean marcaBorrado) {
        this.marcaBorrado = marcaBorrado;
    }
}
