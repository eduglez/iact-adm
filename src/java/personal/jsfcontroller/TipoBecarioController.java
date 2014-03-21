package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.TipoBecarioJpaController;
import jsf.util.JsfUtil;

import personal.modelo.TipoBecario;

public class TipoBecarioController {

    public TipoBecarioController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (TipoBecarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoBecarioJpa");
        converter = new TipoBecarioConverter();
    }
    private TipoBecario tipoBecario = null;
    private List<TipoBecario> tipoBecarioItems = null;
    private TipoBecarioJpaController jpaController = null;
    private TipoBecarioConverter converter = null;

    public SelectItem[] getTipoBecarioItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findTipoBecarioEntities(), false);
    }

    public SelectItem[] getTipoBecarioItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findTipoBecarioEntities(), true);
    }

   
}
