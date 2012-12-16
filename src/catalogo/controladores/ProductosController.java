package catalogo.controladores;

import catalogo.Catalogo;
import catalogo.controladores.JPA.ProductoJpaController;
import catalogo.modelo.Producto;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ProductosController extends Observable {

    private ProductoJpaController productoJpaController;

    public ProductosController() {
        productoJpaController = new ProductoJpaController(Catalogo.getEntityManagerFactory());
    }

    public ArrayList<Producto> obtenerProductos() {
        ArrayList<Producto> productos = new ArrayList();
        Object[] array = productoJpaController.findProductoEntities().toArray();
        for(Object p : array)
            productos.add((Producto) p);
        return productos;
    }

}
