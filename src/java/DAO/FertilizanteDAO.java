/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Modelo.Fertilizante;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.FertilizanteCultivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class FertilizanteDAO implements Serializable {
    
    public FertilizanteDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public FertilizanteDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fertilizante fertilizante) {
        if (fertilizante.getFertilizanteCultivoList() == null) {
            fertilizante.setFertilizanteCultivoList(new ArrayList<FertilizanteCultivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<FertilizanteCultivo> attachedFertilizanteCultivoList = new ArrayList<FertilizanteCultivo>();
            for (FertilizanteCultivo fertilizanteCultivoListFertilizanteCultivoToAttach : fertilizante.getFertilizanteCultivoList()) {
                fertilizanteCultivoListFertilizanteCultivoToAttach = em.getReference(fertilizanteCultivoListFertilizanteCultivoToAttach.getClass(), fertilizanteCultivoListFertilizanteCultivoToAttach.getFertilizanteCultivoPK());
                attachedFertilizanteCultivoList.add(fertilizanteCultivoListFertilizanteCultivoToAttach);
            }
            fertilizante.setFertilizanteCultivoList(attachedFertilizanteCultivoList);
            em.persist(fertilizante);
            for (FertilizanteCultivo fertilizanteCultivoListFertilizanteCultivo : fertilizante.getFertilizanteCultivoList()) {
                Fertilizante oldFertilizanteOfFertilizanteCultivoListFertilizanteCultivo = fertilizanteCultivoListFertilizanteCultivo.getFertilizante();
                fertilizanteCultivoListFertilizanteCultivo.setFertilizante(fertilizante);
                fertilizanteCultivoListFertilizanteCultivo = em.merge(fertilizanteCultivoListFertilizanteCultivo);
                if (oldFertilizanteOfFertilizanteCultivoListFertilizanteCultivo != null) {
                    oldFertilizanteOfFertilizanteCultivoListFertilizanteCultivo.getFertilizanteCultivoList().remove(fertilizanteCultivoListFertilizanteCultivo);
                    oldFertilizanteOfFertilizanteCultivoListFertilizanteCultivo = em.merge(oldFertilizanteOfFertilizanteCultivoListFertilizanteCultivo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fertilizante fertilizante) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fertilizante persistentFertilizante = em.find(Fertilizante.class, fertilizante.getIdFertilizante());
            List<FertilizanteCultivo> fertilizanteCultivoListOld = persistentFertilizante.getFertilizanteCultivoList();
            List<FertilizanteCultivo> fertilizanteCultivoListNew = fertilizante.getFertilizanteCultivoList();
            List<String> illegalOrphanMessages = null;
            for (FertilizanteCultivo fertilizanteCultivoListOldFertilizanteCultivo : fertilizanteCultivoListOld) {
                if (!fertilizanteCultivoListNew.contains(fertilizanteCultivoListOldFertilizanteCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FertilizanteCultivo " + fertilizanteCultivoListOldFertilizanteCultivo + " since its fertilizante field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<FertilizanteCultivo> attachedFertilizanteCultivoListNew = new ArrayList<FertilizanteCultivo>();
            for (FertilizanteCultivo fertilizanteCultivoListNewFertilizanteCultivoToAttach : fertilizanteCultivoListNew) {
                fertilizanteCultivoListNewFertilizanteCultivoToAttach = em.getReference(fertilizanteCultivoListNewFertilizanteCultivoToAttach.getClass(), fertilizanteCultivoListNewFertilizanteCultivoToAttach.getFertilizanteCultivoPK());
                attachedFertilizanteCultivoListNew.add(fertilizanteCultivoListNewFertilizanteCultivoToAttach);
            }
            fertilizanteCultivoListNew = attachedFertilizanteCultivoListNew;
            fertilizante.setFertilizanteCultivoList(fertilizanteCultivoListNew);
            fertilizante = em.merge(fertilizante);
            for (FertilizanteCultivo fertilizanteCultivoListNewFertilizanteCultivo : fertilizanteCultivoListNew) {
                if (!fertilizanteCultivoListOld.contains(fertilizanteCultivoListNewFertilizanteCultivo)) {
                    Fertilizante oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo = fertilizanteCultivoListNewFertilizanteCultivo.getFertilizante();
                    fertilizanteCultivoListNewFertilizanteCultivo.setFertilizante(fertilizante);
                    fertilizanteCultivoListNewFertilizanteCultivo = em.merge(fertilizanteCultivoListNewFertilizanteCultivo);
                    if (oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo != null && !oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo.equals(fertilizante)) {
                        oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo.getFertilizanteCultivoList().remove(fertilizanteCultivoListNewFertilizanteCultivo);
                        oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo = em.merge(oldFertilizanteOfFertilizanteCultivoListNewFertilizanteCultivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fertilizante.getIdFertilizante();
                if (findFertilizante(id) == null) {
                    throw new NonexistentEntityException("The fertilizante with id " + id + " no longer exists.");
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
            Fertilizante fertilizante;
            try {
                fertilizante = em.getReference(Fertilizante.class, id);
                fertilizante.getIdFertilizante();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fertilizante with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<FertilizanteCultivo> fertilizanteCultivoListOrphanCheck = fertilizante.getFertilizanteCultivoList();
            for (FertilizanteCultivo fertilizanteCultivoListOrphanCheckFertilizanteCultivo : fertilizanteCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Fertilizante (" + fertilizante + ") cannot be destroyed since the FertilizanteCultivo " + fertilizanteCultivoListOrphanCheckFertilizanteCultivo + " in its fertilizanteCultivoList field has a non-nullable fertilizante field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(fertilizante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fertilizante> findFertilizanteEntities() {
        return findFertilizanteEntities(true, -1, -1);
    }

    public List<Fertilizante> findFertilizanteEntities(int maxResults, int firstResult) {
        return findFertilizanteEntities(false, maxResults, firstResult);
    }

    private List<Fertilizante> findFertilizanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fertilizante.class));
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

    public Fertilizante findFertilizante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fertilizante.class, id);
        } finally {
            em.close();
        }
    }

    public int getFertilizanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fertilizante> rt = cq.from(Fertilizante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
