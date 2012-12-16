package catalogo.controladores;

import catalogo.Catalogo;
import catalogo.controladores.JPA.OrdenCompraJpaController;

/**
 *
 * @author Mauro Federico Lopez
 */
public class OrdenesDeCompraController {

    private OrdenCompraJpaController ordenCompraJpaController;

    public OrdenesDeCompraController() {
        ordenCompraJpaController = new OrdenCompraJpaController(Catalogo.getEntityManagerFactory());
    }

}
