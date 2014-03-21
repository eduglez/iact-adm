package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.JornadaLaboralJpaController;
import jsf.util.JsfUtil;

import personal.modelo.JornadaLaboral;

public class JornadaLaboralController {

    public JornadaLaboralController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (JornadaLaboralJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "jornadaLaboralJpa");
        converter = new JornadaLaboralConverter();
    }
    private JornadaLaboral tipoBecario = null;
    private List<JornadaLaboral> tipoBecarioItems = null;
    private JornadaLaboralJpaController jpaController = null;
    private JornadaLaboralConverter converter = null;

    public SelectItem[] getJornadaLaboralItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findJornadaLaboralEntities(), false);
    }

    public SelectItem[] getJornadaLaboralItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findJornadaLaboralEntities(), true);
    }

   
}
