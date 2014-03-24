package personal.jsfcontroller;

import java.util.Collection;
import java.util.Iterator;
import personal.modelo.Empleado;

public class FiltrosPersonal {

    public static void filtrarConContrato(Collection<Empleado> c) {
        for (Iterator<Empleado> it = c.iterator(); it.hasNext(); ){
            if(it.next().getContratoActual()!=null)
                it.remove();
        }
    }


    public static void filtrarSinContrato(Collection<Empleado> c) {
        for (Iterator<Empleado> it = c.iterator(); it.hasNext(); ){
            if(it.next().getContratoActual()==null)
                it.remove();
        }
    }
    
}
