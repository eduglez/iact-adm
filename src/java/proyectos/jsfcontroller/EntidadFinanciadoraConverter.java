//TODO Documentar!!!!
package proyectos.jsfcontroller;

import proyectos.jpacontroller.EntidadFinanciadoraJpaController;
import proyectos.modelo.EntidadFinanciadora;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class EntidadFinanciadoraConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        EntidadFinanciadoraJpaController controller = (EntidadFinanciadoraJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entidadFinanciadoraJpa");
        return controller.findEntidadFinanciadora(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EntidadFinanciadora) {
            EntidadFinanciadora o = (EntidadFinanciadora) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: proyectos.modelo.EntidadFinanciadora");
        }
    }

}
