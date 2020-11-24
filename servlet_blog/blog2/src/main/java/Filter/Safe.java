package Filter;

import Util.safe.SQL_Safe;
import Util.safe.XSS_Safe;

import javax.rmi.CORBA.Util;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

import Util.initutil;

/**
 * @auther Skay
 * @date 2020/10/29 10:20
 * @description
 */
public class Safe implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HashMap<String, Object> req_map = new HashMap<>();
        req_map = initutil.parsreq(request.getParameterNames(), request, response, req_map);
        if(req_map != null){
            req_map = XSS_Safe.check(req_map);
            if(!SQL_Safe.check(req_map)){
                response.sendRedirect("./error.html");
            }
        }



        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
