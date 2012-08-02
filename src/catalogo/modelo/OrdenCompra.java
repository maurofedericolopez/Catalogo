package catalogo.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Mauro Federico Lopez
 */
@Entity
public class OrdenCompra implements Serializable {
    private static long serialVersionUID = 1L;

    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date miFecha;
    private Boolean enviado;
    private Long codigoEnvio;

    @ManyToOne
    private Cliente miCliente;

    @OneToMany
    private Collection<Producto> misProductos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        OrdenCompra other = (OrdenCompra) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.modelo.OrdenCompra[ id=" + id + " ]";
    }

    /**
     * @return the miFecha
     */
    public Date getMiFecha() {
        return miFecha;
    }

    /**
     * @param miFecha the miFecha to set
     */
    public void setMiFecha(Date miFecha) {
        this.miFecha = miFecha;
    }

    /**
     * @return the enviado
     */
    public Boolean getEnviado() {
        return enviado;
    }

    /**
     * @param enviado the enviado to set
     */
    public void setEnviado(Boolean enviado) {
        this.enviado = enviado;
    }

    /**
     * @return the codigoEnvio
     */
    public Long getCodigoEnvio() {
        return codigoEnvio;
    }

    /**
     * @param codigoEnvio the codigoEnvio to set
     */
    public void setCodigoEnvio(Long codigoEnvio) {
        this.codigoEnvio = codigoEnvio;
    }

    /**
     * @return the miCliente
     */
    public Cliente getMiCliente() {
        return miCliente;
    }

    /**
     * @param miCliente the miCliente to set
     */
    public void setMiCliente(Cliente miCliente) {
        this.miCliente = miCliente;
    }

    /**
     * @return the misProductos
     */
    public Collection<Producto> getMisProductos() {
        return misProductos;
    }

    /**
     * @param misProductos the misProductos to set
     */
    public void setMisProductos(Collection<Producto> misProductos) {
        this.misProductos = misProductos;
    }
    
}
