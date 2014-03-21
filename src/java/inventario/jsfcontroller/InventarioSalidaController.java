package inventario.jsfcontroller;

import javax.faces.context.FacesContext;
import inventario.jpacontroller.InventarioJpaController;
import inventario.modelo.MovimientoEntrada;
import inventario.modelo.MovimientoSalida;
import inventario.modelo.ProductoAlmacenado;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import jsf.util.JsfUtil;

public class InventarioSalidaController {

    // <editor-fold defaultstate="collapsed" desc="MovimientoSalida movimientoSalida">
    private MovimientoSalida movimientoSalida;

    public MovimientoSalida getMovimientoSalida() {
        return movimientoSalida;
    }

    public void setMovimientoSalida(MovimientoSalida movimientoSalida) {
        this.movimientoSalida = movimientoSalida;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="MovimientoEntrada entradaSeleccionada">
    private MovimientoEntrada entradaSeleccionada;

    public MovimientoEntrada getEntradaSeleccionada() {
        return entradaSeleccionada;
    }

    public void setEntradaSeleccionada(MovimientoEntrada entradaSeleccionada) {
        this.entradaSeleccionada = entradaSeleccionada;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ProductoAlmacenado productoSeleccionado">
    public ProductoAlmacenado getProductoSeleccionado() {
        return productoController.getProducto();
    }// </editor-fold>

    
    FacesContext facesContext;

    private InventarioJpaController inventarioJpa;

    private InventarioProductoController productoController;

    public InventarioSalidaController() {
        facesContext = FacesContext.getCurrentInstance();
        inventarioJpa = (InventarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioJpa");
        productoController=(InventarioProductoController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioProducto");
    }

    

    public String mostrarEntradasProducto() {

        productoController.setRedirectedAction("inventario-mostrar-entradas-producto");
        entradaSeleccionada=null;
        return productoController.prepairSeleccionar();
    }
 
    public String prepairRegistrarSalidaProducto() {
        if (entradaSeleccionada == null) {
            JsfUtil.addErrorMessage("Selecciona una entrada");
            return null;
        }

        movimientoSalida = new MovimientoSalida();

        movimientoSalida.setFechaSalida(new Date());

        if (entradaSeleccionada.getProductoAsignado() != null) {
            if (entradaSeleccionada.getProductoAsignado().getLineaInvestigacion() != null) {
                movimientoSalida.setParaLineaInvestigacion(entradaSeleccionada.getProductoAsignado().getLineaInvestigacion());
            } else if (entradaSeleccionada.getProductoAsignado().getEmpleado() != null) {
                movimientoSalida.setParaEmpleado(entradaSeleccionada.getProductoAsignado().getEmpleado());
            } else if (entradaSeleccionada.getProductoAsignado().getProyecto() != null) {
                movimientoSalida.setParaProyecto(entradaSeleccionada.getProductoAsignado().getProyecto());
            }

            movimientoSalida.setProductoAsignado(entradaSeleccionada.getProductoAsignado());
        }

        return "inventario-registrar-salida-producto";
    }

    public String registrarSalidaProducto() {

        try {
              inventarioJpa.create(movimientoSalida);
             if (movimientoSalida.getProductoAsignado() != null) {
                    movimientoSalida.getProductoAsignado().getSalidas().add(movimientoSalida);
                    inventarioJpa.edit(movimientoSalida.getProductoAsignado());
             }

            return "inventario-ver-movimientosalida";
        } catch (Exception e) {
            return "inventario";
        }
    }



    public Collection<MovimientoEntrada> getEntradasProductoSeleccionado(){
        if(getProductoSeleccionado()!=null)
            return inventarioJpa.findEntradasProducto(getProductoSeleccionado());
        else
            return Collections.EMPTY_LIST;
    }

    public Collection<MovimientoSalida> getUltimosMovimientosSalida(){
        return inventarioJpa.findUltimasSalidas(5);
    }
}
