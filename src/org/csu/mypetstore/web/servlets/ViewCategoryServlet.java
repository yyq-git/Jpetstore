package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.domain.Category;
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

public class ViewCategoryServlet extends HttpServlet {
    //客户端提交的数据处理
    //调用底层的业务逻辑和访问层
    //跳往一个页面并进行展示
    private static final String VIEW_CATEGORY = "/WEB-INF/jsp/catalog/Category.jsp";
    private String categoryId;
    private Account account;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryId = request.getParameter("categoryId");
        CatelogService service = new CatelogService();
        Category category = service.getCategory(categoryId);
        List<Product> productList = service.getProductListByCategory(categoryId);

        HttpSession session = request.getSession();
        session.setAttribute("category",category);
        session.setAttribute("productList",productList);
        account = (Account) session.getAttribute("account");
        if(account != null){
            String strBackUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + request.getServletPath() + "?" + (request.getQueryString());
            LogService logService = new LogService();
            String logInfo = logService.logInfo(" ") + strBackUrl + " 查看商品种类 "+categoryId;
            logService.insertLogInfo(account.getUsername(),logInfo);
        }
        request.getRequestDispatcher(VIEW_CATEGORY).forward(request,response);
    }
}
