package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Cart;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.service.CatelogService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddItemToCartServlet extends HttpServlet {
    //请求处理后跳转界面
    private static final String VIEW_CART = "/WEB-INF/jsp/cart/Cart.jsp";

    //处理请求数据（保存）
    private String workingItemId;
    private Cart cart;
    private Account account;
    //业务逻辑
    private CatelogService catelogService;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        workingItemId = request.getParameter("workingItemId");

        HttpSession session = request.getSession();
        cart = (Cart) session.getAttribute("cart");

        if(cart == null){
            cart = new Cart();
        }

        if(cart.containsItemId(workingItemId)){
            cart.incrementQuantityByItemId(workingItemId);
        }else{
            //是否在库存中
            catelogService = new CatelogService();
            boolean isInStock = catelogService.isItemInStock(workingItemId);
            Item item = catelogService.getItem(workingItemId);
            cart.addItem(item,isInStock);
        }

        session.setAttribute("cart",cart);
        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 添加商品 " + workingItemId + " 到购物车";
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(VIEW_CART).forward(request,response);
    }
}
