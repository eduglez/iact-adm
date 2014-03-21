package personal.jsfcontroller;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import personal.jpacontroller.CategoriaJpaController;
import personal.modelo.Categoria;

public class CategoriaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id = string;
        CategoriaJpaController controller = (CategoriaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "categoriaJpa");
        return controller.findCategoria(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Categoria) {
            Categoria o = (Categoria) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: personal.modelo.Categoria");
        }
    }

}
