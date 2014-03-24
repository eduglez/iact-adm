package proyectos.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import proyectos.jpacontroller.ProyectoJpaController;
import proyectos.modelo.Proyecto;

public class ProyectoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        ProyectoJpaController controller = (ProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");
        return controller.findProyecto(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Proyecto) {
            Proyecto o = (Proyecto) object;
            return o.getId();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.Proyecto");
        }
    }

}
