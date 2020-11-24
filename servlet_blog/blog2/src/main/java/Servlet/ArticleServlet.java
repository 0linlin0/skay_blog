package Servlet;

import Dao.ArticleDao;
import Dao.CommentDao;
import Entity.Article;
import Entity.Comment;
import Entity.Return_result;
import Entity.User;
import Util.initutil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @auther Skay
 * @date 2020/10/20 15:41
 * @description
 */
public class ArticleServlet extends HttpServlet {
    private Return_result return_result = new Return_result();
    Comment comment = new Comment();
    int art_id = 1;

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
        if(Action.equals("comment")){
            comment.setcontent((String) req_map.get("content"));
            User login_user = (User) request.getSession().getAttribute("username");
            comment.setWhich_user(login_user.getUsername());
            comment.setWhich_art((String) req_map.get("id"));
            out.println(addcomment().get_return_result());
        }else {
            ArticleServlet articleServlet = new ArticleServlet(req_map);
            out.println(articleServlet.run(request,response).get_return_result());
        }



    }
    public ArticleServlet(){

    }

    public ArticleServlet(HashMap<String, Object> req_map) {
        art_id = Integer.parseInt((String)req_map.get("id"));
        if(req_map.containsKey("comment_user")){
            comment.setWhich_user((String) req_map.get("comment_user"));
            byte[] tmpcomment = Base64.getDecoder().decode((String)req_map.get("comment_content"));
            comment.setcontent(new String(tmpcomment));
            comment.setWhich_art(String.valueOf(art_id));
        }
    }

    public Return_result run(HttpServletRequest request, HttpServletResponse response) {
        ArticleDao articleDao = new ArticleDao();
        Article article = articleDao.get_article(art_id);
        return_result.add_result_para("art_id",String.valueOf(article.getId()));
        return_result.add_result_para("art_content",article.getContent());
        return_result.add_result_para("art_editor","admin");
        return_result.add_result_para("art_title",article.getTitle());

        CommentDao commentDao = new CommentDao();
        Comment[] comments = commentDao.get_comments(art_id);
        for (Comment each_comment:comments){
            return_result.add_result_para(each_comment.getId(),each_comment.getWhich_user()+"*"+each_comment.getContent());
        }

        return return_result;
    }

    public Return_result addcomment() {
        CommentDao commentDao = new CommentDao();
        if(commentDao.add_comment(comment)){
            return_result.add_result_para("result","true");
        }else {
            return_result.add_result_para("result","false");
        }
        return return_result;
    }
}
