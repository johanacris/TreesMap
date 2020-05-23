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
import Modelo.Veneno;
import Modelo.VenenoCultivo;
import Modelo.VenenoCultivoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class VenenoCultivoDAO implements Serializable {
    
    public VenenoCultivoDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public VenenoCultivoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(VenenoCultivo venenoCultivo) throws PreexistingEntityException, Exception {
        if (venenoCultivo.getVenenoCultivoPK() == null) {
            venenoCultivo.setVenenoCultivoPK(new VenenoCultivoPK());
        }
        venenoCultivo.getVenenoCultivoPK().setIdVeneno(venenoCultivo.getVeneno().getIdVeneno());
        venenoCultivo.getVenenoCultivoPK().setIdCultivo(venenoCultivo.getCultivo().getIdCultivo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo cultivo = venenoCultivo.getCultivo();
            if (cultivo != null) {
                cultivo = em.getReference(cultivo.getClass(), cultivo.getIdCultivo());
                venenoCultivo.setCultivo(cultivo);
            }
            Veneno veneno = venenoCultivo.getVeneno();
            if (veneno != null) {
                veneno = em.getReference(veneno.getClass(), veneno.getIdVeneno());
                venenoCultivo.setVeneno(veneno);
            }
            em.persist(venenoCultivo);
            if (cultivo != null) {
                cultivo.getVenenoCultivoList().add(venenoCultivo);
                cultivo = em.merge(cultivo);
            }
            if (veneno != null) {
                veneno.getVenenoCultivoList().add(venenoCultivo);
                veneno = em.merge(veneno);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findVenenoCultivo(venenoCultivo.getVenenoCultivoPK()) != null) {
                throw new PreexistingEntityException("VenenoCultivo " + venenoCultivo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(VenenoCultivo venenoCultivo) throws NonexistentEntityException, Exception {
        venenoCultivo.getVenenoCultivoPK().setIdVeneno(venenoCultivo.getVeneno().getIdVeneno());
        venenoCultivo.getVenenoCultivoPK().setIdCultivo(venenoCultivo.getCultivo().getIdCultivo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VenenoCultivo persistentVenenoCultivo = em.find(VenenoCultivo.class, venenoCultivo.getVenenoCultivoPK());
            Cultivo cultivoOld = persistentVenenoCultivo.getCultivo();
            Cultivo cultivoNew = venenoCultivo.getCultivo();
            Veneno venenoOld = persistentVenenoCultivo.getVeneno();
            Veneno venenoNew = venenoCultivo.getVeneno();
            if (cultivoNew != null) {
                cultivoNew = em.getReference(cultivoNew.getClass(), cultivoNew.getIdCultivo());
                venenoCultivo.setCultivo(cultivoNew);
            }
            if (venenoNew != null) {
                venenoNew = em.getReference(venenoNew.getClass(), venenoNew.getIdVeneno());
                venenoCultivo.setVeneno(venenoNew);
            }
            venenoCultivo = em.merge(venenoCultivo);
            if (cultivoOld != null && !cultivoOld.equals(cultivoNew)) {
                cultivoOld.getVenenoCultivoList().remove(venenoCultivo);
                cultivoOld = em.merge(cultivoOld);
            }
            if (cultivoNew != null && !cultivoNew.equals(cultivoOld)) {
                cultivoNew.getVenenoCultivoList().add(venenoCultivo);
                cultivoNew = em.merge(cultivoNew);
            }
            if (venenoOld != null && !venenoOld.equals(venenoNew)) {
                venenoOld.getVenenoCultivoList().remove(venenoCultivo);
                venenoOld = em.merge(venenoOld);
            }
            if (venenoNew != null && !venenoNew.equals(venenoOld)) {
                venenoNew.getVenenoCultivoList().add(venenoCultivo);
                venenoNew = em.merge(venenoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                VenenoCultivoPK id = venenoCultivo.getVenenoCultivoPK();
                if (findVenenoCultivo(id) == null) {
                    throw new NonexistentEntityException("The venenoCultivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(VenenoCultivoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            VenenoCultivo venenoCultivo;
            try {
                venenoCultivo = em.getReference(VenenoCultivo.class, id);
                venenoCultivo.getVenenoCultivoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venenoCultivo with id " + id + " no longer exists.", enfe);
            }
            Cultivo cultivo = venenoCultivo.getCultivo();
            if (cultivo != null) {
                cultivo.getVenenoCultivoList().remove(venenoCultivo);
                cultivo = em.merge(cultivo);
            }
            Veneno veneno = venenoCultivo.getVeneno();
            if (veneno != null) {
                veneno.getVenenoCultivoList().remove(venenoCultivo);
                veneno = em.merge(veneno);
            }
            em.remove(venenoCultivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<VenenoCultivo> findVenenoCultivoEntities() {
        return findVenenoCultivoEntities(true, -1, -1);
    }

    public List<VenenoCultivo> findVenenoCultivoEntities(int maxResults, int firstResult) {
        return findVenenoCultivoEntities(false, maxResults, firstResult);
    }

    private List<VenenoCultivo> findVenenoCultivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(VenenoCultivo.class));
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

    public VenenoCultivo findVenenoCultivo(VenenoCultivoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(VenenoCultivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenenoCultivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<VenenoCultivo> rt = cq.from(VenenoCultivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
