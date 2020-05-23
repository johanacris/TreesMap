/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.NonexistentEntityException;
import DAO.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Cultivo;
import Modelo.Plaga;
import Modelo.PlagaCultivo;
import Modelo.PlagaCultivoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class PlagaCultivoDAO implements Serializable {
    
    public PlagaCultivoDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public PlagaCultivoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PlagaCultivo plagaCultivo) throws PreexistingEntityException, Exception {
        if (plagaCultivo.getPlagaCultivoPK() == null) {
            plagaCultivo.setPlagaCultivoPK(new PlagaCultivoPK());
        }
        plagaCultivo.getPlagaCultivoPK().setIdPlaga(plagaCultivo.getPlaga().getIdPlaga());
        plagaCultivo.getPlagaCultivoPK().setIdCultivo(plagaCultivo.getCultivo().getIdCultivo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo cultivo = plagaCultivo.getCultivo();
            if (cultivo != null) {
                cultivo = em.getReference(cultivo.getClass(), cultivo.getIdCultivo());
                plagaCultivo.setCultivo(cultivo);
            }
            Plaga plaga = plagaCultivo.getPlaga();
            if (plaga != null) {
                plaga = em.getReference(plaga.getClass(), plaga.getIdPlaga());
                plagaCultivo.setPlaga(plaga);
            }
            em.persist(plagaCultivo);
            if (cultivo != null) {
                cultivo.getPlagaCultivoList().add(plagaCultivo);
                cultivo = em.merge(cultivo);
            }
            if (plaga != null) {
                plaga.getPlagaCultivoList().add(plagaCultivo);
                plaga = em.merge(plaga);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPlagaCultivo(plagaCultivo.getPlagaCultivoPK()) != null) {
                throw new PreexistingEntityException("PlagaCultivo " + plagaCultivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PlagaCultivo plagaCultivo) throws NonexistentEntityException, Exception {
        plagaCultivo.getPlagaCultivoPK().setIdPlaga(plagaCultivo.getPlaga().getIdPlaga());
        plagaCultivo.getPlagaCultivoPK().setIdCultivo(plagaCultivo.getCultivo().getIdCultivo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlagaCultivo persistentPlagaCultivo = em.find(PlagaCultivo.class, plagaCultivo.getPlagaCultivoPK());
            Cultivo cultivoOld = persistentPlagaCultivo.getCultivo();
            Cultivo cultivoNew = plagaCultivo.getCultivo();
            Plaga plagaOld = persistentPlagaCultivo.getPlaga();
            Plaga plagaNew = plagaCultivo.getPlaga();
            if (cultivoNew != null) {
                cultivoNew = em.getReference(cultivoNew.getClass(), cultivoNew.getIdCultivo());
                plagaCultivo.setCultivo(cultivoNew);
            }
            if (plagaNew != null) {
                plagaNew = em.getReference(plagaNew.getClass(), plagaNew.getIdPlaga());
                plagaCultivo.setPlaga(plagaNew);
            }
            plagaCultivo = em.merge(plagaCultivo);
            if (cultivoOld != null && !cultivoOld.equals(cultivoNew)) {
                cultivoOld.getPlagaCultivoList().remove(plagaCultivo);
                cultivoOld = em.merge(cultivoOld);
            }
            if (cultivoNew != null && !cultivoNew.equals(cultivoOld)) {
                cultivoNew.getPlagaCultivoList().add(plagaCultivo);
                cultivoNew = em.merge(cultivoNew);
            }
            if (plagaOld != null && !plagaOld.equals(plagaNew)) {
                plagaOld.getPlagaCultivoList().remove(plagaCultivo);
                plagaOld = em.merge(plagaOld);
            }
            if (plagaNew != null && !plagaNew.equals(plagaOld)) {
                plagaNew.getPlagaCultivoList().add(plagaCultivo);
                plagaNew = em.merge(plagaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PlagaCultivoPK id = plagaCultivo.getPlagaCultivoPK();
                if (findPlagaCultivo(id) == null) {
                    throw new NonexistentEntityException("The plagaCultivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PlagaCultivoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PlagaCultivo plagaCultivo;
            try {
                plagaCultivo = em.getReference(PlagaCultivo.class, id);
                plagaCultivo.getPlagaCultivoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plagaCultivo with id " + id + " no longer exists.", enfe);
            }
            Cultivo cultivo = plagaCultivo.getCultivo();
            if (cultivo != null) {
                cultivo.getPlagaCultivoList().remove(plagaCultivo);
                cultivo = em.merge(cultivo);
            }
            Plaga plaga = plagaCultivo.getPlaga();
            if (plaga != null) {
                plaga.getPlagaCultivoList().remove(plagaCultivo);
                plaga = em.merge(plaga);
            }
            em.remove(plagaCultivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PlagaCultivo> findPlagaCultivoEntities() {
        return findPlagaCultivoEntities(true, -1, -1);
    }

    public List<PlagaCultivo> findPlagaCultivoEntities(int maxResults, int firstResult) {
        return findPlagaCultivoEntities(false, maxResults, firstResult);
    }

    private List<PlagaCultivo> findPlagaCultivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PlagaCultivo.class));
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

    public PlagaCultivo findPlagaCultivo(PlagaCultivoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PlagaCultivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlagaCultivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PlagaCultivo> rt = cq.from(PlagaCultivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
