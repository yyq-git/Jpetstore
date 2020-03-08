package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewNewOrderServlet extends HttpServlet {
    private static final String VIEW_NEW_ORDER = "/WEB-INF/jsp/order/NewOrderForm.jsp";
    private static final String SIGNON = "/WEB-INF/jsp/account/SignonForm.jsp";
    private Account account;
    private Order order;
    private Cart cart;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        account = (Account) session.getAttribute("account");
        cart = (Cart) session.getAttribute("cart");

        //如果未登录
        if(account == null){
            request.getRequestDispatcher(SIGNON).forward(request,response);
        }else{
            //set Card Type
            order = new Order();
            order.initOrder(account,cart);
            List<String> creditCardTypes = new ArrayList<String>();
            creditCardTypes.add("Visa");
            creditCardTypes.add("MasterCard");
            creditCardTypes.add("American Express");
            session.setAttribute("creditCardTypes",creditCardTypes);
            session.setAttribute("order",order);
            request.getRequestDispatcher(VIEW_NEW_ORDER).forward(request,response);
        }

    }
}
