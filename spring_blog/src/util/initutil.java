package util;

import util.JdbcUtil;
import entity.JDBCConnection;
import entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;


public class initutil extends HttpServlet {
//    private HashMap<String,Object> req_map = new HashMap<>();;
//    private String Action = "";
//    private String token;

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        PrintWriter out = response.getWriter();
//
//        gettoken(request.getCookies()); //获取token
//        Enumeration<String> a = request.getParameterNames(); //获取的请求数 保存到map中
//        parsreq(request.getParameterNames(),request,response);
//        Action = request.getParameter("action");
//
//        if(Action.equals("login")){
//            if(JDBCConnection.JDBC == null){
//                JDBCConnection.JDBC = new JdbcUtil();
//                JDBCConnection.JDBC.getConnection();
//            }
//            LoginView loginView = new LoginView(req_map);
//            out.println(loginView.run(request,response).get_return_result());
//        }else if(Action.equals("register")){
//            if(JDBCConnection.JDBC == null){
//                JDBCConnection.JDBC = new JdbcUtil();
//                JDBCConnection.JDBC.getConnection();
//            }
//            RegisterView registerView = new RegisterView(req_map);
//            out.println(registerView.run(request,response).get_return_result());
//        }else{
//                //首先判断登录状态
//            TokenUtils.creat_token(request.getSession().getId());
//                boolean s = SessionUtils.verifica_session(request.getSession());
//                boolean t = TokenUtils.verifica_token(token,getsession(request.getCookies()));
//                if(SessionUtils.verifica_session(request.getSession()) && TokenUtils.verifica_token(token,getsession(request.getCookies()))){
////                if(true){
//                    User login_user = (User) request.getSession().getAttribute("username");
//                    if(Action.equals("")){
//                        response.sendRedirect("./index.html");
////                        out.println("<script>window.location.href = \"http://localhost:8081/blog2_war/login.html\";</script>");
//                    }else {
////                        JDBCConnection.JDBC = new JdbcUtil();
////                        JDBCConnection.JDBC.getConnection();
//                        switch (Action){
//                            case "index":
//                                IndexView indexView = new IndexView(req_map);
//                                out.println(indexView.run(request,response).get_return_result());
//                                break;
//                            case "reset":
//                                ResetView resetView = new ResetView(req_map);
//                                out.println(resetView.run(request,response).get_return_result());
//                                break;
//                            case "article":
//                                ArticleView articleView = new ArticleView(req_map);
//                                out.println(articleView.run(request,response).get_return_result());
//                                break;
//                            case "articles":
//                                ArticlesView articlesView = new ArticlesView(req_map);
//                                out.println(articlesView.run(request,response).get_return_result());
//                                break;
//                            case "addcomment":
//                                ArticleView articleView_comment = new ArticleView(req_map);
//                                out.println(articleView_comment.addcomment(request,response).get_return_result());
//                                break;
//                            case "admin_index":
//                                out.println("admin_index");
//                                break;
//                            case "admin_article":
//                                if(login_user.getUsername().equals("admin")){
//                                    AdminView adminView_article = new AdminView(req_map);
//                                    if(req_map.get("noname").equals("add_article")){
//                                        out.println(adminView_article.addarticle(request,response).get_return_result());
//                                    }else if(req_map.get("noname").equals("change_article")){
//                                        out.println(adminView_article.changearticle(request,response).get_return_result());
//                                    }else if(req_map.get("noname").equals("private_article")){
//                                        out.println(adminView_article.privatearticle(request,response).get_return_result());
//                                    }else {
//                                        //list
//                                        out.println(adminView_article.listarticle(request,response).get_return_result());
//                                    }
//                                }else {
//                                    response.sendRedirect("./login.html");
//                                    JDBCConnection.JDBC.releaseConn();
//                                }
//                                break;
//                            case "admin_user":
//                                if(login_user.getUsername().equals("admin")){
//                                    AdminView adminView_user = new AdminView(req_map);
//                                    if(req_map.get("noname").equals("change")){
//                                        out.println(adminView_user.changeuser(request,response).get_return_result());
//                                    }else if(req_map.get("noname").equals("del")){
//                                        out.println(adminView_user.deluser(request,response).get_return_result());
//                                    }else {
//                                        //list
//                                        out.println(adminView_user.listuser(request,response).get_return_result());
//                                    }
//                                }else {
//                                    response.sendRedirect("./login.html");
//                                    JDBCConnection.JDBC.releaseConn();
//                                }
//                                break;
//                            case "admin_comment":
//                                if(login_user.getUsername().equals("admin")){
//                                    AdminView adminView_comment = new AdminView(req_map);
//                                    if(req_map.get("noname").equals("del")){
//                                        out.println(adminView_comment.delcomment(request,response).get_return_result());
//                                    }else {
//                                        //list
//                                        out.println(adminView_comment.listcomment(request,response).get_return_result());
//                                    }
//                                }else {
//                                    response.sendRedirect("./login.html");
//                                    JDBCConnection.JDBC.releaseConn();
//                                }
//                                break;
//                        }
//                    }
//                }else {
//                    response.sendRedirect("./login.html");
//                    JDBCConnection.JDBC.releaseConn();
//                }
//            }
//        }
//
//
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        PrintWriter out = response.getWriter();
////        out.println("hello world");
//        doPost(request,response);
//    }

    /**
     * @Author  Skay
     * @Date  2020/10/20 16:08
     * @Param  []
     * @return  java.sql.Connection
     * @Description  初始化与会话对应唯一的数据库连接
     * @todo
     */
    public Connection initJDBC(){
        return JdbcUtil.getConnection();
    }


    public static String gettoken(Cookie[] cookies,String token){
        for (int i =0 ;i < cookies.length;i++){
            if(cookies[i].getName().equals("token")){
                token = cookies[i].getValue();
            }
        }
        return token;
    }

    public static String getsession(Cookie[] cookies){
        String session_str = "";
        for (int i =0 ;i < cookies.length;i++){
            if(cookies[i].getName().equals("JSESSIONID")){
                session_str = cookies[i].getValue();
            }
        }

        return session_str;
    }

    public static HashMap parsreq(Enumeration<String> enumeration,HttpServletRequest request,HttpServletResponse response,HashMap req_map) throws IOException {
        String name;
        String values;
        while (enumeration.hasMoreElements()) {
            name = (String) enumeration.nextElement();
            values = request.getParameter(name);
            if(name.equals("action")){
                System.out.println("");
            }else {
                req_map.put(name,values);
            }
        }

        return req_map;
    }

}
