package org.csu.mypetstore.web.servlets;

import javax.imageio.ImageIO;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class VerificationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("image/jpeg");
        HttpSession session = request.getSession();
        int width = 60;
        int height = 20;
        //设置浏览器不缓存该图片
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires",0);
        //创建内存图像并获得图形上下文
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //获取画布
        Graphics g = image.getGraphics();

        //产生随机的验证码(四位字符)
        String chars = "0123456789ASCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] rands = new char[4];
        for(int i = 0; i < 4; i++){
            int rand = (int) (Math.random()*36);
            rands[i] = chars.charAt(rand);
        }
        //产生图片
        //先画背景
        g.setColor(Color.gray);
        g.fillRect(0,0,width,height);
        //产生干扰
        for(int i = 0; i < 120; i++){
            int x=(int)(Math.random()*width);
            int y=(int)(Math.random()*height);
            int red=(int)(Math.random()*255);
            int green=(int)(Math.random()*255);
            int blue=(int)(Math.random()*255);
            g.setColor(new Color(red,green,blue));
            g.drawOval(x, y, 1, 0);
        }
        //画出字符
        g.setColor(Color.BLACK);
        g.setFont(new Font(null, Font.ITALIC|Font.BOLD,18));
        //在不同的高度进行绘制
        g.drawString(""+rands[0], 1, 17);
        g.drawString(""+rands[1], 16, 15);
        g.drawString(""+rands[2], 31, 18);
        g.drawString(""+rands[3], 46, 16);
        g.dispose();
        //把图像传到客户端
        ServletOutputStream servletOutputStream = response.getOutputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", byteArrayOutputStream);
        byte[] buffer = byteArrayOutputStream.toByteArray();
        response.setContentLength(buffer.length);
        servletOutputStream.write(buffer);
        byteArrayOutputStream.close();
        servletOutputStream.close();

        session.setAttribute("checkcode", new String(rands));
    }
}
