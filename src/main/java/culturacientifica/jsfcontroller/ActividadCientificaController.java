package culturacientifica.jsfcontroller;

import com.icesoft.faces.component.selectinputtext.SelectInputText;
import culturacientifica.jpacontroller.ActividadCientificaJpaController;
import culturacientifica.modelo.ActividadCientifica;
import culturacientifica.modelo.DocumentoActividadCientifica;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jsf.controllers.LoginController;
import personal.jpacontroller.EmpleadoJpaController;
import personal.modelo.Empleado;

public class ActividadCientificaController {

    public ActividadCientificaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ActividadCientificaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "actividadCientificaJpa");
        empleadoJpaController = (EmpleadoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "empleadoJpa");
        login = (LoginController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "login");
    }
    
    private ActividadCientifica actividadCientifica = null;
    private ActividadCientificaJpaController jpaController = null;
    private EmpleadoJpaController empleadoJpaController = null;
    private LoginController login = null;

    public SelectItem[] getActividadCientificaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findActividadCientificaEntities(), true);
    }

    public SelectItem[] getTipoActividadCientificaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(Arrays.asList(ActividadCientifica.ACTIVIDADES), true);
    }

    public SelectItem[] getOrganosFinanciadoresItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(Arrays.asList(ActividadCientifica.ORGANOS_FINANCIADORES), true);
    }

    public ActividadCientifica getActividadCientifica() {

        if (actividadCientifica == null) {
            actividadCientifica = new ActividadCientifica();
        }
        return actividadCientifica;
    }

    public String listSetup() {
        return "culturacientifica-listar";
    }

    public String createSetup() {
        try {
            actividadCientifica = new ActividadCientifica();
            jpaController.create(actividadCientifica);
            JsfUtil.addSuccessMessage("La actividad científica se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al guardar.");
            return null;
        }
        
        return "actividadcientifica-editar";

    }

    public String createSetupInvestigador() {
        try {
            actividadCientifica = new ActividadCientifica();
            actividadCientifica.getAutoresIACT().add(empleadoJpaController.findEmpleado(login.getDni()));
            jpaController.create(actividadCientifica);
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al guardar.");
            return null;
        }
        return "actividadcientifica-editar";

    }

    public String create() {
        try {
            jpaController.create(actividadCientifica);
            JsfUtil.addSuccessMessage("La actividad científica se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al guardar.");
            return null;
        }
        return listSetup();
    }

    public String editSetup() {
        return "actividadcientifica-editar";
    }

    public String edit() {

        if (actividadCientifica == null) {
            JsfUtil.addErrorMessage("No se puede editar");
            return null;
        }

        try {
            jpaController.edit(actividadCientifica);
            JsfUtil.addSuccessMessage("La actividad científica se ha modificado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error de persistencia.");
            return null;
        }
        return listSetup();
    }

    public String destroy() {

        try {
            getActividadCientifica().setBorrado(true);
            jpaController.edit(getActividadCientifica());
            JsfUtil.addSuccessMessage("La Actividad científica se ha eliminado correctamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error de persistencia.");
            return null;
        }
        return listSetup();
    }

    public List<ActividadCientifica> getActividadCientificaItems() {
        return jpaController.findActividadCientificaEntities();
    }

    public List<ActividadCientifica> getActividadCientificaItemsInvestigador() {
        return jpaController.findActividadCientificaEntities(login.getDni());
    }

    public String verActividadCientifica() {
        if (actividadCientifica != null && actividadCientifica.getId()!=null) {
            actividadCientifica=jpaController.findActividadCientifica(actividadCientifica.getId());
            return "actividadcientifica-ver";
        } else {
            return listSetup();
        }
    }

    public String salirSinGuardar(){

        return verActividadCientifica();
    }

    public String salirGuardando(){
        guardarCambios();
        return verActividadCientifica();
    }

    public void guardarCambios() {
        try {
            jpaController.edit(getActividadCientifica());
            JsfUtil.addSuccessMessage("La actividad científica se ha guardado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
        }
    }
    private Empleado autorActividadCientificaSeleccionado;

    public Empleado getAutorActividadCientificaSeleccionado() {
        return autorActividadCientificaSeleccionado;
    }

    public void setAutorActividadCientificaSeleccionado(Empleado autorActividadCientificaSeleccionado) {
        this.autorActividadCientificaSeleccionado = autorActividadCientificaSeleccionado;
    }

    public String quitarDeLaActividad() {
        actividadCientifica.getAutoresIACT().remove(autorActividadCientificaSeleccionado);
        return null;
    }
    private List empleadoIACTMatchPossibilities;

    public List getEmpleadoIACTMatchPossibilities() {
        return empleadoIACTMatchPossibilities;
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
                getActividadCientifica().getAutoresIACT().add((Empleado) autoComplete.getSelectedItem().getValue());
            }
//            // if there was no selection we still want to see if a proper
//            // city was typed and update our selectedCity instance.


        }
    }

    public void setActividadCientifica(ActividadCientifica actividadCientifica) {
        this.actividadCientifica = actividadCientifica;
    }


    public List<DocumentoActividadCientifica> getDocumentosActividadesCientificasAutomaticos() {
        java.io.File f = new File("/home/intranet/SCAN");
        File ficheros[] = f.listFiles();
        Vector<DocumentoActividadCientifica> fr = new Vector<DocumentoActividadCientifica>();
        DocumentoActividadCientifica dc;
        for (File fl : ficheros) {
            dc = new DocumentoActividadCientifica();
            dc.setFile(fl);
            dc.setNombreDocumento(fl.getName());
            fr.add(dc);
        }

        return fr;
    }


    public List<EstadisticaAnio> getEstadisticasAnio(){
        HashMap<Integer, EstadisticaAnio> estadisticasAnio=new HashMap<Integer, EstadisticaAnio>();
        Calendar calendario=Calendar.getInstance();
        Integer anioAC;
        for(ActividadCientifica ac:getActividadCientificaItems()){
            if(ac.getFechaRepresentativa()!=null){
                calendario.setTime(ac.getFechaRepresentativa());
                anioAC=Integer.valueOf(calendario.get(Calendar.YEAR));
                
                if(!estadisticasAnio.containsKey(anioAC)){
                    estadisticasAnio.put(anioAC, new EstadisticaAnio(anioAC.intValue()));
                }

                estadisticasAnio.get(anioAC).computarActividadCientifica(ac);


            }

        }

        return new ArrayList<EstadisticaAnio>(estadisticasAnio.values());
    }
}
