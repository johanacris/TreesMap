/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Modelo.Plaga;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.PlagaCultivo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class PlagaDAO implements Serializable {
    
    public PlagaDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public PlagaDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Plaga plaga) {
        if (plaga.getPlagaCultivoList() == null) {
            plaga.setPlagaCultivoList(new ArrayList<PlagaCultivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PlagaCultivo> attachedPlagaCultivoList = new ArrayList<PlagaCultivo>();
            for (PlagaCultivo plagaCultivoListPlagaCultivoToAttach : plaga.getPlagaCultivoList()) {
                plagaCultivoListPlagaCultivoToAttach = em.getReference(plagaCultivoListPlagaCultivoToAttach.getClass(), plagaCultivoListPlagaCultivoToAttach.getPlagaCultivoPK());
                attachedPlagaCultivoList.add(plagaCultivoListPlagaCultivoToAttach);
            }
            plaga.setPlagaCultivoList(attachedPlagaCultivoList);
            em.persist(plaga);
            for (PlagaCultivo plagaCultivoListPlagaCultivo : plaga.getPlagaCultivoList()) {
                Plaga oldPlagaOfPlagaCultivoListPlagaCultivo = plagaCultivoListPlagaCultivo.getPlaga();
                plagaCultivoListPlagaCultivo.setPlaga(plaga);
                plagaCultivoListPlagaCultivo = em.merge(plagaCultivoListPlagaCultivo);
                if (oldPlagaOfPlagaCultivoListPlagaCultivo != null) {
                    oldPlagaOfPlagaCultivoListPlagaCultivo.getPlagaCultivoList().remove(plagaCultivoListPlagaCultivo);
                    oldPlagaOfPlagaCultivoListPlagaCultivo = em.merge(oldPlagaOfPlagaCultivoListPlagaCultivo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Plaga plaga) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Plaga persistentPlaga = em.find(Plaga.class, plaga.getIdPlaga());
            List<PlagaCultivo> plagaCultivoListOld = persistentPlaga.getPlagaCultivoList();
            List<PlagaCultivo> plagaCultivoListNew = plaga.getPlagaCultivoList();
            List<String> illegalOrphanMessages = null;
            for (PlagaCultivo plagaCultivoListOldPlagaCultivo : plagaCultivoListOld) {
                if (!plagaCultivoListNew.contains(plagaCultivoListOldPlagaCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PlagaCultivo " + plagaCultivoListOldPlagaCultivo + " since its plaga field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PlagaCultivo> attachedPlagaCultivoListNew = new ArrayList<PlagaCultivo>();
            for (PlagaCultivo plagaCultivoListNewPlagaCultivoToAttach : plagaCultivoListNew) {
                plagaCultivoListNewPlagaCultivoToAttach = em.getReference(plagaCultivoListNewPlagaCultivoToAttach.getClass(), plagaCultivoListNewPlagaCultivoToAttach.getPlagaCultivoPK());
                attachedPlagaCultivoListNew.add(plagaCultivoListNewPlagaCultivoToAttach);
            }
            plagaCultivoListNew = attachedPlagaCultivoListNew;
            plaga.setPlagaCultivoList(plagaCultivoListNew);
            plaga = em.merge(plaga);
            for (PlagaCultivo plagaCultivoListNewPlagaCultivo : plagaCultivoListNew) {
                if (!plagaCultivoListOld.contains(plagaCultivoListNewPlagaCultivo)) {
                    Plaga oldPlagaOfPlagaCultivoListNewPlagaCultivo = plagaCultivoListNewPlagaCultivo.getPlaga();
                    plagaCultivoListNewPlagaCultivo.setPlaga(plaga);
                    plagaCultivoListNewPlagaCultivo = em.merge(plagaCultivoListNewPlagaCultivo);
                    if (oldPlagaOfPlagaCultivoListNewPlagaCultivo != null && !oldPlagaOfPlagaCultivoListNewPlagaCultivo.equals(plaga)) {
                        oldPlagaOfPlagaCultivoListNewPlagaCultivo.getPlagaCultivoList().remove(plagaCultivoListNewPlagaCultivo);
                        oldPlagaOfPlagaCultivoListNewPlagaCultivo = em.merge(oldPlagaOfPlagaCultivoListNewPlagaCultivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = plaga.getIdPlaga();
                if (findPlaga(id) == null) {
                    throw new NonexistentEntityException("The plaga with id " + id + " no longer exists.");
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
            Plaga plaga;
            try {
                plaga = em.getReference(Plaga.class, id);
                plaga.getIdPlaga();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The plaga with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PlagaCultivo> plagaCultivoListOrphanCheck = plaga.getPlagaCultivoList();
            for (PlagaCultivo plagaCultivoListOrphanCheckPlagaCultivo : plagaCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Plaga (" + plaga + ") cannot be destroyed since the PlagaCultivo " + plagaCultivoListOrphanCheckPlagaCultivo + " in its plagaCultivoList field has a non-nullable plaga field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(plaga);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Plaga> findPlagaEntities() {
        return findPlagaEntities(true, -1, -1);
    }

    public List<Plaga> findPlagaEntities(int maxResults, int firstResult) {
        return findPlagaEntities(false, maxResults, firstResult);
    }

    private List<Plaga> findPlagaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Plaga.class));
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

    public Plaga findPlaga(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Plaga.class, id);
        } finally {
            em.close();
        }
    }

    public int getPlagaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Plaga> rt = cq.from(Plaga.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
