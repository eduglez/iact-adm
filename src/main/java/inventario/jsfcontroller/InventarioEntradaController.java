package inventario.jsfcontroller;

import javax.faces.context.FacesContext;
import inventario.jpacontroller.InventarioJpaController;
import inventario.modelo.Asignacion;
import inventario.modelo.MovimientoEntrada;
import inventario.modelo.ProductoAlmacenado;
import java.util.Collection;
import java.util.Date;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;

public class InventarioEntradaController {

    // <editor-fold defaultstate="collapsed" desc="MovimientoEntada movimientoEntrada">
    private MovimientoEntrada movimientoEntrada;

    public MovimientoEntrada getMovimientoEntrada() {
        return movimientoEntrada;
    }

    public void setMovimientoEntrada(MovimientoEntrada movimientoEntrada) {
        this.movimientoEntrada = movimientoEntrada;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Asignacion asignacion">
    private Asignacion asignacion;

    public Asignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(Asignacion asignacion) {
        this.asignacion = asignacion;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ProductoAlmacenado productoSeleccionado">
    public ProductoAlmacenado getProductoSeleccionado() {
        return productoController.getProducto();
    }// </editor-fold>

    private FacesContext facesContext;

    private InventarioJpaController inventarioJpa;

    private InventarioProductoController productoController;

    public InventarioEntradaController() {
        facesContext = FacesContext.getCurrentInstance();
        inventarioJpa = (InventarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioJpa");
        productoController=(InventarioProductoController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioProducto");
    }



    public String prepairRegistrarEntradaProducto() {

        productoController.setRedirectedAction("inventario-registrar-entrada-producto");

        movimientoEntrada = new MovimientoEntrada();

        asignacion = new Asignacion();

        movimientoEntrada.setFechaEntrada(new Date());

        return productoController.prepairSeleccionar();
    }

    public String registrarEntradaProducto() {
        try {
            asignacion.setProducto(getProductoSeleccionado());

            getProductoSeleccionado().getAsignaciones().add(asignacion);

            Asignacion asignacionPersistida= inventarioJpa.findAsignacion(asignacion);

            if(asignacionPersistida!=null){
                asignacion=asignacionPersistida;
            }else{
                inventarioJpa.create(asignacion);
            }

            movimientoEntrada.setProductoAsignado(asignacion);

            inventarioJpa.create(movimientoEntrada);

            asignacion.getEntradas().add(movimientoEntrada);

            inventarioJpa.edit(asignacion);

            //TODO: Cambiar a ver el movimiento de entrada
            return "inventario-ver-movimientoentrada";

        } catch (Exception e) {
            e.printStackTrace();
            return "inventario";
        }

    }

    public SelectItem[] getTipoCantidadSelectItems(){
        return JsfUtil.getSelectItems(inventarioJpa.findTiposCantidad(), true);
    }

    public Collection<MovimientoEntrada> getUltimosMovimientosEntrada(){
        return inventarioJpa.findUltimasEntradas(5);
    }

    
}
