package catalogo.controladores.JPA;

import catalogo.controladores.JPA.exceptions.NonexistentEntityException;
import catalogo.modelo.Cliente;
import catalogo.modelo.OrdenCompra;
import catalogo.modelo.ProductoOrdenCompra;
import java.io.Serializable;
import java.util.ArrayList;
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
public class OrdenCompraJpaController implements Serializable {

    public OrdenCompraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrdenCompra ordenCompra) {
        if (ordenCompra.getProductosDeOrdenCompra() == null) {
            ordenCompra.setProductosDeOrdenCompra(new ArrayList<ProductoOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cliente cliente = ordenCompra.getCliente();
            if (cliente != null) {
                cliente = em.getReference(cliente.getClass(), cliente.getId());
                ordenCompra.setCliente(cliente);
            }
            List<ProductoOrdenCompra> attachedProductosDeOrdenCompra = new ArrayList<ProductoOrdenCompra>();
            for (ProductoOrdenCompra productosDeOrdenCompraProductoOrdenCompraToAttach : ordenCompra.getProductosDeOrdenCompra()) {
                productosDeOrdenCompraProductoOrdenCompraToAttach = em.getReference(productosDeOrdenCompraProductoOrdenCompraToAttach.getClass(), productosDeOrdenCompraProductoOrdenCompraToAttach.getId());
                attachedProductosDeOrdenCompra.add(productosDeOrdenCompraProductoOrdenCompraToAttach);
            }
            ordenCompra.setProductosDeOrdenCompra(attachedProductosDeOrdenCompra);
            em.persist(ordenCompra);
            if (cliente != null) {
                cliente.getOrdenes().add(ordenCompra);
                cliente = em.merge(cliente);
            }
            for (ProductoOrdenCompra productosDeOrdenCompraProductoOrdenCompra : ordenCompra.getProductosDeOrdenCompra()) {
                productosDeOrdenCompraProductoOrdenCompra.setOrdenCompra(ordenCompra);
                productosDeOrdenCompraProductoOrdenCompra = em.merge(productosDeOrdenCompraProductoOrdenCompra);
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
            Cliente clienteOld = persistentOrdenCompra.getCliente();
            Cliente clienteNew = ordenCompra.getCliente();
            List<ProductoOrdenCompra> productosDeOrdenCompraOld = persistentOrdenCompra.getProductosDeOrdenCompra();
            List<ProductoOrdenCompra> productosDeOrdenCompraNew = ordenCompra.getProductosDeOrdenCompra();
            if (clienteNew != null) {
                clienteNew = em.getReference(clienteNew.getClass(), clienteNew.getId());
                ordenCompra.setCliente(clienteNew);
            }
            List<ProductoOrdenCompra> attachedProductosDeOrdenCompraNew = new ArrayList<ProductoOrdenCompra>();
            for (ProductoOrdenCompra productosDeOrdenCompraNewProductoOrdenCompraToAttach : productosDeOrdenCompraNew) {
                productosDeOrdenCompraNewProductoOrdenCompraToAttach = em.getReference(productosDeOrdenCompraNewProductoOrdenCompraToAttach.getClass(), productosDeOrdenCompraNewProductoOrdenCompraToAttach.getId());
                attachedProductosDeOrdenCompraNew.add(productosDeOrdenCompraNewProductoOrdenCompraToAttach);
            }
            productosDeOrdenCompraNew = attachedProductosDeOrdenCompraNew;
            ordenCompra.setProductosDeOrdenCompra(productosDeOrdenCompraNew);
            ordenCompra = em.merge(ordenCompra);
            if (clienteOld != null && !clienteOld.equals(clienteNew)) {
                clienteOld.getOrdenes().remove(ordenCompra);
                clienteOld = em.merge(clienteOld);
            }
            if (clienteNew != null && !clienteNew.equals(clienteOld)) {
                clienteNew.getOrdenes().add(ordenCompra);
                clienteNew = em.merge(clienteNew);
            }
            for (ProductoOrdenCompra productosDeOrdenCompraOldProductoOrdenCompra : productosDeOrdenCompraOld) {
                if (!productosDeOrdenCompraNew.contains(productosDeOrdenCompraOldProductoOrdenCompra)) {
                    productosDeOrdenCompraOldProductoOrdenCompra.setOrdenCompra(null);
                    productosDeOrdenCompraOldProductoOrdenCompra = em.merge(productosDeOrdenCompraOldProductoOrdenCompra);
                }
            }
            for (ProductoOrdenCompra productosDeOrdenCompraNewProductoOrdenCompra : productosDeOrdenCompraNew) {
                if (!productosDeOrdenCompraOld.contains(productosDeOrdenCompraNewProductoOrdenCompra)) {
                    productosDeOrdenCompraNewProductoOrdenCompra.setOrdenCompra(ordenCompra);
                    productosDeOrdenCompraNewProductoOrdenCompra = em.merge(productosDeOrdenCompraNewProductoOrdenCompra);
                }
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
            Cliente cliente = ordenCompra.getCliente();
            if (cliente != null) {
                cliente.getOrdenes().remove(ordenCompra);
                cliente = em.merge(cliente);
            }
            List<ProductoOrdenCompra> productosDeOrdenCompra = ordenCompra.getProductosDeOrdenCompra();
            for (ProductoOrdenCompra productosDeOrdenCompraProductoOrdenCompra : productosDeOrdenCompra) {
                productosDeOrdenCompraProductoOrdenCompra.setOrdenCompra(null);
                productosDeOrdenCompraProductoOrdenCompra = em.merge(productosDeOrdenCompraProductoOrdenCompra);
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
