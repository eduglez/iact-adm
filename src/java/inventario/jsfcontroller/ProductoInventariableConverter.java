package inventario.jsfcontroller;

import inventario.jpacontroller.InventarioJpaController;
import inventario.modelo.ProductoAlmacenado;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class ProductoInventariableConverter implements Converter {


    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id = new Long(string);
        InventarioJpaController controller = (InventarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioJpa");
        return controller.findProducto(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ProductoAlmacenado) {
            ProductoAlmacenado o = (ProductoAlmacenado) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: inventario.modelo.ProductoInventariable");
        }
    }

}
