/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.GrupoJpaController;
import personal.modelo.Grupo;

/**
 *
 * @author edu
 */
public class GrupoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        GrupoJpaController controller = (GrupoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "grupoJpa");
        return controller.findGrupo(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Grupo) {
            Grupo o = (Grupo) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Grupo");
        }
    }

}
