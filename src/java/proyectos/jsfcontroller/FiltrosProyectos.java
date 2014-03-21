package proyectos.jsfcontroller;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import personal.modelo.Empleado;
import proyectos.modelo.EstadoProyecto;
import proyectos.modelo.LineaInvestigacion;
import proyectos.modelo.Proyecto;


public class FiltrosProyectos {

    public static void filtrarRangoFechaSolicitud(Collection<Proyecto> c, Date despuesDe, Date antesDe) {
        Proyecto p;
        if(despuesDe==null||antesDe==null)
            return;

        for (Iterator<Proyecto> it = c.iterator(); it.hasNext(); ){
            p=it.next();
            if(p==null)
                continue;
            
            if (p.getFechaSolicitud()==null ||
                    !(
                    despuesDe.before(p.getFechaSolicitud())
                    &&antesDe.after(p.getFechaSolicitud())))
                it.remove();
        }
    }

    public static void filtrarRangoFechaAceptacion(Collection<Proyecto> c, Date despuesDe, Date antesDe) {
        Proyecto p;
        if(despuesDe==null||antesDe==null)
            return;

        for (Iterator<Proyecto> it = c.iterator(); it.hasNext(); ){
            p=it.next();
            if(p==null)
                continue;
            if (p.getFechaAceptacion()==null ||
                    !(
                        despuesDe.before(p.getFechaAceptacion())
                        &&antesDe.after(p.getFechaAceptacion())))
                it.remove();
        }
    }

    public static void filtrarRangoFechaInicio(Collection<Proyecto> c, Date despuesDe, Date antesDe) {
        Proyecto p;
        if(despuesDe==null||antesDe==null)
            return;
        
        for (Iterator<Proyecto> it = c.iterator(); it.hasNext(); ){
            p=it.next();
            if(p==null)
                continue;
            if (p.getFechaInicio()==null ||
                    !(
                    despuesDe.before(p.getFechaInicio())
                    &&antesDe.after(p.getFechaInicio())))
                it.remove();
        }
    }

    public static void filtrarPorEstado(Collection<Proyecto> c, Collection<EstadoProyecto> estados){
        for(Iterator<Proyecto> it= c.iterator();it.hasNext();)
            if(!(estados.contains(it.next().getEstadoProyecto())))
                it.remove();
    }

    public static void filtrarPorResponsable(Collection<Proyecto> c, Empleado responsable){
        Proyecto p;
        for(Iterator<Proyecto> it= c.iterator();it.hasNext();){
            p=it.next();
            if(p.getResponsable()==null
                    || !(responsable.equals(p.getResponsable().getEmpleadoIACT())))
                it.remove();
        }
    }

    public static void filtroJustificarMenosDeUnMes(Collection<Proyecto> c){
        for(Iterator<Proyecto> it= c.iterator();it.hasNext();)
            if(!(it.next().isJustificarEnMenosUnMes()))
                it.remove();
    }

    public static void filtroFondosFeder(Collection<Proyecto> c){
        for(Iterator<Proyecto> it=c.iterator();it.hasNext();)
            if(!(it.next().isFinanciadoFondosFEDER()))
                it.remove();
    }

    public static void filtroContrato(Collection<Proyecto> c){
        for(Iterator<Proyecto> it=c.iterator();it.hasNext();)
            if(!(it.next().isContrato()))
                it.remove();
    }


    public static void filtroLineaInvestigacion(Collection<Proyecto> c, LineaInvestigacion l){
        Proyecto p;
        for(Iterator<Proyecto> it=c.iterator();it.hasNext();){
            p=it.next();
             if(p==null)
                continue;
            if(p.getResponsable()==null)
                it.remove();
            else if(p.getResponsable().getEmpleadoIACT()==null)
                it.remove();
            else if(p.getResponsable().getEmpleadoIACT().getContratoActual()==null)
                it.remove();
            else if(p.getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion()==null)
                it.remove();
            else if(p.getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().isLineaInvestigacionRaiz()){
                if(!p.getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().equals(l))
                    it.remove();
            }else{
                if(!p.getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().equals(l)){
                    if(!p.getResponsable().getEmpleadoIACT().getContratoActual().getLineaInvestigacion().getLineaInvestigacionPadre().equals(l))
                            it.remove();
                }
            }
        }
    }
}
