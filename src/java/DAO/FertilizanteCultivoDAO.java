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
import Modelo.Fertilizante;
import Modelo.FertilizanteCultivo;
import Modelo.FertilizanteCultivoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class FertilizanteCultivoDAO implements Serializable {
    
    public FertilizanteCultivoDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public FertilizanteCultivoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(FertilizanteCultivo fertilizanteCultivo) throws PreexistingEntityException, Exception {
        if (fertilizanteCultivo.getFertilizanteCultivoPK() == null) {
            fertilizanteCultivo.setFertilizanteCultivoPK(new FertilizanteCultivoPK());
        }
        fertilizanteCultivo.getFertilizanteCultivoPK().setIdCultivo(fertilizanteCultivo.getCultivo().getIdCultivo());
        fertilizanteCultivo.getFertilizanteCultivoPK().setIdFertilizante(fertilizanteCultivo.getFertilizante().getIdFertilizante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo cultivo = fertilizanteCultivo.getCultivo();
            if (cultivo != null) {
                cultivo = em.getReference(cultivo.getClass(), cultivo.getIdCultivo());
                fertilizanteCultivo.setCultivo(cultivo);
            }
            Fertilizante fertilizante = fertilizanteCultivo.getFertilizante();
            if (fertilizante != null) {
                fertilizante = em.getReference(fertilizante.getClass(), fertilizante.getIdFertilizante());
                fertilizanteCultivo.setFertilizante(fertilizante);
            }
            em.persist(fertilizanteCultivo);
            if (cultivo != null) {
                cultivo.getFertilizanteCultivoList().add(fertilizanteCultivo);
                cultivo = em.merge(cultivo);
            }
            if (fertilizante != null) {
                fertilizante.getFertilizanteCultivoList().add(fertilizanteCultivo);
                fertilizante = em.merge(fertilizante);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findFertilizanteCultivo(fertilizanteCultivo.getFertilizanteCultivoPK()) != null) {
                throw new PreexistingEntityException("FertilizanteCultivo " + fertilizanteCultivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(FertilizanteCultivo fertilizanteCultivo) throws NonexistentEntityException, Exception {
        fertilizanteCultivo.getFertilizanteCultivoPK().setIdCultivo(fertilizanteCultivo.getCultivo().getIdCultivo());
        fertilizanteCultivo.getFertilizanteCultivoPK().setIdFertilizante(fertilizanteCultivo.getFertilizante().getIdFertilizante());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FertilizanteCultivo persistentFertilizanteCultivo = em.find(FertilizanteCultivo.class, fertilizanteCultivo.getFertilizanteCultivoPK());
            Cultivo cultivoOld = persistentFertilizanteCultivo.getCultivo();
            Cultivo cultivoNew = fertilizanteCultivo.getCultivo();
            Fertilizante fertilizanteOld = persistentFertilizanteCultivo.getFertilizante();
            Fertilizante fertilizanteNew = fertilizanteCultivo.getFertilizante();
            if (cultivoNew != null) {
                cultivoNew = em.getReference(cultivoNew.getClass(), cultivoNew.getIdCultivo());
                fertilizanteCultivo.setCultivo(cultivoNew);
            }
            if (fertilizanteNew != null) {
                fertilizanteNew = em.getReference(fertilizanteNew.getClass(), fertilizanteNew.getIdFertilizante());
                fertilizanteCultivo.setFertilizante(fertilizanteNew);
            }
            fertilizanteCultivo = em.merge(fertilizanteCultivo);
            if (cultivoOld != null && !cultivoOld.equals(cultivoNew)) {
                cultivoOld.getFertilizanteCultivoList().remove(fertilizanteCultivo);
                cultivoOld = em.merge(cultivoOld);
            }
            if (cultivoNew != null && !cultivoNew.equals(cultivoOld)) {
                cultivoNew.getFertilizanteCultivoList().add(fertilizanteCultivo);
                cultivoNew = em.merge(cultivoNew);
            }
            if (fertilizanteOld != null && !fertilizanteOld.equals(fertilizanteNew)) {
                fertilizanteOld.getFertilizanteCultivoList().remove(fertilizanteCultivo);
                fertilizanteOld = em.merge(fertilizanteOld);
            }
            if (fertilizanteNew != null && !fertilizanteNew.equals(fertilizanteOld)) {
                fertilizanteNew.getFertilizanteCultivoList().add(fertilizanteCultivo);
                fertilizanteNew = em.merge(fertilizanteNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                FertilizanteCultivoPK id = fertilizanteCultivo.getFertilizanteCultivoPK();
                if (findFertilizanteCultivo(id) == null) {
                    throw new NonexistentEntityException("The fertilizanteCultivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(FertilizanteCultivoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FertilizanteCultivo fertilizanteCultivo;
            try {
                fertilizanteCultivo = em.getReference(FertilizanteCultivo.class, id);
                fertilizanteCultivo.getFertilizanteCultivoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fertilizanteCultivo with id " + id + " no longer exists.", enfe);
            }
            Cultivo cultivo = fertilizanteCultivo.getCultivo();
            if (cultivo != null) {
                cultivo.getFertilizanteCultivoList().remove(fertilizanteCultivo);
                cultivo = em.merge(cultivo);
            }
            Fertilizante fertilizante = fertilizanteCultivo.getFertilizante();
            if (fertilizante != null) {
                fertilizante.getFertilizanteCultivoList().remove(fertilizanteCultivo);
                fertilizante = em.merge(fertilizante);
            }
            em.remove(fertilizanteCultivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<FertilizanteCultivo> findFertilizanteCultivoEntities() {
        return findFertilizanteCultivoEntities(true, -1, -1);
    }

    public List<FertilizanteCultivo> findFertilizanteCultivoEntities(int maxResults, int firstResult) {
        return findFertilizanteCultivoEntities(false, maxResults, firstResult);
    }

    private List<FertilizanteCultivo> findFertilizanteCultivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(FertilizanteCultivo.class));
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

    public FertilizanteCultivo findFertilizanteCultivo(FertilizanteCultivoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(FertilizanteCultivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getFertilizanteCultivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<FertilizanteCultivo> rt = cq.from(FertilizanteCultivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
