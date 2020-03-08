package org.csu.mypetstore.web.servlets;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpServlet extends HttpServlet {

    private static final String SIGN_UP = "/WEB-INF/jsp/account/NewAccountForm.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        if(session.getAttribute("categories") == null){
            List<String> categories = new ArrayList<String>();
            categories.add("FISH");
            categories.add("DOGS");
            categories.add("REPTILES");
            categories.add("CATS");
            categories.add("BIRDS");

            List<String> languages = new ArrayList<String>();
            languages.add("English");
            languages.add("Japanese");


            session.setAttribute("categories",categories);
            session.setAttribute("languages",languages);
        }
        request.getRequestDispatcher(SIGN_UP).forward(request,response);

    }
}
