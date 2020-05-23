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
import Modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Romario
 */
public class UsuarioDAO implements Serializable {
    
    public UsuarioDAO() {
        this.emf=Persistence.createEntityManagerFactory("TreeMapLocalPU");
    }

    public UsuarioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getCultivoList() == null) {
            usuario.setCultivoList(new ArrayList<Cultivo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Cultivo> attachedCultivoList = new ArrayList<Cultivo>();
            for (Cultivo cultivoListCultivoToAttach : usuario.getCultivoList()) {
                cultivoListCultivoToAttach = em.getReference(cultivoListCultivoToAttach.getClass(), cultivoListCultivoToAttach.getIdCultivo());
                attachedCultivoList.add(cultivoListCultivoToAttach);
            }
            usuario.setCultivoList(attachedCultivoList);
            em.persist(usuario);
            for (Cultivo cultivoListCultivo : usuario.getCultivoList()) {
                Usuario oldUsuarioOfCultivoListCultivo = cultivoListCultivo.getUsuario();
                cultivoListCultivo.setUsuario(usuario);
                cultivoListCultivo = em.merge(cultivoListCultivo);
                if (oldUsuarioOfCultivoListCultivo != null) {
                    oldUsuarioOfCultivoListCultivo.getCultivoList().remove(cultivoListCultivo);
                    oldUsuarioOfCultivoListCultivo = em.merge(oldUsuarioOfCultivoListCultivo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getEmail()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getEmail());
            List<Cultivo> cultivoListOld = persistentUsuario.getCultivoList();
            List<Cultivo> cultivoListNew = usuario.getCultivoList();
            List<Cultivo> attachedCultivoListNew = new ArrayList<Cultivo>();
            for (Cultivo cultivoListNewCultivoToAttach : cultivoListNew) {
                cultivoListNewCultivoToAttach = em.getReference(cultivoListNewCultivoToAttach.getClass(), cultivoListNewCultivoToAttach.getIdCultivo());
                attachedCultivoListNew.add(cultivoListNewCultivoToAttach);
            }
            cultivoListNew = attachedCultivoListNew;
            usuario.setCultivoList(cultivoListNew);
            usuario = em.merge(usuario);
            for (Cultivo cultivoListOldCultivo : cultivoListOld) {
                if (!cultivoListNew.contains(cultivoListOldCultivo)) {
                    cultivoListOldCultivo.setUsuario(null);
                    cultivoListOldCultivo = em.merge(cultivoListOldCultivo);
                }
            }
            for (Cultivo cultivoListNewCultivo : cultivoListNew) {
                if (!cultivoListOld.contains(cultivoListNewCultivo)) {
                    Usuario oldUsuarioOfCultivoListNewCultivo = cultivoListNewCultivo.getUsuario();
                    cultivoListNewCultivo.setUsuario(usuario);
                    cultivoListNewCultivo = em.merge(cultivoListNewCultivo);
                    if (oldUsuarioOfCultivoListNewCultivo != null && !oldUsuarioOfCultivoListNewCultivo.equals(usuario)) {
                        oldUsuarioOfCultivoListNewCultivo.getCultivoList().remove(cultivoListNewCultivo);
                        oldUsuarioOfCultivoListNewCultivo = em.merge(oldUsuarioOfCultivoListNewCultivo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getEmail();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getEmail();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Cultivo> cultivoList = usuario.getCultivoList();
            for (Cultivo cultivoListCultivo : cultivoList) {
                cultivoListCultivo.setUsuario(null);
                cultivoListCultivo = em.merge(cultivoListCultivo);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
