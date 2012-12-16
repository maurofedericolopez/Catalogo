package catalogo;

import catalogo.controladores.ClientesController;
import catalogo.controladores.OrdenesDeCompraController;
import catalogo.controladores.ProductosController;
import catalogo.vistas.ProveedorUI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Mauro Federico Lopez
 */
public class Catalogo {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("CatalogoPU");
    public static ClientesController clientesController;
    public static ProductosController productosController;
    public static OrdenesDeCompraController ordenesCompraController;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        clientesController = new ClientesController();
        productosController = new ProductosController();
        EntityManager entityManager = getEntityManagerFactory().createEntityManager();
        ProveedorUI proveedor = new ProveedorUI();
        proveedor.setVisible(true);
        proveedor.pack();
    }

    /**
     * @return the emf
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }

}
