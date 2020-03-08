package org.csu.mypetstore.web.servlets;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
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

public class ViewOrderServlet extends HttpServlet {
    private static final String VIEW_ORDER = "/WEB-INF/jsp/order/ViewOrder.jsp";
    private Cart cart;
    private Order order;
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService service = new OrderService();
        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");
        order = (Order) session.getAttribute("order");
        if(order != null){
            service.insertOrder(order);
            session.setAttribute("order",order);
            cart = null;
            session.setAttribute("cart",cart);
            session.setAttribute("message","Thank you, your order has been submitted.");
            account = (Account) session.getAttribute("account");
            if(account != null){
                String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
                LogService logService = new LogService();
                String logInfo = logService.logInfo(" ") + strBackUrl + " 查看订单 " + order ;
                logService.insertLogInfo(account.getUsername(),logInfo);
            }
            request.getRequestDispatcher(VIEW_ORDER).forward(request,response);
        }

    }
}
