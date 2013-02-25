package catalogo;

import catalogo.controladores.JPA.ProductoJpaController;
import catalogo.controladores.JPA.VendedorJpaController;
import catalogo.vistas.ProveedorUI;
import catalogo.vistas.VendedorUI;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mauro Federico Lopez
 */
public class Catalogo {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoPU");
    private static VendedorJpaController vendedorJpaController = new VendedorJpaController();
    private static ProductoJpaController productoJpaController = new ProductoJpaController();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ProveedorUI proveedorUI = new ProveedorUI();
        proveedorUI.setVisible(true);
        proveedorUI.pack();
        VendedorUI vendedorUI = new VendedorUI();
        vendedorUI.setVisible(true);
        vendedorUI.pack();
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
     * @return the vendedorJpaController
     */
    public static VendedorJpaController getVendedorJpaController() {
        return vendedorJpaController;
    }

    /**
     * @param aClienteJpaController the vendedorJpaController to set
     */
    public static void setVendedorJpaController(VendedorJpaController aClienteJpaController) {
        vendedorJpaController = aClienteJpaController;
    }

    /**
     * @return the productoJpaController
     */
    public static ProductoJpaController getProductoJpaController() {
        return productoJpaController;
    }

    /**
     * @param aProductosController the productoJpaController to set
     */
    public static void setProductosJpaController(ProductoJpaController aProductosController) {
        productoJpaController = aProductosController;
    }

}
