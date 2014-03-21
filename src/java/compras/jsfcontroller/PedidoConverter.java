package compras.jsfcontroller;

import compras.jpacontroller.PedidoJpaController;
import compras.modelo.Pedido;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class PedidoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        PedidoJpaController controller = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");
        return controller.findPedido(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Pedido) {
            Pedido o = (Pedido) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.Pedido");
        }
    }

}
