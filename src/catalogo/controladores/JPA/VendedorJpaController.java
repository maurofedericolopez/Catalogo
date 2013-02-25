package catalogo.controladores.JPA;

import catalogo.Catalogo;
import catalogo.controladores.JPA.exceptions.NonexistentEntityException;
import catalogo.modelo.OrdenCompra;
import catalogo.modelo.Vendedor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author Mauro Federico Lopez
 */
public class VendedorJpaController extends Observable implements Serializable {

    public VendedorJpaController() {
        this.emf = Catalogo.getEmf();
    }
    private EntityManagerFactory emf = null;

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void crearVendedor(Vendedor vendedor) {
        if (vendedor.getOrdenes() == null) {
            vendedor.setOrdenes(new ArrayList<OrdenCompra>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<OrdenCompra> attachedOrdenes = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenesOrdenCompraToAttach : vendedor.getOrdenes()) {
                ordenesOrdenCompraToAttach = em.getReference(ordenesOrdenCompraToAttach.getClass(), ordenesOrdenCompraToAttach.getId());
                attachedOrdenes.add(ordenesOrdenCompraToAttach);
            }
            vendedor.setOrdenes(attachedOrdenes);
            em.persist(vendedor);
            for (OrdenCompra ordenesOrdenCompra : vendedor.getOrdenes()) {
                Vendedor oldVendedorOfOrdenesOrdenCompra = ordenesOrdenCompra.getVendedor();
                ordenesOrdenCompra.setVendedor(vendedor);
                ordenesOrdenCompra = em.merge(ordenesOrdenCompra);
                if (oldVendedorOfOrdenesOrdenCompra != null) {
                    oldVendedorOfOrdenesOrdenCompra.getOrdenes().remove(ordenesOrdenCompra);
                    oldVendedorOfOrdenesOrdenCompra = em.merge(oldVendedorOfOrdenesOrdenCompra);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
            notificarCambios();
        }
    }

    public void editarVendedor(Vendedor vendedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getId());
            List<OrdenCompra> ordenesOld = persistentVendedor.getOrdenes();
            List<OrdenCompra> ordenesNew = vendedor.getOrdenes();
            List<OrdenCompra> attachedOrdenesNew = new ArrayList<OrdenCompra>();
            for (OrdenCompra ordenesNewOrdenCompraToAttach : ordenesNew) {
                ordenesNewOrdenCompraToAttach = em.getReference(ordenesNewOrdenCompraToAttach.getClass(), ordenesNewOrdenCompraToAttach.getId());
                attachedOrdenesNew.add(ordenesNewOrdenCompraToAttach);
            }
            ordenesNew = attachedOrdenesNew;
            vendedor.setOrdenes(ordenesNew);
            vendedor = em.merge(vendedor);
            for (OrdenCompra ordenesOldOrdenCompra : ordenesOld) {
                if (!ordenesNew.contains(ordenesOldOrdenCompra)) {
                    ordenesOldOrdenCompra.setVendedor(null);
                    ordenesOldOrdenCompra = em.merge(ordenesOldOrdenCompra);
                }
            }
            for (OrdenCompra ordenesNewOrdenCompra : ordenesNew) {
                if (!ordenesOld.contains(ordenesNewOrdenCompra)) {
                    Vendedor oldVendedorOfOrdenesNewOrdenCompra = ordenesNewOrdenCompra.getVendedor();
                    ordenesNewOrdenCompra.setVendedor(vendedor);
                    ordenesNewOrdenCompra = em.merge(ordenesNewOrdenCompra);
                    if (oldVendedorOfOrdenesNewOrdenCompra != null && !oldVendedorOfOrdenesNewOrdenCompra.equals(vendedor)) {
                        oldVendedorOfOrdenesNewOrdenCompra.getOrdenes().remove(ordenesNewOrdenCompra);
                        oldVendedorOfOrdenesNewOrdenCompra = em.merge(oldVendedorOfOrdenesNewOrdenCompra);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = vendedor.getId();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.");
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

    public void destruirVendedor(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cliente with id " + id + " no longer exists.", enfe);
            }
            List<OrdenCompra> ordenes = vendedor.getOrdenes();
            for (OrdenCompra ordenesOrdenCompra : ordenes) {
                ordenesOrdenCompra.setVendedor(null);
                ordenesOrdenCompra = em.merge(ordenesOrdenCompra);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
            notificarCambios();
        }
    }

    private List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    private List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
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

    private Vendedor findVendedor(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    private int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public ArrayList<Vendedor> obtenerVendedores() {
        ArrayList<Vendedor> vendedor = new ArrayList();
        Object[] array = findVendedorEntities().toArray();
        for(Object o : array)
            vendedor.add((Vendedor) o);
        return vendedor;
    }

    public void registrarNuevoVendedor(String apellido, String nombre, String correo, Long telefono, String username, String password) throws Exception {
        if(usernameValido(username)) {
            Vendedor nuevoVendedor = new Vendedor();
            nuevoVendedor.setApellido(apellido);
            nuevoVendedor.setNombre(nombre);
            nuevoVendedor.setCorreo(correo);
            nuevoVendedor.setTelefono(telefono);
            nuevoVendedor.setUsername(username);
            nuevoVendedor.setPassword(password);
            crearVendedor(nuevoVendedor);
        } else {
            throw new Exception("El nombre de usuario ya está registrado.");
        }
    }

    private Boolean usernameValido(String username) {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vendedor> c = cb.createQuery(Vendedor.class);
            Root<Vendedor> p = c.from(Vendedor.class);
            c.select(p).where(cb.equal(p.get("username"), username.toUpperCase()));
            em.createQuery(c).getSingleResult();
            return false;
        } catch (NoResultException ex) {
            return true;
        } finally {
            em.close();
        }
    }

    private void notificarCambios() {
        setChanged();
        notifyObservers();
    }

    public Vendedor iniciarSesion(String username, String password) throws Exception {
        EntityManager em = getEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Vendedor> cq = cb.createQuery(Vendedor.class);
            Root<Vendedor> root = cq.from(Vendedor.class);
            cq.select(root);

            List<Predicate> predicateList = new ArrayList<Predicate>();

            Predicate usernamePredicate, passwordPredicate;

            if (username != null) {
                usernamePredicate = cb.equal(root.get("username"), username.toUpperCase());
                predicateList.add(usernamePredicate);
            }
            if (password != null) {
                passwordPredicate = cb.equal(root.get("password"), password.toUpperCase());
                predicateList.add(passwordPredicate);
            }
 
            Predicate[] predicates = new Predicate[predicateList.size()];
            predicateList.toArray(predicates);
            cq.where(predicates);
            return em.createQuery(cq).getSingleResult();
        } catch (NoResultException ex) {
            throw new Exception("No existe ningún vendedor con el nombre de usuario y contraseña indicados.");
        } finally {
            em.close();
        }
    }

}
