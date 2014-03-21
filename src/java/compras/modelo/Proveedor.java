package compras.modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Proveedor implements Serializable {

    @Id
    private String razonSocial;
    private String cif;
    private String telefono;
    private String direccion;
    private String eMail;
    private String fax;
    private String nombrePersonaContacto;
    private String observaciones;


    public String getRazonSocial() {
        return razonSocial;
    }


    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }


    public String getCif() {
        return cif;
    }


    public void setCif(String cif) {
        this.cif = cif;
    }


    public String getTelefono() {
        return telefono;
    }


    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }


    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getEMail() {
        return eMail;
    }


    public void setEMail(String eMail) {
        this.eMail = eMail;
    }


    public String getFax() {
        return fax;
    }


    public void setFax(String fax) {
        this.fax = fax;
    }


    public String getNombrePersonaContacto() {
        return nombrePersonaContacto;
    }


    public void setNombrePersonaContacto(String nombrePersonaContacto) {
        this.nombrePersonaContacto = nombrePersonaContacto;
    }


    public String getObservaciones() {
        return observaciones;
    }


    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


    public String getId() {
        return getRazonSocial();
    }


    @Override
    public String toString() {
        return getRazonSocial();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)) {
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }




}
