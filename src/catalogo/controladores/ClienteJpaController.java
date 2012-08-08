/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.controladores;

import catalogo.controladores.exceptions.NonexistentEntityException;
import catalogo.modelo.Cliente;
import catalogo.modelo.OrdenCompra;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        if (cliente.getMisOrdenes() == null) {
            cliente.setMisOrdenes(new ArrayList<OrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<OrdenCompra> attachedMisOrdenes = new ArrayList<OrdenCompra>();
            for (OrdenCompra misOrdenesOrdenCompraToAttach : cliente.getMisOrdenes()) {
                misOrdenesOrdenCompraToAttach = em.getReference(misOrdenesOrdenCompraToAttach.getClass(), misOrdenesOrdenCompraToAttach.getId());
                attachedMisOrdenes.add(misOrdenesOrdenCompraToAttach);
            }
            cliente.setMisOrdenes(attachedMisOrdenes);
            em.persist(cliente);
            for (OrdenCompra misOrdenesOrdenCompra : cliente.getMisOrdenes()) {
                Cliente oldMiClienteOfMisOrdenesOrdenCompra = misOrdenesOrdenCompra.getMiCliente();
                misOrdenesOrdenCompra.setMiCliente(cliente);
                misOrdenesOrdenCompra = em.merge(misOrdenesOrdenCompra);
                if (oldMiClienteOfMisOrdenesOrdenCompra != null) {
                    oldMiClienteOfMisOrdenesOrdenCompra.getMisOrdenes().remove(misOrdenesOrdenCompra);
                    oldMiClienteOfMisOrdenesOrdenCompra = em.merge(oldMiClienteOfMisOrdenesOrdenCompra);
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
            Collection<OrdenCompra> misOrdenesOld = persistentCliente.getMisOrdenes();
            Collection<OrdenCompra> misOrdenesNew = cliente.getMisOrdenes();
            Collection<OrdenCompra> attachedMisOrdenesNew = new ArrayList<OrdenCompra>();
            for (OrdenCompra misOrdenesNewOrdenCompraToAttach : misOrdenesNew) {
                misOrdenesNewOrdenCompraToAttach = em.getReference(misOrdenesNewOrdenCompraToAttach.getClass(), misOrdenesNewOrdenCompraToAttach.getId());
                attachedMisOrdenesNew.add(misOrdenesNewOrdenCompraToAttach);
            }
            misOrdenesNew = attachedMisOrdenesNew;
            cliente.setMisOrdenes(misOrdenesNew);
            cliente = em.merge(cliente);
            for (OrdenCompra misOrdenesOldOrdenCompra : misOrdenesOld) {
                if (!misOrdenesNew.contains(misOrdenesOldOrdenCompra)) {
                    misOrdenesOldOrdenCompra.setMiCliente(null);
                    misOrdenesOldOrdenCompra = em.merge(misOrdenesOldOrdenCompra);
                }
            }
            for (OrdenCompra misOrdenesNewOrdenCompra : misOrdenesNew) {
                if (!misOrdenesOld.contains(misOrdenesNewOrdenCompra)) {
                    Cliente oldMiClienteOfMisOrdenesNewOrdenCompra = misOrdenesNewOrdenCompra.getMiCliente();
                    misOrdenesNewOrdenCompra.setMiCliente(cliente);
                    misOrdenesNewOrdenCompra = em.merge(misOrdenesNewOrdenCompra);
                    if (oldMiClienteOfMisOrdenesNewOrdenCompra != null && !oldMiClienteOfMisOrdenesNewOrdenCompra.equals(cliente)) {
                        oldMiClienteOfMisOrdenesNewOrdenCompra.getMisOrdenes().remove(misOrdenesNewOrdenCompra);
                        oldMiClienteOfMisOrdenesNewOrdenCompra = em.merge(oldMiClienteOfMisOrdenesNewOrdenCompra);
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
            Collection<OrdenCompra> misOrdenes = cliente.getMisOrdenes();
            for (OrdenCompra misOrdenesOrdenCompra : misOrdenes) {
                misOrdenesOrdenCompra.setMiCliente(null);
                misOrdenesOrdenCompra = em.merge(misOrdenesOrdenCompra);
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
