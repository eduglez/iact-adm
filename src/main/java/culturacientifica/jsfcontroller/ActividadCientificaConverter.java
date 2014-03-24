package culturacientifica.jsfcontroller;


import culturacientifica.jpacontroller.ActividadCientificaJpaController;
import culturacientifica.modelo.ActividadCientifica;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;



public class ActividadCientificaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {
        if (string == null || string.length() == 0) {
            return null;
        }
        Long id=Long.parseLong(string);
        ActividadCientificaJpaController controller = (ActividadCientificaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "actividadCientificaJpa");
        return controller.findActividadCientifica(id);
    }

    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof ActividadCientifica) {
            ActividadCientifica o = (ActividadCientifica) object;
            return o.getId() == null ? "" : o.getId().toString();
        } else {
            throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: culturacienfica.modelo.ActividadCientifica");
        }
    }

}
