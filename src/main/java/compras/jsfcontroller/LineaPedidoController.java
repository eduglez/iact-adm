package compras.jsfcontroller;

import compras.jpacontroller.PedidoJpaController;
import compras.modelo.LineaPedido;
import compras.modelo.Proveedor;
import java.util.Collections;
import java.util.List;
import javax.faces.context.FacesContext;



public class LineaPedidoController {


    private PedidoJpaController pedidosJpa;

    public LineaPedidoController() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        pedidosJpa = (PedidoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "pedidoJpa");
        filtroProveedor=null;
        filtroInvetariables=true;
        filtroFungibles=true;
    }

    private boolean filtroInvetariables;
    private boolean filtroFungibles;
    private boolean filtroOtrosGastos;
    private Proveedor filtroProveedor;

    public boolean isFiltroFungibles() {
        return filtroFungibles;
    }

    public void setFiltroFungibles(boolean filtroFungibles) {
        this.filtroFungibles = filtroFungibles;
    }

    public boolean isFiltroInvetariables() {
        return filtroInvetariables;
    }

    public void setFiltroInvetariables(boolean filtroInvetariables) {
        this.filtroInvetariables = filtroInvetariables;
    }

    public boolean isFiltroOtrosGastos() {
        return filtroOtrosGastos;
    }

    public void setFiltroOtrosGastos(boolean filtroOtrosGastos) {
        this.filtroOtrosGastos = filtroOtrosGastos;
    }

    
    public Proveedor getFiltroProveedor() {
        return filtroProveedor;
    }

    public void setFiltroProveedor(Proveedor filtroProveedor) {
        this.filtroProveedor = filtroProveedor;
    }


    public List<LineaPedido> getLineasPedidoItems(){
        List<LineaPedido> lineasPedido=pedidosJpa.findLineasPedido();
        if(!filtroFungibles){
            FiltrosLineaPedido.filtrarFungible(lineasPedido);
        }
        
        if(!filtroInvetariables){
            FiltrosLineaPedido.filtrarInventariable(lineasPedido);
        }
        
        if(!filtroOtrosGastos){
            FiltrosLineaPedido.filtrarOtrosGastos(lineasPedido);
        }

        if(filtroProveedor!=null){
            FiltrosLineaPedido.seleccionarPorProveedor(lineasPedido, filtroProveedor);
        }

        Collections.sort(lineasPedido, new LineaPedidoComparator(ascendente, columnaOrden));
        return lineasPedido;
    }


    private boolean ascendente;
    private String columnaOrden;

    public boolean isAscendente() {
        return ascendente;
    }

    public void setAscendente(boolean ascendente) {
        this.ascendente = ascendente;
    }

    public String getColumnaOrden() {
        return columnaOrden;
    }

    public void setColumnaOrden(String columnaOrden) {
        this.columnaOrden = columnaOrden;
    }

    

}
