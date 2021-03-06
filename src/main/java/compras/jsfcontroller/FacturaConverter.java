package compras.jsfcontroller;

import compras.jpacontroller.FacturaJpaController;
import compras.modelo.Factura;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


/**
 *
 * @author edu
 */
public class FacturaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id=new Long(string);
        FacturaJpaController controller = (FacturaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "facturaJpa");
        return controller.findFactura(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Factura) {
            Factura o = (Factura) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.Factura");
        }
    }

}
