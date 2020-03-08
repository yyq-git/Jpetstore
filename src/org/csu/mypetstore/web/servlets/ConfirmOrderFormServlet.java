package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;
import org.csu.mypetstore.domain.Sequence;
import org.csu.mypetstore.service.LogService;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmOrderFormServlet extends HttpServlet {
    private static final String CONFIRM_SHIPPING = "/WEB-INF/jsp/order/ShippingForm.jsp";
    private static final String CONFIRM_ORDER = "/WEB-INF/jsp/order/ConfirmOrder.jsp";
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Order order = (Order) session.getAttribute("order");
        OrderService service = new OrderService();
        Cart cart = (Cart) session.getAttribute("cart");

        Account account = (Account) session.getAttribute("account");
        order.setCardType(request.getParameter("order.cardType"));
        order.setCreditCard(request.getParameter("order.creditCard"));
        order.setExpiryDate(request.getParameter("order.expiryDate"));
        order.setBillToFirstName(request.getParameter("order.billToFirstName"));
        order.setBillToLastName(request.getParameter("order.billToLastName"));
        order.setBillAddress1(request.getParameter("order.billAddress1"));
        order.setBillAddress2(request.getParameter("order.billAddress2"));
        order.setBillCity(request.getParameter("order.billCity"));
        order.setBillState(request.getParameter("order.billState"));
        order.setBillZip(request.getParameter("order.billZip"));
        order.setBillCountry(request.getParameter("order.billCountry"));

        session.setAttribute("order",order);

        System.out.println(request.getParameter("shippingAddressRequired"));
        if(request.getParameter("shippingAddressRequired") != null){
            account = (Account) session.getAttribute("account");

            if(account != null){
                String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
                LogService logService = new LogService();
                String logInfo = logService.logInfo(" ") + strBackUrl + " 跳转到修改到货信息界面 ";
                logService.insertLogInfo(account.getUsername(),logInfo);
            }

            request.getRequestDispatcher(CONFIRM_SHIPPING).forward(request,response);
        }else{
            service.insertOrder(order);

            account = (Account) session.getAttribute("account");
            if(account != null){
                String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                        + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
                LogService logService = new LogService();
                String logInfo = logService.logInfo(" ") + strBackUrl + " 跳转到确认订单信息界面 ";
                logService.insertLogInfo(account.getUsername(),logInfo);
            }

            request.getRequestDispatcher(CONFIRM_ORDER).forward(request,response);
        }
    }
}
