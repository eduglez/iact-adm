package compras.jsfcontroller;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;

import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;
import compras.jpacontroller.DatosFacturacionJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;

import compras.modelo.DatosFacturacion;

public class DatosFacturacionController {

    public DatosFacturacionController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (DatosFacturacionJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosFacturacionJpa");
    }
    private DatosFacturacion datosFacturacion = null;
    private List<DatosFacturacion> datosFacturacionItems = null;
    private DatosFacturacionJpaController jpaController = null;
    
    public SelectItem[] getDatosFacturacionItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findDatosFacturacionEntities(), false);
    }

    public SelectItem[] getDatosFacturacionItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findDatosFacturacionEntities(), true);
    }

    public DatosFacturacion getDatosFacturacion() {
        
        if (datosFacturacion == null) {
            datosFacturacion = new DatosFacturacion();
        }
        return datosFacturacion;
    }

    public String listSetup() {
        reset();
        return "datos-facturacion";
    }

    public String createSetup() {
        reset();
        datosFacturacion=new DatosFacturacion();
        return "datosFacturacion-insertar";
    }

    public String create() {
        try {
            jpaController.create(datosFacturacion);
            JsfUtil.addSuccessMessage("Datos de facturación was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("datosFacturacion_detail");
    }

    public String editSetup() {
        return scalarSetup("datosFacturacion-editar");
    }

    private String scalarSetup(String destination) {
        DatosFacturacion d=datosFacturacion;
        reset();
        datosFacturacion = d;
        if (datosFacturacion == null) {
            String requestCuerpoString = JsfUtil.getRequestParameter("jsfcrud.currentProveedor");
            JsfUtil.addErrorMessage("The proveedor with id " + requestCuerpoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
      
        if (datosFacturacion == null) {

                JsfUtil.addErrorMessage("No se puede editar");

            return null;
        }
        try {
            jpaController.edit(datosFacturacion);
            JsfUtil.addSuccessMessage("Los datos de facturación se ha modificado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String destroy() {
 
        try {
            String id = datosFacturacion.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Los datos de facturación se han borrado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<DatosFacturacion> getDatosFacturacionItems() {
        if (datosFacturacionItems == null) {
            
            datosFacturacionItems = jpaController.findDatosFacturacionEntities();
        }
        return datosFacturacionItems;
    }

    

    private String relatedControllerOutcome() {
        String relatedControllerString = JsfUtil.getRequestParameter("jsfcrud.relatedController");
        String relatedControllerTypeString = JsfUtil.getRequestParameter("jsfcrud.relatedControllerType");
        if (relatedControllerString != null && relatedControllerTypeString != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            Object relatedController = context.getApplication().getELResolver().getValue(context.getELContext(), null, relatedControllerString);
            try {
                Class<?> relatedControllerType = Class.forName(relatedControllerTypeString);
                Method detailSetupMethod = relatedControllerType.getMethod("detailSetup");
                return (String) detailSetupMethod.invoke(relatedController);
            } catch (ClassNotFoundException e) {
                throw new FacesException(e);
            } catch (NoSuchMethodException e) {
                throw new FacesException(e);
            } catch (IllegalAccessException e) {
                throw new FacesException(e);
            } catch (InvocationTargetException e) {
                throw new FacesException(e);
            }
        }
        return null;
    }

    private void reset() {
        datosFacturacion = null;
        datosFacturacionItems = null;
        
    }

    
    public void selectionListener(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        datosFacturacion=datosFacturacionItems.get(selectedRowIndex);
    }

}
