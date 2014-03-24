package inventario.jsfcontroller;

import inventario.modelo.MovimientoEntrada;
import inventario.modelo.MovimientoSalida;
import java.util.Date;

public class Movimiento implements Comparable<Movimiento>{

    private MovimientoEntrada entrada;

    private MovimientoSalida salida;

    public MovimientoEntrada getEntrada() {
        return entrada;
    }

    public void setEntrada(MovimientoEntrada entrada) {
        this.entrada = entrada;
    }

    public MovimientoSalida getSalida() {
        return salida;
    }

    public void setSalida(MovimientoSalida salida) {
        this.salida = salida;
    }
    
    


    public Date getFecha(){
        if(isMovimientoEntrada())
            return entrada.getFechaEntrada();
        else if(isMovimientoSalida())
            return salida.getFechaSalida();
        else
            return null;
    }

    public Movimiento(MovimientoEntrada movimientoEntrada){
        this.entrada=movimientoEntrada;
    }

    public Movimiento(MovimientoSalida movimientoSalida){
        this.salida=movimientoSalida;
    }

    public boolean isMovimientoEntrada(){
        return entrada!=null && salida==null;
    }

    public boolean isMovimientoSalida(){
        return salida!=null&&entrada==null;
    }

    public int compareTo(Movimiento o) {
        if(this.getFecha()==null)
            return Integer.MIN_VALUE;

        if(o.getFecha()==null)
            return Integer.MAX_VALUE;
        
        return o.getFecha().compareTo(this.getFecha());
    }

}
