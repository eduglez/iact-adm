package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.PuestoDeTrabajoJpaController;
import personal.modelo.PuestoDeTrabajo;

public class PuestoDeTrabajoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        PuestoDeTrabajoJpaController controller = (PuestoDeTrabajoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "puestoDeTrabajoJpa");
        return controller.findPuestoDeTrabajo(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof PuestoDeTrabajo) {
            PuestoDeTrabajo o = (PuestoDeTrabajo) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.PuestoDeTrabajo");
        }
    }

}
