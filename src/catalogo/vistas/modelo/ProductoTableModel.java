package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.ProductoJpaController;
import catalogo.controladores.exceptions.NonexistentEntityException;
import catalogo.modelo.Producto;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ProductoTableModel extends AbstractTableModel {

    private String[] columnas = {"Código","Nombre","Descripción","Precio","Ruta Imagen"};
    private List<Producto> productos;
    private ProductoJpaController controlador;

    public ProductoTableModel() {
        super();
        this.controlador = Catalogo.getProductoC();
        this.productos = (List<Producto>) this.controlador.findProductoEntities();
    }

    @Override
    public int getRowCount() {
        return controlador.getProductoCount();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return productos.get(rowIndex).getId();
            case 1:
                return productos.get(rowIndex).getNombre();
            case 2:
                return productos.get(rowIndex).getDescripcion();
            case 3:
                return "$"+productos.get(rowIndex).getPrecio();
            case 4:
                return productos.get(rowIndex).getPathImage();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        try {
            Producto aux = controlador.findProducto((Long)getValueAt(rowIndex,0));
            switch(columnIndex) {
                case 1:
                    aux.setNombre(String.valueOf(aValue));
                case 2:
                    aux.setDescripcion(String.valueOf(aValue));
                case 3:
                    aux.setPrecio(Double.parseDouble(String.valueOf(aValue)));
                case 4:
                    aux.setPathImage(String.valueOf(aValue));
            }
            controlador.edit(aux);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ProductoTableModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,
                    "El precio ingresado no es válido",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            Logger.getLogger(ProductoTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if(columnIndex > 0)
            return true;
        else
            return false;
    }

}
