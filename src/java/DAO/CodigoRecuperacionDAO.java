/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import Modelo.CodigoRecuperacion;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Romario
 */
public class CodigoRecuperacionDAO implements Serializable {
    
    public CodigoRecuperacionDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public CodigoRecuperacionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CodigoRecuperacion codigoRecuperacion) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(codigoRecuperacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCodigoRecuperacion(codigoRecuperacion.getCodigo()) != null) {
                throw new PreexistingEntityException("CodigoRecuperacion " + codigoRecuperacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CodigoRecuperacion codigoRecuperacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            codigoRecuperacion = em.merge(codigoRecuperacion);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = codigoRecuperacion.getCodigo();
                if (findCodigoRecuperacion(id) == null) {
                    throw new NonexistentEntityException("The codigoRecuperacion with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CodigoRecuperacion codigoRecuperacion;
            try {
                codigoRecuperacion = em.getReference(CodigoRecuperacion.class, id);
                codigoRecuperacion.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The codigoRecuperacion with id " + id + " no longer exists.", enfe);
            }
            em.remove(codigoRecuperacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CodigoRecuperacion> findCodigoRecuperacionEntities() {
        return findCodigoRecuperacionEntities(true, -1, -1);
    }

    public List<CodigoRecuperacion> findCodigoRecuperacionEntities(int maxResults, int firstResult) {
        return findCodigoRecuperacionEntities(false, maxResults, firstResult);
    }

    private List<CodigoRecuperacion> findCodigoRecuperacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CodigoRecuperacion.class));
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

    public CodigoRecuperacion findCodigoRecuperacion(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CodigoRecuperacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getCodigoRecuperacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CodigoRecuperacion> rt = cq.from(CodigoRecuperacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
