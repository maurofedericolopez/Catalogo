package catalogo.controladores.JPA;

import catalogo.Catalogo;
import catalogo.controladores.JPA.exceptions.NonexistentEntityException;
import catalogo.modelo.OrdenCompra;
import catalogo.modelo.Producto;
import catalogo.modelo.ProductoOrdenCompra;
import catalogo.modelo.Vendedor;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Mauro Federico Lopez
 */
public class OrdenDeCompraJpaController extends Observable implements Serializable {

    private Vendedor miVendedor = null;
    private ArrayList<ProductoOrdenCompra> productosDeOrdenDeCompra = new ArrayList();

    public OrdenDeCompraJpaController(Vendedor cliente) {
        this.emf = Catalogo.getEmf();
        this.miVendedor = cliente;
    }

    private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void crearOrdenDeCompra(OrdenCompra ordenCompra) {
        if (ordenCompra.getProductosDeOrdenCompra() == null) {
            ordenCompra.setProductosDeOrdenCompra(new ArrayList<ProductoOrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor vendedor = ordenCompra.getVendedor();
            if (vendedor != null) {
                vendedor = em.getReference(vendedor.getClass(), vendedor.getId());
                ordenCompra.setVendedor(vendedor);
            }
            List<ProductoOrdenCompra> attachedProductosDeOrdenCompra = new ArrayList<ProductoOrdenCompra>();
            for (ProductoOrdenCompra productosDeOrdenCompraProductoOrdenCompraToAttach : ordenCompra.getProductosDeOrdenCompra()) {
                productosDeOrdenCompraProductoOrdenCompraToAttach = em.getReference(productosDeOrdenCompraProductoOrdenCompraToAttach.getClass(), productosDeOrdenCompraProductoOrdenCompraToAttach.getId());
                attachedProductosDeOrdenCompra.add(productosDeOrdenCompraProductoOrdenCompraToAttach);
            }
            ordenCompra.setProductosDeOrdenCompra(attachedProductosDeOrdenCompra);
            em.persist(ordenCompra);
            if (vendedor != null) {
                vendedor.getOrdenes().add(ordenCompra);
                vendedor = em.merge(vendedor);
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
            notificarCambios();
        }
    }

    public void editarOrdenDeCompra(OrdenCompra ordenCompra) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            OrdenCompra persistentOrdenCompra = em.find(OrdenCompra.class, ordenCompra.getId());
            Vendedor vendedorOld = persistentOrdenCompra.getVendedor();
            Vendedor vendedorNew = ordenCompra.getVendedor();
            List<ProductoOrdenCompra> productosDeOrdenCompraOld = persistentOrdenCompra.getProductosDeOrdenCompra();
            List<ProductoOrdenCompra> productosDeOrdenCompraNew = ordenCompra.getProductosDeOrdenCompra();
            if (vendedorNew != null) {
                vendedorNew = em.getReference(vendedorNew.getClass(), vendedorNew.getId());
                ordenCompra.setVendedor(vendedorNew);
            }
            List<ProductoOrdenCompra> attachedProductosDeOrdenCompraNew = new ArrayList<ProductoOrdenCompra>();
            for (ProductoOrdenCompra productosDeOrdenCompraNewProductoOrdenCompraToAttach : productosDeOrdenCompraNew) {
                productosDeOrdenCompraNewProductoOrdenCompraToAttach = em.getReference(productosDeOrdenCompraNewProductoOrdenCompraToAttach.getClass(), productosDeOrdenCompraNewProductoOrdenCompraToAttach.getId());
                attachedProductosDeOrdenCompraNew.add(productosDeOrdenCompraNewProductoOrdenCompraToAttach);
            }
            productosDeOrdenCompraNew = attachedProductosDeOrdenCompraNew;
            ordenCompra.setProductosDeOrdenCompra(productosDeOrdenCompraNew);
            ordenCompra = em.merge(ordenCompra);
            if (vendedorOld != null && !vendedorOld.equals(vendedorNew)) {
                vendedorOld.getOrdenes().remove(ordenCompra);
                vendedorOld = em.merge(vendedorOld);
            }
            if (vendedorNew != null && !vendedorNew.equals(vendedorOld)) {
                vendedorNew.getOrdenes().add(ordenCompra);
                vendedorNew = em.merge(vendedorNew);
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
            notificarCambios();
        }
    }

    public void destruirOrdenDeCompra(Long id) throws NonexistentEntityException {
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
            Vendedor vendedor = ordenCompra.getVendedor();
            if (vendedor != null) {
                vendedor.getOrdenes().remove(ordenCompra);
                vendedor = em.merge(vendedor);
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
            notificarCambios();
        }
    }

    private List<OrdenCompra> findOrdenCompraEntities() {
        return findOrdenCompraEntities(true, -1, -1);
    }

    private List<OrdenCompra> findOrdenCompraEntities(int maxResults, int firstResult) {
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

    private OrdenCompra findOrdenCompra(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    private int getOrdenCompraCount() {
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

    public void crearProductoOrdenCompra(ProductoOrdenCompra productoOrdenCompra) {
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
            notificarCambios();
        }
    }

    public void editarProductoOrdenCompra(ProductoOrdenCompra productoOrdenCompra) throws NonexistentEntityException, Exception {
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
            notificarCambios();
        }
    }

    public void destruirProductoOrdenCompra(Long id) throws NonexistentEntityException {
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
            notificarCambios();
        }
    }

    private List<ProductoOrdenCompra> findProductoOrdenCompraEntities() {
        return findProductoOrdenCompraEntities(true, -1, -1);
    }

    private List<ProductoOrdenCompra> findProductoOrdenCompraEntities(int maxResults, int firstResult) {
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

    private ProductoOrdenCompra findProductoOrdenCompra(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ProductoOrdenCompra.class, id);
        } finally {
            em.close();
        }
    }

    private int getProductoOrdenCompraCount() {
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

    public ArrayList<OrdenCompra> obtenerOrdenesDeCompraPendientes() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrdenCompra> cq = cb.createQuery(OrdenCompra.class);
            Root<OrdenCompra> root = cq.from(OrdenCompra.class);
            cq.select(root);

            ArrayList<Predicate> predicateList = new ArrayList();

            Predicate vendedorPredicate, enviadoPredicate;

            if (getVendedor() != null) {
                vendedorPredicate = cb.equal(root.get("vendedor"), getVendedor());
                predicateList.add(vendedorPredicate);
            }

            Boolean enviado = false;

            if (enviado != null) {
                enviadoPredicate = cb.equal(root.get("enviado"), enviado);
                predicateList.add(enviadoPredicate);
            }
 
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            cq.where(predicates);

            ArrayList<OrdenCompra> ordenesPendientes = new ArrayList();
            Object[] array = em.createQuery(cq).getResultList().toArray();
            for(Object o : array)
                ordenesPendientes.add((OrdenCompra) o);
            return ordenesPendientes;
        } catch (NoResultException ex) {
            return new ArrayList();
        } finally {
            em.close();
        }
    }

    public void finalizarEnvio(OrdenCompra ordenDeCompra, Long codigoEnvio) throws Exception {
        ordenDeCompra.setCodigoEnvio(codigoEnvio);
        ordenDeCompra.setEnviado(true);
        editarOrdenDeCompra(ordenDeCompra);
        notificarCambios();
    }

    private void notificarCambios() {
        setChanged();
        notifyObservers();
    }

    public ArrayList<ProductoOrdenCompra> obtenerProductosOrdenDeCompraEnCurso() {
        return productosDeOrdenDeCompra;
    }

    public void eliminarProductoOrdenDeCompra(int filaSeleccionada) throws NonexistentEntityException {
        productosDeOrdenDeCompra.remove(filaSeleccionada);
    }

    public void agregarProducto(Producto producto, Long cantidad) {
        ProductoOrdenCompra productoOrdenCompra = new ProductoOrdenCompra();
        productoOrdenCompra.setProducto(producto);
        productoOrdenCompra.setCantidad(cantidad);
        productosDeOrdenDeCompra.add(null);
    }

    public void cancelarOrdenDeCompraEnCurso() {
        productosDeOrdenDeCompra = new ArrayList();
    }

    public void comprarOrdenDeCompraEnCurso() throws Exception {
        if(!productosDeOrdenDeCompra.isEmpty()) {
            OrdenCompra ordenCompra = new OrdenCompra();
            ordenCompra.setVendedor(getVendedor());
            ordenCompra.setFecha(new Date());
            ordenCompra.setCodigoEnvio(null);
            ordenCompra.setEnviado(false);
            crearOrdenDeCompra(ordenCompra);
            Iterator<ProductoOrdenCompra> i = productosDeOrdenDeCompra.iterator();
            while(i.hasNext()) {
                ProductoOrdenCompra productoOrdenCompra = i.next();
                productoOrdenCompra.setOrdenCompra(ordenCompra);
                crearProductoOrdenCompra(productoOrdenCompra);
            }
            ordenCompra.setProductosDeOrdenCompra(productosDeOrdenDeCompra);
            editarOrdenDeCompra(ordenCompra);
            productosDeOrdenDeCompra.clear();
        } else {
            throw new Exception("No hay productos en el carrito.");
        }
    }

    /**
     * @return the miVendedor
     */
    public Vendedor getVendedor() {
        return miVendedor;
    }

    public ArrayList<OrdenCompra> obtenerTodasLasOrdenesDeCompra() {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<OrdenCompra> cq = cb.createQuery(OrdenCompra.class);
            Root<OrdenCompra> root = cq.from(OrdenCompra.class);
            cq.select(root).where(cb.equal(root.get("vendedor"), miVendedor));

            ArrayList<OrdenCompra> ordenesPendientes = new ArrayList();
            Object[] array = em.createQuery(cq).getResultList().toArray();
            for(Object o : array)
                ordenesPendientes.add((OrdenCompra) o);
            return ordenesPendientes;
        } catch (NoResultException ex) {
            return new ArrayList();
        } finally {
            em.close();
        }
    }

}
