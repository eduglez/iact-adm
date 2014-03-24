package inventario.jsfcontroller;

import javax.faces.context.FacesContext;
import inventario.jpacontroller.InventarioJpaController;
import inventario.modelo.Asignacion;
import inventario.modelo.MovimientoEntrada;
import inventario.modelo.MovimientoSalida;
import inventario.modelo.ProductoAlmacenado;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;

public class InventarioController {

    private FacesContext facesContext;

    private InventarioJpaController inventarioJpa;

    public InventarioController() {
        facesContext = FacesContext.getCurrentInstance();
        inventarioJpa = (InventarioJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "inventarioJpa");
    }

    public Collection<Movimiento> getMovimientos(){
        ArrayList<Movimiento> movimientos=new ArrayList<Movimiento>();

        for(MovimientoEntrada me:inventarioJpa.findMovimientosEntrada()){
            movimientos.add(new Movimiento(me));
        }

        for(MovimientoSalida ms:inventarioJpa.findMovimientosSalida()){
            movimientos.add(new Movimiento(ms));
        }

        Collections.sort(movimientos);
        
        return movimientos;

    }


}
