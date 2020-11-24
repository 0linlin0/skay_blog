package Filter;

import Dao.JdbcUtil;
import Entity.JDBCConnection;
import Entity.User;
import Util.SessionUtils;
import Util.TokenUtils;
import Util.initutil;

import javax.rmi.CORBA.Util;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import Util.*;

/**
 * @auther Skay
 * @date 2020/10/29 10:20
 * @description
 */
public class Check_person implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        PrintWriter out = response.getWriter();
        if(request.getParameter("action")!=null){
            if(request.getParameter("action").equals("login") || request.getParameter("action").equals("register")){
                if(JDBCConnection.JDBC == null){
                    JDBCConnection.JDBC = new JdbcUtil();
                    JDBCConnection.JDBC.getConnection();
                }
            }else {
                User login_user = (User) request.getSession().getAttribute("username");
                String token = "";
                token = initutil.gettoken(request.getCookies(),token); //获取token
                Enumeration<String> a = request.getParameterNames(); //获取的请求数 保存到map中
                TokenUtils.creat_token(request.getSession().getId());
                boolean s = SessionUtils.verifica_session(request.getSession());
                boolean t = TokenUtils.verifica_token(token, initutil.getsession(request.getCookies()));
                if(SessionUtils.verifica_session(request.getSession()) && TokenUtils.verifica_token(token,initutil.getsession(request.getCookies()))){
                    System.out.println("aaa");
                }else {
                    if(JDBCConnection.JDBC == null){
                        JDBCConnection.JDBC.releaseConn();
                    }
                    response.sendRedirect("./login.html");
                }
            }
        }

        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
