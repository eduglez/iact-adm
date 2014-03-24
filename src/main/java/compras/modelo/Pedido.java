package compras.modelo;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import personal.modelo.Empleado;
import proyectos.modelo.Proyecto;

@Entity
public class Pedido implements Serializable {

    public final static String PRESUPUESTO="Presupuesto";
    public final static String INFRAESTRUCTURA="Infraestructura";
    public final static String[] ASIGNACIONES={PRESUPUESTO, INFRAESTRUCTURA};

    //CAMPOS
    @Id
    private String numPedido;
    @Column(length = 400)
    private String notas;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaCancelacion;
    private String asignacion;
    private boolean borrado;

    
    //RELACIONES
    @ManyToOne
    private EstadoPedido estadoPedido;
    @ManyToOne
    private Proveedor proveedor;
    @ManyToOne
    private DatosFacturacion datosFacturacion;
    @ManyToOne
    private DatosEntrega datosEntrega;
    @ManyToOne
    private EntidadSolicitante entidad;
    @ManyToOne
    private Proyecto proyecto;
    @ManyToOne
    private Empleado solicitante;


    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.MERGE})
    private Collection<LineaPedido> lineas;
    @OneToMany(mappedBy = "pedido", cascade = {CascadeType.MERGE})
    private Collection<Albaran> albaranes;
    @ManyToMany(cascade = {CascadeType.MERGE})
    private Collection<Factura> facturas;


    //CONSTRUCTORES
    public Pedido() {
        this.facturas = new ArrayList<Factura>();
        this.albaranes = new ArrayList<Albaran>();
        this.lineas = new ArrayList<LineaPedido>();
    }

    //METODOS
    public boolean isCancelado() {
        if (getEstadoPedido() != null) {
            return getEstadoPedido().getNombre().equals("Cancelado");
        } else {
            return false;
        }
    }

    public Resource getOrdenPedido() {
        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            File reportfile = new File(Pedido.class.getResource("/reports/Compras/Listar/pedido.jasper").getPath());
            in = new FileInputStream(reportfile);
            JRDataSource r = new JRBeanCollectionDataSource(getLineas());
            JasperRunManager.runReportToPdfStream(in, outStream, getParametrosOrdenPedido(), r);
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    private ConcurrentHashMap getParametrosOrdenPedido() {
        ConcurrentHashMap h = new ConcurrentHashMap();
        h.put("FECHA_PEDIDO", getFecha());
        h.put("NUM_PEDIDO", getNumPedido());
        if (getEntidad().isProyecto()) {
            try {
                h.put("NOMBRE_PROYECTO", getProyecto().getReferencia());
            } catch (NullPointerException ex) {
                h.put("NOMBRE_PROYECTO", getEntidad().getNombre());
            }
            try {
                h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", getProyecto().getResponsable().getApellidos() + ", " + getProyecto().getResponsable().getNombre());
            } catch (NullPointerException ex) {
                h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", "-");
            }
        } else {
            h.put("NOMBRE_PROYECTO", getStringOrEmpty(getEntidad().getNombre()));
            h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", "-");

        }

        if (getSolicitante() != null) {
            h.put("NOMBRE_SOLICITANTE", getStringOrEmpty(getSolicitante().getNombre()) + " " + getStringOrEmpty(getSolicitante().getApellidos()));
        }
        if (getDatosEntrega() != null) {
            h.put("RAZON_SOCIAL_ENTREGA", getStringOrEmpty(getDatosEntrega().getRazonSocial()));
            h.put("DIRECCION_ENTREGA", getStringOrEmpty(getDatosEntrega().getDireccion()));
            h.put("TELEFONO_ENTREGA", getStringOrEmpty(getDatosEntrega().getTelefono()));
        }

        if (getDatosFacturacion() != null) {
            h.put("CIF", getStringOrEmpty(getDatosFacturacion().getCif()));
        }

        if (getProveedor() != null) {
            h.put("RAZON_SOCIAL_FACTURACION", getStringOrEmpty(getProveedor().getRazonSocial()));
            h.put("DIRECCION_FACTURACION", getStringOrEmpty(getProveedor().getDireccion()));
            h.put("TELEFONO_FACTURACION", getStringOrEmpty(getProveedor().getTelefono()));

            h.put("FAX_FACTURACION", getStringOrEmpty(getProveedor().getFax()));
            h.put("CIF_FACTUARCION", getStringOrEmpty(getProveedor().getCif()));
        }
        return h;


    }

    private String getStringOrEmpty(String s){
        return s!=null?s:"";
    }
    public long getBaseImponible() {
        long totalBaseImponible = 0;
        for (LineaPedido lp : getLineas()) {
            totalBaseImponible += lp.getCantidad() * lp.getPrecioUnitario();
        }
        return totalBaseImponible;
    }

    public float getBaseImponibleFloat() {
        return (float)getBaseImponible()/1000;
    }

    public long getIvaAplicado(){
        long totalIva = 0;
        for (LineaPedido lp : getLineas()) {
            totalIva += lp.getIvaAplicado();
        }
        return totalIva;
    }

    public float getIvaAplicadoFloat(){
        return (float)getIvaAplicado()/1000;
    }

    public long getTotal() {
        long totalBaseImponible = 0;
        for (LineaPedido lp : getLineas()) {
            totalBaseImponible += lp.getCantidad() * lp.getPrecioUnitario()+ lp.getIvaAplicado();
        }
        return totalBaseImponible;
    }

    public float getTotalFloat(){
        return (float)getTotal()/1000;
    }

    public boolean isPendienteAceptacion(){
        if(getEstadoPedido()!=null){
            return getEstadoPedido().getNombre().equals("Pendiente de aceptación");
        }else
            return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pedido other = (Pedido) obj;
        if ((this.numPedido == null) ? (other.numPedido != null) : !this.numPedido.equals(other.numPedido)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.numPedido != null ? this.numPedido.hashCode() : 0);
        return hash;
    }

    //ACCESORES
    public EntidadSolicitante getEntidad() {
        return entidad;
    }

    public void setEntidad(EntidadSolicitante entidad) {
        this.entidad = entidad;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(String numPedido) {
        this.numPedido = numPedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Date getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(Date fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public Collection<LineaPedido> getLineas() {
        return lineas;
    }

    public void setLineas(Collection<LineaPedido> lineas) {
        this.lineas = lineas;
    }

    public Collection<Albaran> getAlbaranes() {
        return albaranes;
    }

    public void setAlbaranes(Collection<Albaran> albaranes) {
        this.albaranes = albaranes;
    }

    public Collection<Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(Collection<Factura> facturas) {
        this.facturas = facturas;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public DatosEntrega getDatosEntrega() {
        return datosEntrega;
    }

    public void setDatosEntrega(DatosEntrega datosEntrega) {
        this.datosEntrega = datosEntrega;
    }

    public Empleado getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(Empleado solicitante) {
        this.solicitante = solicitante;
    }

    public String getId() {
        return getNumPedido();
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }

    public String getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(String asignacion) {
        this.asignacion = asignacion;
    }

    
}
