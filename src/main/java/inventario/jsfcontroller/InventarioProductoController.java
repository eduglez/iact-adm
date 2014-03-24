package inventario.jsfcontroller;

import javax.faces.context.FacesContext;
import inventario.jpacontroller.InventarioJpaController;
import inventario.modelo.MovimientoEntrada;
import inventario.modelo.MovimientoSalida;
import inventario.modelo.ProductoAlmacenado;
import java.util.Collection;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;

public class InventarioProductoController {

    // <editor-fold defaultstate="collapsed" desc="ProductoAlmacenado producto">
    private ProductoAlmacenado producto;

    public ProductoAlmacenado getProducto() {
        return producto;
    }

    public void setProducto(ProductoAlmacenado producto) {
        this.producto = producto;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String entradaCodigoBarras">
    private String entradaCodigoBarras;

    public String getEntradaCodigoBarras() {
        return entradaCodigoBarras;
    }

    public void setEntradaCodigoBarras(String entradaCodigoBarras) {
        if (entradaCodigoBarras != null) {
            this.entradaCodigoBarras = entradaCodigoBarras;
        } else {
            this.entradaCodigoBarras = "";
        }
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="boolean productoEncontrado">
    private boolean productoEncontrado;

    public boolean isProductoEncontrado() {
        return productoEncontrado;
    }

    public void setProductoEncontrado(boolean productoEncontrado) {
        this.productoEncontrado = productoEncontrado;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sortColEntradas">
    private String sortColEntradas;

    public String getSortColEntradas() {
        return sortColEntradas;
    }

    public void setSortColEntradas(String sortColEntradas) {
        this.sortColEntradas = sortColEntradas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sortAscEntradas">
    private String sortAscEntradas;

    public String getSortAscEntradas() {
        return sortAscEntradas;
    }

    public void setSortAscEntradas(String sortAscEntradas) {
        this.sortAscEntradas = sortAscEntradas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sortColSalidas">
    private String sortColSalidas;

    public String getSortColSalidas() {
        return sortColSalidas;
    }

    public void setSortColSalidas(String sortColSalidas) {
        this.sortColSalidas = sortColSalidas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String sortAscSalidas">
    private String sortAscSalidas;

    public String getSortAscSalidas() {
        return sortAscSalidas;
    }

    public void setSortAscSalidas(String sortAscSalidas) {
        this.sortAscSalidas = sortAscSalidas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="String redirectedAction">
    private String redirectedAction;

    public String getRedirectedAction() {
        return redirectedAction;
    }

    public void setRedirectedAction(String redirectedAction) {
        this.redirectedAction = redirectedAction;
    }// </editor-fold>




    FacesContext facesContext;
    private InventarioJpaController inventarioJpa;

    public InventarioProductoController() {
        facesContext = FacesContext.getCurrentInstance();
        entradaAnterior = "";
        inventarioJpa = (InventarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioJpa");
        entradaCodigoBarras = "";


    }


    
    private String entradaAnterior;
    public String comprobarEntradaEAN() {
        if (!entradaCodigoBarras.equals(entradaAnterior)) {
            entradaAnterior = entradaCodigoBarras;

            producto = inventarioJpa.findProductoPorEAN(this.entradaCodigoBarras);

            if(producto!=null)
                productoEncontrado=true;
            else
                productoEncontrado=false;
        }

        return null;
    }


    public String verProducto() {
        this.redirectedAction="inventario-ver-producto";
        return prepairSeleccionar();
    }


    public String prepairSeleccionar(){
        producto=null;
        productoEncontrado=true;
        return "inventario-seleccionar-producto";
    }
    public String seleccionar(){
        return redirectedAction;
    }


    public String prepairGuardarNuevoProducto(){
        producto = new ProductoAlmacenado();
        producto.setEan(entradaCodigoBarras);
        return "inventario-nuevo-producto";
    }
    
    public String guardarNuevoProducto() {
        try {
            inventarioJpa.create(producto);
        } catch (Exception ex) {
            return redirectedAction;
        }

        return redirectedAction;
    }




    

    public SelectItem[] getProductoInventarioItemsSelectOne() {
        return JsfUtil.getSelectItems(inventarioJpa.findProductos(), true);
    }

    public Collection<MovimientoEntrada> getEntradasProducto() {
        return inventarioJpa.findEntradasProducto(producto);
    }

    public Collection<MovimientoSalida> getSalidasProducto() {
        return inventarioJpa.findSalidasProducto(producto);
    }

    public Collection<ProductoAlmacenado> getProductos(){
        return inventarioJpa.findProductos();
    }
}
