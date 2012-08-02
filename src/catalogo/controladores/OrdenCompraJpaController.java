/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package catalogo.controladores;

import catalogo.controladores.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import catalogo.modelo.Cliente;
import catalogo.modelo.OrdenCompra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Mauro
 */
public class OrdenCompraJpaController implements Serializable {

    public OrdenCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompra ordenCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente miCliente = ordenCompra.getMiCliente();
            if (miCliente != null) {
                miCliente = em.getReference(miCliente.getClass(), miCliente.getId());
                ordenCompra.setMiCliente(miCliente);
            }
            em.persist(ordenCompra);
            if (miCliente != null) {
                miCliente.getMisOrdenes().add(ordenCompra);
                miCliente = em.merge(miCliente);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrdenCompra ordenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra persistentOrdenCompra = em.find(OrdenCompra.class, ordenCompra.getId());
            Cliente miClienteOld = persistentOrdenCompra.getMiCliente();
            Cliente miClienteNew = ordenCompra.getMiCliente();
            if (miClienteNew != null) {
                miClienteNew = em.getReference(miClienteNew.getClass(), miClienteNew.getId());
                ordenCompra.setMiCliente(miClienteNew);
            }
            ordenCompra = em.merge(ordenCompra);
            if (miClienteOld != null && !miClienteOld.equals(miClienteNew)) {
                miClienteOld.getMisOrdenes().remove(ordenCompra);
                miClienteOld = em.merge(miClienteOld);
            }
            if (miClienteNew != null && !miClienteNew.equals(miClienteOld)) {
                miClienteNew.getMisOrdenes().add(ordenCompra);
                miClienteNew = em.merge(miClienteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ordenCompra.getId();
                if (findOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.");
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
            OrdenCompra ordenCompra;
            try {
                ordenCompra = em.getReference(OrdenCompra.class, id);
                ordenCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ordenCompra with id " + id + " no longer exists.", enfe);
            }
            Cliente miCliente = ordenCompra.getMiCliente();
            if (miCliente != null) {
                miCliente.getMisOrdenes().remove(ordenCompra);
                miCliente = em.merge(miCliente);
            }
            em.remove(ordenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<OrdenCompra> findOrdenCompraEntities() {
        return findOrdenCompraEntities(true, -1, -1);
    }

    public List<OrdenCompra> findOrdenCompraEntities(int maxResults, int firstResult) {
        return findOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<OrdenCompra> findOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OrdenCompra.class));
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

    public OrdenCompra findOrdenCompra(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OrdenCompra> rt = cq.from(OrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
