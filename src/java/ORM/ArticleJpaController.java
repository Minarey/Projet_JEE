/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ORM;

import ORM.exceptions.IllegalOrphanException;
import ORM.exceptions.NonexistentEntityException;
import ORM.exceptions.RollbackFailureException;
import POJOs.Article;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import POJOs.User;
import POJOs.Comment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author alainlesage
 */
public class ArticleJpaController implements Serializable {

    public ArticleJpaController() {
        this.emf = Persistence.createEntityManagerFactory("Projet_JEEPU");
        this.utx = getEntityManager().getTransaction();
    }
    private EntityTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Article article) throws RollbackFailureException, Exception {
        if (article.getCommentList() == null) {
            article.setCommentList(new ArrayList<Comment>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            User authorID = article.getAuthorID();
            if (authorID != null) {
                authorID = em.getReference(authorID.getClass(), authorID.getUserID());
                article.setAuthorID(authorID);
            }
            List<Comment> attachedCommentList = new ArrayList<Comment>();
            for (Comment commentListCommentToAttach : article.getCommentList()) {
                commentListCommentToAttach = em.getReference(commentListCommentToAttach.getClass(), commentListCommentToAttach.getCommentID());
                attachedCommentList.add(commentListCommentToAttach);
            }
            article.setCommentList(attachedCommentList);
            em.persist(article);
            if (authorID != null) {
                authorID.getArticleList().add(article);
                authorID = em.merge(authorID);
            }
            for (Comment commentListComment : article.getCommentList()) {
                Article oldNewsIDOfCommentListComment = commentListComment.getNewsID();
                commentListComment.setNewsID(article);
                commentListComment = em.merge(commentListComment);
                if (oldNewsIDOfCommentListComment != null) {
                    oldNewsIDOfCommentListComment.getCommentList().remove(commentListComment);
                    oldNewsIDOfCommentListComment = em.merge(oldNewsIDOfCommentListComment);
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

    public void edit(Article article) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Article persistentArticle = em.find(Article.class, article.getArticleID());
            User authorIDOld = persistentArticle.getAuthorID();
            User authorIDNew = article.getAuthorID();
            List<Comment> commentListOld = persistentArticle.getCommentList();
            List<Comment> commentListNew = article.getCommentList();
            List<String> illegalOrphanMessages = null;
            for (Comment commentListOldComment : commentListOld) {
                if (!commentListNew.contains(commentListOldComment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comment " + commentListOldComment + " since its newsID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (authorIDNew != null) {
                authorIDNew = em.getReference(authorIDNew.getClass(), authorIDNew.getUserID());
                article.setAuthorID(authorIDNew);
            }
            List<Comment> attachedCommentListNew = new ArrayList<Comment>();
            for (Comment commentListNewCommentToAttach : commentListNew) {
                commentListNewCommentToAttach = em.getReference(commentListNewCommentToAttach.getClass(), commentListNewCommentToAttach.getCommentID());
                attachedCommentListNew.add(commentListNewCommentToAttach);
            }
            commentListNew = attachedCommentListNew;
            article.setCommentList(commentListNew);
            article = em.merge(article);
            if (authorIDOld != null && !authorIDOld.equals(authorIDNew)) {
                authorIDOld.getArticleList().remove(article);
                authorIDOld = em.merge(authorIDOld);
            }
            if (authorIDNew != null && !authorIDNew.equals(authorIDOld)) {
                authorIDNew.getArticleList().add(article);
                authorIDNew = em.merge(authorIDNew);
            }
            for (Comment commentListNewComment : commentListNew) {
                if (!commentListOld.contains(commentListNewComment)) {
                    Article oldNewsIDOfCommentListNewComment = commentListNewComment.getNewsID();
                    commentListNewComment.setNewsID(article);
                    commentListNewComment = em.merge(commentListNewComment);
                    if (oldNewsIDOfCommentListNewComment != null && !oldNewsIDOfCommentListNewComment.equals(article)) {
                        oldNewsIDOfCommentListNewComment.getCommentList().remove(commentListNewComment);
                        oldNewsIDOfCommentListNewComment = em.merge(oldNewsIDOfCommentListNewComment);
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
                Integer id = article.getArticleID();
                if (findArticle(id) == null) {
                    throw new NonexistentEntityException("The article with id " + id + " no longer exists.");
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
            Article article;
            try {
                article = em.getReference(Article.class, id);
                article.getArticleID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The article with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comment> commentListOrphanCheck = article.getCommentList();
            for (Comment commentListOrphanCheckComment : commentListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Article (" + article + ") cannot be destroyed since the Comment " + commentListOrphanCheckComment + " in its commentList field has a non-nullable newsID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User authorID = article.getAuthorID();
            if (authorID != null) {
                authorID.getArticleList().remove(article);
                authorID = em.merge(authorID);
            }
            em.remove(article);
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

    public List<Article> findArticleEntities() {
        return findArticleEntities(true, -1, -1);
    }

    public List<Article> findArticleEntities(int maxResults, int firstResult) {
        return findArticleEntities(false, maxResults, firstResult);
    }

    private List<Article> findArticleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Article.class));
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

    public Article findArticle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Article.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Article> rt = cq.from(Article.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
