/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.EspecialidadLaboralJpaController;
import personal.modelo.EspecialidadLaboral;

/**
 *
 * @author edu
 */
public class EspecialidadLaboralConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        EspecialidadLaboralJpaController controller = (EspecialidadLaboralJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "especialidadLaboralJpa");
        return controller.findEspecialidadLaboral(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EspecialidadLaboral) {
            EspecialidadLaboral o = (EspecialidadLaboral) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.EspecialidadLaboral");
        }
    }

}
