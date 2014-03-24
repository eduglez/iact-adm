package proyectos.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import proyectos.jpacontroller.ProyectoJpaController;
import proyectos.modelo.distribuciondotacion.AnualidadPartida;


public class AnualidadPartidaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id=Long.parseLong(string);
        ProyectoJpaController controller = (ProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proyectoJpa");
        return controller.findAnualidadPartida(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof AnualidadPartida) {
            AnualidadPartida o = (AnualidadPartida) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.AnualidadPartida");
        }
    }

}
