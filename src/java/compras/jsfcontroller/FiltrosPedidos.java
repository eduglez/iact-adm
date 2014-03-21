package compras.jsfcontroller;

import compras.modelo.Pedido;
import java.util.Collection;
import java.util.Iterator;
import proyectos.modelo.LineaInvestigacion;



public class FiltrosPedidos {

   

    public static void filtroLineaInvestigacion(Collection<Pedido> c, LineaInvestigacion l){
        Pedido p;
        for(Iterator<Pedido> it=c.iterator();it.hasNext();){
            p=it.next();
             if(p==null)
                continue;

            if(p.getProyecto()==null)
                it.remove();
            else if(p.getProyecto().getResponsable()==null)
                it.remove();
            else if(p.getProyecto().getResponsable().getEmpleadoIACT()==null)
                it.remove();
            else if(p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual()==null)
                it.remove();
            else if(p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion()==null)
                it.remove();
            else if(p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().isLineaInvestigacionRaiz()){
                if(!p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().equals(l))
                    it.remove();
            }else{
                if(!p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().equals(l)){
                    if(!p.getProyecto().getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().getLineaInvestigacionPadre().equals(l))
                            it.remove();
                }
            }
        }
    }
}
