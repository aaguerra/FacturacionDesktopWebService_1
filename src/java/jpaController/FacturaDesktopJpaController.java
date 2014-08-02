/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entity.FacturaDesktop;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.PersonaEmpresaUsuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.RollbackFailureException;

/**
 *
 * @author aaguerra
 */
public class FacturaDesktopJpaController implements Serializable {

    public FacturaDesktopJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FacturaDesktop facturaDesktop) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PersonaEmpresaUsuario clienteId = facturaDesktop.getClienteId();
            if (clienteId != null) {
                clienteId = em.getReference(clienteId.getClass(), clienteId.getCiRuc());
                facturaDesktop.setClienteId(clienteId);
            }
            em.persist(facturaDesktop);
            if (clienteId != null) {
                clienteId.getFacturaDesktopCollection().add(facturaDesktop);
                clienteId = em.merge(clienteId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FacturaDesktop facturaDesktop) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FacturaDesktop persistentFacturaDesktop = em.find(FacturaDesktop.class, facturaDesktop.getId());
            PersonaEmpresaUsuario clienteIdOld = persistentFacturaDesktop.getClienteId();
            PersonaEmpresaUsuario clienteIdNew = facturaDesktop.getClienteId();
            if (clienteIdNew != null) {
                clienteIdNew = em.getReference(clienteIdNew.getClass(), clienteIdNew.getCiRuc());
                facturaDesktop.setClienteId(clienteIdNew);
            }
            facturaDesktop = em.merge(facturaDesktop);
            if (clienteIdOld != null && !clienteIdOld.equals(clienteIdNew)) {
                clienteIdOld.getFacturaDesktopCollection().remove(facturaDesktop);
                clienteIdOld = em.merge(clienteIdOld);
            }
            if (clienteIdNew != null && !clienteIdNew.equals(clienteIdOld)) {
                clienteIdNew.getFacturaDesktopCollection().add(facturaDesktop);
                clienteIdNew = em.merge(clienteIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = facturaDesktop.getId();
                if (findFacturaDesktop(id) == null) {
                    throw new NonexistentEntityException("The facturaDesktop with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            FacturaDesktop facturaDesktop;
            try {
                facturaDesktop = em.getReference(FacturaDesktop.class, id);
                facturaDesktop.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The facturaDesktop with id " + id + " no longer exists.", enfe);
            }
            PersonaEmpresaUsuario clienteId = facturaDesktop.getClienteId();
            if (clienteId != null) {
                clienteId.getFacturaDesktopCollection().remove(facturaDesktop);
                clienteId = em.merge(clienteId);
            }
            em.remove(facturaDesktop);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FacturaDesktop> findFacturaDesktopEntities() {
        return findFacturaDesktopEntities(true, -1, -1);
    }

    public List<FacturaDesktop> findFacturaDesktopEntities(int maxResults, int firstResult) {
        return findFacturaDesktopEntities(false, maxResults, firstResult);
    }

    private List<FacturaDesktop> findFacturaDesktopEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FacturaDesktop.class));
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

    public FacturaDesktop findFacturaDesktop(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FacturaDesktop.class, id);
        } finally {
            em.close();
        }
    }

    public int getFacturaDesktopCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FacturaDesktop> rt = cq.from(FacturaDesktop.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
