/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proyectos.jsfcontroller;

import proyectos.modelo.EstadoProyecto;
import proyectos.jpacontroller.EstadoProyectoJpaController;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 *
 * @author edu
 */
public class EstadoProyectoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        EstadoProyectoJpaController controller = (EstadoProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "estadoProyectoJpa");
        return controller.findEstadoProyecto(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EstadoProyecto) {
            EstadoProyecto o = (EstadoProyecto) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.EstadoProyecto");
        }
    }

}
