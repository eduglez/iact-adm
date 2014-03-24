//TODO Documentar!!!
package proyectos.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import proyectos.jpacontroller.TipoProyectoJpaController;
import proyectos.modelo.TipoProyecto;


public class TipoProyectoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        TipoProyectoJpaController controller = (TipoProyectoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoProyectoJpa");
        return controller.findTipoProyecto(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof TipoProyecto) {
            TipoProyecto o = (TipoProyecto) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.TipoProyecto");
        }
    }

}
