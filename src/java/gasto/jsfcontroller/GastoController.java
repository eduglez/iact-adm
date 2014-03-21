package gasto.jsfcontroller;

import javax.faces.context.FacesContext;
import gasto.jpacontroller.*;import java.util.Arrays;
import gasto.modelo.*;
import java.util.Collection;
import java.util.Date;
import javax.faces.model.SelectItem;
import jsf.util.JsfUtil;

public class GastoController {



    // <editor-fold defaultstate="collapsed" desc="Gasto gasto">
    private Gasto gasto;

    public Gasto getGasto() {
        return gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="GastoRealizado gastoRealizado">
    private GastoRealizado gastoRealizado;

    public GastoRealizado getGastoRealizado() {
        return gastoRealizado;
    }

    public void setGastoRealizado(GastoRealizado gastoRealizado) {
        this.gastoRealizado = gastoRealizado;
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AsignacionGasto asignacion">
    private AsignacionGasto asignacion;

    public AsignacionGasto getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AsignacionGasto asignacion) {
        this.asignacion = asignacion;
    }// </editor-fold>
    


    private FacesContext facesContext;

    private GastoJpaController gastoJpa;

    public GastoController() {
        facesContext = FacesContext.getCurrentInstance();
        gastoJpa = (GastoJpaController) facesContext.getApplication().getELResolver().getValue(facesContext.getELContext(), null, "gastoJpa");
       
    }



    public String prepairRegistrarGasto() {
        gastoRealizado=new GastoRealizado();
        gastoRealizado.setFecha(new Date());

        asignacion= new AsignacionGasto();

        return "gasto-registrar-gasto";
    }

    public String registrarGasto() {
        try {
            AsignacionGasto asignacionPersistida= gastoJpa.findAsignacionGasto(asignacion);

            if(asignacionPersistida!=null){
                asignacion=asignacionPersistida;
            }else{
                gastoJpa.create(asignacion);
            }

            gastoRealizado.setAsignadoA(asignacion);

            gastoJpa.create(gastoRealizado);

            return "gasto-ver-gasto";

        } catch (Exception e) {
            e.printStackTrace();
            return "inventario";
        }

    }


    public String prepairRegistrarTipoGasto(){
        gasto= new Gasto();
        return "gasto-registrar-tipogasto";
    }

    public String registrarTipoGasto() {
        try{
            gastoJpa.create(gasto);
            return "gasto-ver-tipogasto";
        }catch(Exception e){
            e.printStackTrace();
            return "inventario";
        }
    }
    
    public SelectItem[] getGastosSelectItems(){
        return JsfUtil.getSelectItems(gastoJpa.findGastos(), true);
    }

    public SelectItem[] getTipoGastoSelectItems(){
        return JsfUtil.getSelectItems(Arrays.asList(Gasto.TIPOS_GASTO), true);
    }

    public Collection<GastoRealizado> getGastosRealizados(){
        return gastoJpa.findGastosRealizados();
    }

    public Collection<GastoRealizado> getUltimosGastosRealizados(){
        return gastoJpa.findUltimosGastosRealizados(5);
    }


}
