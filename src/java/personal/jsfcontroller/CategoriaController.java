package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.CategoriaJpaController;
import jsf.util.JsfUtil;

import personal.modelo.Categoria;

public class CategoriaController {

    public CategoriaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (CategoriaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "categoriaJpa");
        converter = new CategoriaConverter();
    }
    private Categoria categoria = null;
    private List<Categoria> categoriaItems = null;
    private CategoriaJpaController jpaController = null;
    private CategoriaConverter converter = null;

    public SelectItem[] getCategoriaItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findCategoriaEntities(), false);
    }

    public SelectItem[] getCategoriaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findCategoriaEntities(), true);
    }

   
}
