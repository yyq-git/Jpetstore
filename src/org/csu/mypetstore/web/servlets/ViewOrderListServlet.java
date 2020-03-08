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
import java.util.List;

public class ViewOrderListServlet extends HttpServlet {
    private static final String VIEW_LIST_ORDERS = "/WEB-INF/jsp/order/ListOrders.jsp";
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        OrderService service = new OrderService();
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        String username = account.getUsername();
        System.out.println(service);
        List<Order> orderList = service.getOrdersByUsername(username);
        session.setAttribute("orderList",orderList);
        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 查看订单列表 ";
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(VIEW_LIST_ORDERS).forward(request,response);
    }

}
