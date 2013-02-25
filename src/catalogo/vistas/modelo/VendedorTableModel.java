package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.JPA.VendedorJpaController;
import catalogo.modelo.Vendedor;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class VendedorTableModel extends AbstractTableModel implements Observer {

    private String[] columnas = {"Apellido", "Nombre", "Correo", "Teléfono", "Username"};
    private ArrayList<Vendedor> vendedor = new ArrayList();
    private VendedorJpaController vendedorJpaController;

    public VendedorTableModel() {
        super();
        vendedorJpaController = Catalogo.getVendedorJpaController();
        vendedorJpaController.addObserver(this);
        vendedor = vendedorJpaController.obtenerVendedores();
    }

    @Override
    public int getRowCount() {
        return vendedor.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return vendedor.get(rowIndex).getApellido();
            case 1 :
                return vendedor.get(rowIndex).getNombre();
            case 2 :
                return vendedor.get(rowIndex).getCorreo();
            case 3 :
                return vendedor.get(rowIndex).getTelefono();
            case 4 :
                return vendedor.get(rowIndex).getUsername();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return String.class;
            case 1 :
                return String.class;
            case 2 :
                return String.class;
            case 3 :
                return Long.class;
            case 4 :
                return String.class;
            default :
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnas[columnIndex];
    }

    @Override
    public void update(Observable o, Object arg) {
        vendedor = vendedorJpaController.obtenerVendedores();
        fireTableDataChanged();
    }

    public Vendedor obtenerCliente(Integer filaSeleccionada) {
        return vendedor.get(filaSeleccionada);
    }

}
