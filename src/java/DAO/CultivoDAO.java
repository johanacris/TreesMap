/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAO.exceptions.IllegalOrphanException;
import DAO.exceptions.NonexistentEntityException;
import Modelo.Cultivo;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Modelo.Usuario;
import Modelo.PlagaCultivo;
import java.util.ArrayList;
import java.util.List;
import Modelo.FertilizanteCultivo;
import Modelo.VenenoCultivo;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class CultivoDAO implements Serializable {
    
    public CultivoDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public CultivoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cultivo cultivo) {
        if (cultivo.getPlagaCultivoList() == null) {
            cultivo.setPlagaCultivoList(new ArrayList<PlagaCultivo>());
        }
        if (cultivo.getFertilizanteCultivoList() == null) {
            cultivo.setFertilizanteCultivoList(new ArrayList<FertilizanteCultivo>());
        }
        if (cultivo.getVenenoCultivoList() == null) {
            cultivo.setVenenoCultivoList(new ArrayList<VenenoCultivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario = cultivo.getUsuario();
            if (usuario != null) {
                usuario = em.getReference(usuario.getClass(), usuario.getEmail());
                cultivo.setUsuario(usuario);
            }
            List<PlagaCultivo> attachedPlagaCultivoList = new ArrayList<PlagaCultivo>();
            for (PlagaCultivo plagaCultivoListPlagaCultivoToAttach : cultivo.getPlagaCultivoList()) {
                plagaCultivoListPlagaCultivoToAttach = em.getReference(plagaCultivoListPlagaCultivoToAttach.getClass(), plagaCultivoListPlagaCultivoToAttach.getPlagaCultivoPK());
                attachedPlagaCultivoList.add(plagaCultivoListPlagaCultivoToAttach);
            }
            cultivo.setPlagaCultivoList(attachedPlagaCultivoList);
            List<FertilizanteCultivo> attachedFertilizanteCultivoList = new ArrayList<FertilizanteCultivo>();
            for (FertilizanteCultivo fertilizanteCultivoListFertilizanteCultivoToAttach : cultivo.getFertilizanteCultivoList()) {
                fertilizanteCultivoListFertilizanteCultivoToAttach = em.getReference(fertilizanteCultivoListFertilizanteCultivoToAttach.getClass(), fertilizanteCultivoListFertilizanteCultivoToAttach.getFertilizanteCultivoPK());
                attachedFertilizanteCultivoList.add(fertilizanteCultivoListFertilizanteCultivoToAttach);
            }
            cultivo.setFertilizanteCultivoList(attachedFertilizanteCultivoList);
            List<VenenoCultivo> attachedVenenoCultivoList = new ArrayList<VenenoCultivo>();
            for (VenenoCultivo venenoCultivoListVenenoCultivoToAttach : cultivo.getVenenoCultivoList()) {
                venenoCultivoListVenenoCultivoToAttach = em.getReference(venenoCultivoListVenenoCultivoToAttach.getClass(), venenoCultivoListVenenoCultivoToAttach.getVenenoCultivoPK());
                attachedVenenoCultivoList.add(venenoCultivoListVenenoCultivoToAttach);
            }
            cultivo.setVenenoCultivoList(attachedVenenoCultivoList);
            em.persist(cultivo);
            if (usuario != null) {
                usuario.getCultivoList().add(cultivo);
                usuario = em.merge(usuario);
            }
            for (PlagaCultivo plagaCultivoListPlagaCultivo : cultivo.getPlagaCultivoList()) {
                Cultivo oldCultivoOfPlagaCultivoListPlagaCultivo = plagaCultivoListPlagaCultivo.getCultivo();
                plagaCultivoListPlagaCultivo.setCultivo(cultivo);
                plagaCultivoListPlagaCultivo = em.merge(plagaCultivoListPlagaCultivo);
                if (oldCultivoOfPlagaCultivoListPlagaCultivo != null) {
                    oldCultivoOfPlagaCultivoListPlagaCultivo.getPlagaCultivoList().remove(plagaCultivoListPlagaCultivo);
                    oldCultivoOfPlagaCultivoListPlagaCultivo = em.merge(oldCultivoOfPlagaCultivoListPlagaCultivo);
                }
            }
            for (FertilizanteCultivo fertilizanteCultivoListFertilizanteCultivo : cultivo.getFertilizanteCultivoList()) {
                Cultivo oldCultivoOfFertilizanteCultivoListFertilizanteCultivo = fertilizanteCultivoListFertilizanteCultivo.getCultivo();
                fertilizanteCultivoListFertilizanteCultivo.setCultivo(cultivo);
                fertilizanteCultivoListFertilizanteCultivo = em.merge(fertilizanteCultivoListFertilizanteCultivo);
                if (oldCultivoOfFertilizanteCultivoListFertilizanteCultivo != null) {
                    oldCultivoOfFertilizanteCultivoListFertilizanteCultivo.getFertilizanteCultivoList().remove(fertilizanteCultivoListFertilizanteCultivo);
                    oldCultivoOfFertilizanteCultivoListFertilizanteCultivo = em.merge(oldCultivoOfFertilizanteCultivoListFertilizanteCultivo);
                }
            }
            for (VenenoCultivo venenoCultivoListVenenoCultivo : cultivo.getVenenoCultivoList()) {
                Cultivo oldCultivoOfVenenoCultivoListVenenoCultivo = venenoCultivoListVenenoCultivo.getCultivo();
                venenoCultivoListVenenoCultivo.setCultivo(cultivo);
                venenoCultivoListVenenoCultivo = em.merge(venenoCultivoListVenenoCultivo);
                if (oldCultivoOfVenenoCultivoListVenenoCultivo != null) {
                    oldCultivoOfVenenoCultivoListVenenoCultivo.getVenenoCultivoList().remove(venenoCultivoListVenenoCultivo);
                    oldCultivoOfVenenoCultivoListVenenoCultivo = em.merge(oldCultivoOfVenenoCultivoListVenenoCultivo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cultivo cultivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cultivo persistentCultivo = em.find(Cultivo.class, cultivo.getIdCultivo());
            Usuario usuarioOld = persistentCultivo.getUsuario();
            Usuario usuarioNew = cultivo.getUsuario();
            List<PlagaCultivo> plagaCultivoListOld = persistentCultivo.getPlagaCultivoList();
            List<PlagaCultivo> plagaCultivoListNew = cultivo.getPlagaCultivoList();
            List<FertilizanteCultivo> fertilizanteCultivoListOld = persistentCultivo.getFertilizanteCultivoList();
            List<FertilizanteCultivo> fertilizanteCultivoListNew = cultivo.getFertilizanteCultivoList();
            List<VenenoCultivo> venenoCultivoListOld = persistentCultivo.getVenenoCultivoList();
            List<VenenoCultivo> venenoCultivoListNew = cultivo.getVenenoCultivoList();
            List<String> illegalOrphanMessages = null;
            for (PlagaCultivo plagaCultivoListOldPlagaCultivo : plagaCultivoListOld) {
                if (!plagaCultivoListNew.contains(plagaCultivoListOldPlagaCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PlagaCultivo " + plagaCultivoListOldPlagaCultivo + " since its cultivo field is not nullable.");
                }
            }
            for (FertilizanteCultivo fertilizanteCultivoListOldFertilizanteCultivo : fertilizanteCultivoListOld) {
                if (!fertilizanteCultivoListNew.contains(fertilizanteCultivoListOldFertilizanteCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain FertilizanteCultivo " + fertilizanteCultivoListOldFertilizanteCultivo + " since its cultivo field is not nullable.");
                }
            }
            for (VenenoCultivo venenoCultivoListOldVenenoCultivo : venenoCultivoListOld) {
                if (!venenoCultivoListNew.contains(venenoCultivoListOldVenenoCultivo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain VenenoCultivo " + venenoCultivoListOldVenenoCultivo + " since its cultivo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (usuarioNew != null) {
                usuarioNew = em.getReference(usuarioNew.getClass(), usuarioNew.getEmail());
                cultivo.setUsuario(usuarioNew);
            }
            List<PlagaCultivo> attachedPlagaCultivoListNew = new ArrayList<PlagaCultivo>();
            for (PlagaCultivo plagaCultivoListNewPlagaCultivoToAttach : plagaCultivoListNew) {
                plagaCultivoListNewPlagaCultivoToAttach = em.getReference(plagaCultivoListNewPlagaCultivoToAttach.getClass(), plagaCultivoListNewPlagaCultivoToAttach.getPlagaCultivoPK());
                attachedPlagaCultivoListNew.add(plagaCultivoListNewPlagaCultivoToAttach);
            }
            plagaCultivoListNew = attachedPlagaCultivoListNew;
            cultivo.setPlagaCultivoList(plagaCultivoListNew);
            List<FertilizanteCultivo> attachedFertilizanteCultivoListNew = new ArrayList<FertilizanteCultivo>();
            for (FertilizanteCultivo fertilizanteCultivoListNewFertilizanteCultivoToAttach : fertilizanteCultivoListNew) {
                fertilizanteCultivoListNewFertilizanteCultivoToAttach = em.getReference(fertilizanteCultivoListNewFertilizanteCultivoToAttach.getClass(), fertilizanteCultivoListNewFertilizanteCultivoToAttach.getFertilizanteCultivoPK());
                attachedFertilizanteCultivoListNew.add(fertilizanteCultivoListNewFertilizanteCultivoToAttach);
            }
            fertilizanteCultivoListNew = attachedFertilizanteCultivoListNew;
            cultivo.setFertilizanteCultivoList(fertilizanteCultivoListNew);
            List<VenenoCultivo> attachedVenenoCultivoListNew = new ArrayList<VenenoCultivo>();
            for (VenenoCultivo venenoCultivoListNewVenenoCultivoToAttach : venenoCultivoListNew) {
                venenoCultivoListNewVenenoCultivoToAttach = em.getReference(venenoCultivoListNewVenenoCultivoToAttach.getClass(), venenoCultivoListNewVenenoCultivoToAttach.getVenenoCultivoPK());
                attachedVenenoCultivoListNew.add(venenoCultivoListNewVenenoCultivoToAttach);
            }
            venenoCultivoListNew = attachedVenenoCultivoListNew;
            cultivo.setVenenoCultivoList(venenoCultivoListNew);
            cultivo = em.merge(cultivo);
            if (usuarioOld != null && !usuarioOld.equals(usuarioNew)) {
                usuarioOld.getCultivoList().remove(cultivo);
                usuarioOld = em.merge(usuarioOld);
            }
            if (usuarioNew != null && !usuarioNew.equals(usuarioOld)) {
                usuarioNew.getCultivoList().add(cultivo);
                usuarioNew = em.merge(usuarioNew);
            }
            for (PlagaCultivo plagaCultivoListNewPlagaCultivo : plagaCultivoListNew) {
                if (!plagaCultivoListOld.contains(plagaCultivoListNewPlagaCultivo)) {
                    Cultivo oldCultivoOfPlagaCultivoListNewPlagaCultivo = plagaCultivoListNewPlagaCultivo.getCultivo();
                    plagaCultivoListNewPlagaCultivo.setCultivo(cultivo);
                    plagaCultivoListNewPlagaCultivo = em.merge(plagaCultivoListNewPlagaCultivo);
                    if (oldCultivoOfPlagaCultivoListNewPlagaCultivo != null && !oldCultivoOfPlagaCultivoListNewPlagaCultivo.equals(cultivo)) {
                        oldCultivoOfPlagaCultivoListNewPlagaCultivo.getPlagaCultivoList().remove(plagaCultivoListNewPlagaCultivo);
                        oldCultivoOfPlagaCultivoListNewPlagaCultivo = em.merge(oldCultivoOfPlagaCultivoListNewPlagaCultivo);
                    }
                }
            }
            for (FertilizanteCultivo fertilizanteCultivoListNewFertilizanteCultivo : fertilizanteCultivoListNew) {
                if (!fertilizanteCultivoListOld.contains(fertilizanteCultivoListNewFertilizanteCultivo)) {
                    Cultivo oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo = fertilizanteCultivoListNewFertilizanteCultivo.getCultivo();
                    fertilizanteCultivoListNewFertilizanteCultivo.setCultivo(cultivo);
                    fertilizanteCultivoListNewFertilizanteCultivo = em.merge(fertilizanteCultivoListNewFertilizanteCultivo);
                    if (oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo != null && !oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo.equals(cultivo)) {
                        oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo.getFertilizanteCultivoList().remove(fertilizanteCultivoListNewFertilizanteCultivo);
                        oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo = em.merge(oldCultivoOfFertilizanteCultivoListNewFertilizanteCultivo);
                    }
                }
            }
            for (VenenoCultivo venenoCultivoListNewVenenoCultivo : venenoCultivoListNew) {
                if (!venenoCultivoListOld.contains(venenoCultivoListNewVenenoCultivo)) {
                    Cultivo oldCultivoOfVenenoCultivoListNewVenenoCultivo = venenoCultivoListNewVenenoCultivo.getCultivo();
                    venenoCultivoListNewVenenoCultivo.setCultivo(cultivo);
                    venenoCultivoListNewVenenoCultivo = em.merge(venenoCultivoListNewVenenoCultivo);
                    if (oldCultivoOfVenenoCultivoListNewVenenoCultivo != null && !oldCultivoOfVenenoCultivoListNewVenenoCultivo.equals(cultivo)) {
                        oldCultivoOfVenenoCultivoListNewVenenoCultivo.getVenenoCultivoList().remove(venenoCultivoListNewVenenoCultivo);
                        oldCultivoOfVenenoCultivoListNewVenenoCultivo = em.merge(oldCultivoOfVenenoCultivoListNewVenenoCultivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cultivo.getIdCultivo();
                if (findCultivo(id) == null) {
                    throw new NonexistentEntityException("The cultivo with id " + id + " no longer exists.");
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
            Cultivo cultivo;
            try {
                cultivo = em.getReference(Cultivo.class, id);
                cultivo.getIdCultivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cultivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PlagaCultivo> plagaCultivoListOrphanCheck = cultivo.getPlagaCultivoList();
            for (PlagaCultivo plagaCultivoListOrphanCheckPlagaCultivo : plagaCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cultivo (" + cultivo + ") cannot be destroyed since the PlagaCultivo " + plagaCultivoListOrphanCheckPlagaCultivo + " in its plagaCultivoList field has a non-nullable cultivo field.");
            }
            List<FertilizanteCultivo> fertilizanteCultivoListOrphanCheck = cultivo.getFertilizanteCultivoList();
            for (FertilizanteCultivo fertilizanteCultivoListOrphanCheckFertilizanteCultivo : fertilizanteCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cultivo (" + cultivo + ") cannot be destroyed since the FertilizanteCultivo " + fertilizanteCultivoListOrphanCheckFertilizanteCultivo + " in its fertilizanteCultivoList field has a non-nullable cultivo field.");
            }
            List<VenenoCultivo> venenoCultivoListOrphanCheck = cultivo.getVenenoCultivoList();
            for (VenenoCultivo venenoCultivoListOrphanCheckVenenoCultivo : venenoCultivoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cultivo (" + cultivo + ") cannot be destroyed since the VenenoCultivo " + venenoCultivoListOrphanCheckVenenoCultivo + " in its venenoCultivoList field has a non-nullable cultivo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuario usuario = cultivo.getUsuario();
            if (usuario != null) {
                usuario.getCultivoList().remove(cultivo);
                usuario = em.merge(usuario);
            }
            em.remove(cultivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cultivo> findCultivoEntities() {
        return findCultivoEntities(true, -1, -1);
    }

    public List<Cultivo> findCultivoEntities(int maxResults, int firstResult) {
        return findCultivoEntities(false, maxResults, firstResult);
    }

    private List<Cultivo> findCultivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cultivo.class));
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

    public Cultivo findCultivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cultivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCultivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cultivo> rt = cq.from(Cultivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
