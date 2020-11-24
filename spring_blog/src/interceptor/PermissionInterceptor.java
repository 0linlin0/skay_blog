package interceptor;

import entity.JDBCConnection;
import entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import util.JdbcUtil;
import util.SessionUtils;
import util.TokenUtils;

import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * @auther Skay
 * @date 2020/11/13 16:35
 * @description
 */
@Interceptor
public class PermissionInterceptor implements HandlerInterceptor {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        JDBCConnection.JDBC = new JdbcUtil();
        JDBCConnection.JDBC.getConnection();


//        String url = request.getRequestURI();
//        if(url.contains("login")|| url.contains("register")){
//            if(JDBCConnection.JDBC == null){
//                JDBCConnection.JDBC = new JdbcUtil();
//                JDBCConnection.JDBC.getConnection();
//            }else {
//                System.out.println("aaaa");
//            }
//        }else {
//            if(JDBCConnection.JDBC == null){
//                JDBCConnection.JDBC = new JdbcUtil();
//                JDBCConnection.JDBC.getConnection();
//            }else {
//                System.out.println("aaaa");
//            }
//            String token = "";
//            if(request.getCookies() == null){
//                request.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(request, response);
//            }else {
//                token = util.initutil.gettoken(request.getCookies(),token);
//                Enumeration<String> a = request.getParameterNames();
//                TokenUtils.creat_token(request.getSession().getId());
//                boolean s = SessionUtils.verifica_session(request.getSession());
//                boolean t = TokenUtils.verifica_token(token, util.initutil.getsession(request.getCookies()));
//                if(SessionUtils.verifica_session(request.getSession()) && TokenUtils.verifica_token(token, util.initutil.getsession(request.getCookies()))){
//                    System.out.println("aaa");
//                }else {
//                    if(JDBCConnection.JDBC == null){
//                        JDBCConnection.JDBC.releaseConn();
//                    }
//                    request.getRequestDispatcher("/WEB-INF/page/login.jsp").forward(request, response);
//                }
//            }

//        }

        return true;
    }

    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub
    }
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
        // TODO Auto-generated method stub
    }
}
