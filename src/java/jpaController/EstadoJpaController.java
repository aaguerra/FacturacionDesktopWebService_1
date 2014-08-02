/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jpaController;

import entity.Estado;
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
public class EstadoJpaController implements Serializable {

    public EstadoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estado estado) throws RollbackFailureException, Exception {
        if (estado.getPersonaEmpresaUsuarioCollection() == null) {
            estado.setPersonaEmpresaUsuarioCollection(new ArrayList<PersonaEmpresaUsuario>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollection = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach : estado.getPersonaEmpresaUsuarioCollection()) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollection.add(personaEmpresaUsuarioCollectionPersonaEmpresaUsuarioToAttach);
            }
            estado.setPersonaEmpresaUsuarioCollection(attachedPersonaEmpresaUsuarioCollection);
            em.persist(estado);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : estado.getPersonaEmpresaUsuarioCollection()) {
                Estado oldEstadoIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getEstadoId();
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setEstadoId(estado);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                if (oldEstadoIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario != null) {
                    oldEstadoIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
                    oldEstadoIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(oldEstadoIdOfPersonaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
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

    public void edit(Estado estado) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Estado persistentEstado = em.find(Estado.class, estado.getId());
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionOld = persistentEstado.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollectionNew = estado.getPersonaEmpresaUsuarioCollection();
            Collection<PersonaEmpresaUsuario> attachedPersonaEmpresaUsuarioCollectionNew = new ArrayList<PersonaEmpresaUsuario>();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach : personaEmpresaUsuarioCollectionNew) {
                personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach = em.getReference(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getClass(), personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach.getCiRuc());
                attachedPersonaEmpresaUsuarioCollectionNew.add(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuarioToAttach);
            }
            personaEmpresaUsuarioCollectionNew = attachedPersonaEmpresaUsuarioCollectionNew;
            estado.setPersonaEmpresaUsuarioCollection(personaEmpresaUsuarioCollectionNew);
            estado = em.merge(estado);
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionOld) {
                if (!personaEmpresaUsuarioCollectionNew.contains(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario)) {
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario.setEstadoId(null);
                    personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionOldPersonaEmpresaUsuario);
                }
            }
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario : personaEmpresaUsuarioCollectionNew) {
                if (!personaEmpresaUsuarioCollectionOld.contains(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario)) {
                    Estado oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getEstadoId();
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.setEstadoId(estado);
                    personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                    if (oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario != null && !oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.equals(estado)) {
                        oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
                        oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario = em.merge(oldEstadoIdOfPersonaEmpresaUsuarioCollectionNewPersonaEmpresaUsuario);
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
                Integer id = estado.getId();
                if (findEstado(id) == null) {
                    throw new NonexistentEntityException("The estado with id " + id + " no longer exists.");
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
            Estado estado;
            try {
                estado = em.getReference(Estado.class, id);
                estado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estado with id " + id + " no longer exists.", enfe);
            }
            Collection<PersonaEmpresaUsuario> personaEmpresaUsuarioCollection = estado.getPersonaEmpresaUsuarioCollection();
            for (PersonaEmpresaUsuario personaEmpresaUsuarioCollectionPersonaEmpresaUsuario : personaEmpresaUsuarioCollection) {
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario.setEstadoId(null);
                personaEmpresaUsuarioCollectionPersonaEmpresaUsuario = em.merge(personaEmpresaUsuarioCollectionPersonaEmpresaUsuario);
            }
            em.remove(estado);
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

    public List<Estado> findEstadoEntities() {
        return findEstadoEntities(true, -1, -1);
    }

    public List<Estado> findEstadoEntities(int maxResults, int firstResult) {
        return findEstadoEntities(false, maxResults, firstResult);
    }

    private List<Estado> findEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estado.class));
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

    public Estado findEstado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estado.class, id);
        } finally {
            em.close();
        }
    }

    public int getEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estado> rt = cq.from(Estado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
