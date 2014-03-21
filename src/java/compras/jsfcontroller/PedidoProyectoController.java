package compras.jsfcontroller;

import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import compras.jpacontroller.DatosEntregaJpaController;
import compras.jpacontroller.DatosFacturacionJpaController;
import compras.jpacontroller.EntidadSolicitanteJpaController;
import compras.jpacontroller.EstadoPedidoJpaController;
import compras.jpacontroller.FacturaJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.PedidoJpaController;
import compras.jpacontroller.ProductoJpaController;
import compras.modelo.EntidadSolicitante;
import compras.modelo.LineaPedido;
import jpa.exceptions.RollbackFailureException;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import compras.modelo.Pedido;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import proyectos.jpacontroller.ProyectoJpaController;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import jsf.controllers.LoginController;
import personal.jpacontroller.EmpleadoJpaController;


public class PedidoProyectoController {

    private LoginController loginController;
    private EmpleadoJpaController empleadoJpaController;
    private EstadoPedidoJpaController estadoPedidosJpaController;
    private final ProductoJpaController productosJpaController;
    private final DatosEntregaJpaController datosEntregaJpaController;
    private final DatosFacturacionJpaController datosFacturacionJpaController;
    private final EntidadSolicitanteJpaController entidadSolicitanteJpaController;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }



    public PedidoProyectoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");
        facturaJpaController= (FacturaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "facturaJpa");
        proyectoJpaController=(ProyectoJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");
        loginController=(LoginController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(),null,"login");
        empleadoJpaController=(EmpleadoJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "empleadoJpa");
        estadoPedidosJpaController=(EstadoPedidoJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoPedidoJpa");
        productosJpaController=(ProductoJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "productoJpa");
        datosEntregaJpaController=(DatosEntregaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosEntregaJpa");
        datosFacturacionJpaController = (DatosFacturacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosFacturacionJpa");
        entidadSolicitanteJpaController = (EntidadSolicitanteJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entidadSolicitanteJpa");
        anioFiltro=Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        ascendente=true;
        columnaFiltro="fecha";
    }
    private Pedido pedido = null;
    private PedidoJpaController jpaController = null;
    private LineaPedido lineaPedidoSeleccionada;
    private ProyectoJpaController proyectoJpaController;
    private FacturaJpaController facturaJpaController;

  public String getNombreFicheroOrdenPedido() {
        return "OrdenPedido_" + getPedido().getNumPedido() + ".pdf";
    }

    public Resource getOrdenPedido() {
        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            File reportfile = new File(PedidoController.class.getResource("/reports/Compras/Listar/pedido.jasper").getPath());
            in = new FileInputStream(reportfile);
            JRDataSource r = new JRBeanCollectionDataSource(pedido.getLineas());
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
        h.put("FECHA_PEDIDO", pedido.getFecha());
        h.put("NUM_PEDIDO", pedido.getNumPedido());
        if (pedido.getEntidad() != null) {
            if (pedido.getEntidad().isProyecto()) {
                try {
                    h.put("NOMBRE_PROYECTO", pedido.getProyecto().getReferencia());
                } catch (NullPointerException ex) {
                    h.put("NOMBRE_PROYECTO", pedido.getEntidad().getNombre());
                }
                try {
                    h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", pedido.getProyecto().getResponsable().getEmpleadoIACT().getApellidos() + ", " + pedido.getProyecto().getResponsable().getEmpleadoIACT().getNombre());
                } catch (NullPointerException ex) {
                    h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", "-");
                }
            } else {
                h.put("NOMBRE_PROYECTO", pedido.getEntidad().getNombre());
                h.put("NOMBRE_INVESTIGADOR_PRINCIPAL", "-");

            }
        }

        if (pedido.getSolicitante() != null) {
            h.put("NOMBRE_SOLICITANTE", pedido.getSolicitante().getNombre() + " " + pedido.getSolicitante().getApellidos());
        }

        if (pedido.getDatosEntrega() != null) {
            h.put("RAZON_SOCIAL_ENTREGA", pedido.getDatosEntrega().getRazonSocial());
            h.put("DIRECCION_ENTREGA", pedido.getDatosEntrega().getDireccion());
            h.put("TELEFONO_ENTREGA", pedido.getDatosEntrega().getTelefono());
        }

        if (pedido.getProveedor() != null) {
            h.put("RAZON_SOCIAL_FACTURACION", pedido.getProveedor().getRazonSocial());
            h.put("DIRECCION_FACTURACION", pedido.getProveedor().getDireccion());
            h.put("TELEFONO_FACTURACION", pedido.getProveedor().getTelefono());

            h.put("FAX_FACTURACION", pedido.getProveedor().getFax());
            h.put("CIF_FACTUARCION", pedido.getProveedor().getCif());
        }

        if (pedido.getDatosFacturacion() != null) {
            h.put("CIF", pedido.getDatosFacturacion().getCif());
        }
        return h;
    }

    public SelectItem[] getPedidoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findPedidoEntities(), false);
    }

    public SelectItem[] getPedidoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findPedidoEntities(), true);
    }

     public SelectItem[] getFacturasPedidoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(facturaJpaController.findFacturaEntities(pedido.getNumPedido()), true);
    }

     public SelectItem[] getAnioFiltroItemsAvailableSelectOne(){
         return JsfUtil.getSelectItems(jpaController.getRangoFecha(), true);
     }

      public SelectItem[] getProyectosVigentesItemsAvailableSelectOne(){
         return JsfUtil.getSelectItems(proyectoJpaController.findProyectoVigentesEntities(loginController.getDni()), true);
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
     private String anioFiltro;

    public String getAnioFiltro() {
        return anioFiltro;
    }

    public void setAnioFiltro(String anioFiltro) {
        this.anioFiltro = anioFiltro;
    }
    public Pedido getPedido() {
        return pedido;
    }

    public String listSetup() {
        reset();
        return "compras-listar";
    }

    public String createSetup() {
        reset();
        pedido = new Pedido();
        pedido.setFecha(new Date());
        pedido.setNumPedido(jpaController.getSiguienteNumPedido());
        pedido.setSolicitante(empleadoJpaController.findEmpleado(loginController.getDni()));
        pedido.setEstadoPedido(estadoPedidosJpaController.findEstadoPedido("Pendiente de aceptación"));
        pedido.setDatosEntrega(datosEntregaJpaController.findDatosEntrega("INSTITUTO ANDALUZ DE CIENCIAS DE LA TIERRA (ARMILLA)"));
        pedido.setDatosFacturacion(datosFacturacionJpaController.findDatosFacturacion("CSIC"));
        pedido.setEntidad(entidadSolicitanteJpaController.findEntidadSolicitante("Proyecto"));
        if(pedido.getSolicitante()!=null){
            try {
                jpaController.create(getPedido());
                return "realizar-pedido-proyecto";
            } catch (Exception e) {
            
                JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
                return null;
        }
            
        }else
        {
            JsfUtil.addErrorMessage("No puede realizar pedidos por que no está registrado en el sistema");
            return null;
        }
    }

    public String create() {
        try {
            jpaController.create(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha almacenado correctamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return "compras-listar";
    }

    public String salirSinGuardar(){

        return verPedido();
    }

    public String salirGuardando(){
        guardarCambios();
        return verPedido();
    }

    public String verPedido() {
        if (pedido != null&&pedido.getId()!=null) {
            pedido=jpaController.findPedido(pedido.getId());
            return "pedido-ver";
        } else {
            return detailSetup();
        }
    }

    public String editSetup() {
        if(getPedido()!=null && (getPedido().getEstadoPedido().getNombre().equals("Pendiente de aceptación"))){
            return "pedido-editar";
        }else{
            JsfUtil.addSuccessMessage("El pedido no se puede borrar o editar una vez tramitado");
            return null;
        }
    }



    public void guardarCambios(){
         try {
                         for (LineaPedido lp : getPedido().getLineas()) {
                if (lp.isMarcaBorrado()) {
                    lp.setPedido(null);
                }
            }
            jpaController.edit(getPedido());


            Iterator<LineaPedido> itLineasPedido = getPedido().getLineas().iterator();
            while (itLineasPedido.hasNext()) {
                if (itLineasPedido.next().isMarcaBorrado()) {
                    itLineasPedido.remove();
                }
            }

            jpaController.edit(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
        }
    }

    public String edit() {

        if (getPedido() == null) {

            JsfUtil.addErrorMessage("No se puede editar el pedido. Seleccione otra vez.");

            return null;
        }
        try {
            jpaController.edit(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha editado correctamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return detailSetup();
    }

    public String destroy() {
        try {
            if(getPedido().getEstadoPedido()==null||getPedido().getEstadoPedido().getNombre().equals("Pendiente de aceptación")){
                getPedido().setBorrado(true);
                jpaController.edit(getPedido());
                JsfUtil.addSuccessMessage("El pedido se ha borrado satisfactoriamente.");
            }else{
                JsfUtil.addSuccessMessage("El pedido no se puede borrar o editar una vez tramitado");
                return null;
            }
            
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al actualizar la base de datos.");
            return null;
        }
        return listSetup();
    }

    public List<Pedido> getPedidoItems() {
      return jpaController.findPedidoEntities(loginController.getDni(),columnaFiltro, ascendente, anioFiltro);
    }

    private void reset() {
        pedido = null;
    }


    public void insertarLineaPedido() {
        LineaPedido linea = new LineaPedido();
        linea.setCantidad(1);
        linea.setPedido(pedido);
        pedido.getLineas().add(linea);
        linea.setProducto(productosJpaController.findProducto("Fungible"));
        
        try {
            jpaController.createLineaPedido(linea);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PedidoProyectoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PedidoProyectoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarLineaPedido() {
        getLineaPedidoSeleccionada().setMarcaBorrado(!getLineaPedidoSeleccionada().isMarcaBorrado());
    }



    public void borrarLineaPedido() {
        getLineaPedidoSeleccionada().setPedido(null);
        pedido.getLineas().remove(getLineaPedidoSeleccionada());
    }



    public LineaPedido getLineaPedidoSeleccionada() {
        return lineaPedidoSeleccionada;
    }

    public void setLineaPedidoSeleccionada(LineaPedido lineaPedidoSeleccionada) {
        this.lineaPedidoSeleccionada = lineaPedidoSeleccionada;
    }



  

}
