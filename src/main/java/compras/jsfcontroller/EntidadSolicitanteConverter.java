package compras.jsfcontroller;

import compras.jpacontroller.EntidadSolicitanteJpaController;
import compras.modelo.EntidadSolicitante;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;



public class EntidadSolicitanteConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        EntidadSolicitanteJpaController controller = (EntidadSolicitanteJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "entidadSolicitanteJpa");
        return controller.findEntidadSolicitante(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof EntidadSolicitante) {
            EntidadSolicitante o = (EntidadSolicitante) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.EntidadSolicitante");
        }
    }

}
