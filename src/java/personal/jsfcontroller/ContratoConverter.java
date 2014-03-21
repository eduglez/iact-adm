package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.ContratoJpaController;
import personal.modelo.Contrato;

public class ContratoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = Long.parseLong(string);
        ContratoJpaController controller = (ContratoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "contratoJpa");
        return controller.findContrato(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Contrato) {
            Contrato o = (Contrato) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Contrato");
        }
    }

}
