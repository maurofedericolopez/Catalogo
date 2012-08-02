package catalogo.vistas.modelo;

import catalogo.Catalogo;
import catalogo.controladores.ClienteJpaController;
import catalogo.controladores.exceptions.NonexistentEntityException;
import catalogo.modelo.Cliente;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ClienteTableModel extends AbstractTableModel {

    private String[] columnas = {"N°Cliente","Apellido y Nombre","Correo","Teléfono"};
    private List<Cliente> clientes;
    private ClienteJpaController controlador;

    public ClienteTableModel() {
        this.controlador = Catalogo.getClienteC();
        this.clientes = (List<Cliente>) this.controlador.findClienteEntities();
    }

    @Override
    public int getRowCount() {
        return controlador.getClienteCount();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0:
                return clientes.get(rowIndex).getId();
            case 1:
                return clientes.get(rowIndex).getApellido()+" "+clientes.get(rowIndex).getNombre();
            case 2:
                return clientes.get(rowIndex).getCorreo();
            case 3:
                return clientes.get(rowIndex).getTelefono();
            default:
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

    public void eliminarCliente(int fila) {
        try {
            Long id = (Long) getValueAt(fila,0);
            controlador.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ClienteTableModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
