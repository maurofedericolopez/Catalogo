package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.ClientesController;
import catalogo.modelo.Cliente;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ClienteTableModel extends AbstractTableModel implements Observer {

    private String[] columnas = {"Apellido", "Nombre", "Correo", "Teléfono", "Username"};
    private ArrayList<Cliente> clientes;
    private ClientesController controlador;

    public ClienteTableModel() {
        super();
        controlador = Catalogo.clientesController;
        clientes = controlador.obtenerClientes();
    }

    @Override
    public int getRowCount() {
        return clientes.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 :
                return clientes.get(rowIndex).getApellido();
            case 1 :
                return clientes.get(rowIndex).getNombre();
            case 2 :
                return clientes.get(rowIndex).getCorreo();
            case 3 :
                return clientes.get(rowIndex).getTelefono();
            case 4 :
                return clientes.get(rowIndex).getUsername();
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
        clientes = controlador.obtenerClientes();
        fireTableDataChanged();
    }

}
