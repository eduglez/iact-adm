package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.AdscripcionJpaController;
import personal.modelo.Adscripcion;

public class AdscripcionConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        AdscripcionJpaController controller = (AdscripcionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "adscripcionJpa");
        return controller.findAdscripcion(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Adscripcion) {
            Adscripcion o = (Adscripcion) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Adscripcion");
        }
    }

}
