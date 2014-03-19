/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ORM;

import ORM.exceptions.IllegalOrphanException;
import ORM.exceptions.NonexistentEntityException;
import ORM.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import POJOs.Article;
import POJOs.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.transaction.UserTransaction;

/**
 *
 * @author alainlesage
 */
public class UserJpaController implements Serializable {

    public UserJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Projet_JEEPU");
        this.utx = getEntityManager().getTransaction();
    }
    private EntityTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(User user) throws RollbackFailureException, Exception {
        if (user.getArticleList() == null) {
            user.setArticleList(new ArrayList<Article>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Article> attachedArticleList = new ArrayList<Article>();
            for (Article articleListArticleToAttach : user.getArticleList()) {
                articleListArticleToAttach = em.getReference(articleListArticleToAttach.getClass(), articleListArticleToAttach.getArticleID());
                attachedArticleList.add(articleListArticleToAttach);
            }
            user.setArticleList(attachedArticleList);
            em.persist(user);
            for (Article articleListArticle : user.getArticleList()) {
                User oldAuthorIDOfArticleListArticle = articleListArticle.getAuthorID();
                articleListArticle.setAuthorID(user);
                articleListArticle = em.merge(articleListArticle);
                if (oldAuthorIDOfArticleListArticle != null) {
                    oldAuthorIDOfArticleListArticle.getArticleList().remove(articleListArticle);
                    oldAuthorIDOfArticleListArticle = em.merge(oldAuthorIDOfArticleListArticle);
                }
            }
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

    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User persistentUser = em.find(User.class, user.getUserID());
            List<Article> articleListOld = persistentUser.getArticleList();
            List<Article> articleListNew = user.getArticleList();
            List<String> illegalOrphanMessages = null;
            for (Article articleListOldArticle : articleListOld) {
                if (!articleListNew.contains(articleListOldArticle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Article " + articleListOldArticle + " since its authorID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Article> attachedArticleListNew = new ArrayList<Article>();
            for (Article articleListNewArticleToAttach : articleListNew) {
                articleListNewArticleToAttach = em.getReference(articleListNewArticleToAttach.getClass(), articleListNewArticleToAttach.getArticleID());
                attachedArticleListNew.add(articleListNewArticleToAttach);
            }
            articleListNew = attachedArticleListNew;
            user.setArticleList(articleListNew);
            user = em.merge(user);
            for (Article articleListNewArticle : articleListNew) {
                if (!articleListOld.contains(articleListNewArticle)) {
                    User oldAuthorIDOfArticleListNewArticle = articleListNewArticle.getAuthorID();
                    articleListNewArticle.setAuthorID(user);
                    articleListNewArticle = em.merge(articleListNewArticle);
                    if (oldAuthorIDOfArticleListNewArticle != null && !oldAuthorIDOfArticleListNewArticle.equals(user)) {
                        oldAuthorIDOfArticleListNewArticle.getArticleList().remove(articleListNewArticle);
                        oldAuthorIDOfArticleListNewArticle = em.merge(oldAuthorIDOfArticleListNewArticle);
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
                Integer id = user.getUserID();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getUserID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Article> articleListOrphanCheck = user.getArticleList();
            for (Article articleListOrphanCheckArticle : articleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Article " + articleListOrphanCheckArticle + " in its articleList field has a non-nullable authorID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(user);
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

    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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

    public User findUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
