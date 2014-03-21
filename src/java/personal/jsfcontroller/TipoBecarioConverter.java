package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.TipoBecarioJpaController;
import personal.modelo.TipoBecario;

public class TipoBecarioConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        TipoBecarioJpaController controller = (TipoBecarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoBecarioJpa");
        return controller.findTipoBecario(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof TipoBecario) {
            TipoBecario o = (TipoBecario) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.TipoBecario");
        }
    }

}
