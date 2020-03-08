package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewEditAccountFormServlet extends HttpServlet {
    private static final String EDIT_ACCOUNT = "/WEB-INF/jsp/account/EditAccountForm.jsp";
    private Account account;
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
        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 跳转到编辑个人信息界面 ";
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(EDIT_ACCOUNT).forward(request,response);
    }
}
