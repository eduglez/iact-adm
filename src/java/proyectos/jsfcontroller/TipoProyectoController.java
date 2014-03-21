//TODO Documentar!!!!
package proyectos.jsfcontroller;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import proyectos.jpacontroller.TipoProyectoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import proyectos.modelo.TipoProyecto;


public class TipoProyectoController {

    public TipoProyectoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (TipoProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoProyectoJpa");
    }
    private TipoProyecto tipoProyecto = null;
    private List<TipoProyecto> tipoProyectoItems = null;
    private TipoProyectoJpaController jpaController = null;
    
    public SelectItem[] getTipoProyectoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findTipoProyectoEntities(), false);
    }

    public SelectItem[] getTipoProyectoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findTipoProyectoEntities(), true);
    }

    public TipoProyecto getTipoProyecto() {
        
        if (tipoProyecto == null) {
            tipoProyecto = new TipoProyecto();
        }
        return tipoProyecto;
    }

    public String listSetup() {
        reset();
        return "tipos-proyecto";
    }

    public String createSetup() {
        reset();
        tipoProyecto=new TipoProyecto();
        return "tipoProyecto-insertar";
    }

    public String create() {
        try {
            jpaController.create(tipoProyecto);
            JsfUtil.addSuccessMessage("El tipo de proyecto se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A ocurrido un error al almacenar en la base de datos.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("tipoProyecto_detail");
    }

    public String editSetup() {
        return scalarSetup("tipoProyecto-editar");
    }

    private String scalarSetup(String destination) {
        TipoProyecto p=tipoProyecto;
        reset();
        tipoProyecto = p;
        if (tipoProyecto == null) {
            String requestCuerpoString = JsfUtil.getRequestParameter("jsfcrud.currentTipoProyecto");
            JsfUtil.addErrorMessage("The tipo proyecto with id " + requestCuerpoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
      
        if (tipoProyecto == null) {

                JsfUtil.addErrorMessage("No se puede editar");

            return null;
        }
        try {
            jpaController.edit(tipoProyecto);
            JsfUtil.addSuccessMessage("El tipo de proyecto se ha modificado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al almacenar en la base de datos");
            return null;
        }
        return listSetup();
    }

    public String destroy() {
 
        try {
            String id = tipoProyecto.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El tipo de proyecto se ha borrado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al almacenar en la base de datos.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<TipoProyecto> getTipoProyectoItems() {
        if (tipoProyectoItems == null) {
            
            tipoProyectoItems = jpaController.findTipoProyectoEntities();
        }
        return tipoProyectoItems;
    }



    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset() {
        tipoProyecto = null;
        tipoProyectoItems = null;
        
    }

    
    public void selectionListener(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        tipoProyecto=tipoProyectoItems.get(selectedRowIndex);
    }

}
