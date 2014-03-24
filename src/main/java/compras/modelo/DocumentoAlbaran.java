package compras.modelo;

import personal.modelo.*;
import com.icesoft.faces.context.Resource;
import java.io.File;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import jsf.util.JsfUtil;



@Entity
public class DocumentoAlbaran implements Serializable {
    @Id
    private String rutaDocumento;

    @ManyToOne
    private Albaran albaran;

   private String nombreDocumento;

    public Albaran getAlbaran() {
        return albaran;
    }

    public void setAlbaran(Albaran albaran) {
        this.albaran = albaran;
    }

       

   
    public String getId() {
        return getRutaDocumento();
    }

   
    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

   public Resource getResource(){
       return new com.icesoft.faces.context.FileResource(new File(rutaDocumento));
   }

   public File getFile(){
       return new File(getRutaDocumento());
   }

   public void setFile(File fichero){
       setRutaDocumento(fichero.getAbsolutePath());
   }
   
   public String getCreadoHace(){
       File fichero = getFile();
       Date ahora=new Date();
       long diferencia=ahora.getTime()-fichero.lastModified();

       int cantidad;
       if((diferencia-86400000)>=0){
            cantidad=(int)((float)diferencia/86400000);
            if(cantidad==1){
                return "1 día";
            }else
                return cantidad+" días";

       }else if((diferencia-3600000)>=0){
           cantidad=(int)((float)diferencia/3600000);
            if(cantidad==1){
                return "1 hora";
            }else
                return cantidad+" horas";
       }else if((diferencia-60000)>=0){
           cantidad=(int)((float)diferencia/60000);
            if(cantidad==1){
                return "1 minuto";
            }else
                return cantidad+" minutos";
       }else{
           cantidad=(int)((float)diferencia/1000);
            if(cantidad==1){
                return "1 segundo";
            }else
                return cantidad+" segundos";
       }

   }

   public String borrar(){
          if(getFile().exists()){
            if(getFile().delete()){
                if(getAlbaran()!=null){
                    getAlbaran().setDocumento(null);
                }
                JsfUtil.addSuccessMessage("Documento borrado satisfactoriamente");
            }else{
                JsfUtil.addErrorMessage("El documento no se ha podido borrar");
            }

        }else{
            JsfUtil.addErrorMessage("El documento no existe");

        }
       return null;
   }


   public String asignarAAlbaran(){
            File directorioEmpleado = new File("/home/intranet/DOCUMENTOS/pedidos/" + getAlbaran().getPedido().getId()+"/"+getAlbaran().getNumAlbaran());
            if (!directorioEmpleado.exists()) {
                directorioEmpleado.mkdirs();
            }

            if(getFile()!=null){

                File f=getFile();
                File fnuevo=new File(directorioEmpleado,"albaran.pdf");
                f.renameTo(fnuevo);
                setFile(fnuevo);
                getAlbaran().setDocumento(this);
                f.delete();
            }else{
                JsfUtil.addErrorMessage("No se ha seleccionado ningún documento");
            }

        return null;
   }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentoAlbaran other = (DocumentoAlbaran) obj;
        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return getNombreDocumento();
    }




}
