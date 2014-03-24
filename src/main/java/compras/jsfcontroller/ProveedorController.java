package compras.jsfcontroller;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;

import javax.faces.context.FacesContext;

import javax.faces.model.SelectItem;
import compras.jpacontroller.ProveedorJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;

import compras.modelo.Proveedor;
import javax.faces.convert.Converter;


public class ProveedorController {

    public ProveedorController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ProveedorJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "proveedorJpa");
        converter=new ProveedorConverter();
    }
    private Proveedor proveedor = null;
    private List<Proveedor> proveedorItems = null;
    private ProveedorJpaController jpaController = null;
    private ProveedorConverter converter=null;

    public SelectItem[] getProveedorItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProveedorEntities(), false);
    }

    public SelectItem[] getProveedorItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProveedorEntities(), true);
    }

    public Proveedor getProveedor() {
        
        if (proveedor == null) {
            proveedor = new Proveedor();
        }
        return proveedor;
    }

    public String listSetup() {
        reset();
        return "proveedores";
    }

    public String createSetup() {
        reset();
        proveedor=new Proveedor();
        return "proveedor-insertar";
    }

    public String create() {
        try {
            jpaController.create(proveedor);
            JsfUtil.addSuccessMessage("Proveedor was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("proveedor_detail");
    }

    public String editSetup() {
        return scalarSetup("proveedor-editar");
    }

    private String scalarSetup(String destination) {
        Proveedor p=proveedor;
        reset();
        proveedor = p;
        if (proveedor == null) {
            String requestCuerpoString = JsfUtil.getRequestParameter("jsfcrud.currentProveedor");
            JsfUtil.addErrorMessage("The proveedor with id " + requestCuerpoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
      
        if (proveedor == null) {

                JsfUtil.addErrorMessage("No se puede editar");

            return null;
        }
        try {
            jpaController.edit(proveedor);
            JsfUtil.addSuccessMessage("Proveedor se ha modificado satisfactoriamente");
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
            String id = proveedor.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Proveedor was successfully deleted.");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return relatedOrListOutcome();
    }
    
    public Converter getConverter(){
        return converter;
    }

    private String relatedOrListOutcome() {
        String relatedControllerOutcome = relatedControllerOutcome();
        if (relatedControllerOutcome != null) {
            return relatedControllerOutcome;
        }
        return listSetup();
    }

    public List<Proveedor> getProveedorItems() {
        if (proveedorItems == null) {
            
            proveedorItems = jpaController.findProveedorEntities();
        }
        return proveedorItems;
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
        proveedor = null;
        proveedorItems = null;
        
    }

    
    public void selectionListener(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        proveedor=proveedorItems.get(selectedRowIndex);
    }

}
