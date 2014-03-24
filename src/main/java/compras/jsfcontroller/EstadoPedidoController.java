package compras.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.EstadoPedidoJpaController;
import jsf.util.JsfUtil;

import compras.modelo.EstadoPedido;


public class EstadoPedidoController {

    public EstadoPedidoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EstadoPedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoPedidoJpa");
    }
   
  
    private EstadoPedidoJpaController jpaController = null;
    
  

    public SelectItem[] getDatosEntregaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEstadoPedidoEntities(), true);
    }

   

    public List<EstadoPedido> getDatosEntregaItems() {
        
        return jpaController.findEstadoPedidoEntities();
    }


}
