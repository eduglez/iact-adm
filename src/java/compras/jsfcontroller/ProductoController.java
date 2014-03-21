package compras.jsfcontroller;

import com.icesoft.faces.component.ext.RowSelectorEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import compras.jpacontroller.ProductoJpaController;
import jsf.util.JsfUtil;
import jpa.exceptions.NonexistentEntityException;
import compras.modelo.Producto;


public class ProductoController {

   
    public ProductoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        jpaController = (ProductoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "productoJpa");
     
    }



    private Producto producto = null;
    private List<Producto> productoItems = null;
    private ProductoJpaController jpaController = null;
    
    public SelectItem[] getProductoItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(jpaController.findProductoEntities(), false);
    }

    public SelectItem[] getProductoItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(jpaController.findProductoEntities(), true);
    }

   

    public Producto getProducto() {
        
        if (producto == null) {
            producto = new Producto();
        }
        return producto;
    }

    public String listSetup() {
        reset();
        return "productos";
    }

    public String createSetup() {
        reset();
        producto=new Producto();
        return "producto-insertar";
    }

    public String create() {
        try {
            jpaController.create(producto);
            JsfUtil.addSuccessMessage("El producto se ha creado satisfactoriamente.");
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "A ocurrido un error al almacenar en la base de datos.");
            return null;
        }
        return listSetup();
    }

    public String detailSetup() {
        return scalarSetup("producto_detail");
    }

    public String editSetup() {
        return scalarSetup("producto-editar");
    }

    private String scalarSetup(String destination) {
        Producto p=producto;
        reset();
        producto = p;
        if (producto == null) {
            String requestCuerpoString = JsfUtil.getRequestParameter("jsfcrud.currentProveedor");
            JsfUtil.addErrorMessage("The producto with id " + requestCuerpoString + " no longer exists.");
            return relatedOrListOutcome();
        }
        return destination;
    }

    public String edit() {
      
        if (producto == null) {

                JsfUtil.addErrorMessage("No se puede editar");

            return null;
        }
        try {
            jpaController.edit(producto);
            JsfUtil.addSuccessMessage("Producto se ha modificado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return listSetup();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al almacenar en la base de datos");
            return null;
        }
        return listSetup();
    }

    public String destroy() {
 
        try {
            String id = producto.getId();
            jpaController.destroy(id);
            JsfUtil.addSuccessMessage("El producto se ha borrado satisfactoriamente");
        } catch (NonexistentEntityException ne) {
            JsfUtil.addErrorMessage(ne.getLocalizedMessage());
            return relatedOrListOutcome();
        } catch (Exception e) {
            JsfUtil.ensureAddErrorMessage(e, "Ha ocurrido un error al almacenar en la base de datos.");
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

    public List<Producto> getProductoItems() {
        if (productoItems == null) {
            
            productoItems = jpaController.findProductoEntities();
        }
        return productoItems;
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
        producto = null;
        productoItems = null;
        
    }

    
    public void selectionListener(RowSelectorEvent rse) {
        int selectedRowIndex = rse.getRow();
        producto=productoItems.get(selectedRowIndex);
    }

}
