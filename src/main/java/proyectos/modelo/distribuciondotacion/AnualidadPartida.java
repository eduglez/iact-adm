package proyectos.modelo.distribuciondotacion;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import proyectos.modelo.Proyecto;

@Entity
public class AnualidadPartida implements Comparable<AnualidadPartida>, Serializable{

    // <editor-fold defaultstate="collapsed" desc="id">
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="TRANSIENT marcaBorrado">
    @Transient
    private boolean marcaBorrado;

    public boolean isMarcaBorrado() {
        return marcaBorrado;
    }

    public void setMarcaBorrado(boolean marcaBorrado) {
        this.marcaBorrado = marcaBorrado;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="proyecto (many AnualidadPartida toOne Proyecto)">
    @ManyToOne
    private Proyecto proyecto;

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="anualidad (many AnualidadPartida toOne Anualidad)">
    @ManyToOne
    private Anualidad anualidad;

    public Anualidad getAnualidad() {
        return anualidad;
    }

    public void setAnualidad(Anualidad anualidad) {
        this.anualidad = anualidad;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="complementosSalariales">
    private long complementosSalariales;

    public long getComplementosSalariales() {
        return complementosSalariales;
    }

    public void setComplementosSalariales(long complementosSalariales) {
        this.complementosSalariales = complementosSalariales;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="contratacionPersonal">
    private long contratacionPersonal;

    public long getContratacionPersonal() {
        return contratacionPersonal;
    }

    public void setContratacionPersonal(long contratacionPersonal) {
        this.contratacionPersonal = contratacionPersonal;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="seguridadSocial">
    private long seguridadSocial;

    public long getSeguridadSocial() {
        return seguridadSocial;
    }

    public void setSeguridadSocial(long seguridadSocial) {
        this.seguridadSocial = seguridadSocial;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="inventariable">
    private long inventariable;
    
    public long getInventariable() {
        return inventariable;
    }

    public void setInventariable(long inventariable) {
        this.inventariable = inventariable;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="fungible">
    private long fungible;

    public long getFungible() {
        return fungible;
    }

    public void setFungible(long fungible) {
        this.fungible = fungible;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="viajesDietas">
    private long viajesDietas;

    public long getViajesDietas() {
        return viajesDietas;
    }

    public void setViajesDietas(long viajesDietas) {
        this.viajesDietas = viajesDietas;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="otrosGastos">
    private long otrosGastos;

    public long getOtrosGastos() {
        return otrosGastos;
    }

    public void setOtrosGastos(long otrosGastos) {
        this.otrosGastos = otrosGastos;
    }// </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="costesIndirectos">
    private long costesIndirectos;

    public long getCostesIndirectos() {
        return costesIndirectos;
    }

    public void setCostesIndirectos(long costesIndirectos) {
        this.costesIndirectos = costesIndirectos;
    }// </editor-fold>

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnualidadPartida other = (AnualidadPartida) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    public int compareTo(AnualidadPartida o) {
        if(o.getAnualidad()!=null&&this.getAnualidad()!=null){
            return Integer.parseInt(o.getAnualidad().getAnio())-Integer.parseInt(this.getAnualidad().getAnio());
        }else{
            return 0;
        }
    }


    public long getTotalPersonal(){
        return complementosSalariales+contratacionPersonal+seguridadSocial;
    }

    public long getTotalGastosEjecucion(){
        return inventariable+fungible+viajesDietas+otrosGastos;
    }

    public long getTotalIndirectos(){
        return costesIndirectos;
    }

    public long getTotalInventariable(){
        return inventariable;
    }

    public long getTotalFuncionamiento(){
        return fungible+otrosGastos;
    }

    public long getTotal(){
        return complementosSalariales+contratacionPersonal+seguridadSocial+inventariable+fungible+viajesDietas+otrosGastos+costesIndirectos;
    }

    public AnualidadPartida(){
        complementosSalariales=0;
        contratacionPersonal=0;
        costesIndirectos=0;
        fungible=0;
        inventariable=0;
        otrosGastos=0;
        seguridadSocial=0;
        viajesDietas=0;
        marcaBorrado=false;
    }


    /*

    public long getGastoComplementosSalariales() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.COMPLEMENTOS_SALARIALES)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoContratacionPersonal() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.CONTRATACION_PERSONAL)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoCostesIndirectos() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.COSTES_INDIRECTOS)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoFungible() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.FUNGIBLE)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoInventariable() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.INVENTARIABLE)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoOtrosGastos() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.OTROS_GASTOS)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getGastoSeguridadSocial() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.SEGURIDAD_SOCIAL)){
                total+=g.getTotal();
            }
        }
        return total;
    }


    public long getGastoViajesDietas() {
        long total=0;
        for(Gasto g:getProyecto().getGastos()){
            if(getAnualidad().isEnAnualidad(g.getFecha()) && g.getTipoGasto().equals(Gasto.VIAJES_DIETAS)){
                total+=g.getTotal();
            }
        }
        return total;
    }

    public long getTotalGastoPersonal(){
        return getGastoComplementosSalariales()+getGastoContratacionPersonal()+getGastoSeguridadSocial();
    }

    public long getTotalGastoGastosEjecucion(){
        return getGastoInventariable()+getGastoFungible()+getGastoViajesDietas()+getGastoOtrosGastos();
    }

    public long getTotalGastoIndirectos(){
        return getGastoCostesIndirectos();
    }

    public long getTotalGastoInventariable(){
        return getGastoInventariable();
    }

    public long getTotalGastoFuncionamiento(){
        return getGastoFungible()+getGastoOtrosGastos();
    }

    public long getTotalGasto(){
        return getGastoComplementosSalariales()+getGastoContratacionPersonal()+getGastoSeguridadSocial()+getGastoInventariable()+getGastoFungible()+getGastoViajesDietas()+getGastoOtrosGastos()+getGastoCostesIndirectos();
    }

*/

}
