package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import POJOs.Article;
import Modele.Articles;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author alainlesage
 */
@WebServlet(urlPatterns = {""})
public class FrontController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            try {
            StringBuilder articles = new StringBuilder();
//            String menuVisitor = "<a href='login.jsp'>Log in</a>";
//            String menuMembre = "<a href='#'>Write an article</a><br/><a href='#'>Log out</a>";
            
//            if (request.getSession().getAttribute("user") != null)
//            {
//                request.setAttribute("menu", menuMembre);
//            }
//            else
//            {
//                request.setAttribute("menu", menuVisitor);
//            }
            
            for (Article article : Articles.getInstance().getArticles()) 
            {
                articles.append("<h2>");
                articles.append(article.getTitle()).append("<br/>");
                articles.append("</h2>");
                
                articles.append("<p>");
                articles.append(article.getContent()).append("<br/>");
                articles.append("</p>");
            }

                        
            request.setAttribute("articles", articles.toString());
            getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
            }
            catch (Exception e)
            {
                getServletContext().getRequestDispatcher("/erreur.jsp").forward(request, response);
                e.printStackTrace();
            }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
