/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entity.Contribuyente;
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
public class ContribuyenteJpaController implements Serializable {

    public ContribuyenteJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contribuyente contribuyente) throws RollbackFailureException, Exception {
        if (contribuyente.getPersonaEmpresaUsuarioCollection() == null) {
            contribuyente.setPersonaEmpresaUsuarioCollection(new ArrayList<PersonaEmpresaUsuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollection = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach : contribuyente.getPersonaEmpresaUsuarioCollection()) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollection.add(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach);
            }
            contribuyente.setPersonaEmpresaUsuarioCollection(attachedPersonaEmpresaUsuarioCollection);
            em.persist(contribuyente);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : contribuyente.getPersonaEmpresaUsuarioCollection()) {
                Contribuyente oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getContribuyenteId();
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setContribuyenteId(contribuyente);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                if (oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario != null) {
                    oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                    oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
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

    public void edit(Contribuyente contribuyente) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Contribuyente persistentContribuyente = em.find(Contribuyente.class, contribuyente.getId());
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionOld = persistentContribuyente.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionNew = contribuyente.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollectionNew = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach : personaEmpresaUsuarioCollectionNew) {
                personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollectionNew.add(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach);
            }
            personaEmpresaUsuarioCollectionNew = attachedPersonaEmpresaUsuarioCollectionNew;
            contribuyente.setPersonaEmpresaUsuarioCollection(personaEmpresaUsuarioCollectionNew);
            contribuyente = em.merge(contribuyente);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionOld) {
                if (!personaEmpresaUsuarioCollectionNew.contains(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario)) {
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario.setContribuyenteId(null);
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario);
                }
            }
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionNew) {
                if (!personaEmpresaUsuarioCollectionOld.contains(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario)) {
                    Contribuyente oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getContribuyenteId();
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.setContribuyenteId(contribuyente);
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                    if (oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario != null && !oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.equals(contribuyente)) {
                        oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                        oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(oldContribuyenteIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
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
                Integer id = contribuyente.getId();
                if (findContribuyente(id) == null) {
                    throw new NonexistentEntityException("The contribuyente with id " + id + " no longer exists.");
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
            Contribuyente contribuyente;
            try {
                contribuyente = em.getReference(Contribuyente.class, id);
                contribuyente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contribuyente with id " + id + " no longer exists.", enfe);
            }
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection = contribuyente.getPersonaEmpresaUsuarioCollection();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : personaEmpresaUsuarioCollection) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setContribuyenteId(null);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
            }
            em.remove(contribuyente);
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

    public List<Contribuyente> findContribuyenteEntities() {
        return findContribuyenteEntities(true, -1, -1);
    }

    public List<Contribuyente> findContribuyenteEntities(int maxResults, int firstResult) {
        return findContribuyenteEntities(false, maxResults, firstResult);
    }

    private List<Contribuyente> findContribuyenteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contribuyente.class));
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

    public Contribuyente findContribuyente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contribuyente.class, id);
        } finally {
            em.close();
        }
    }

    public int getContribuyenteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contribuyente> rt = cq.from(Contribuyente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
