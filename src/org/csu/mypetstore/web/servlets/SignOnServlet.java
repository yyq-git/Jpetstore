package org.csu.mypetstore.web.servlets;


import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOnServlet extends HttpServlet {

    private static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";
    private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";//登录成功跳转的界面
    private String username;
    private String password;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String message;
        if(request.getParameter("username") == null){
            request.getRequestDispatcher(SIGNON).forward(request,response);
        }else{
            HttpSession session = request.getSession();
            AccountService service = new AccountService();
            username = request.getParameter("username");
            password = request.getParameter("password");
            Account account = service.getAccount(username, password);
            String clientCheckcode = request.getParameter("vCode");
            String serverCheckcode = (String)session.getAttribute("checkcode");
            //登录成功后设置错误信息提示为空
            message = "";
            session.setAttribute("message", message);
            if(account != null && serverCheckcode.equals(clientCheckcode)){
                session.setAttribute("account",account);
                String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
                LogService logService = new LogService();
                String logInfo = logService.logInfo(" ") + strBackUrl + " 用户登录";
                logService.insertLogInfo(account.getUsername(),logInfo);

                request.getRequestDispatcher(MAIN).forward(request, response);
            }else{
                message = "Invalid username or password or checkcode.  Signon failed.";
                session.setAttribute("message", message);
                request.getRequestDispatcher(SIGNON).forward(request,response);
            }
        }
    }
}
