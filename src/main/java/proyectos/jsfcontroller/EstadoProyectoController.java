/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectos.jsfcontroller;



import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import proyectos.jpacontroller.EstadoProyectoJpaController;
import jsf.util.JsfUtil;
import proyectos.modelo.EstadoProyecto;

/**
 *
 * @author edu
 */
public class EstadoProyectoController {

    public EstadoProyectoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EstadoProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoProyectoJpa");
    }
   
  
    private EstadoProyectoJpaController jpaController = null;
    
  

    public SelectItem[] getEstadoProyectoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEstadoProyectoEntities(), true);
    }

   

    public List<EstadoProyecto> getEstadoProyectoItems() {
        
        return jpaController.findEstadoProyectoEntities();
    }


}
