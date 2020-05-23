/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Modelo.Veneno;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.VenenoCultivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class VenenoDAO implements Serializable {
    
    public VenenoDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public VenenoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Veneno veneno) {
        if (veneno.getVenenoCultivoList() == null) {
            veneno.setVenenoCultivoList(new ArrayList<VenenoCultivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<VenenoCultivo> attachedVenenoCultivoList = new ArrayList<VenenoCultivo>();
            for (VenenoCultivo venenoCultivoListVenenoCultivoToAttach : veneno.getVenenoCultivoList()) {
                venenoCultivoListVenenoCultivoToAttach = em.getReference(venenoCultivoListVenenoCultivoToAttach.getClass(), venenoCultivoListVenenoCultivoToAttach.getVenenoCultivoPK());
                attachedVenenoCultivoList.add(venenoCultivoListVenenoCultivoToAttach);
            }
            veneno.setVenenoCultivoList(attachedVenenoCultivoList);
            em.persist(veneno);
            for (VenenoCultivo venenoCultivoListVenenoCultivo : veneno.getVenenoCultivoList()) {
                Veneno oldVenenoOfVenenoCultivoListVenenoCultivo = venenoCultivoListVenenoCultivo.getVeneno();
                venenoCultivoListVenenoCultivo.setVeneno(veneno);
                venenoCultivoListVenenoCultivo = em.merge(venenoCultivoListVenenoCultivo);
                if (oldVenenoOfVenenoCultivoListVenenoCultivo != null) {
                    oldVenenoOfVenenoCultivoListVenenoCultivo.getVenenoCultivoList().remove(venenoCultivoListVenenoCultivo);
                    oldVenenoOfVenenoCultivoListVenenoCultivo = em.merge(oldVenenoOfVenenoCultivoListVenenoCultivo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Veneno veneno) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veneno persistentVeneno = em.find(Veneno.class, veneno.getIdVeneno());
            List<VenenoCultivo> venenoCultivoListOld = persistentVeneno.getVenenoCultivoList();
            List<VenenoCultivo> venenoCultivoListNew = veneno.getVenenoCultivoList();
            List<String> illegalOrphanMessages = null;
            for (VenenoCultivo venenoCultivoListOldVenenoCultivo : venenoCultivoListOld) {
                if (!venenoCultivoListNew.contains(venenoCultivoListOldVenenoCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VenenoCultivo " + venenoCultivoListOldVenenoCultivo + " since its veneno field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<VenenoCultivo> attachedVenenoCultivoListNew = new ArrayList<VenenoCultivo>();
            for (VenenoCultivo venenoCultivoListNewVenenoCultivoToAttach : venenoCultivoListNew) {
                venenoCultivoListNewVenenoCultivoToAttach = em.getReference(venenoCultivoListNewVenenoCultivoToAttach.getClass(), venenoCultivoListNewVenenoCultivoToAttach.getVenenoCultivoPK());
                attachedVenenoCultivoListNew.add(venenoCultivoListNewVenenoCultivoToAttach);
            }
            venenoCultivoListNew = attachedVenenoCultivoListNew;
            veneno.setVenenoCultivoList(venenoCultivoListNew);
            veneno = em.merge(veneno);
            for (VenenoCultivo venenoCultivoListNewVenenoCultivo : venenoCultivoListNew) {
                if (!venenoCultivoListOld.contains(venenoCultivoListNewVenenoCultivo)) {
                    Veneno oldVenenoOfVenenoCultivoListNewVenenoCultivo = venenoCultivoListNewVenenoCultivo.getVeneno();
                    venenoCultivoListNewVenenoCultivo.setVeneno(veneno);
                    venenoCultivoListNewVenenoCultivo = em.merge(venenoCultivoListNewVenenoCultivo);
                    if (oldVenenoOfVenenoCultivoListNewVenenoCultivo != null && !oldVenenoOfVenenoCultivoListNewVenenoCultivo.equals(veneno)) {
                        oldVenenoOfVenenoCultivoListNewVenenoCultivo.getVenenoCultivoList().remove(venenoCultivoListNewVenenoCultivo);
                        oldVenenoOfVenenoCultivoListNewVenenoCultivo = em.merge(oldVenenoOfVenenoCultivoListNewVenenoCultivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = veneno.getIdVeneno();
                if (findVeneno(id) == null) {
                    throw new NonexistentEntityException("The veneno with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Veneno veneno;
            try {
                veneno = em.getReference(Veneno.class, id);
                veneno.getIdVeneno();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The veneno with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<VenenoCultivo> venenoCultivoListOrphanCheck = veneno.getVenenoCultivoList();
            for (VenenoCultivo venenoCultivoListOrphanCheckVenenoCultivo : venenoCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Veneno (" + veneno + ") cannot be destroyed since the VenenoCultivo " + venenoCultivoListOrphanCheckVenenoCultivo + " in its venenoCultivoList field has a non-nullable veneno field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(veneno);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Veneno> findVenenoEntities() {
        return findVenenoEntities(true, -1, -1);
    }

    public List<Veneno> findVenenoEntities(int maxResults, int firstResult) {
        return findVenenoEntities(false, maxResults, firstResult);
    }

    private List<Veneno> findVenenoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Veneno.class));
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

    public Veneno findVeneno(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Veneno.class, id);
        } finally {
            em.close();
        }
    }

    public int getVenenoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Veneno> rt = cq.from(Veneno.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
