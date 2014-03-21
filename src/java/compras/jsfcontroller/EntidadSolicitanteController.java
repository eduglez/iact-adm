package compras.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.EntidadSolicitanteJpaController;
import jsf.util.JsfUtil;

import compras.modelo.EntidadSolicitante;


public class EntidadSolicitanteController {

    public EntidadSolicitanteController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EntidadSolicitanteJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entidadSolicitanteJpa");
    }
   
  
    private EntidadSolicitanteJpaController jpaController = null;
    
  

    public SelectItem[] getEntidadSolicitanteItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEntidadSolicitanteEntities(), true);
    }

    public SelectItem[] getEntidadSolicitanteProyectosItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEntidadSolicitanteProyectosEntities(), true);
    }

   

    public List<EntidadSolicitante> getEntidadSolicitanteItems() {
        
        return jpaController.findEntidadSolicitanteEntities();
    }


}
