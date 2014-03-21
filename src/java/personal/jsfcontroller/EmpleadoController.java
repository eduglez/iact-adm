package personal.jsfcontroller;

import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import compras.jpacontroller.PedidoJpaController;
import compras.modelo.Pedido;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import jpa.exceptions.RollbackFailureException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import personal.jpacontroller.EmpleadoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jsf.util.EmpleadoComparator;
import personal.modelo.Contrato;
import personal.modelo.Empleado;
import net.sf.jasperreports.engine.export.*;
import personal.modelo.DocumentoContrato;
import personal.modelo.Falta;
import personal.modelo.TipoEmpleado;
import proyectos.jpacontroller.ProyectoJpaController;
import proyectos.modelo.EmpleadoProyecto;
import proyectos.modelo.Proyecto;

public class EmpleadoController {

    private ProyectoJpaController proyectoJpaController;
    private PedidoJpaController pedidosJpaController;
    private List proyectoMatchPossibilities;
    private FacesContext facesContext;
    private boolean filtroConContrato;

    public EmpleadoController() {
        facesContext = FacesContext.getCurrentInstance();
        jpaController = (EmpleadoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "empleadoJpa");
        proyectoJpaController = (ProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");
        pedidosJpaController = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");

        converter = new EmpleadoConverter();
        columnaFiltro = "apellidos";
        ascendente = true;
        filtroConContrato = true;

    }
    private Empleado empleado = null;
    private EmpleadoJpaController jpaController = null;
    private EmpleadoConverter converter = null;
    private boolean ascendente;
    private String columnaFiltro;
    private boolean filtroSinContrato;

    public boolean isFiltroSinContrato() {
        return filtroSinContrato;
    }

    public void setFiltroSinContrato(boolean filtroSinContrato) {
        this.filtroSinContrato = filtroSinContrato;
    }

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

    public Contrato getContratoAnteriorSeleccionado() {
        return contratoAnteriorSeleccionado;
    }

    public void setContratoAnteriorSeleccionado(Contrato contratoAnteriorSeleccionado) {
        this.contratoAnteriorSeleccionado = contratoAnteriorSeleccionado;
    }

    
    private Contrato contratoAnteriorSeleccionado = null;

    public SelectItem[] getEmpleadoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findEmpleadoEntities(), false);
    }

    public SelectItem[] getEmpleadoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEmpleadoEntities(), true);
    }

    public SelectItem[] getContratosAnterioresSelectOne() {
        return JsfUtil.getSelectItems((List<Contrato>) empleado.getContratos(), true);
    }

    public SelectItem[] getTiposEmpleadosSelectOne() {
        return JsfUtil.getSelectItems(Arrays.asList(TipoEmpleado.NOMBRES_TIPOS_EMPLEADOS), true);
    }

    public SelectItem[] getSexoItemsAvailableSelectOne() {
        ArrayList<String> listaSexos = new ArrayList<String>();
        listaSexos.add(Empleado.HOMBRE);
        listaSexos.add(Empleado.MUJER);

        return JsfUtil.getSelectItems(listaSexos, true);
    }

    public Empleado getEmpleado() {
        Empleado empleadoSolicitadoPorURL = (Empleado) JsfUtil.getObjectFromRequestParameter("id", converter, null);
        if (empleadoSolicitadoPorURL != null) {
            empleado = empleadoSolicitadoPorURL;
        }
        return empleado;
    }

    public String verEmpleado() {
        if (empleado != null && empleado.getId() != null) {
            empleado = jpaController.findEmpleado(empleado.getId());
            return "empleado-ver";
        } else {
            return listSetup();
        }
    }

    public String salirSinGuardar() {

        return verEmpleado();
    }

    public String salirGuardando() {
        guardarCambios();
        return verEmpleado();
    }

    public String editSetup() {
        if (empleado != null) {
            if (getEmpleado().getContratoActual() == null) {
                Contrato contrato = new Contrato();
                contrato.setEmpleado(empleado);
                getEmpleado().setContratoActual(contrato);
                getEmpleado().getContratos().add(contrato);
            }

            return "empleado-editar";
        } else {
            return null;
        }
    }

    public String listSetup() {
        reset();
        return "personal-listar";
    }

    public String createSetup() {
        reset();
        empleado = new Empleado();
        Contrato c = new Contrato();
        c.setEmpleado(empleado);
        empleado.getContratos().add(c);
        empleado.setContratoActual(c);
        return create();
    }

    public String create() {
        try {
            jpaController.create(getEmpleado());
            JsfUtil.addSuccessMessage("Empleado creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A ocurrido un error al crear el empleado: " + e.getMessage());
            return null;
        }
        return "empleado-editar";
    }

    public void guardarCambios() {
        try {

            Iterator<Contrato> itContratos = getEmpleado().getContratos().iterator();
            Contrato c;
            while (itContratos.hasNext()) {
                c = itContratos.next();
                if (c.isVacio()) {
                    itContratos.remove();
                }
            }

            if (getEmpleado().getContratoActual() != null) {
                if (getEmpleado().getContratoActual().isVacio()) {
                    getEmpleado().setContratoActual(null);
                } else if (getEmpleado().getContratoActual().getProyecto() != null) {
                    Proyecto proyecto = getEmpleado().getContratoActual().getProyecto().getProyecto();
                    boolean enProyecto = false;
                    for (EmpleadoProyecto ep : proyecto.getPersonal()) {
                        if (ep.equals(getEmpleado().getContratoActual().getProyecto())) {
                            enProyecto = true;
                            break;
                        }
                    }

                    if (!enProyecto) {
                        proyectoJpaController.createEmpleadoProyecto(getEmpleado().getContratoActual().getProyecto());
                        proyecto.getPersonal().add(getEmpleado().getContratoActual().getProyecto());
                    }

                    proyectoJpaController.edit(proyecto);
                }
            }

            jpaController.edit(getEmpleado());

            if (getEmpleado().getContratoActual() == null) {
                Contrato contrato = new Contrato();
                contrato.setEmpleado(empleado);
                getEmpleado().setContratoActual(contrato);
                getEmpleado().getContratos().add(contrato);
            }

            JsfUtil.addSuccessMessage("El empleado se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            ne.printStackTrace();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
            e.printStackTrace();
        }
    }

    public String destroy() {
        try {
            getEmpleado().setBorrado(true);
            jpaController.edit(getEmpleado());
            JsfUtil.addSuccessMessage("El empleado se ha borrado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public List<Empleado> getEmpleadoItems() {
        List<Empleado> empleadoItems = jpaController.findEmpleadoEntities();

        if (!filtroSinContrato) {
            FiltrosPersonal.filtrarSinContrato(empleadoItems);
        }

        if (!filtroConContrato) {
            FiltrosPersonal.filtrarConContrato(empleadoItems);
        }


        EmpleadoComparator ec = new EmpleadoComparator();
        ec.setAscendente(isAscendente());
        if (columnaFiltro == null || columnaFiltro.equals("apellidos")) {
            ec.setCampoOrden(EmpleadoComparator.POR_APELLIDO);

        } else {
            ec.setCampoOrden(EmpleadoComparator.POR_NOMBRE);

        }

        Collections.sort(empleadoItems, ec);

        return empleadoItems;
    }

    private void reset() {
        empleado = null;
    }

    /**
     * <td><ice:outputResource mimeType="application/pdf" resource="#{personal.personalPDF}" label="PDF" type="link" fileName="ListadoPersonal.pdf" attachment="true"/></td>
     */
    public Resource getPersonalPDF() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {

            in = new FileInputStream(EmpleadoController.class.getResource("/reports/Personal/Consultar/informePersonalApaisado.jasper").getPath());

            JRDataSource r = new JRBeanCollectionDataSource(getEmpleadoItems());

            JasperRunManager.runReportToPdfStream(in, outStream, new ConcurrentHashMap(), r);
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public Resource getPersonalPorTipoPersonalPDF() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {

            in = new FileInputStream(EmpleadoController.class.getResource("/reports/Personal/Consultar/informePersonalApaisado.jasper").getPath());

            JRDataSource r = new JRBeanCollectionDataSource(jpaController.getEmpleadosEntitiesPorCategorias());

            JasperRunManager.runReportToPdfStream(in, outStream, new ConcurrentHashMap(), r);
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public Resource getPersonalExcel() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(EmpleadoController.class.getResource("/reports/Personal/Consultar/informePersonalExcel.jasper").getPath());
            JRDataSource r = new JRBeanCollectionDataSource(getEmpleadoItems());
            JasperPrint print = JasperFillManager.fillReport(in, new ConcurrentHashMap(), r);
            JRXlsExporter ex = new JRXlsExporter();
            ex.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            ex.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outStream);
            ex.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            ex.exportReport();
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }


    public Resource getContratosExcel() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(EmpleadoController.class.getResource("/reports/Personal/Consultar/ContratosExcel.jasper").getPath());
            JRDataSource r = new JRBeanCollectionDataSource(jpaController.findContratos());
            JasperPrint print = JasperFillManager.fillReport(in, new ConcurrentHashMap(), r);
            JRXlsExporter ex = new JRXlsExporter();
            ex.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            ex.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outStream);
            ex.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            ex.exportReport();
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public Resource getPersonalPorTipoPersonalExcel() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(EmpleadoController.class.getResource("/reports/Personal/Consultar/informePersonalExcel.jasper").getPath());
            JRDataSource r = new JRBeanCollectionDataSource(jpaController.getEmpleadosEntitiesPorCategorias());
            JasperPrint print = JasperFillManager.fillReport(in, new ConcurrentHashMap(), r);

            JRXlsExporter ex = new JRXlsExporter();
            ex.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
            ex.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, outStream);
            ex.setParameter(JRXlsExporterParameter.IS_IGNORE_GRAPHICS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            ex.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            ex.exportReport();
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void llevarAContratoActual() {
        if (getContratoAnteriorSeleccionado() != null) {
            empleado.setContratoActual(getContratoAnteriorSeleccionado());
        }
    }

    public void llevarAContratosAnteriores() {
        if (!getEmpleado().getContratoActual().isVacio()) {
            Contrato c = new Contrato();
            c.setEmpleado(getEmpleado());
            getEmpleado().getContratos().add(c);
            getEmpleado().setContratoActual(c);
        }


    }

    public void borrarContratoAnterior() {
        if (getContratoAnteriorSeleccionado() != null) {
            getEmpleado().getContratos().remove(getContratoAnteriorSeleccionado());
        }
    }

    public List<Contrato> getContratosAnteriores() {
        return getEmpleado().getContratosAnteriores();
    }

    public List<DocumentoContrato> getFicherosAutomaticos() {
        java.io.File f = new File("/home/intranet/SCAN");
        File ficheros[] = f.listFiles();
        Vector<DocumentoContrato> fr = new Vector<DocumentoContrato>();
        DocumentoContrato dc;
        for (File fl : ficheros) {
            dc = new DocumentoContrato();
            dc.setFile(fl);
            dc.setContrato(empleado.getContratoActual());
            fr.add(dc);
        }

        return fr;
    }

    public List getProyectoMatchPossibilities() {
        return proyectoMatchPossibilities;
    }

    public void selectInputValueChanged(ValueChangeEvent event) {

        if (event.getComponent() instanceof SelectInputText) {

            SelectInputText autoComplete =
                    (SelectInputText) event.getComponent();

            String newWord = (String) event.getNewValue();

            proyectoMatchPossibilities =
                    Arrays.asList(JsfUtil.getSelectItems(proyectoJpaController.findProyectoEntities(newWord), false));


            if (autoComplete.getSelectedItem() != null) {
                Proyecto proyectoSeleccionado = (Proyecto) autoComplete.getSelectedItem().getValue();

                if (proyectoSeleccionado != null) {
                    EmpleadoProyecto empleadoProyectoActualEmpleado=getEmpleado().getContratoActual().getProyecto();
                    
                    if(empleadoProyectoActualEmpleado!=null){
                        if(empleadoProyectoActualEmpleado.getProyecto().equals(proyectoSeleccionado)){
                            return;
                        }else{
                            empleadoProyectoActualEmpleado.setContrato(null);
                        }
                    }

                    EmpleadoProyecto ep = null;
                    for (EmpleadoProyecto empProy : proyectoSeleccionado.getPersonal()) {
                        if (empProy.isIACT() && empProy.getEmpleadoIACT().equals(getEmpleado()) && empProy.getProyecto() != null) {
                            ep = empProy;
                            break;
                        }
                    }

                    if (ep == null) {
                        ep = new EmpleadoProyecto();
                        ep.setProyecto(proyectoSeleccionado);
                        ep.setContrato(empleado.getContratoActual());
                        ep.setEmpleadoIACT(empleado);
                        getEmpleado().getContratoActual().setProyecto(ep);
//                        getEmpleado().getProyectos().add(ep);
                    } else {
                        ep.setContrato(getEmpleado().getContratoActual());
                        getEmpleado().getContratoActual().setProyecto(ep);
                    }
                }
            }
        }
    }
    private Falta faltaSeleccionada;

    public Falta getFaltaSeleccionada() {
        return faltaSeleccionada;
    }

    public void setFaltaSeleccionada(Falta faltaSeleccionada) {
        this.faltaSeleccionada = faltaSeleccionada;
    }

    public String nuevaFalta() {
        try {
            Falta falta;
            falta = new Falta();
            falta.setEmpleado(getEmpleado());
            getEmpleado().getFaltas().add(falta);
            jpaController.createFalta(falta);

        } catch (RollbackFailureException ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmpleadoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;

    }

    public String borrarFalta() {
        if (getFaltaSeleccionada() != null) {
            getEmpleado().getFaltas().remove(getFaltaSeleccionada());
        }
        return null;
    }

    public boolean isFiltroConContrato() {
        return filtroConContrato;
    }

    public void setFiltroConContrato(boolean filtroConContrato) {
        this.filtroConContrato = filtroConContrato;
    }

    public List<Pedido> getPedidosEmpleado() {
        return pedidosJpaController.findPedidosEmpleado(empleado);
    }
}
