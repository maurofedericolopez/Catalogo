package catalogo.controladores;

import catalogo.Catalogo;
import catalogo.controladores.JPA.ClienteJpaController;
import catalogo.modelo.Cliente;
import java.util.ArrayList;

/**
 *
 * @author Mauro Federico Lopez
 */
public class ClientesController {

    private ClienteJpaController clienteJpaController;

    public ClientesController() {
        clienteJpaController = new ClienteJpaController(Catalogo.getEntityManagerFactory());
    }

    public ArrayList<Cliente> obtenerClientes() {
        ArrayList<Cliente> clientes = new ArrayList();
        Object[] array = clienteJpaController.findClienteEntities().toArray();
        for(Object c : array)
            clientes.add((Cliente) c);
        return clientes;
    }

}
