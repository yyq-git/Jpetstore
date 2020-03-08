package org.csu.mypetstore.web.servlets;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.service.AccountService;
import org.csu.mypetstore.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AddAccountServlet extends HttpServlet {
    private static final String MAIN = "/WEB-INF/jsp/catalog/Main.jsp";
    private static final String NEW_ACCOUNT_FORM = "/WEB-INF/jsp/account/NewAccountForm.jsp";
    //创建新用户
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String languagePreference;
    private String favouriteCategoryId;
    private boolean listOption;
    private boolean bannerOption;

    private Account account;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AccountService service = new AccountService();
        HttpSession session = request.getSession();
        username = request.getParameter("username");
        password = request.getParameter("password");
        firstName = request.getParameter("account.firstName");
        lastName = request.getParameter("account.lastName");
        email = request.getParameter("account.email");
        phone = request.getParameter("account.phone");
        address1 = request.getParameter("account.address1");
        address2 = request.getParameter("account.address2");
        city = request.getParameter("account.city");
        state = request.getParameter("account.state");
        zip = request.getParameter("account.zip");
        country = request.getParameter("account.country");
        languagePreference = request.getParameter("account.languagePreference");
        favouriteCategoryId = request.getParameter("account.favouriteCategoryId");
        if (request.getParameter("account.listOption") != null){
            listOption = true;
        }else{
            listOption = false;
        }
        if (request.getParameter("account.bannerOption") != null){
            bannerOption = true;
        }else{
            bannerOption = false;
        }
        String clientCheckcode = request.getParameter("vCode");
        String serverCheckcode = (String)session.getAttribute("checkcode");
        System.out.println(clientCheckcode + " " + serverCheckcode);
        //特别注意想要刷新二维码，不能使用clientCheckcode.equals(serverCheckcode)，clientCheckcode可能为空
        if(username != "" && password != "" && serverCheckcode.equals(clientCheckcode)){
            account = new Account();
            account.setUsername(username);
            account.setPassword(password);
            account.setEmail(email);
            account.setFirstName(firstName);
            account.setLastName(lastName);
            account.setPhone(phone);
            account.setAddress1(address1);
            account.setAddress2(address2);
            account.setCity(city);
            account.setState(state);
            account.setZip(zip);
            account.setCountry(country);
            account.setLanguagePreference(languagePreference);
            account.setFavouriteCategoryId(favouriteCategoryId);
            account.setListOption(listOption);
            account.setBannerOption(bannerOption);
            service.insertAccount(account);
            session.setAttribute("account",account);
            request.getRequestDispatcher(MAIN).forward(request,response);
        }else{
            request.getRequestDispatcher(NEW_ACCOUNT_FORM).forward(request,response);
        }
    }
}
