package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.CuerpoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import personal.modelo.Cuerpo;

public class CuerpoController {

    public CuerpoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (CuerpoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "cuerpoJpa");
    }

    private Cuerpo cuerpo = null;
    private CuerpoJpaController jpaController = null;
   
   

    public SelectItem[] getCuerpoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findCuerpoEntities(), false);
    }

    public SelectItem[] getCuerpoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findCuerpoEntities(), true);
    }

    public Cuerpo getCuerpo() {
        if (cuerpo == null) {
            cuerpo = new Cuerpo();
        }
        return cuerpo;
    }

    public void setCuerpo(Cuerpo cuerpo) {
        this.cuerpo = cuerpo;
    }

    public String listSetup() {
        reset();
        return "cuerpos";
    }

    public String createSetup() {
        reset();
        cuerpo = new Cuerpo();
        return "cuerpo-insertar";
    }

    public String create() {
        try {
            jpaController.create(cuerpo);
            JsfUtil.addSuccessMessage("El Cuerpo se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "No se ha podido crear el Cuerpo. Puede que ya exista.");
            return null;
        }
        return listSetup();
    }

  
    public String destroy() {
 
        try {
            String id = cuerpo.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El Cuerpo se ha eliminado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }

    public List<Cuerpo> getCuerpoItems() {
            return jpaController.findCuerpoEntities();
    }

    private void reset() {
        cuerpo = null;
    }


}
