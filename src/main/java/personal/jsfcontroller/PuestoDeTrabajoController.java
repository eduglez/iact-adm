package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.PuestoDeTrabajoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import personal.modelo.PuestoDeTrabajo;

public class PuestoDeTrabajoController {

    public PuestoDeTrabajoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (PuestoDeTrabajoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "puestoDeTrabajoJpa");
    }

    private PuestoDeTrabajo puestoDeTrabajo = null;
    private PuestoDeTrabajoJpaController jpaController = null;
 
    
    public SelectItem[] getPuestoDeTrabajoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findPuestoDeTrabajoEntities(), false);
    }

    public SelectItem[] getPuestoDeTrabajoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findPuestoDeTrabajoEntities(), true);
    }

    public PuestoDeTrabajo getPuestoDeTrabajo() {

        if (puestoDeTrabajo == null) {
            puestoDeTrabajo = new PuestoDeTrabajo();
        }
        return puestoDeTrabajo;
    }

    public void setPuestoDeTrabajo(PuestoDeTrabajo puestoDeTrabajo) {
        this.puestoDeTrabajo = puestoDeTrabajo;
    }

    public String listSetup() {
        reset();
        return "puestos-trabajo";
    }

    public String createSetup() {
        reset();
        puestoDeTrabajo = new PuestoDeTrabajo();
        return "puestoTrabajo-insertar";
    }

    public String create() {
        try {
            jpaController.create(puestoDeTrabajo);
            JsfUtil.addSuccessMessage("El Puesto de trabajo se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un problema. Puede que ya exista un Puesto de trabajo con ese nombre.");
            return null;
        }
        return listSetup();
    }

  
    public String destroy() {
        
        
        try {
            String id = puestoDeTrabajo.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El Puesto de trabajo se ha eliminado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }

    public List<PuestoDeTrabajo> getPuestoDeTrabajoItems() {
            return jpaController.findPuestoDeTrabajoEntities();
    }

  
    private void reset() {
        puestoDeTrabajo = null;
        
    }

}
