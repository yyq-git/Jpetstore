package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
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

public class SearchProductsServlet extends HttpServlet {
    private static final String VIEW_PRODUCT = "/WEB-INF/jsp/catalog/SearchProducts.jsp";
    private String searchProducts;
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        searchProducts = request.getParameter("keyword");
        CatelogService service = new CatelogService();
        List<Product> productList = service.searchProductList(searchProducts);

        HttpSession session = request.getSession();
        session.setAttribute("productList",productList);
        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 跳转到搜索商品结果界面 ";
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        //别忘记跳转界面
        request.getRequestDispatcher(VIEW_PRODUCT).forward(request,response);
    }
}
