package com.example.captcha.servlet;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.ImageCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@WebServlet
@ComponentScan(basePackages = "com.example.captcha.config.captcha.*")
public class CaptchaServlet extends HttpServlet {

    @Autowired
    private ImageCaptchaService captchaService;

    private WebApplicationContext springContext;

    @Override
    public void init(final ServletConfig config) throws ServletException {
        super.init(config);
        springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
        final AutowireCapableBeanFactory beanFactory = springContext.getAutowireCapableBeanFactory();
        beanFactory.autowireBean(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        byte[] captchaChallengeAsJpeg = null;
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        //
        String captchaId = request.getParameter("uuid");
        try {
            BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
            ImageIO.write(challenge, "jpeg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } catch (CaptchaServiceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        response.setHeader(HttpHeaders.CACHE_CONTROL, "no-store");
        response.setHeader(HttpHeaders.PRAGMA, "no-cache");
        response.setDateHeader(HttpHeaders.EXPIRES, 0);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String answer = request.getParameter("answer");
        String captchaID = request.getParameter("captchaID");
        boolean captchaPassed = this.captchaService.validateResponseForID(captchaID, answer);
        if (captchaPassed) {
              response.getWriter().write("<script> alert('captcha passed'); location.href='/captcha/auth' </script>");
        } else {
            response.getWriter().write("<script> alert('captcha failed'); location.href='/captcha/auth' </script>");
        }
      }
}
