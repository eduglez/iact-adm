/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compras.jsfcontroller;

import compras.jpacontroller.DatosEntregaJpaController;
import compras.modelo.DatosEntrega;
import personal.jsfcontroller.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 *
 * @author edu
 */
public class DatosEntregaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        DatosEntregaJpaController controller = (DatosEntregaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosEntregaJpa");
        return controller.findDatosEntrega(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof DatosEntrega) {
            DatosEntrega o = (DatosEntrega) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.DatosEntrega");
        }
    }

}
