package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.JPA.ProductoJpaController;
import catalogo.modelo.Producto;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractListModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class CatalogoListModel extends AbstractListModel implements Observer {

    private ProductoJpaController productoJpaController;
    private ArrayList<Producto> productos;

    public CatalogoListModel() {
        super();
        productoJpaController = Catalogo.getProductoJpaController();
        productos = productoJpaController.obtenerProductos();
    }

    @Override
    public int getSize() {
        return productos.size();
    }

    @Override
    public Object getElementAt(int index) {
        return productos.get(index);
    }

    @Override
    public void update(Observable o, Object arg) {
        productos = productoJpaController.obtenerProductos();
        fireContentsChanged(this, 0, productos.size());
    }

}
