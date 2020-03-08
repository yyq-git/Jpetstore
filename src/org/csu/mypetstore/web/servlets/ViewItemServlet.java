package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatelogService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewItemServlet extends HttpServlet {
    private static final String VIEW_ITEM = "/WEB-INF/jsp/catalog/Item.jsp";
    private String itemId;
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        itemId = request.getParameter("itemId");
        CatelogService service = new CatelogService();
        //获得选择对象
        Item item = service.getItem(itemId);

        HttpSession session = request.getSession();
        session.setAttribute("item",item);

        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 查看具体产品 " + itemId;
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(VIEW_ITEM).forward(request,response);
    }
}
