<%--
  Created by IntelliJ IDEA.
  User: aaa
  Date: 2019-10-11
  Time: 0:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">

<head><!--css的路径需要更改（此处已修改）：说明一下， ../ 指明向上跳一级，下面表示跳了三级-->
    <link rel="StyleSheet" href="css/jpetstore.css" type="text/css"
          media="screen" />

    <meta name="generator"
          content="HTML Tidy for Linux/x86 (vers 1st November 2002), see www.w3.org" />
    <title>MyJPetStore</title><!--改为自己的标题，这里我改成了项目的名称-->
    <meta content="text/html; charset=windows-1252"
          http-equiv="Content-Type" />
    <meta http-equiv="Cache-Control" content="max-age=0" />
    <meta http-equiv="Cache-Control" content="no-cache" />
    <meta http-equiv="expires" content="0" />
    <meta http-equiv="Expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
    <meta http-equiv="Pragma" content="no-cache" />
</head>

<body>

<div id="Header">
    <!--都进行相应的路径修改-->
    <div id="Logo">
        <div id="LogoContent">
            <a href="main"><img src="images/logo-topbar.gif" /></a>
        </div>
    </div>

    <div id="Menu">
        <div id="MenuContent">
            <a href="viewCart"><img align="middle" name="img_cart" src="images/cart.gif" /></a>
            <img align="middle" src="images/separator.gif" />
            <c:if test="${sessionScope.account == null}">
                <a href="signOn">Sign In</a>
            </c:if>
            <!--判断语句-->
            <c:if test="${sessionScope.account != null}">
                <a href="signOut">Sign Out</a> <img align="middle" src="images/separator.gif" />
                <a href="viewEditAccountForm">My Account</a> <img align="middle" src="images/separator.gif" />
            </c:if>
             <a href="viewHelp">?</a>
        </div>
    </div>

    <div id="Search">
        <div id="SearchContent">
            <form action="searchProducts" method="post">
                <input type="text" name="keyword" size="14" /> <input type="submit" name="searchProducts" value="Search" />
            </form>
        </div>
    </div>

    <div id="QuickLinks">
        <a href="viewCategory?categoryId=FISH">
            <img src="images/sm_fish.gif" />
        </a>
            <img src="images/separator.gif" />
        <a href="viewCategory?categoryId=DOGS">
            <img src="images/sm_dogs.gif" />
        </a>
            <img src="images/separator.gif" />
        <a href="viewCategory?categoryId=REPTILES">
            <img src="images/sm_reptiles.gif" />
        </a>
            <img src="images/separator.gif" />
        <a href="viewCategory?categoryId=CATS">
            <img src="images/sm_cats.gif" />
        </a>
        <img src="images/separator.gif" />
        <a href="viewCategory?categoryId=BIRDS">
            <img src="images/sm_birds.gif" />
        </a>
    </div>

</div>

<div id="Content">
    <!--此处由于是通用类，所以后边的内容是其他jsp负责的-->