/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POJOs;

/**
 *
 * @author alainlesage
 */
public class Comment {
    
    private Integer commentID = null;
    
    private String author = null;
    
    private String content = null;
    
    private Integer articleID = null;
    
    public Comment(String author, String content, Integer articleID)
    {
        setAuthor(author);
        setContent(content);
        setArticleID(articleID);
    }
    
    public Comment(Integer commentID, String authorID, String content, Integer articleID)
    {
        this(authorID, content, articleID);
        setCommentID(commentID);
    }

    private void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    private void setAuthor(String author) {
        this.author =  author;
    }

    private void setContent(String content) {
        this.content =  content;
    }

    private void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }
}
