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
import entity.TipoCiRuc;
import entity.Ir;
import entity.Estado;
import entity.Contribuyente;
import entity.FacturaDesktop;
import entity.PersonaEmpresaUsuario;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import jpaController.exceptions.NonexistentEntityException;
import jpaController.exceptions.PreexistingEntityException;
import jpaController.exceptions.RollbackFailureException;

/**
 *
 * @author aaguerra
 */
public class PersonaEmpresaUsuarioJpaController implements Serializable {

    public PersonaEmpresaUsuarioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersonaEmpresaUsuario personaEmpresaUsuario) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (personaEmpresaUsuario.getFacturaDesktopCollection() == null) {
            personaEmpresaUsuario.setFacturaDesktopCollection(new ArrayList<FacturaDesktop>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            TipoCiRuc tipoCiRucId = personaEmpresaUsuario.getTipoCiRucId();
            if (tipoCiRucId != null) {
                tipoCiRucId = em.getReference(tipoCiRucId.getClass(), tipoCiRucId.getId());
                personaEmpresaUsuario.setTipoCiRucId(tipoCiRucId);
            }
            Ir irId = personaEmpresaUsuario.getIrId();
            if (irId != null) {
                irId = em.getReference(irId.getClass(), irId.getId());
                personaEmpresaUsuario.setIrId(irId);
            }
            Estado estadoId = personaEmpresaUsuario.getEstadoId();
            if (estadoId != null) {
                estadoId = em.getReference(estadoId.getClass(), estadoId.getId());
                personaEmpresaUsuario.setEstadoId(estadoId);
            }
            Contribuyente contribuyenteId = personaEmpresaUsuario.getContribuyenteId();
            if (contribuyenteId != null) {
                contribuyenteId = em.getReference(contribuyenteId.getClass(), contribuyenteId.getId());
                personaEmpresaUsuario.setContribuyenteId(contribuyenteId);
            }
            Collection<FacturaDesktop> attachedFacturaDesktopCollection = new ArrayList<FacturaDesktop>();
            for (FacturaDesktop facturaDesktopCollectionFacturaDesktopToAttach : personaEmpresaUsuario.getFacturaDesktopCollection()) {
                facturaDesktopCollectionFacturaDesktopToAttach = em.getReference(facturaDesktopCollectionFacturaDesktopToAttach.getClass(), facturaDesktopCollectionFacturaDesktopToAttach.getId());
                attachedFacturaDesktopCollection.add(facturaDesktopCollectionFacturaDesktopToAttach);
            }
            personaEmpresaUsuario.setFacturaDesktopCollection(attachedFacturaDesktopCollection);
            em.persist(personaEmpresaUsuario);
            if (tipoCiRucId != null) {
                tipoCiRucId.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                tipoCiRucId = em.merge(tipoCiRucId);
            }
            if (irId != null) {
                irId.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                irId = em.merge(irId);
            }
            if (estadoId != null) {
                estadoId.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                estadoId = em.merge(estadoId);
            }
            if (contribuyenteId != null) {
                contribuyenteId.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                contribuyenteId = em.merge(contribuyenteId);
            }
            for (FacturaDesktop facturaDesktopCollectionFacturaDesktop : personaEmpresaUsuario.getFacturaDesktopCollection()) {
                PersonaEmpresaUsuario oldClienteIdOfFacturaDesktopCollectionFacturaDesktop = facturaDesktopCollectionFacturaDesktop.getClienteId();
                facturaDesktopCollectionFacturaDesktop.setClienteId(personaEmpresaUsuario);
                facturaDesktopCollectionFacturaDesktop = em.merge(facturaDesktopCollectionFacturaDesktop);
                if (oldClienteIdOfFacturaDesktopCollectionFacturaDesktop != null) {
                    oldClienteIdOfFacturaDesktopCollectionFacturaDesktop.getFacturaDesktopCollection().remove(facturaDesktopCollectionFacturaDesktop);
                    oldClienteIdOfFacturaDesktopCollectionFacturaDesktop = em.merge(oldClienteIdOfFacturaDesktopCollectionFacturaDesktop);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findPersonaEmpresaUsuario(personaEmpresaUsuario.getCiRuc()) != null) {
                throw new PreexistingEntityException("PersonaEmpresaUsuario " + personaEmpresaUsuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersonaEmpresaUsuario personaEmpresaUsuario) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PersonaEmpresaUsuario persistentPersonaEmpresaUsuario = em.find(PersonaEmpresaUsuario.class, personaEmpresaUsuario.getCiRuc());
            TipoCiRuc tipoCiRucIdOld = persistentPersonaEmpresaUsuario.getTipoCiRucId();
            TipoCiRuc tipoCiRucIdNew = personaEmpresaUsuario.getTipoCiRucId();
            Ir irIdOld = persistentPersonaEmpresaUsuario.getIrId();
            Ir irIdNew = personaEmpresaUsuario.getIrId();
            Estado estadoIdOld = persistentPersonaEmpresaUsuario.getEstadoId();
            Estado estadoIdNew = personaEmpresaUsuario.getEstadoId();
            Contribuyente contribuyenteIdOld = persistentPersonaEmpresaUsuario.getContribuyenteId();
            Contribuyente contribuyenteIdNew = personaEmpresaUsuario.getContribuyenteId();
            Collection<FacturaDesktop> facturaDesktopCollectionOld = persistentPersonaEmpresaUsuario.getFacturaDesktopCollection();
            Collection<FacturaDesktop> facturaDesktopCollectionNew = personaEmpresaUsuario.getFacturaDesktopCollection();
            if (tipoCiRucIdNew != null) {
                tipoCiRucIdNew = em.getReference(tipoCiRucIdNew.getClass(), tipoCiRucIdNew.getId());
                personaEmpresaUsuario.setTipoCiRucId(tipoCiRucIdNew);
            }
            if (irIdNew != null) {
                irIdNew = em.getReference(irIdNew.getClass(), irIdNew.getId());
                personaEmpresaUsuario.setIrId(irIdNew);
            }
            if (estadoIdNew != null) {
                estadoIdNew = em.getReference(estadoIdNew.getClass(), estadoIdNew.getId());
                personaEmpresaUsuario.setEstadoId(estadoIdNew);
            }
            if (contribuyenteIdNew != null) {
                contribuyenteIdNew = em.getReference(contribuyenteIdNew.getClass(), contribuyenteIdNew.getId());
                personaEmpresaUsuario.setContribuyenteId(contribuyenteIdNew);
            }
            Collection<FacturaDesktop> attachedFacturaDesktopCollectionNew = new ArrayList<FacturaDesktop>();
            for (FacturaDesktop facturaDesktopCollectionNewFacturaDesktopToAttach : facturaDesktopCollectionNew) {
                facturaDesktopCollectionNewFacturaDesktopToAttach = em.getReference(facturaDesktopCollectionNewFacturaDesktopToAttach.getClass(), facturaDesktopCollectionNewFacturaDesktopToAttach.getId());
                attachedFacturaDesktopCollectionNew.add(facturaDesktopCollectionNewFacturaDesktopToAttach);
            }
            facturaDesktopCollectionNew = attachedFacturaDesktopCollectionNew;
            personaEmpresaUsuario.setFacturaDesktopCollection(facturaDesktopCollectionNew);
            personaEmpresaUsuario = em.merge(personaEmpresaUsuario);
            if (tipoCiRucIdOld != null && !tipoCiRucIdOld.equals(tipoCiRucIdNew)) {
                tipoCiRucIdOld.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                tipoCiRucIdOld = em.merge(tipoCiRucIdOld);
            }
            if (tipoCiRucIdNew != null && !tipoCiRucIdNew.equals(tipoCiRucIdOld)) {
                tipoCiRucIdNew.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                tipoCiRucIdNew = em.merge(tipoCiRucIdNew);
            }
            if (irIdOld != null && !irIdOld.equals(irIdNew)) {
                irIdOld.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                irIdOld = em.merge(irIdOld);
            }
            if (irIdNew != null && !irIdNew.equals(irIdOld)) {
                irIdNew.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                irIdNew = em.merge(irIdNew);
            }
            if (estadoIdOld != null && !estadoIdOld.equals(estadoIdNew)) {
                estadoIdOld.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                estadoIdOld = em.merge(estadoIdOld);
            }
            if (estadoIdNew != null && !estadoIdNew.equals(estadoIdOld)) {
                estadoIdNew.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                estadoIdNew = em.merge(estadoIdNew);
            }
            if (contribuyenteIdOld != null && !contribuyenteIdOld.equals(contribuyenteIdNew)) {
                contribuyenteIdOld.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                contribuyenteIdOld = em.merge(contribuyenteIdOld);
            }
            if (contribuyenteIdNew != null && !contribuyenteIdNew.equals(contribuyenteIdOld)) {
                contribuyenteIdNew.getPersonaEmpresaUsuarioCollection().add(personaEmpresaUsuario);
                contribuyenteIdNew = em.merge(contribuyenteIdNew);
            }
            for (FacturaDesktop facturaDesktopCollectionOldFacturaDesktop : facturaDesktopCollectionOld) {
                if (!facturaDesktopCollectionNew.contains(facturaDesktopCollectionOldFacturaDesktop)) {
                    facturaDesktopCollectionOldFacturaDesktop.setClienteId(null);
                    facturaDesktopCollectionOldFacturaDesktop = em.merge(facturaDesktopCollectionOldFacturaDesktop);
                }
            }
            for (FacturaDesktop facturaDesktopCollectionNewFacturaDesktop : facturaDesktopCollectionNew) {
                if (!facturaDesktopCollectionOld.contains(facturaDesktopCollectionNewFacturaDesktop)) {
                    PersonaEmpresaUsuario oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop = facturaDesktopCollectionNewFacturaDesktop.getClienteId();
                    facturaDesktopCollectionNewFacturaDesktop.setClienteId(personaEmpresaUsuario);
                    facturaDesktopCollectionNewFacturaDesktop = em.merge(facturaDesktopCollectionNewFacturaDesktop);
                    if (oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop != null && !oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop.equals(personaEmpresaUsuario)) {
                        oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop.getFacturaDesktopCollection().remove(facturaDesktopCollectionNewFacturaDesktop);
                        oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop = em.merge(oldClienteIdOfFacturaDesktopCollectionNewFacturaDesktop);
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
                String id = personaEmpresaUsuario.getCiRuc();
                if (findPersonaEmpresaUsuario(id) == null) {
                    throw new NonexistentEntityException("The personaEmpresaUsuario with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            PersonaEmpresaUsuario personaEmpresaUsuario;
            try {
                personaEmpresaUsuario = em.getReference(PersonaEmpresaUsuario.class, id);
                personaEmpresaUsuario.getCiRuc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The personaEmpresaUsuario with id " + id + " no longer exists.", enfe);
            }
            TipoCiRuc tipoCiRucId = personaEmpresaUsuario.getTipoCiRucId();
            if (tipoCiRucId != null) {
                tipoCiRucId.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                tipoCiRucId = em.merge(tipoCiRucId);
            }
            Ir irId = personaEmpresaUsuario.getIrId();
            if (irId != null) {
                irId.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                irId = em.merge(irId);
            }
            Estado estadoId = personaEmpresaUsuario.getEstadoId();
            if (estadoId != null) {
                estadoId.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                estadoId = em.merge(estadoId);
            }
            Contribuyente contribuyenteId = personaEmpresaUsuario.getContribuyenteId();
            if (contribuyenteId != null) {
                contribuyenteId.getPersonaEmpresaUsuarioCollection().remove(personaEmpresaUsuario);
                contribuyenteId = em.merge(contribuyenteId);
            }
            Collection<FacturaDesktop> facturaDesktopCollection = personaEmpresaUsuario.getFacturaDesktopCollection();
            for (FacturaDesktop facturaDesktopCollectionFacturaDesktop : facturaDesktopCollection) {
                facturaDesktopCollectionFacturaDesktop.setClienteId(null);
                facturaDesktopCollectionFacturaDesktop = em.merge(facturaDesktopCollectionFacturaDesktop);
            }
            em.remove(personaEmpresaUsuario);
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

    public List<PersonaEmpresaUsuario> findPersonaEmpresaUsuarioEntities() {
        return findPersonaEmpresaUsuarioEntities(true, -1, -1);
    }

    public List<PersonaEmpresaUsuario> findPersonaEmpresaUsuarioEntities(int maxResults, int firstResult) {
        return findPersonaEmpresaUsuarioEntities(false, maxResults, firstResult);
    }

    private List<PersonaEmpresaUsuario> findPersonaEmpresaUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersonaEmpresaUsuario.class));
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

    public PersonaEmpresaUsuario findPersonaEmpresaUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersonaEmpresaUsuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaEmpresaUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersonaEmpresaUsuario> rt = cq.from(PersonaEmpresaUsuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
