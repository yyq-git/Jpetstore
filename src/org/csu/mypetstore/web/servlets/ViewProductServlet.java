package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.service.CatelogService;
import org.csu.mypetstore.service.LogService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class ViewProductServlet extends HttpServlet {
    private String productId;
    private static final String VIEW_PRODUCT = "/WEB-INF/jsp/catalog/Product.jsp";
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        productId = request.getParameter("productId");
        CatelogService service = new CatelogService();
        Product product = service.getProduct(productId);
        List<Item> itemList = service.getItemListByProduct(productId);

        HttpSession session = request.getSession();
        session.setAttribute("product",product);
        session.setAttribute("itemList",itemList);

        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 查看产品 " + product.getName();
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(VIEW_PRODUCT).forward(request,response);
    }
}
