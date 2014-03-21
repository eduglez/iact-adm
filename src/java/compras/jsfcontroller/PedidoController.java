package compras.jsfcontroller;

import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import compras.jpacontroller.DatosEntregaJpaController;
import compras.jpacontroller.DatosFacturacionJpaController;
import compras.jpacontroller.EstadoPedidoJpaController;
import compras.jpacontroller.FacturaJpaController;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.PedidoJpaController;
import compras.modelo.Albaran;
import compras.modelo.DocumentoAlbaran;
import compras.modelo.DocumentoFactura;
import compras.modelo.Factura;
import compras.modelo.LineaPedido;
import jpa.exceptions.RollbackFailureException;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import compras.modelo.Pedido;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import proyectos.jpacontroller.ProyectoJpaController;
import proyectos.modelo.Proyecto;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import ooxmlreports.Helper;
import org.apache.velocity.VelocityContext;
import personal.jsfcontroller.FiltrosPersonal;
import proyectos.modelo.LineaInvestigacion;

public class PedidoController {

    private Factura facturaSeleccionada;
    private Albaran albaranSeleccionado;
    private final EstadoPedidoJpaController estadoPedidoJpaController;
    private PedidoConverter pedidoConverter;
    private final DatosFacturacionJpaController datosFacturacionJpaController;
    private final DatosEntregaJpaController datosEntregaJpaController;

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");
        facturaJpaController = (FacturaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "facturaJpa");
        proyectoJpaController = (ProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");
        datosEntregaJpaController=(DatosEntregaJpaController)facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosEntregaJpa");
        datosFacturacionJpaController = (DatosFacturacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosFacturacionJpa");
        anioFiltro = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
        ascendente = false;
        columnaFiltro = "fecha";
        pedidoConverter = new PedidoConverter();
        estadoPedidoJpaController = (EstadoPedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoPedidoJpa");

    }
    private Pedido pedido = null;
    private PedidoJpaController jpaController = null;
    private LineaPedido lineaPedidoSeleccionada;
    private ProyectoJpaController proyectoJpaController;
    private FacturaJpaController facturaJpaController;
    private LineaInvestigacion lineaInvestigacionFiltro;



    // <editor-fold defaultstate="collapsed" desc="JasperReports">

    //<ice:outputResource mimeType="application/pdf" resource="#{pedidos.ordenPedido}" label="Ver orden de pedido" type="link" filename="#{pedidos.nombreFicheroOrdenPedido}" attachment="false" />
    //<td><ice:outputResource mimeType="application/pdf" resource="#{pedidos.pedidosPDF}" label="PDF" type="link" fileName="ListadoCompras.pdf" attachment="true"/></td>
    //<td><ice:outputResource mimeType="application/vnd.ms-excel" resource="#{pedidos.pedidosExcel}" label="Excel" type="link" fileName="ListadoCompras.xls" attachment="true"/></td>
    
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
            h.put("NOMBRE_SOLICITANTE", getStringOrEmpty(pedido.getSolicitante().getNombre()) + " " + getStringOrEmpty(pedido.getSolicitante().getApellidos()));
        }

        if (pedido.getDatosEntrega() != null) {
            h.put("RAZON_SOCIAL_ENTREGA", getStringOrEmpty(pedido.getDatosEntrega().getRazonSocial()));
            h.put("DIRECCION_ENTREGA", getStringOrEmpty(pedido.getDatosEntrega().getDireccion()));
            h.put("TELEFONO_ENTREGA", getStringOrEmpty(pedido.getDatosEntrega().getTelefono()));
        }

        if (pedido.getProveedor() != null) {
            h.put("RAZON_SOCIAL_FACTURACION", getStringOrEmpty(pedido.getProveedor().getRazonSocial()));
            h.put("DIRECCION_FACTURACION", getStringOrEmpty(pedido.getProveedor().getDireccion()));
            h.put("TELEFONO_FACTURACION", getStringOrEmpty(pedido.getProveedor().getTelefono()));

            h.put("FAX_FACTURACION", getStringOrEmpty(pedido.getProveedor().getFax()));
            h.put("CIF_FACTUARCION", getStringOrEmpty(pedido.getProveedor().getCif()));
        }

        if (pedido.getDatosFacturacion() != null) {
            h.put("CIF", getStringOrEmpty(pedido.getDatosFacturacion().getCif()));
        }
        return h;
    }

    private String getStringOrEmpty(String s){
        return s!=null?s:"";
    }

    public Resource getPedidosExcel() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(PedidoController.class.getResource("/reports/Compras/Consultar/excelListadoCompletoCompras.jasper").getPath());
            JRDataSource r = new JRBeanCollectionDataSource(getPedidoItems());

            JasperPrint print = JasperFillManager.fillReport(in, new ConcurrentHashMap(), r);

            JRXlsExporter ex = new JRXlsExporter();
            ex.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            ex.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outStream);
            ex.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            ex.exportReport();
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public Resource getPedidosPDF() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {

            in = new FileInputStream(PedidoController.class.getResource("/reports/Compras/Consultar/informeListadoSimpleCompras.jasper").getPath());

            JRDataSource r = new JRBeanCollectionDataSource(getPedidoItems());

            JasperRunManager.runReportToPdfStream(in, outStream, new ConcurrentHashMap(), r);
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

   

    // </editor-fold>

    
    public Resource getOrdenPedidoWordProcessingML(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        VelocityContext context = new VelocityContext();
        context.put("fechaPedido", sdf.format(getPedido().getFecha()));

        context.put("numPedido", getPedido().getNumPedido());

        if (pedido.getEntidad() != null) {
            if (pedido.getEntidad().isProyecto()) {
                try {
                    context.put("nombre_proyecto", pedido.getProyecto().getReferencia());
                } catch (NullPointerException ex) {
                    context.put("nombre_proyecto", pedido.getEntidad().getNombre());
                }
                try {
                    context.put("nombre_ip", pedido.getProyecto().getResponsable().getEmpleadoIACT().getApellidos() + ", " + pedido.getProyecto().getResponsable().getEmpleadoIACT().getNombre());
                } catch (NullPointerException ex) {
                    context.put("nombre_ip", "-");
                }
            } else {
                context.put("nombre_proyecto", pedido.getEntidad().getNombre());
                context.put("nombre_ip", "-");

            }
        }
        
        
         if (pedido.getSolicitante() != null) {
            context.put("nombre_solicitante", pedido.getSolicitante().getNombre() + " " + pedido.getSolicitante().getApellidos());
        }else{
            context.put("nombre_solicitante","-");
        }

        if (pedido.getDatosEntrega() != null) {
            context.put("razonSocialEntrega", pedido.getDatosEntrega().getRazonSocial()!=null?pedido.getDatosEntrega().getRazonSocial():"-");
            context.put("direccionEntrega", pedido.getDatosEntrega().getDireccion()!=null?pedido.getDatosEntrega().getDireccion():"-");
            context.put("telefonoEntrega", pedido.getDatosEntrega().getTelefono()!=null?pedido.getDatosEntrega().getTelefono():"-");
        }

        if (pedido.getProveedor() != null) {
            context.put("razonSocialFacturacion", pedido.getProveedor().getRazonSocial()!=null?pedido.getProveedor().getRazonSocial():"-");
            context.put("direccionFacturacion", pedido.getProveedor().getDireccion()!=null?pedido.getProveedor().getDireccion():"-");
            context.put("telefonoFacturacion", pedido.getProveedor().getTelefono()!=null?pedido.getProveedor().getTelefono():"-");

            context.put("faxFacturacion", pedido.getProveedor().getFax()!=null
                                            ?pedido.getProveedor().getFax()
                                            :"-");
            
            context.put("cifFacturacion", pedido.getProveedor().getCif()!=null?pedido.getProveedor().getCif():"-");
        }

        if (pedido.getDatosFacturacion() != null) {
            context.put("CIF", pedido.getDatosFacturacion().getCif()!=null?pedido.getDatosFacturacion().getCif():"-");
        }

        if(pedido.getLineas()!=null){
            context.put("lineas", pedido.getLineas().iterator());
        }



        return new ByteArrayResource(Helper.openPattern(PedidoController.class.getResource("/reports/Compras/Listar/OrdenPedido.docx").getPath(), context).toByteArray());
    }

    public String getNombreFicheroOrdenPedidoWordProcessingML() {
        return "OrdenPedido_" + getPedido().getNumPedido() + ".docx";
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

    public SelectItem[] getAnioFiltroItemsAvailableSelectOne() {
//         List<String> rangoAnios=jpaController.getRangoFecha();
//         rangoAnios.add("TODOS");
        return JsfUtil.getSelectItems(jpaController.getRangoFecha(), true);
    }


    public SelectItem[] getAsignacionItemsAvailableSelectOne(){
        return JsfUtil.getSelectItems(Arrays.asList(Pedido.ASIGNACIONES), true);
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

    public LineaInvestigacion getLineaInvestigacionFiltro() {
        return lineaInvestigacionFiltro;
    }

    public void setLineaInvestigacionFiltro(LineaInvestigacion lineaInvestigacionFiltro) {
        this.lineaInvestigacionFiltro = lineaInvestigacionFiltro;
    }

    

    public Pedido getPedido() {
        Pedido pedidoSolicitadoPorURL = (Pedido) JsfUtil.getObjectFromRequestParameter("id", pedidoConverter, null);
        if (pedidoSolicitadoPorURL != null) {
            pedido = pedidoSolicitadoPorURL;
        }
        return pedido;
    }

    public String verPedido() {
        if (pedido != null && pedido.getId()!=null) {
            pedido=jpaController.findPedido(pedido.getId());
            return "pedido-ver";
        } else {
            return listSetup();
        }
    }

    public String salirSinGuardar(){

        return verPedido();
    }

    public String salirGuardando(){
        guardarCambios();
        return verPedido();
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
        pedido.setEstadoPedido(estadoPedidoJpaController.findEstadoPedido("En proceso"));
        pedido.setDatosFacturacion(datosFacturacionJpaController.findDatosFacturacion("CSIC"));
        pedido.setDatosEntrega(datosEntregaJpaController.findDatosEntrega("INSTITUTO ANDALUZ DE CIENCIAS DE LA TIERRA (ARMILLA)"));
        try {
            jpaController.create(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha almacenado correctamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return "pedido-editar";
    }

    public String create() {
        try {
            jpaController.create(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha almacenado correctamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            return null;
        }
        return editSetup();
    }

    public String editSetup() {
        if (pedido == null) {
            return listSetup();
        }

        return "pedido-editar";
    }

    public void guardarCambios() {
        try {
            for (LineaPedido lp : getPedido().getLineas()) {
                if (lp.isMarcaBorrado()) {
                    lp.setPedido(null);
                }
            }

            for (Factura f : getPedido().getFacturas()) {
                if (f.isMarcaBorrado()) {
                    f.getPedidos().remove(pedido);
                }
            }

            for (Albaran al : getPedido().getAlbaranes()) {
                if (al.isMarcaBorrado()) {
                    al.setPedido(null);
                }
            }


            jpaController.edit(getPedido());

            Iterator<LineaPedido> itLineasPedido = getPedido().getLineas().iterator();
            while (itLineasPedido.hasNext()) {
                if (itLineasPedido.next().isMarcaBorrado()) {
                    itLineasPedido.remove();
                }
            }

            Iterator<Factura> itFacturasPedido = getPedido().getFacturas().iterator();
            while (itFacturasPedido.hasNext()) {
                if (itFacturasPedido.next().isMarcaBorrado()) {
                    itFacturasPedido.remove();
                }
            }

            Iterator<Albaran> itAlbaranesPedido = getPedido().getAlbaranes().iterator();
            while (itAlbaranesPedido.hasNext()) {
                if (itAlbaranesPedido.next().isMarcaBorrado()) {
                    itAlbaranesPedido.remove();
                }
            }

            jpaController.edit(getPedido());

            JsfUtil.addSuccessMessage("El pedido se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            e.printStackTrace();
        }
    }

    public String destroy() {
        try {
            getPedido().setBorrado(true);
            jpaController.edit(getPedido());
            JsfUtil.addSuccessMessage("El pedido se ha borrado satisfactoriamente.");
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

    public List<Pedido> getPedidoItems() {
        List<Pedido> pedidosEncontrados;
        if (columnaFiltro == null || anioFiltro == null) {
            pedidosEncontrados= jpaController.findPedidoEntities();
        }else{
            pedidosEncontrados=jpaController.findPedidoEntities(columnaFiltro, ascendente, anioFiltro);
        }

        if(lineaInvestigacionFiltro!=null)
            FiltrosPedidos.filtroLineaInvestigacion(pedidosEncontrados, lineaInvestigacionFiltro);

        
        return pedidosEncontrados;

    }

    private void reset() {
        pedido = null;
    }

    public void insertarLineaPedido() {
        try {
            LineaPedido linea = new LineaPedido();
            linea.setCantidad(1);
            linea.setPedido(pedido);
            pedido.getLineas().add(linea);
            jpaController.createLineaPedido(linea);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarLineaPedido() {
        getLineaPedidoSeleccionada().setMarcaBorrado(!getLineaPedidoSeleccionada().isMarcaBorrado());
    }

    public void insertarFactura() {
        try {
            Factura factura = new Factura();
            factura.getPedidos().add(pedido);
            pedido.getFacturas().add(factura);
            jpaController.createFactura(factura);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarFactura() {
        getFacturaSeleccionada().setMarcaBorrado(!getFacturaSeleccionada().isMarcaBorrado());
    }

    public void insertarAlbaran() {
        try {
            Albaran albaran = new Albaran();
            albaran.setPedido(pedido);
            pedido.getAlbaranes().add(albaran);
            jpaController.createAlbaran(albaran);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(PedidoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarAlbaran() {
        getAlbaranSeleccionado().setMarcaBorrado(!getAlbaranSeleccionado().isMarcaBorrado());
    }

    private boolean mostrarCargaFicheros;

    public boolean isMostrarCarga() {
        return mostrarCargaFicheros;
    }

    public String prepararCargaFicheros() {
        mostrarCargaFicheros = true;
        return null;
    }


    public LineaPedido getLineaPedidoSeleccionada() {
        return lineaPedidoSeleccionada;
    }

    public void setLineaPedidoSeleccionada(LineaPedido lineaPedidoSeleccionada) {
        this.lineaPedidoSeleccionada = lineaPedidoSeleccionada;
    }

    public Factura getFacturaSeleccionada() {
        return facturaSeleccionada;
    }

    public Albaran getAlbaranSeleccionado() {
        return albaranSeleccionado;
    }

    public void setAlbaranSeleccionado(Albaran albaranSeleccionado) {
        this.albaranSeleccionado = albaranSeleccionado;
    }

    public void setFacturaSeleccionada(Factura facturaSeleccionada) {
        this.facturaSeleccionada = facturaSeleccionada;
    }
    
    private List proyectoMatchPossibilities;

    public List getProyectoMatchPossibilities() {
        return proyectoMatchPossibilities;
    }


    private String proyectoSeleccionadoValue;

    public String getProyectoSeleccionadoValue() {
        return proyectoSeleccionadoValue;
    }

    public void setProyectoSeleccionadoValue(String proyectoSeleccionadoValue) {
        this.proyectoSeleccionadoValue = proyectoSeleccionadoValue;
    }

    public void selectInputValueChanged(ValueChangeEvent event) {

        if (event.getComponent() instanceof SelectInputText) {

            // get the number of displayable records from the component
            SelectInputText autoComplete =
                    (SelectInputText) event.getComponent();
            // get the new value typed by component user.
            String newWord = (String) event.getNewValue();

            proyectoMatchPossibilities =
                    Arrays.asList(JsfUtil.getSelectItems(proyectoJpaController.findProyectosVigentes(newWord), false));

            // if there is a selected item then find the city object of the
            // same name
            if (autoComplete.getSelectedItem() != null) {
                pedido.setProyecto((Proyecto) autoComplete.getSelectedItem().getValue());
                System.out.println("Cambio de proyecto a: " + pedido.getProyecto().getReferencia());
            }
//            else {
//                Proyecto tmp = proyectoJpaController.findProyecto(newWord);
//                if (tmp != null) {
//                    pedido.setProyecto(tmp);
//                }
//            }

        }
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

    public List<DocumentoAlbaran> getDocumentosAlbaranAutomaticos() {
        java.io.File f = new File("/home/intranet/SCAN");
        File ficheros[] = f.listFiles();
        Vector<DocumentoAlbaran> fr = new Vector<DocumentoAlbaran>();
        DocumentoAlbaran dc;
        for (File fl : ficheros) {
            dc = new DocumentoAlbaran();
            dc.setFile(fl);
            fr.add(dc);
        }

        return fr;
    }

}
