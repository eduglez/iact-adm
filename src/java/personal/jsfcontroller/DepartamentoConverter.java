/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.DepartamentoJpaController;
import personal.modelo.Departamento;

/**
 *
 * @author edu
 */
public class DepartamentoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        DepartamentoJpaController controller = (DepartamentoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "departamentoJpa");
        return controller.findDepartamento(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Departamento) {
            Departamento o = (Departamento) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Departamento");
        }
    }

}
