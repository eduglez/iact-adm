package personal.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class TipoEmpleado implements Serializable {


    public static String FUNCIONARIO = "Funcionario";
    public static String ESTANCIA = "Estancia";
    public static String BECARIO = "Becario";
    public static String LABORAL_FIJO = "LaboralFijo";
    public static String LABORAL_TEMPORAL = "LaboralTemporal";
    public static String[] NOMBRES_TIPOS_EMPLEADOS = {FUNCIONARIO, ESTANCIA, BECARIO, LABORAL_FIJO, LABORAL_TEMPORAL};

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String tipoEmpleado;

    @OneToOne
    private Contrato contratoEmpleado;

    @ManyToOne
    private Empleado investigadorPrincipal;

    @ManyToOne
    private Adscripcion adscripcion;

    public Empleado getInvestigadorPrincipal() {
        return investigadorPrincipal;
    }

    public void setInvestigadorPrincipal(Empleado investigadorPrincipal) {
        this.investigadorPrincipal = investigadorPrincipal;
    }

    
    public Adscripcion getAdscripcion() {
        return adscripcion;
    }

    public void setAdscripcion(Adscripcion adscripcion) {
        this.adscripcion = adscripcion;
    }

    public Contrato getContratoEmpleado() {
        return contratoEmpleado;
    }

    public void setContratoEmpleado(Contrato contratoEmpleado) {
        this.contratoEmpleado = contratoEmpleado;
    }

  

    public String getTipoEmpleado() {
        return tipoEmpleado;
    }

    public void setTipoEmpleado(String tipoEmpleado) {
        this.tipoEmpleado = tipoEmpleado;
    }

    public TipoEmpleado() {
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @ManyToOne
    private TipoBecario tipoBecario;

    public TipoBecario getTipoBecario() {
        return tipoBecario;
    }

    public void setTipoBecario(TipoBecario tipoBecario) {
        this.tipoBecario = tipoBecario;
    }
    private String nivel;
    private String telefono;
    private String eMail;
    @ManyToOne
    private Cuerpo cuerpo;
    @ManyToOne
    private PuestoDeTrabajo puestoTrabajo;
    @ManyToOne
    private Grupo grupo;

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Cuerpo getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(Cuerpo cuerpo) {
        this.cuerpo = cuerpo;
    }

    public PuestoDeTrabajo getPuestoTrabajo() {
        return puestoTrabajo;
    }

    public void setPuestoTrabajo(PuestoDeTrabajo puestoTrabajo) {
        this.puestoTrabajo = puestoTrabajo;
    }
    @ManyToOne
    private Categoria categoria;
    @ManyToOne
    private EspecialidadLaboral especialidadLaboral;

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public EspecialidadLaboral getEspecialidadLaboral() {
        return especialidadLaboral;
    }

    public void setEspecialidadLaboral(EspecialidadLaboral especialidadLaboral) {
        this.especialidadLaboral = especialidadLaboral;
    }
    @ManyToOne
    private TipoLaboralTemporal tipoLaboralTemporal;

    public TipoLaboralTemporal getTipoLaboralTemporal() {
        return tipoLaboralTemporal;
    }

    public void setTipoLaboralTemporal(TipoLaboralTemporal tipoLaboralTemporal) {
        this.tipoLaboralTemporal = tipoLaboralTemporal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TipoEmpleado other = (TipoEmpleado) obj;
        if ((this.getId() == null) ? (other.getId() != null) : !this.getId().equals(other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

}
