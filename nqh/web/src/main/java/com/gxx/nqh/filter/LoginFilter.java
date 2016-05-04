package com.gxx.nqh.filter;

import com.gxx.nqh.entity.UserInfo;
import com.gxx.nqh.exceptions.LoginTimeOutException;
import com.gxx.nqh.exceptions.NoPermitionException;
import com.gxx.nqh.service.UserInfoService;
import com.gxx.nqh.util.ResponseUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ZHUKE on 2016/4/5.
 */
@Component
public class LoginFilter implements Filter {
    private Logger logger = LogManager.getLogger(LoginFilter.class);

    @Autowired
    private UserInfoService loginUserService;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ResponseUtil responseUtil = new ResponseUtil();
        try {
            ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", "*");

            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            logger.info("A new http connect, client host:" + request.getRemoteAddr() + ", request the url:" + ((HttpServletRequest) request).getRequestURL());

            response.setContentType("json");

            String url = ((HttpServletRequest) request).getRequestURL().toString();

            if (url.contains("/assets") || url.contains("/login") ||url.contains("admin") || url.contains("/register") || url.contains("/validation") || url.contains(".jpg") || url.contains(".css") || url.contains(".js")) {
                chain.doFilter(request, response);
                return;
            } else {

                String token = null;
                token = request.getParameter("accessToken");

                if (StringUtils.isEmpty(token)) {
                    responseUtil = ResponseUtil._403Error;
                    response.getWriter().print(responseUtil.toString());
                    response.getWriter().flush();
                    return;
                } else {
                    UserInfo loginUser = loginUserService.getLoginUser(token);
                    if (loginUser != null) {
                        chain.doFilter(request, response);
                        return;
                    } else {
                        responseUtil = ResponseUtil._403Error;
                        response.getWriter().flush();
                        return;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (LoginTimeOutException e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._401Error;
            response.getWriter().print(responseUtil.toString());
            response.getWriter().flush();
        } catch (NoPermitionException e) {
            e.printStackTrace();
            responseUtil = ResponseUtil._403Error;
            response.getWriter().print(responseUtil.toString());
            response.getWriter().flush();
        }
    }

    public void destroy() {

    }

}
