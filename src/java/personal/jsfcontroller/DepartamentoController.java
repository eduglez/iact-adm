package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.DepartamentoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import personal.modelo.Departamento;


public class DepartamentoController {

    public DepartamentoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (DepartamentoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "departamentoJpa");
    }
    private Departamento departamento = null;

    private DepartamentoJpaController jpaController = null;

    public SelectItem[] getDepartamentoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findDepartamentoEntities(), false);
    }

    public SelectItem[] getDepartamentoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findDepartamentoEntities(), true);
    }

    public Departamento getDepartamento() {
        if (departamento == null) {
            departamento = new Departamento();
        }
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    
    public String listSetup() {
        reset();
        return "departamentos";
    }

    public String createSetup() {
        reset();
        departamento = new Departamento();
        return "departamento-insertar";
    }

    public String create() {
        try {
            jpaController.create(departamento);
            JsfUtil.addSuccessMessage("Departamento se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }

  
    public String destroy() {
        String id = departamento.getId();
        
        try {
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Departamento se ha borrado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }



    public List<Departamento> getDepartamentoItems() {
        return jpaController.findDepartamentoEntities();
  
    }

    private void reset() {
        departamento = null;
        

    }

   

}
