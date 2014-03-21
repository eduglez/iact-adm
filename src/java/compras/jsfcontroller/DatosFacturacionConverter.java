package compras.jsfcontroller;

import compras.jpacontroller.DatosFacturacionJpaController;
import compras.modelo.DatosFacturacion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class DatosFacturacionConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        DatosFacturacionJpaController controller = (DatosFacturacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosFacturacionJpa");
        return controller.findDatosFacturacion(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof DatosFacturacion) {
            DatosFacturacion o = (DatosFacturacion) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.DatosFacturacion");
        }
    }

}
