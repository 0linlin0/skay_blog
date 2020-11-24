package Servlet;

import Dao.JdbcUtil;
import Dao.UserDao;
import Entity.JDBCConnection;
import Entity.Return_result;
import Entity.User;
import Util.initutil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @auther Skay
 * @date 2020/10/20 15:41
 * @description
 */
public class RegisterServlet extends HttpServlet {
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

        RegisterServlet registerServlet = new RegisterServlet(req_map);
//        out.println(registerServlet.run(request,response).get_return_result());
        if(registerServlet.run(request,response).get_map_result().get("result").equals("true")){
            response.sendRedirect("./login.html");
        }else {
            response.sendRedirect("./register.html");
        }

    }

    public RegisterServlet() {
    }

    public RegisterServlet(HashMap<String, Object> req_map) {
        user.setUsername((String) req_map.get("username"));
        user.setPassword((String) req_map.get("password"));
        user.setEmail((String) req_map.get("email"));
    }

    public Return_result run(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        if(userDao.register_dao(user)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }

        return return_result;
    }
}
