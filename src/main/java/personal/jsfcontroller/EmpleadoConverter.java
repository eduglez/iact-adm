package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.EmpleadoJpaController;
import personal.modelo.Empleado;

public class EmpleadoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(string);
        EmpleadoJpaController controller = (EmpleadoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "empleadoJpa");
        return controller.findEmpleado(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Empleado) {
            Empleado o = (Empleado) object;
            return o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Empleado");
        }
    }

}
