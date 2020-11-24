package Servlet;

import Dao.ArticleDao;
import Dao.CommentDao;
import Dao.UserDao;
import Entity.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import Util.*;

/**
 * @auther Skay
 * @date 2020/10/22 16:02
 * @description
 */
public class AdminServlet extends HttpServlet {
    private Return_result return_result = new Return_result();
    private User user = new User();
    private Article article = new Article();
    private Comment comment = new Comment();
    private int page = 1;

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

        User login_user = (User) request.getSession().getAttribute("username");

        AdminServlet adminServlet = new AdminServlet(req_map);
        switch (Action){
            case "admin_index":
                out.println("admin_index");
                break;
            case "admin_article":
                if(login_user.getUsername().equals("admin")){
                    if(req_map.get("noname").equals("add_article")){
                        out.println(adminServlet.addarticle(request,response).get_return_result());
                    }else if(req_map.get("noname").equals("change_article")){
                        out.println(adminServlet.changearticle(request,response).get_return_result());
                    }else if(req_map.get("noname").equals("private_article")){
                        out.println(adminServlet.privatearticle(request,response).get_return_result());
                    }else {
                        //list
                        out.println(adminServlet.listarticle(request,response).get_return_result());
                    }
                }else {
                    response.sendRedirect("./login.html");
                    JDBCConnection.JDBC.releaseConn();
                }
                break;
            case "admin_user":
                if(login_user.getUsername().equals("admin")){
                    if(req_map.get("noname").equals("change")){
                        out.println(adminServlet.changeuser(request,response).get_return_result());
                    }else if(req_map.get("noname").equals("del")){
                        out.println(adminServlet.deluser(request,response).get_return_result());
                    }else {
                        //list
                        out.println(adminServlet.listuser(request,response).get_return_result());
                    }
                }else {
                    response.sendRedirect("./login.html");
                    JDBCConnection.JDBC.releaseConn();
                }
                break;
            case "admin_comment":
                if(login_user.getUsername().equals("Admin")){
                    if(req_map.get("noname").equals("del")){
                        out.println(adminServlet.delcomment(request,response).get_return_result());
                    }else {
                        //list
                        out.println(adminServlet.listcomment(request,response).get_return_result());
                    }
                }else {
                    response.sendRedirect("./login.html");
                    JDBCConnection.JDBC.releaseConn();
                }
                break;
        }
    }


    public AdminServlet(){

    }

    public AdminServlet(HashMap<String, Object> req_map) {
        String noname = (String) req_map.get("noname");
        switch (noname){
            case "add_article":
                article.setTitle((String) req_map.get("title"));
                article.setContent((String) req_map.get("content"));
                article.setTag((String) req_map.get("tag"));
                article.setDescription((String) req_map.get("description"));
                break;
            case "change_article":
                article.setTitle((String) req_map.get("title"));
                article.setContent((String) req_map.get("content"));
                article.setTag((String) req_map.get("tag"));
                article.setDescription((String) req_map.get("description"));
                article.setId((Integer) req_map.get("id"));
                break;
            case "list_article":
                page = (int) req_map.get("page");
                break;
            case "private_article":
                article.setId((Integer) req_map.get("id"));
                break;
            case "list_comment":
                page = (int) req_map.get("page");
                break;
            case "del_comment":
                comment.setId((String) req_map.get("id"));
                break;
            case "list_user":
                page = (int) req_map.get("page");
                break;
            case "del_user":
                user.setId((String) req_map.get("id"));
                break;
            case "change_user":
                user.setEmail((String) req_map.get("email"));
                user.setPassword((String) req_map.get("password"));
                user.setUsername((String) req_map.get("username"));
                break;
        }
    }

    public Return_result addarticle(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        if(articleDao.addarticle(article)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result changearticle(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        if(articleDao.changearticle(article)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result listarticle(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        Article[] all_articles = articleDao.get_all(page);
        for (Article each_article:all_articles){
            return_result.add_result_para(String.valueOf(each_article.getId()),each_article.getTitle());
        }
        return return_result;
    }

    public Return_result privatearticle(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        if(articleDao.privatearticle(article)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result changeuser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        if(userDao.changeuser(user)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result deluser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        if(userDao.del_user(user)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result listuser(HttpServletRequest request, HttpServletResponse response) {
        UserDao userDao = new UserDao();
        User[] all_user = userDao.get_all(page);
        for (User each_user:all_user){
            return_result.add_result_para(String.valueOf(each_user.getId())+"*"+each_user.getUsername(),each_user.getEmail());
        }
        return return_result;
    }


    public Return_result delcomment(HttpServletRequest request, HttpServletResponse response) {
        CommentDao commentDao = new CommentDao();
        if(commentDao.del_comments(comment)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }

    public Return_result listcomment(HttpServletRequest request, HttpServletResponse response) {
        CommentDao commentDao = new CommentDao();
        Comment[] comments = commentDao.get_all_comments(page);
        for (Comment each_comment:comments){
            return_result.add_result_para(each_comment.getId(),each_comment.getWhich_user()+"*"+each_comment.getContent());
        }

        return return_result;
    }
}
