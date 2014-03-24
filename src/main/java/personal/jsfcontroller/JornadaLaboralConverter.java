package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.JornadaLaboralJpaController;
import personal.modelo.JornadaLaboral;

public class JornadaLaboralConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(string);
        JornadaLaboralJpaController controller = (JornadaLaboralJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "jornadaLaboralJpa");
        return controller.findJornadaLaboral(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof JornadaLaboral) {
            JornadaLaboral o = (JornadaLaboral) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.JornadaLaboral");
        }
    }

}
