package proyectos.jsfcontroller;

import java.util.logging.Level;
import java.util.logging.Logger;
import jpa.exceptions.RollbackFailureException;
import proyectos.jsfcontroller.distribuciondotacion.TablaDistribucionConcesion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import proyectos.jpacontroller.ProyectoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import proyectos.jpacontroller.EstadoProyectoJpaController;
import proyectos.modelo.EmpleadoProyecto;
import proyectos.modelo.EstadoProyecto;
import proyectos.modelo.Justificacion;
import proyectos.modelo.Proyecto;
import com.icesoft.faces.component.selectinputtext.SelectInputText;
import com.icesoft.faces.context.ByteArrayResource;
import com.icesoft.faces.context.Resource;
import compras.modelo.Pedido;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import javax.faces.event.ValueChangeEvent;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import personal.jpacontroller.EmpleadoJpaController;
import personal.modelo.Empleado;
import proyectos.jpacontroller.AnualidadJpaController;
import proyectos.jpacontroller.LineaInvestigacionJpaController;
import proyectos.modelo.DocumentoProyecto;
import gasto.modelo.Gasto;
import java.util.concurrent.ConcurrentHashMap;
import proyectos.modelo.LineaInvestigacion;
import jsf.util.ProyectoComparator;
import proyectos.modelo.distribuciondotacion.AnualidadPartida;

public class ProyectoController {

    private final EstadoProyectoJpaController jpaEstadoProyecto;
    private AnualidadJpaController anualidadJpaController;

    public ProyectoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();

        jpaController = (ProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");

        jpaEstadoProyecto = (EstadoProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoProyectoJpa");

        empleadoJpaController = (EmpleadoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "empleadoJpa");

        anualidadJpaController = (AnualidadJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "anualidadJpa");

        lineaInvestigacionJpaController = (LineaInvestigacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "lineaInvestigacionJpa");

        converter = new ProyectoConverter();

        setFiltroVigente(true);
        
    }
    private Proyecto proyecto = null;
    private List<Proyecto> proyectoItems = null;
    private ProyectoJpaController jpaController = null;
    private ProyectoConverter converter = null;
    private EmpleadoJpaController empleadoJpaController = null;
    private LineaInvestigacionJpaController lineaInvestigacionJpaController = null;
    private boolean filtroFaseSolicitud;
    private boolean filtroFaseAceptacion;
    private boolean filtroVigente;
    private boolean filtroFinalizadoEjecucion;
    private boolean filtroFinalizadoJustificado;
    private boolean filtroDesestimado;


    private String columnaOrden;
    private boolean ascendente;

    private Empleado filtroResponsable;



    public Empleado getFiltroResponsable() {
        return filtroResponsable;
    }

    public void setFiltroResponsable(Empleado filtroResponsable) {
        this.filtroResponsable = filtroResponsable;
    }

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

    public String getColumnaOrden() {
        return columnaOrden;
    }

    public void setColumnaOrden(String columnaOrden) {
        this.columnaOrden = columnaOrden;
    }

    

    public boolean isFiltroDesestimado() {
        return filtroDesestimado;
    }

    public void setFiltroDesestimado(boolean filtroDesestimado) {
        this.filtroDesestimado = filtroDesestimado;
    }

    public boolean isFiltroFaseAceptacion() {
        return filtroFaseAceptacion;
    }

    public void setFiltroFaseAceptacion(boolean filtroFaseAceptacion) {
        this.filtroFaseAceptacion = filtroFaseAceptacion;
    }

    public boolean isFiltroFaseSolicitud() {
        return filtroFaseSolicitud;
    }

    public void setFiltroFaseSolicitud(boolean filtroFaseSolicitud) {
        this.filtroFaseSolicitud = filtroFaseSolicitud;
    }

    public boolean isFiltroFinalizadoEjecucion() {
        return filtroFinalizadoEjecucion;
    }

    public void setFiltroFinalizadoEjecucion(boolean filtroFinalizadoEjecucion) {
        this.filtroFinalizadoEjecucion = filtroFinalizadoEjecucion;
    }

    public boolean isFiltroFinalizadoJustificado() {
        return filtroFinalizadoJustificado;
    }

    public void setFiltroFinalizadoJustificado(boolean filtroFinalizadoJustificado) {
        this.filtroFinalizadoJustificado = filtroFinalizadoJustificado;
    }

    public boolean isFiltroVigente() {
        return filtroVigente;
    }

    public void setFiltroVigente(boolean filtroVigente) {
        this.filtroVigente = filtroVigente;
    }

    public SelectItem[] getAnualidadItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(anualidadJpaController.findAnualidadEntities(),true);
    }

    public SelectItem[] getProyectoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProyectoEntities(), false);
    }

    public SelectItem[] getProyectoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProyectoEntities(), true);
    }

    public SelectItem[] getLineaInvestigacionItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(lineaInvestigacionJpaController.findLineaInvestigacionEntities(), true);
    }

    public SelectItem[] getTipoGastoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(Arrays.asList(Gasto.TIPOS_GASTO), true);
    }

    public ProyectoJpaController getJpaController() {
        return jpaController;
    }

    
    public Proyecto getProyecto() {
        Proyecto proyectoFromRequestParameter = (Proyecto) JsfUtil.getObjectFromRequestParameter("id", converter, null);
        if (proyectoFromRequestParameter != null) {
            proyecto = proyectoFromRequestParameter;
        }
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return proyecto;
    }

    public String verProyecto() {
        if (proyecto != null && proyecto.getId()!=null) {
            proyecto=jpaController.findProyecto(getProyecto().getId());
            return "proyecto-ver";
        } else {
            return listSetup();
        }
    }

    public String listSetup() {
        reset();
        return "proyectos-listar";
    }


    public String salirSinGuardar(){
        
        return verProyecto();
    }

    public String salirGuardando(){
        guardarCambios();
        return verProyecto();
    }
    public AnualidadJpaController getAnualidadJpaController() {
        return anualidadJpaController;
    }

    public String createSetup() {
        reset();
        proyecto = new Proyecto();
        proyecto.setEstadoProyecto(jpaEstadoProyecto.findEstadoProyecto("Fase de solicitud"));
        try {
            jpaController.create(proyecto);
            JsfUtil.addSuccessMessage("El proyecto se ha creado correctamente.");
        } catch (RollbackFailureException ex) {
            JsfUtil.ensureAddErrorMessage(ex, "Error de persistencia");
        } catch (Exception ex) {
            JsfUtil.ensureAddErrorMessage(ex, "Error de persistencia");
        }
        return "proyecto-editar";
    }

    public String editSetup() {
        if (proyecto != null) {
            tablaDistribucionConcesion = null;
            return "proyecto-editar";
        } else {
            return null;
        }
    }
    
    public String edit() {
        try {
            jpaController.edit(proyecto);
            JsfUtil.addSuccessMessage("El proyecto se ha editado satisfactoriamente");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al guardar.");
            return null;
        }
        return editSetup();
    }

    public String destroy() {
        try {
            getProyecto().setBorrado(true);
            jpaController.edit(getProyecto());
            JsfUtil.addSuccessMessage("El proyecto se ha borrado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al guardar");
            return null;
        }
        return listSetup();
    }


    public List<Proyecto> getProyectoItems() {
        if (proyectoItems == null) {
            proyectoItems = jpaController.findProyectoEntities();
        }
        return proyectoItems;
    }

    public List<Proyecto> getProyectoItemsFiltrados() {
        List<Proyecto> proyectos=jpaController.findProyectoEntities();

        ArrayList<EstadoProyecto> estadosFiltro = new ArrayList<EstadoProyecto>();
        if (isFiltroFaseSolicitud()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Fase de solicitud"));
        }
        if (isFiltroFaseAceptacion()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Fase aceptación"));
        }
        if (isFiltroVigente()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Vigente"));
        }
        if (isFiltroFinalizadoEjecucion()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Finalizado ejecución"));
        }
        if (isFiltroFinalizadoJustificado()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Finalizado y justificado"));
        }
        if (isFiltroDesestimado()) {
            estadosFiltro.add(jpaEstadoProyecto.findEstadoProyecto("Desestimado"));
        }

        FiltrosProyectos.filtrarPorEstado(proyectos, estadosFiltro);


        if(getFiltroResponsable()!=null)
            FiltrosProyectos.filtrarPorResponsable(proyectos, filtroResponsable);

        if(getFiltroFechaAceptacionAntesDe()!=null
                && getFiltroFechaAceptacionDespuesDe()!=null)
            FiltrosProyectos.filtrarRangoFechaAceptacion(proyectos, filtroFechaAceptacionDespuesDe, filtroFechaAceptacionAntesDe);

        if(getFiltroFechaSolicitudAntesDe()!=null
                && getFiltroFechaSolicitudDespuesDe()!=null)
            FiltrosProyectos.filtrarRangoFechaSolicitud(proyectos, filtroFechaSolicitudDespuesDe, filtroFechaSolicitudAntesDe);

        if(getFiltroFechaInicioAntesDe()!=null
                && getFiltroFechaInicioDespuesDe()!=null)
            FiltrosProyectos.filtrarRangoFechaInicio(proyectos, filtroFechaInicioDespuesDe, filtroFechaInicioAntesDe);

        if(getFiltroLineaInvestigacion()!=null)
            FiltrosProyectos.filtroLineaInvestigacion(proyectos, filtroLineaInvestigacion);

        ProyectoComparator pc=new ProyectoComparator(ascendente, columnaOrden);

        Collections.sort(proyectos,pc);
        
        return proyectos;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    private void reset() {
        proyecto = null;
        proyectoItems = null;
        tablaDistribucionConcesion = null;
    }

    public Converter getConverter() {
        return converter;
    }


    private Justificacion justificacionSeleccionada;

    public Justificacion getJustificacionSeleccionada() {
        return justificacionSeleccionada;
    }

    public void setJustificacionSeleccionada(Justificacion justificacionSeleccionada) {
        this.justificacionSeleccionada = justificacionSeleccionada;
    }
    

    public void insertarJustificacion() {
        Justificacion j = new Justificacion();
        j.setProyecto(proyecto);
        proyecto.getJustificaciones().add(j);
        try {
            jpaController.createJustificacion(j);
        } catch (Exception ex) {
            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarJustificacion() {
        getJustificacionSeleccionada().setMarcaBorrado(!getJustificacionSeleccionada().isMarcaBorrado());
    }

    private Gasto gastoSeleccionado;

    public Gasto getGastoSeleccionado() {
        return gastoSeleccionado;
    }

    public void setGastoSeleccionado(Gasto gastoSeleccionado) {
        this.gastoSeleccionado = gastoSeleccionado;
    }

    /*
    public void insertarGasto() {
        Gasto j = new Gasto();
        j.setProyecto(proyecto);
        proyecto.getGastos().add(j);
        try {
            jpaController.createGasto(j);
        } catch (Exception ex) {
            Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void toggleMarcaBorrarGasto() {
        getGastoSeleccionado().setMarcaBorrado(!getGastoSeleccionado().isMarcaBorrado());
    }
*/
    public List<DocumentoProyecto> getDocumentosProyectosAutomaticos() {
        java.io.File f = new File("/home/intranet/SCAN");
        File ficheros[] = f.listFiles();
        Vector<DocumentoProyecto> fr = new Vector<DocumentoProyecto>();
        DocumentoProyecto dc;
        for (File fl : ficheros) {
            dc = new DocumentoProyecto();
            dc.setFile(fl);
            dc.setNombreDocumento(fl.getName());
            fr.add(dc);
        }

        return fr;
    }
    private List empleadoIACTMatchPossibilities;

    public List getEmpleadoIACTMatchPossibilities() {
        return empleadoIACTMatchPossibilities;
    }

    public void guardarCambios() {
        try {
            for(AnualidadPartida ap:getProyecto().getDatosEconomicos()){
                 if(ap.isMarcaBorrado())
                     ap.setProyecto(null);
             }

             for(Justificacion j:getProyecto().getJustificaciones()){
                 if(j.isMarcaBorrado())
                     j.setProyecto(null);
             }

//             for(Gasto g:getProyecto().getGastos()){
//                 if(g.isMarcaBorrado())
//                     g.setProyecto(null);
//             }

            jpaController.edit(getProyecto());

            Iterator<AnualidadPartida> itAnualidades=getProyecto().getDatosEconomicos().iterator();
            while(itAnualidades.hasNext()){
                if(itAnualidades.next().isMarcaBorrado())
                    itAnualidades.remove();
            }

            Iterator<Justificacion> itJustificaciones=getProyecto().getJustificaciones().iterator();
            while(itJustificaciones.hasNext()){
                if(itJustificaciones.next().isMarcaBorrado())
                    itJustificaciones.remove();
            }

//            Iterator<Gasto> itGastos=getProyecto().getGastos().iterator();
//            while(itGastos.hasNext()){
//                if(itGastos.next().isMarcaBorrado())
//                    itGastos.remove();
//            }

            Iterator<EmpleadoProyecto> itPersonal=getProyecto().getPersonal().iterator();
            while(itPersonal.hasNext()){
                if(itPersonal.next().getProyecto()==null)
                    itPersonal.remove();
            }

            Iterator<DocumentoProyecto> itDocumentos=getProyecto().getDocumentos().iterator();
            while(itDocumentos.hasNext()){
                if(itDocumentos.next().getProyecto()==null)
                    itDocumentos.remove();
            }

            jpaController.edit(getProyecto());

            

            JsfUtil.addSuccessMessage("El proyecto se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
        }
    }

    public void empleadoIACTValueChanged(ValueChangeEvent event) {

        if (event.getComponent() instanceof SelectInputText) {

            // get the number of displayable records from the component
            SelectInputText autoComplete =
                    (SelectInputText) event.getComponent();
            // get the new value typed by component user.
            String newWord = (String) event.getNewValue();

            empleadoIACTMatchPossibilities =
                    Arrays.asList(JsfUtil.getSelectItems(empleadoJpaController.findEmpleadoEntities(newWord), false));

//            // if there is a selected item then find the city object of the
//            // same name
            if (autoComplete.getSelectedItem() != null) {
                EmpleadoProyecto empProy = new EmpleadoProyecto();
                empProy.setEmpleadoIACT((Empleado) autoComplete.getSelectedItem().getValue());
                empProy.setProyecto(proyecto);
                empProy.getEmpleadoIACT().getProyectos().add(empProy);
                getProyecto().getPersonal().add(empProy);
                try {
                    jpaController.createEmpleadoProyecto(empProy);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            // if there was no selection we still want to see if a proper
//            // city was typed and update our selectedCity instance.


        }
    }
    private EmpleadoProyecto nuevoEmpleadoProyecto;

    public EmpleadoProyecto getNuevoEmpleadoProyecto() {
        if (nuevoEmpleadoProyecto == null) {
            nuevoEmpleadoProyecto = new EmpleadoProyecto();
        }
        return nuevoEmpleadoProyecto;
    }

    public void insertarEmpleadoProyectoExterno() {

        getProyecto().getPersonal().add(nuevoEmpleadoProyecto);
        nuevoEmpleadoProyecto.setProyecto(proyecto);
        

        try {
                    jpaController.createEmpleadoProyecto(nuevoEmpleadoProyecto);
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(ProyectoController.class.getName()).log(Level.SEVERE, null, ex);
                }

        nuevoEmpleadoProyecto = new EmpleadoProyecto();


    }

    public List<Pedido> getPedidosProyecto() {
        return jpaController.findPedidosProyecto(proyecto);
    }

 

    //INFORMES
    public Resource getProyectosPDF() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {

            in = new FileInputStream(ProyectoController.class.getResource("/reports/Proyectos/Consultar/informeListadoSimpleProyectos.jasper").getPath());

            JRDataSource r = new JRBeanCollectionDataSource(getProyectoItemsFiltrados());

            JasperRunManager.runReportToPdfStream(in, outStream, new ConcurrentHashMap(), r);
            return new ByteArrayResource(outStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
        }

        return null;
    }

    public Resource getProyectosExcel() {

        InputStream in = null;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(ProyectoController.class.getResource("/reports/Proyectos/Consultar/excelListadoCompletoProyectos.jasper").getPath());
            JRDataSource r = new JRBeanCollectionDataSource(getProyectoItemsFiltrados());
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

  
    private TablaDistribucionConcesion tablaDistribucionConcesion;

    public TablaDistribucionConcesion getTablaDistribucionConcesion() {

        if (tablaDistribucionConcesion == null) {
            tablaDistribucionConcesion = new TablaDistribucionConcesion(this);
        }
        return tablaDistribucionConcesion;
    }


    //Filtro por fecha de solicitud

    private Date filtroFechaSolicitudDespuesDe;
    private Date filtroFechaSolicitudAntesDe;

    public Date getFiltroFechaSolicitudAntesDe() {
        return filtroFechaSolicitudAntesDe;
    }

    public void setFiltroFechaSolicitudAntesDe(Date filtroFechaSolicitudAntesDe) {
        this.filtroFechaSolicitudAntesDe = filtroFechaSolicitudAntesDe;
        if(filtroFechaSolicitudAntesDe==null)
            return;
        this.filtroFechaSolicitudAntesDe.setTime(filtroFechaSolicitudAntesDe.getTime()+1000*60*60*24-1);
    }

    public Date getFiltroFechaSolicitudDespuesDe() {
        return filtroFechaSolicitudDespuesDe;
    }

    public void setFiltroFechaSolicitudDespuesDe(Date filtroFechaSolicitudDespuesDe) {
        this.filtroFechaSolicitudDespuesDe = filtroFechaSolicitudDespuesDe;
    }




    //Filtro por fecha de aceptacion
    private Date filtroFechaAceptacionDespuesDe;
    private Date filtroFechaAceptacionAntesDe;

    public Date getFiltroFechaAceptacionAntesDe() {
        return filtroFechaAceptacionAntesDe;
    }

    public void setFiltroFechaAceptacionAntesDe(Date filtroFechaAceptacionAntesDe) {
        this.filtroFechaAceptacionAntesDe = filtroFechaAceptacionAntesDe;
        if(filtroFechaAceptacionAntesDe==null)
            return;
        this.filtroFechaAceptacionAntesDe.setTime(filtroFechaAceptacionAntesDe.getTime()+1000*60*60*24-1);
    }

    public Date getFiltroFechaAceptacionDespuesDe() {
        return filtroFechaAceptacionDespuesDe;
    }

    public void setFiltroFechaAceptacionDespuesDe(Date filtroFechaAceptacionDespuesDe) {
        this.filtroFechaAceptacionDespuesDe = filtroFechaAceptacionDespuesDe;
    }



    //Filtro por fecha de inicio
    private Date filtroFechaInicioDespuesDe;
    private Date filtroFechaInicioAntesDe;

    public Date getFiltroFechaInicioAntesDe() {
        return filtroFechaInicioAntesDe;
    }

    public void setFiltroFechaInicioAntesDe(Date filtroFechaInicioAntesDe) {
        this.filtroFechaInicioAntesDe = filtroFechaInicioAntesDe;
        if(filtroFechaInicioAntesDe==null)
            return;
        this.filtroFechaInicioAntesDe.setTime(filtroFechaInicioAntesDe.getTime()+1000*60*60*24-1);
    }

    public Date getFiltroFechaInicioDespuesDe() {
        return filtroFechaInicioDespuesDe;
    }

    public void setFiltroFechaInicioDespuesDe(Date filtroFechaInicioDespuesDe) {
        this.filtroFechaInicioDespuesDe = filtroFechaInicioDespuesDe;
    }

    //Filtro por linea de investigación

    private LineaInvestigacion filtroLineaInvestigacion;

    public LineaInvestigacion getFiltroLineaInvestigacion() {
        return filtroLineaInvestigacion;
    }

    public void setFiltroLineaInvestigacion(LineaInvestigacion filtroLineaInvestigacion) {
        this.filtroLineaInvestigacion = filtroLineaInvestigacion;
    }
    
}
