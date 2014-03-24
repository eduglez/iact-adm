package compras.jsfcontroller;


import compras.jpacontroller.FacturaJpaController;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.PedidoJpaController;
import compras.modelo.DocumentoFactura;
import compras.modelo.Factura;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import compras.modelo.Pedido;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Vector;
import jsf.util.FacturaComparator;

public class FacturaController {

    private Factura factura;
   
    public FacturaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");
        facturaJpaController = (FacturaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "facturaJpa");
        ascendente = false;
        columnaFiltro = "numFactura";
    }
    private Pedido pedido = null;
    private PedidoJpaController jpaController = null;
    private FacturaJpaController facturaJpaController;


    private Pedido pedidoSeleccionado;

    public Pedido getPedidoSeleccionado() {
        return pedidoSeleccionado;
    }

    public void setPedidoSeleccionado(Pedido pedidoSeleccionado) {
        this.pedidoSeleccionado = pedidoSeleccionado;
    }

    

    public SelectItem[] getPedidoItemsAvailableSelectOne() {
        List<Pedido> pedidos=jpaController.findPedidoEntities("fecha",false,null);
        SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");

        int size = pedidos.size();
        SelectItem[] items = new SelectItem[size];
        int i = 0;
        for (Pedido x : pedidos) {
            items[i++] = new SelectItem(x, (x.getFecha()!=null?dateFormatter.format(x.getFecha()):"")+" "+x.getNumPedido()+" "+(x.getProveedor()!=null?x.getProveedor().getRazonSocial():""));
        }
        return items;
    }

    public SelectItem[] getFacturasPedidoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(facturaJpaController.findFacturaEntities(pedido.getNumPedido()), true);
    }

    private boolean ascendente;
    private String columnaFiltro;

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

    public String getColumnaFiltro() {
        return columnaFiltro;
    }

    public void setColumnaFiltro(String columnaFiltro) {
        this.columnaFiltro = columnaFiltro;
    }
   
    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura){
        this.factura=factura;
    }

    public String verFactura() {
        if (factura != null && factura.getId()!=null) {
            factura=facturaJpaController.findFactura(factura.getId());
            return "factura-ver";
        } else {
            return listSetup();
        }
    }

    public String salirSinGuardar(){

        return verFactura();
    }

    public String salirGuardando(){
        guardarCambios();
        return verFactura();
    }

    public String listSetup() {
        reset();
        return "facturas-listar";
    }

    public String createSetup() {
        reset();
        factura=new Factura();
        try {
            facturaJpaController.create(factura);
            JsfUtil.addSuccessMessage("La factura se ha almacenado correctamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return "factura-editar";
    }

    public String create() {
        try {
            facturaJpaController.create(factura);
            JsfUtil.addSuccessMessage("La factura se ha almacenado correctamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return editSetup();
    }

    
    public String editSetup() {
        if (factura == null) {
            return listSetup();
        }
        return "factura-editar";
    }

    public String destroy() {
        try {
            facturaJpaController.destroy(getFactura());
            JsfUtil.addSuccessMessage("La factura se ha borrado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.ensureAddErrorMessage(e, "Error al actualizar la base de datos.");
            return null;
        }
        return listSetup();
    }

    public void guardarCambios() {
        try {
            facturaJpaController.edit(factura);
            JsfUtil.addSuccessMessage("La factura se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            e.printStackTrace();
        }
    }

    public List<Factura> getFacturaItems() {
        if (columnaFiltro == null) {
            return facturaJpaController.findFacturaEntities();
        }else if(columnaFiltro.equals("pedidos")){
            List<Factura> facturas=facturaJpaController.findFacturaEntities();
            FacturaComparator comparator=new FacturaComparator(FacturaComparator.POR_PEDIDO_MAS_RECIENTE, ascendente);
            Collections.sort(facturas,comparator);
            return facturas;
        }
        return facturaJpaController.findFacturaEntities(columnaFiltro, ascendente);
    }

    private void reset() {
        factura = null;
    }

    public List<DocumentoFactura> getDocumentosFacturasAutomaticos() {
        java.io.File f = new File("/home/intranet/SCAN");
        File ficheros[] = f.listFiles();
        Vector<DocumentoFactura> fr = new Vector<DocumentoFactura>();
        DocumentoFactura dc;
        for (File fl : ficheros) {
            dc = new DocumentoFactura();
            dc.setFile(fl);
            fr.add(dc);
        }

        return fr;
    }


    public void asignarPedidoAFactura(){
        if(getPedidoSeleccionado()!=null){
            if(!getFactura().getPedidos().contains(getPedidoSeleccionado()))
                getFactura().getPedidos().add(getPedidoSeleccionado());

            if(!getPedidoSeleccionado().getFacturas().contains(getFactura()))
                getPedidoSeleccionado().getFacturas().add(getFactura());

        }
    }

    public void borrarPedidoDeFactura(){
        if(getPedidoSeleccionado()!=null){
            getFactura().getPedidos().remove(getPedidoSeleccionado());
        }
    }
    
}
