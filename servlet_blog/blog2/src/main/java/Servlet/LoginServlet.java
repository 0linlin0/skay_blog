package Servlet;

import Dao.JdbcUtil;
import Dao.UserDao;
import Entity.JDBCConnection;
import Entity.Return_result;
import Entity.User;
import Util.SessionUtils;
import Util.TokenUtils;
import Util.initutil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @auther Skay
 * @date 2020/10/20 15:40
 * @description
 */
public class LoginServlet extends HttpServlet {
    private Return_result return_result = new Return_result();
    private User user = new User();
    private String token = "";
    private HashMap<String,Object> req_map = new HashMap<>();;
    private String Action = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //        PrintWriter out = response.getWriter();
//        out.println("hello world");
        doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        token = initutil.gettoken(request.getCookies(),token); //获取token
        Enumeration<String> a = request.getParameterNames(); //获取的请求数 保存到map中
        req_map = initutil.parsreq(request.getParameterNames(),request,response,req_map);
        Action = request.getParameter("action");

        LoginServlet loginServlet = new LoginServlet(req_map);
        String aa = (String) loginServlet.run(request,response).get_map_result().get("result");
        if(loginServlet.run(request,response).get_map_result().get("result").equals("ture")){
            response.sendRedirect("./index.html");
        }else {
            response.sendRedirect("./login.html");
        }
//        out.println(loginServlet.run(request,response).get_return_result());

    }

    public LoginServlet() {
    }

    public LoginServlet(HashMap<String, Object> req_map) {
        user.setUsername((String) req_map.get("username"));
        user.setPassword((String) req_map.get("password"));
    }

    public Return_result run(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        if(userDao.login_dao(user)){
            return_result.add_result_para("result","ture");
            HttpSession session = SessionUtils.creat_session(user,request);
            User session_user = (User)session.getAttribute("username");
            response.addCookie(TokenUtils.creat_token( session.getId()));
        }else {
            return_result.add_result_para("result","false");
        }

        return return_result;
    }
}


