package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.service.LogService;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ViewOrderByIdServlet extends HttpServlet {
    private static final String VIEW_ORDER = "/WEB-INF/jsp/order/ViewOrder.jsp";
    private int orderId;
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        orderId = Integer.parseInt(request.getParameter("orderId"));
        OrderService service = new OrderService();
        Order order = service.getOrder(orderId);

        if(order != null){
            session.setAttribute("order",order);
            if(account != null){
                String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
                LogService logService = new LogService();
                String logInfo = logService.logInfo(" ") + strBackUrl + " 查看订单 " + order ;
                logService.insertLogInfo(account.getUsername(),logInfo);
            }
        }
        request.getRequestDispatcher(VIEW_ORDER).forward(request,response);
    }
}
