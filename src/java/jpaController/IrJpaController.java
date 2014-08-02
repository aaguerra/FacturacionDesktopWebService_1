/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entity.Ir;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.PersonaEmpresaUsuario;
import java.util.ArrayList;
import java.util.Collection;
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
public class IrJpaController implements Serializable {

    public IrJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ir ir) throws RollbackFailureException, Exception {
        if (ir.getPersonaEmpresaUsuarioCollection() == null) {
            ir.setPersonaEmpresaUsuarioCollection(new ArrayList<PersonaEmpresaUsuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollection = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach : ir.getPersonaEmpresaUsuarioCollection()) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollection.add(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach);
            }
            ir.setPersonaEmpresaUsuarioCollection(attachedPersonaEmpresaUsuarioCollection);
            em.persist(ir);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : ir.getPersonaEmpresaUsuarioCollection()) {
                Ir oldIrIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getIrId();
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setIrId(ir);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                if (oldIrIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario != null) {
                    oldIrIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                    oldIrIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(oldIrIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                }
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

    public void edit(Ir ir) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Ir persistentIr = em.find(Ir.class, ir.getId());
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionOld = persistentIr.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionNew = ir.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollectionNew = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach : personaEmpresaUsuarioCollectionNew) {
                personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollectionNew.add(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach);
            }
            personaEmpresaUsuarioCollectionNew = attachedPersonaEmpresaUsuarioCollectionNew;
            ir.setPersonaEmpresaUsuarioCollection(personaEmpresaUsuarioCollectionNew);
            ir = em.merge(ir);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionOld) {
                if (!personaEmpresaUsuarioCollectionNew.contains(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario)) {
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario.setIrId(null);
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario);
                }
            }
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionNew) {
                if (!personaEmpresaUsuarioCollectionOld.contains(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario)) {
                    Ir oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getIrId();
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.setIrId(ir);
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                    if (oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario != null && !oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.equals(ir)) {
                        oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                        oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(oldIrIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                    }
                }
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
                Integer id = ir.getId();
                if (findIr(id) == null) {
                    throw new NonexistentEntityException("The ir with id " + id + " no longer exists.");
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
            Ir ir;
            try {
                ir = em.getReference(Ir.class, id);
                ir.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ir with id " + id + " no longer exists.", enfe);
            }
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection = ir.getPersonaEmpresaUsuarioCollection();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : personaEmpresaUsuarioCollection) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setIrId(null);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
            }
            em.remove(ir);
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

    public List<Ir> findIrEntities() {
        return findIrEntities(true, -1, -1);
    }

    public List<Ir> findIrEntities(int maxResults, int firstResult) {
        return findIrEntities(false, maxResults, firstResult);
    }

    private List<Ir> findIrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Ir.class));
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

    public Ir findIr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ir.class, id);
        } finally {
            em.close();
        }
    }

    public int getIrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Ir> rt = cq.from(Ir.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
