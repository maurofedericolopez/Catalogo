package catalogo.modelo;

import java.io.Serializable;
import java.util.ArrayList;
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
    @Column(name = "idOrdenCompra")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = true)
    private Long codigoEnvio;
    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Boolean enviado;
    @ManyToOne
    private Vendedor vendedor;
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
        return fecha.toLocaleString();
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
     * @return the vendedor
     */
    public Vendedor getVendedor() {
        return vendedor;
    }

    /**
     * @param vendedor the vendedor to set
     */
    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * @return the productosDeOrdenCompra
     */
    public ArrayList<ProductoOrdenCompra> getProductosDeOrdenCompra() {
        ArrayList<ProductoOrdenCompra> productos = new ArrayList();
        Object[] array = productosDeOrdenCompra.toArray();
        for(Object o : array)
            productos.add((ProductoOrdenCompra) o);
        return productos;
    }

    /**
     * @param productosDeOrdenCompra the productosDeOrdenCompra to set
     */
    public void setProductosDeOrdenCompra(List<ProductoOrdenCompra> productosDeOrdenCompra) {
        this.productosDeOrdenCompra = productosDeOrdenCompra;
    }

}
