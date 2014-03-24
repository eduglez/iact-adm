package gasto.jsfcontroller;

import gasto.jsfcontroller.*;
import gasto.jpacontroller.*;
import gasto.modelo.*;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class GastoRealizadoConverter implements Converter {


    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        GastoJpaController controller = (GastoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "gastoJpa");
        return controller.findGastoRealizado(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof GastoRealizado) {
            GastoRealizado o = (GastoRealizado) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: gasto.modelo.GastoRealizado");
        }
    }

}
