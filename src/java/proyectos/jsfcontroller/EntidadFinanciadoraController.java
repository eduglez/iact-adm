package proyectos.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import proyectos.jpacontroller.EntidadFinanciadoraJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import proyectos.modelo.EntidadFinanciadora;


public class EntidadFinanciadoraController {

    public EntidadFinanciadoraController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EntidadFinanciadoraJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entidadFinanciadoraJpa");
    }
    private EntidadFinanciadora entidadFinanciadora = null;
    private EntidadFinanciadoraJpaController jpaController = null;
    
    public SelectItem[] getEntidadFinanciaItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findEntidadFinanciadoraEntities(), false);
    }

    public SelectItem[] getEntidadFinanciadoraItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEntidadFinanciadoraEntities(), true);
    }

    public EntidadFinanciadora getEntidadFinanciadora() {
        
        if (entidadFinanciadora == null) {
            entidadFinanciadora = new EntidadFinanciadora();
        }
        return entidadFinanciadora;
    }

    public void setEntidadFinanciadora(EntidadFinanciadora entidadFinanciadora) {
        this.entidadFinanciadora = entidadFinanciadora;
    }


    public String listSetup() {
        reset();
        return "entidades-financiadoras";
    }

    public String createSetup() {
        reset();
        entidadFinanciadora=new EntidadFinanciadora();
        return "entidadFinanciadora-insertar";
    }

    public String create() {
        try {
            jpaController.create(entidadFinanciadora);
            JsfUtil.addSuccessMessage("La entidad financiadora se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A ocurrido un error al almacenar en la base de datos.");
            return null;
        }
        return listSetup();
    }

 
    public String destroy() {
 
        try {
            String id = entidadFinanciadora.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("La entidad financiadora se ha borrado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al almacenar en la base de datos.");
            return null;
        }
        return listSetup();
    }

  
    public List<EntidadFinanciadora> getEntidadFinanciadoraItems() {

            
            return jpaController.findEntidadFinanciadoraEntities();

    }

    

  
    private void reset() {
        entidadFinanciadora = null;
      
        
    }

    

}
