package proyectos.jsfcontroller;

import proyectos.jpacontroller.LineaInvestigacionJpaController;
import proyectos.modelo.LineaInvestigacion;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;



public class LineaInvestigacionConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        String id=string;
        LineaInvestigacionJpaController controller = (LineaInvestigacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "lineaInvestigacionJpa");
        return controller.findLineaInvestigacion(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof LineaInvestigacion) {
            LineaInvestigacion o = (LineaInvestigacion) object;
            return o.getId();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: compras.modelo.LineaInvestigacion");
        }
    }

}
