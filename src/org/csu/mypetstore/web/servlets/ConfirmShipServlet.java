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

public class ConfirmShipServlet extends HttpServlet {
    private static final String CONFIRM_SHIPPING = "/WEB-INF/jsp/order/ShippingForm.jsp";
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        OrderService service = new OrderService();
        Order order = (Order) session.getAttribute("order");
        if(request.getParameter("order.shipToFirstName") != null){
            order.setShipToFirstName(request.getParameter("order.shipToFirstName"));
            order.setShipToLastName(request.getParameter("order.shipToLastName"));
            order.setShipAddress1(request.getParameter("order.shipAddress1"));
            order.setShipAddress2(request.getParameter("order.shipAddress2"));
            order.setShipCity(request.getParameter("order.shipCity"));
            order.setShipState(request.getParameter("order.shipState"));
            order.setShipZip(request.getParameter("order.shipZip"));
            order.setShipCountry(request.getParameter("order.shipCountry"));
            order.setCourier(request.getParameter("order.shipCountry"));
            session.setAttribute("order",order);
            request.getRequestDispatcher(CONFIRM_ORDER).forward(request,response);
        }else{
            service.insertOrder(order);
            request.getRequestDispatcher(CONFIRM_SHIPPING).forward(request,response);
        }
    }
}
