package catalogo.controladores.JPA;

import catalogo.controladores.JPA.exceptions.NonexistentEntityException;
import catalogo.modelo.ProductoOrdenCompra;
import java.io.Serializable;
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
public class ProductoOrdenCompraJpaController implements Serializable {

    public ProductoOrdenCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ProductoOrdenCompra productoOrdenCompra) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(productoOrdenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ProductoOrdenCompra productoOrdenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            productoOrdenCompra = em.merge(productoOrdenCompra);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = productoOrdenCompra.getId();
                if (findProductoOrdenCompra(id) == null) {
                    throw new NonexistentEntityException("The productoOrdenCompra with id " + id + " no longer exists.");
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
            ProductoOrdenCompra productoOrdenCompra;
            try {
                productoOrdenCompra = em.getReference(ProductoOrdenCompra.class, id);
                productoOrdenCompra.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The productoOrdenCompra with id " + id + " no longer exists.", enfe);
            }
            em.remove(productoOrdenCompra);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ProductoOrdenCompra> findProductoOrdenCompraEntities() {
        return findProductoOrdenCompraEntities(true, -1, -1);
    }

    public List<ProductoOrdenCompra> findProductoOrdenCompraEntities(int maxResults, int firstResult) {
        return findProductoOrdenCompraEntities(false, maxResults, firstResult);
    }

    private List<ProductoOrdenCompra> findProductoOrdenCompraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ProductoOrdenCompra.class));
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

    public ProductoOrdenCompra findProductoOrdenCompra(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoOrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductoOrdenCompraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ProductoOrdenCompra> rt = cq.from(ProductoOrdenCompra.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
