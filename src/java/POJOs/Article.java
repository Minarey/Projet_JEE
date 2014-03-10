/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package POJOs;

import java.util.Date;

/**
 *
 * @author alainlesage
 */
public class Article {
    
    private Integer articleID = null;
    
    private Date pubDate = null;
    
    private String title = null;
    
    private String content = null;
    
    private Integer authorID = null; 
    
    Article(Date pubDate, String title, String content, Integer authorID)
    {
        setPubDate(pubDate);
        setTitle(title);
        setContent(content);
        setAuthorID(authorID);
    }
    
    Article(Integer articleID, Date pubDate, String title, String content, Integer authorID)
    {
        this(pubDate, title, content, authorID);
        setArticleID(articleID);
    }

    private void setArticleID(Integer articleID) {
        this.articleID = articleID;
    }

    private void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setAuthorID(Integer authorID) {
        this.authorID = authorID;
    }
}
