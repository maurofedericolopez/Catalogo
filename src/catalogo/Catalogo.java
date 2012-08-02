package catalogo;

import catalogo.controladores.ClienteJpaController;
import catalogo.controladores.OrdenCompraJpaController;
import catalogo.controladores.ProductoJpaController;
import catalogo.vistas.Proveedor;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mauro
 */
public class Catalogo {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoPU");
    private static ClienteJpaController clienteC = new ClienteJpaController(emf);
    private static ProductoJpaController productoC = new ProductoJpaController(emf);
    private static OrdenCompraJpaController ordenCompraC = new OrdenCompraJpaController(emf);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Proveedor proveedor = new Proveedor();
        proveedor.setVisible(true);
        proveedor.pack();
    }

    /**
     * @return the emf
     */
    public static EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * @param aEmf the emf to set
     */
    public static void setEmf(EntityManagerFactory aEmf) {
        emf = aEmf;
    }

    /**
     * @return the clienteC
     */
    public static ClienteJpaController getClienteC() {
        return clienteC;
    }

    /**
     * @param aClienteC the clienteC to set
     */
    public static void setClienteC(ClienteJpaController aClienteC) {
        clienteC = aClienteC;
    }

    /**
     * @return the productoC
     */
    public static ProductoJpaController getProductoC() {
        return productoC;
    }

    /**
     * @param aProductoC the productoC to set
     */
    public static void setProductoC(ProductoJpaController aProductoC) {
        productoC = aProductoC;
    }

    /**
     * @return the ordenCompraC
     */
    public static OrdenCompraJpaController getOrdenCompraC() {
        return ordenCompraC;
    }

    /**
     * @param aOrdenCompraC the ordenCompraC to set
     */
    public static void setOrdenCompraC(OrdenCompraJpaController aOrdenCompraC) {
        ordenCompraC = aOrdenCompraC;
    }
}
