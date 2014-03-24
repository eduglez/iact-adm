/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.CuerpoJpaController;
import personal.modelo.Cuerpo;

/**
 *
 * @author edu
 */
public class CuerpoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = new String(string);
        CuerpoJpaController controller = (CuerpoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "cuerpoJpa");
        return controller.findCuerpo(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Cuerpo) {
            Cuerpo o = (Cuerpo) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Cuerpo");
        }
    }

}
