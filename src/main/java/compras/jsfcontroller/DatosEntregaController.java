/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package compras.jsfcontroller;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;
import compras.jpacontroller.DatosEntregaJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import jsf.util.PagingInfo;
import compras.modelo.DatosEntrega;

/**
 *
 * @author edu
 */
public class DatosEntregaController {

    public DatosEntregaController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (DatosEntregaJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "datosEntregaJpa");
    }
    private DatosEntrega datosEntrega = null;
    private List<DatosEntrega> datosEntregaItems = null;
    private DatosEntregaJpaController jpaController = null;
    
    public SelectItem[] getDatosEntregaItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findDatosEntregaEntities(), false);
    }

    public SelectItem[] getDatosEntregaItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findDatosEntregaEntities(), true);
    }

    public DatosEntrega getDatosEntrega() {
        
        if (datosEntrega == null) {
            datosEntrega = new DatosEntrega();
        }
        return datosEntrega;
    }

    public String listSetup() {
        reset();
        return "datos-entrega";
    }

    public String createSetup() {
        reset();
        datosEntrega=new DatosEntrega();
        return "datosEntrega-insertar";
    }

    public String create() {
        try {
            jpaController.create(datosEntrega);
            JsfUtil.addSuccessMessage("Datos de Entrega was successfully created.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A persistence error occurred.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("datosEntrega_detail");
    }

    public String editSetup() {
        return scalarSetup("datosEntrega-editar");
    }

    private String scalarSetup(String destination) {
        DatosEntrega d = datosEntrega;
        reset();
        datosEntrega=d;
        if (datosEntrega == null) {
            String requestCuerpoString = JsfUtil.getRequestParameter("jsfcrud.currentDatosEntrega");
            JsfUtil.addErrorMessage("The Datos Entrega with id " + requestCuerpoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
      
        if (datosEntrega == null) {

                JsfUtil.addErrorMessage("No se puede editar");

            return null;
        }
        try {
            jpaController.edit(datosEntrega);
            JsfUtil.addSuccessMessage("Datos de entrega se ha modificado satisfactoriamente");
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
            String id = datosEntrega.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("Datos entrega was successfully deleted.");
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

    public List<DatosEntrega> getDatosEntregaItems() {
        if (datosEntregaItems == null) {
            
            datosEntregaItems = jpaController.findDatosEntregaEntities();
        }
        return datosEntregaItems;
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
        datosEntrega = null;
        datosEntregaItems = null;
        
    }

    
    public void selectionListener(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        datosEntrega=datosEntregaItems.get(selectedRowIndex);
    }

}
