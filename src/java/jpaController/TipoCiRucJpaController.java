/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entity.PersonaEmpresaUsuario;
import entity.TipoCiRuc;
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
public class TipoCiRucJpaController implements Serializable {

    public TipoCiRucJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoCiRuc tipoCiRuc) throws RollbackFailureException, Exception {
        if (tipoCiRuc.getPersonaEmpresaUsuarioCollection() == null) {
            tipoCiRuc.setPersonaEmpresaUsuarioCollection(new ArrayList<PersonaEmpresaUsuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollection = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach : tipoCiRuc.getPersonaEmpresaUsuarioCollection()) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollection.add(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach);
            }
            tipoCiRuc.setPersonaEmpresaUsuarioCollection(attachedPersonaEmpresaUsuarioCollection);
            em.persist(tipoCiRuc);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : tipoCiRuc.getPersonaEmpresaUsuarioCollection()) {
                TipoCiRuc oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getTipoCiRucId();
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setTipoCiRucId(tipoCiRuc);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                if (oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario != null) {
                    oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                    oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
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

    public void edit(TipoCiRuc tipoCiRuc) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoCiRuc persistentTipoCiRuc = em.find(TipoCiRuc.class, tipoCiRuc.getId());
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionOld = persistentTipoCiRuc.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionNew = tipoCiRuc.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollectionNew = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach : personaEmpresaUsuarioCollectionNew) {
                personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollectionNew.add(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach);
            }
            personaEmpresaUsuarioCollectionNew = attachedPersonaEmpresaUsuarioCollectionNew;
            tipoCiRuc.setPersonaEmpresaUsuarioCollection(personaEmpresaUsuarioCollectionNew);
            tipoCiRuc = em.merge(tipoCiRuc);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionOld) {
                if (!personaEmpresaUsuarioCollectionNew.contains(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario)) {
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario.setTipoCiRucId(null);
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario);
                }
            }
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionNew) {
                if (!personaEmpresaUsuarioCollectionOld.contains(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario)) {
                    TipoCiRuc oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getTipoCiRucId();
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.setTipoCiRucId(tipoCiRuc);
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                    if (oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario != null && !oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.equals(tipoCiRuc)) {
                        oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                        oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(oldTipoCiRucIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
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
                Integer id = tipoCiRuc.getId();
                if (findTipoCiRuc(id) == null) {
                    throw new NonexistentEntityException("The tipoCiRuc with id " + id + " no longer exists.");
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
            TipoCiRuc tipoCiRuc;
            try {
                tipoCiRuc = em.getReference(TipoCiRuc.class, id);
                tipoCiRuc.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoCiRuc with id " + id + " no longer exists.", enfe);
            }
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection = tipoCiRuc.getPersonaEmpresaUsuarioCollection();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : personaEmpresaUsuarioCollection) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setTipoCiRucId(null);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
            }
            em.remove(tipoCiRuc);
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

    public List<TipoCiRuc> findTipoCiRucEntities() {
        return findTipoCiRucEntities(true, -1, -1);
    }

    public List<TipoCiRuc> findTipoCiRucEntities(int maxResults, int firstResult) {
        return findTipoCiRucEntities(false, maxResults, firstResult);
    }

    private List<TipoCiRuc> findTipoCiRucEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoCiRuc.class));
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

    public TipoCiRuc findTipoCiRuc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoCiRuc.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoCiRucCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoCiRuc> rt = cq.from(TipoCiRuc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
