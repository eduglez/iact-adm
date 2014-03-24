package proyectos.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import proyectos.jpacontroller.OrganismoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import proyectos.modelo.Organismo;


public class OrganismoController {

    public OrganismoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (OrganismoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "organismoJpa");
    }
    private Organismo organismo = null;
    private List<Organismo> organismoItems = null;
    private OrganismoJpaController jpaController = null;
    
    public SelectItem[] getOrganismoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findOrganismoEntities(), false);
    }

    public SelectItem[] getOrganismoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findOrganismoEntities(), true);
    }

    public void setOrganismo(Organismo organismo) {
        this.organismo = organismo;
    }

    public Organismo getOrganismo() {
        
        if (organismo == null) {
            organismo = new Organismo();
        }
        return organismo;
    }

    public String listSetup() {
        reset();
        return "organismos";
    }

    public String createSetup() {
        reset();
        organismo=new Organismo();
        return "organismo-insertar";
    }


    public void guardarCambios(){
         try {
            jpaController.edit(getOrganismo());
            JsfUtil.addSuccessMessage("El organismo se ha guardado correctamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al almacenar.");
        }
    }



    public void destroy() {
 
        try {
            String id = organismo.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El organismo se ha borrado satisfactoriamente");
            reset();
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al borar el organismo.");
        }
    }


    public List<Organismo> getOrganismoItems() {
        if (organismoItems == null) {     
            organismoItems = jpaController.findOrganismoEntities();
        }

        return organismoItems;
    }

    private void reset() {
        organismo = null;
        organismoItems = null;
    }

}
