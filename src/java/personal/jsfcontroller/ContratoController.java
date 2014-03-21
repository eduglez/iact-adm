package personal.jsfcontroller;


import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.ContratoJpaController;
import jsf.util.JsfUtil;
import personal.modelo.Contrato;



public class ContratoController {

    public ContratoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ContratoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "contratoJpa");
        converter = new ContratoConverter();

    }
    private Contrato contrato = null;
    private List<Contrato> contratoItems = null;
    private ContratoJpaController jpaController = null;
    private ContratoConverter converter = null;


    public SelectItem[] getContratosVigentesItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findContratosVigentes(),true);
    }

  

}
