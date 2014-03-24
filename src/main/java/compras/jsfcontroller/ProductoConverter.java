package compras.jsfcontroller;

import compras.jpacontroller.ProductoJpaController;
import compras.modelo.Producto;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;


public class ProductoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        ProductoJpaController controller = (ProductoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "productoJpa");
        return controller.findProducto(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Producto) {
            Producto o = (Producto) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.Producto");
        }
    }

}
