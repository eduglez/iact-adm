package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.TipoLaboralTemporalJpaController;
import jsf.util.JsfUtil;

import personal.modelo.TipoLaboralTemporal;

public class TipoLaboralTemporalController {

    public TipoLaboralTemporalController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (TipoLaboralTemporalJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "tipoLaboralTemporalJpa");
        converter = new AdscripcionConverter();
    }
    private TipoLaboralTemporal  tipoLaboralTemporal = null;
    private List<TipoLaboralTemporal> tipoLaboralTemporalItems = null;
    private TipoLaboralTemporalJpaController jpaController = null;
    private AdscripcionConverter converter = null;

    public SelectItem[] getTipoLaboralTemporalItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findTipoLaboralTemporalEntities(), false);
    }

    public SelectItem[] getTipoLaboralTemporalItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findTipoLaboralTemporalEntities(), true);
    }

   
}
