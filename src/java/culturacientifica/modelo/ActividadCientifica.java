package culturacientifica.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import personal.modelo.Empleado;

@Entity
public class ActividadCientifica implements Serializable {

    public final static String CD_DVD="CD/DVD";

    public final static String CONFERENCIA="Conferencia o mesa redonda";

    public final static String DIA_PUERTAS_ABIERTAS="Día de puertas abiertas";

    public final static String EXPOSICION="Exposición";

    public final static String FERIA="Feria de la ciencia o tecnológica";

    public final static String OTRA_ACTIVIDAD="Otra actividad financiada";

    public final static String RUTA_CIENTIFICA="Ruta científica";

    public final static String SEMANA_CIENCIA="Semana de la ciencia";

    public final static String TALLER="Seminario/Taller";

    public final static String[] ACTIVIDADES=
        {
         CD_DVD,
         CONFERENCIA,
         DIA_PUERTAS_ABIERTAS,
         EXPOSICION,
         FERIA,
         OTRA_ACTIVIDAD,
         RUTA_CIENTIFICA,
         SEMANA_CIENCIA,
         TALLER};

    public boolean isCDDVD(){
        return getTipo().equals(CD_DVD);
    }

    public boolean isConferencia(){
        return getTipo().equals(CONFERENCIA);
    }

    public boolean isDiaPuertasAbiertas(){
        return getTipo().equals(DIA_PUERTAS_ABIERTAS);
    }

    public boolean isExposicion(){
        return getTipo().equals(EXPOSICION);
    }

    public boolean isFeria(){
        return getTipo().equals(FERIA);
    }

    public boolean isOtraActividad(){
        return getTipo().equals(OTRA_ACTIVIDAD);
    }

    public boolean isRutaCientifica(){
        return getTipo().equals(RUTA_CIENTIFICA);
    }

    public boolean isSemanaCiencia(){
        return getTipo().equals(SEMANA_CIENCIA);
    }

    public boolean isTaller(){
        return getTipo().equals(TALLER);
    }

    public final static String FINANCIADO_PLAN_NACIONAL="Plan Nacional";

    public final static String FINANCIADO_OTROS_NACINAL="Otros (nacional)";

    public final static String FINANCIADO_UNION_EUROPEA="Unión Europea";

    public final static String FINANCIADO_OTROS_INTERNACIONAL="Otros (internacional)";

    public final static String FINANCIADO_COMUNIDADES_AUTONOMAS="CC.AA.";

    public final static String NO_FINANCIADA="No financiada";

    public final static String[] ORGANOS_FINANCIADORES=
        {FINANCIADO_PLAN_NACIONAL,
         FINANCIADO_OTROS_NACINAL,
         FINANCIADO_UNION_EUROPEA,
         FINANCIADO_OTROS_INTERNACIONAL,
         FINANCIADO_COMUNIDADES_AUTONOMAS,
         NO_FINANCIADA};

    public boolean isFinanciadoPlanNacional(){
        return getOrganismoFinanciador().equals(FINANCIADO_PLAN_NACIONAL);
    }

    public boolean isFinanciadoOtrosNacional(){
        return getOrganismoFinanciador().equals(FINANCIADO_OTROS_NACINAL);
    }

    public boolean isFinanciadoUnionEuropea(){
        return getOrganismoFinanciador().equals(FINANCIADO_UNION_EUROPEA);
    }

    public boolean isFinanciadoOtrosInternacional(){
        return getOrganismoFinanciador().equals(FINANCIADO_OTROS_INTERNACIONAL);
    }

    public boolean isFinanciadoComunidadesAutonomas(){
        return getOrganismoFinanciador().equals(FINANCIADO_COMUNIDADES_AUTONOMAS);
    }

    public boolean isNoFinanciado(){
        return getOrganismoFinanciador().equals(NO_FINANCIADA);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean borrado;

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
    
    @Column(length= 5000)
    private String notas;

    private String descripcion;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaFin;

    private String lugar;

    private String ISBN;

    private String titulo;

    private String tipo;

    @ManyToMany
    private Collection<Empleado> autoresIACT;

    private long cantidad;

    private String organismoFinanciador;

    @OneToMany(mappedBy = "actividadCientifica", cascade={CascadeType.ALL})
    private Collection<DocumentoActividadCientifica> documentos;

    public Collection<DocumentoActividadCientifica> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Collection<DocumentoActividadCientifica> documentos) {
        this.documentos = documentos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ActividadCientifica)) {
            return false;
        }
        ActividadCientifica other = (ActividadCientifica) object;
        if ((getId() == null && other.getId() != null) || (getId() != null && !getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   
    public Collection<Empleado> getAutoresIACT() {
        return autoresIACT;
    }

    public void setAutoresIACT(Collection<Empleado> autoresIACT) {
        this.autoresIACT = autoresIACT;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getCantidad() {
        return cantidad;
    }

    public void setCantidad(long cantidad) {
        this.cantidad = cantidad;
    }

    public String getOrganismoFinanciador() {
        return organismoFinanciador;
    }

    public void setOrganismoFinanciador(String organismoFinanciador) {
        this.organismoFinanciador = organismoFinanciador;
    }

    public ActividadCientifica() {
        setTipo(TALLER);
        setOrganismoFinanciador(NO_FINANCIADA);
        setAutoresIACT(new ArrayList<Empleado>());
        setDocumentos(new ArrayList<DocumentoActividadCientifica>());
    }

    public String getAutoresString(){
        String autores="";
        for(Empleado e:getAutoresIACT()){
            autores+=e.getApellidos()+", "+e.getNombre()+"; ";
        }
        
        return autores.length()>2?autores.substring(0,autores.length()-2):"";
    }


    public Date getFechaRepresentativa(){
        return getFecha()!=null?getFecha():getFechaInicio();
    }

    public String getTituloRepresentativo(){
        return getTitulo()!=null?getTitulo():getDescripcion();
    }
    
}
