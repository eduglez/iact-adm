package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.GrupoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import personal.modelo.Grupo;

public class GrupoController {

    public GrupoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (GrupoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "grupoJpa");
    }
    private Grupo grupo = null;
    private GrupoJpaController jpaController = null;

    public SelectItem[] getGrupoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findGrupoEntities(), false);
    }

    public SelectItem[] getGrupoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findGrupoEntities(), true);
    }

    public Grupo getGrupo() {
        if (grupo == null) {
            grupo = new Grupo();
        }
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }


    public String listSetup() {
        reset();
        return "grupos";
    }

    public String createSetup() {
        reset();
        grupo = new Grupo();
        return "grupo-insertar";
    }

    public String create() {
        try {
            jpaController.create(grupo);
            JsfUtil.addSuccessMessage("El Grupo se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "No se ha podido crear el Grupo. Puede que ya exista.");
            return null;
        }
        return listSetup();
    }

  
    public String destroy() {
        
        try {
            String id=grupo.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El Grupo se ha eliminado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }

   
    public List<Grupo> getGrupoItems() {
       return jpaController.findGrupoEntities();
        
    }

    private void reset() {
        grupo = null;      
    }


}
