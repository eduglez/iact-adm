package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.TipoLaboralTemporalJpaController;
import personal.modelo.TipoLaboralTemporal;

public class TipoLaboralTemporalConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        TipoLaboralTemporalJpaController controller = (TipoLaboralTemporalJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoLaboralTemporalJpa");
        return controller.findTipoLaboralTemporal(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof TipoLaboralTemporal) {
            TipoLaboralTemporal o = (TipoLaboralTemporal) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.TipoLaboralTemporal");
        }
    }

}
