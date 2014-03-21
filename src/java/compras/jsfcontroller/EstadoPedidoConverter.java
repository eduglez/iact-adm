/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compras.jsfcontroller;

import compras.jpacontroller.EstadoPedidoJpaController;
import compras.modelo.EstadoPedido;
import personal.jsfcontroller.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 *
 * @author edu
 */
public class EstadoPedidoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        EstadoPedidoJpaController controller = (EstadoPedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoPedidoJpa");
        return controller.findEstadoPedido(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EstadoPedido) {
            EstadoPedido o = (EstadoPedido) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.EstadoPedido");
        }
    }

}
