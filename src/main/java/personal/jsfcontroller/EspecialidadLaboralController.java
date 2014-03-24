package personal.jsfcontroller;

import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import personal.jpacontroller.EspecialidadLaboralJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import personal.modelo.EspecialidadLaboral;


public class EspecialidadLaboralController {

    public EspecialidadLaboralController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (EspecialidadLaboralJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "especialidadLaboralJpa");
    }
    private EspecialidadLaboral especialidadLaboral = null;
    private EspecialidadLaboralJpaController jpaController = null;



    public SelectItem[] getEspecialidadLaboralItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findEspecialidadLaboralEntities(), false);
    }

    public SelectItem[] getEspecialidadLaboralItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findEspecialidadLaboralEntities(), true);
    }

    public EspecialidadLaboral getEspecialidadLaboral() {
     
        if (especialidadLaboral == null) {
            especialidadLaboral = new EspecialidadLaboral();
        }
        return especialidadLaboral;
    }

    public void setEspecialidadLaboral(EspecialidadLaboral especialidadLaboral) {
        this.especialidadLaboral = especialidadLaboral;
    }

    
    public String listSetup() {
        reset();
        return "especialidades-laborales";
    }

    public String createSetup() {
        reset();
        especialidadLaboral = new EspecialidadLaboral();
        return "especialidadLaboral-insertar";
    }

    public String create() {
        try {
            jpaController.create(especialidadLaboral);
            JsfUtil.addSuccessMessage("La Especialidad Laboral se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Error al crear la Especialidad Laboral. Puede que ya exista.");
            return null;
        }
        return listSetup();
    }

  

    public String destroy() {
 
        try {
            String id = especialidadLaboral.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("La Especialidad Laboral se ha creado satisfactoriamente.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e.getLocalizedMessage());
            return null;
        }
        return listSetup();
    }

   
    public List<EspecialidadLaboral> getEspecialidadLaboralItems() {     
        return jpaController.findEspecialidadLaboralEntities();  
    }


   
    private void reset() {
        especialidadLaboral = null;
        
    }

 

}
