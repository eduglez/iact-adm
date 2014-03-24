package compras.jsfcontroller;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import compras.modelo.Proveedor;
import compras.jpacontroller.ProveedorJpaController;

public class ProveedorConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        ProveedorJpaController controller = (ProveedorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proveedorJpa");
        return controller.findProveedor(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Proveedor) {
            Proveedor o = (Proveedor) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.Proveedor");
        }
    }

}
