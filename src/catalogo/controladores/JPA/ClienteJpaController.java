package catalogo.controladores.JPA;

import catalogo.controladores.JPA.exceptions.NonexistentEntityException;
import catalogo.modelo.Cliente;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import catalogo.modelo.OrdenCompra;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class ClienteJpaController implements Serializable {

    public ClienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cliente cliente) {
        if (cliente.getOrdenes() == null) {
            cliente.setOrdenes(new ArrayList<OrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<OrdenCompra> attachedOrdenes = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenesOrdenCompraToAttach : cliente.getOrdenes()) {
                ordenesOrdenCompraToAttach = em.getReference(ordenesOrdenCompraToAttach.getClass(), ordenesOrdenCompraToAttach.getId());
                attachedOrdenes.add(ordenesOrdenCompraToAttach);
            }
            cliente.setOrdenes(attachedOrdenes);
            em.persist(cliente);
            for (OrdenCompra ordenesOrdenCompra : cliente.getOrdenes()) {
                Cliente oldClienteOfOrdenesOrdenCompra = ordenesOrdenCompra.getCliente();
                ordenesOrdenCompra.setCliente(cliente);
                ordenesOrdenCompra = em.merge(ordenesOrdenCompra);
                if (oldClienteOfOrdenesOrdenCompra != null) {
                    oldClienteOfOrdenesOrdenCompra.getOrdenes().remove(ordenesOrdenCompra);
                    oldClienteOfOrdenesOrdenCompra = em.merge(oldClienteOfOrdenesOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cliente cliente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente persistentCliente = em.find(Cliente.class, cliente.getId());
            List<OrdenCompra> ordenesOld = persistentCliente.getOrdenes();
            List<OrdenCompra> ordenesNew = cliente.getOrdenes();
            List<OrdenCompra> attachedOrdenesNew = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenesNewOrdenCompraToAttach : ordenesNew) {
                ordenesNewOrdenCompraToAttach = em.getReference(ordenesNewOrdenCompraToAttach.getClass(), ordenesNewOrdenCompraToAttach.getId());
                attachedOrdenesNew.add(ordenesNewOrdenCompraToAttach);
            }
            ordenesNew = attachedOrdenesNew;
            cliente.setOrdenes(ordenesNew);
            cliente = em.merge(cliente);
            for (OrdenCompra ordenesOldOrdenCompra : ordenesOld) {
                if (!ordenesNew.contains(ordenesOldOrdenCompra)) {
                    ordenesOldOrdenCompra.setCliente(null);
                    ordenesOldOrdenCompra = em.merge(ordenesOldOrdenCompra);
                }
            }
            for (OrdenCompra ordenesNewOrdenCompra : ordenesNew) {
                if (!ordenesOld.contains(ordenesNewOrdenCompra)) {
                    Cliente oldClienteOfOrdenesNewOrdenCompra = ordenesNewOrdenCompra.getCliente();
                    ordenesNewOrdenCompra.setCliente(cliente);
                    ordenesNewOrdenCompra = em.merge(ordenesNewOrdenCompra);
                    if (oldClienteOfOrdenesNewOrdenCompra != null && !oldClienteOfOrdenesNewOrdenCompra.equals(cliente)) {
                        oldClienteOfOrdenesNewOrdenCompra.getOrdenes().remove(ordenesNewOrdenCompra);
                        oldClienteOfOrdenesNewOrdenCompra = em.merge(oldClienteOfOrdenesNewOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cliente.getId();
                if (findCliente(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente;
            try {
                cliente = em.getReference(Cliente.class, id);
                cliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<OrdenCompra> ordenes = cliente.getOrdenes();
            for (OrdenCompra ordenesOrdenCompra : ordenes) {
                ordenesOrdenCompra.setCliente(null);
                ordenesOrdenCompra = em.merge(ordenesOrdenCompra);
            }
            em.remove(cliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cliente> findClienteEntities() {
        return findClienteEntities(true, -1, -1);
    }

    public List<Cliente> findClienteEntities(int maxResults, int firstResult) {
        return findClienteEntities(false, maxResults, firstResult);
    }

    private List<Cliente> findClienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cliente.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Cliente findCliente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getClienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cliente> rt = cq.from(Cliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
