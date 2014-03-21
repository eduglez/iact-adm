package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.AdscripcionJpaController;
import jsf.util.JsfUtil;

import personal.modelo.Adscripcion;

public class AdscripcionController {

    public AdscripcionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (AdscripcionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "adscripcionJpa");
        converter = new AdscripcionConverter();
    }
    private Adscripcion adscripcion = null;
    private List<Adscripcion> adscripcionItems = null;
    private AdscripcionJpaController jpaController = null;
    private AdscripcionConverter converter = null;

    public SelectItem[] getAdscripcionItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findAdscripcionEntities(), false);
    }

    public SelectItem[] getAdscripcionItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findAdscripcionEntities(), true);
    }

   
}
