package catalogo.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long codigoEnvio;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private Boolean enviado;
    @ManyToOne
    private Cliente cliente;
    @OneToMany(mappedBy = "OrdenCompra")
    private List<ProductoOrdenCompra> productosDeOrdenCompra;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrdenCompra)) {
            return false;
        }
        OrdenCompra other = (OrdenCompra) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "catalogo.modelo.OrdenCompra[ id=" + getId() + " ]";
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
     * @return the cliente
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the productosDeOrdenCompra
     */
    public List<ProductoOrdenCompra> getProductosDeOrdenCompra() {
        return productosDeOrdenCompra;
    }

    /**
     * @param productosDeOrdenCompra the productosDeOrdenCompra to set
     */
    public void setProductosDeOrdenCompra(List<ProductoOrdenCompra> productosDeOrdenCompra) {
        this.productosDeOrdenCompra = productosDeOrdenCompra;
    }

}
