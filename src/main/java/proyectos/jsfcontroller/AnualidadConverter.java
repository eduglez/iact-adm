package proyectos.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import proyectos.jpacontroller.AnualidadJpaController;
import proyectos.modelo.distribuciondotacion.Anualidad;


public class AnualidadConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        AnualidadJpaController controller = (AnualidadJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "anualidadJpa");
        return controller.findAnualidad(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Anualidad) {
            Anualidad o = (Anualidad) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.Anualidad");
        }
    }

}
