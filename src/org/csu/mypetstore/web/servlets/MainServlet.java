package org.csu.mypetstore.web.servlets;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainServlet extends javax.servlet.http.HttpServlet {
    //实现跳转
    //此处不用写web 而直接使用/表示根目录,注意区分大小写
    private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        //请求转发,特别注意转发请求与重定向的区别，决定着图片的路径如何改变

        request.getRequestDispatcher(MAIN).forward(request,response);
    }

}
