package repository;

import entity.Article;
import entity.JDBCConnection;
import org.springframework.stereotype.Repository;
import util.JdbcUtil;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther Skay
 * @date 2020/10/20 15:30
 * @description
 */

@Repository
public class ArticleDao {
    public Article[] get_newest() {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result1 = null;
        Article art1 = new Article();
        Article art2 = new Article();
        Article art3 = new Article();

        art1.setTitle("aaa");
        art2.setTitle("bbb");
        art3.setTitle("ccc");

        Article[] new_articles = {art1,art2,art3};
        try {
            result1 = jdbcUtil.findResult(
                    "SELECT * FROM `blog_article` order by art_id desc LIMIT 3;", null);
            int i1 = 0;
            for (Map<String, Object> m : result1) {
                new_articles[i1].setTitle((String) m.get("art_title"));
                new_articles[i1].setId((Integer) m.get("art_id"));
                i1++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new_articles;

    }

    public Article[] get_recommend() {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result = null;
        Article art4 = new Article();
        Article art5 = new Article();
        art4.setTitle("ddd");
        art5.setTitle("eee");
        Article[] recommend_articles = {art4,art5};

        try {
            result = jdbcUtil.findResult(
                    "SELECT * FROM `blog_article` LIMIT 0 , 2;", null);
            int i = 0;
            for (Map<String, Object> m : result) {
                recommend_articles[i].setTitle((String) m.get("art_title"));
                recommend_articles[i].setId((Integer) m.get("art_id"));
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recommend_articles;
    }

    public Article[] get_all(String page) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result = null;
        Article art1 = new Article();
        Article art2 = new Article();
        Article art3 = new Article();
        Article art4 = new Article();
        Article art5 = new Article();

        art1.setTitle("aaa");
        art2.setTitle("bbb");
        art3.setTitle("ccc");
        art3.setTitle("ddd");
        art3.setTitle("eee");

        art1.setTime("2020-10-13");
        art2.setTime("2020-10-1");
        art3.setTime("2020-1-1");
        art4.setTime("2019-10-1");
        art5.setTime("2019-10-1");
        Article[] articles = {art1,art2,art3,art4,art5};

        String result_num = String.valueOf(Integer.parseInt(page) * 10);

        try {
            result = jdbcUtil.findResult(
                    "SELECT * FROM `blog_article` LIMIT "+ String.valueOf(Integer.parseInt(page) * 10-10)+" , "+result_num + ";", null);
            int i = 0;
            for (Map<String, Object> m : result) {
                articles[i].setTitle((String)m.get("art_title"));
                articles[i].setTime(m.get("art_time").toString());
                articles[i].setId((Integer) m.get("art_id"));
                ++i;
                if(i == 5){
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return articles;

    }

    public Article get_article(String which_article) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        String art_id = which_article;
        Article art = new Article();
        art.setContent("文章内内容");
        art.setTitle("标题");
        if(art_id != ""){
            paramList.add(art_id);
            List<Map<String, Object>> result = null;
            try {
                result = jdbcUtil.findResult(
                        "select * from blog_article where art_id = ?", paramList);
                for (Map<String, Object> m : result) {
                    art.setTitle((String) m.get("art_title"));
                    art.setContent((String) m.get("art_content"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return art;
    }

    //todo 待测试
    public boolean addarticle(Article article) {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);

        article.setTitle("this is test");
        article.setContent("content content content content");
        article.setTime(dateNowStr);
        article.setDescription("test");
        article.setEditor("admin");
        article.setTag("test");

        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(article.getTitle());
        paramList.add(article.getTag());
        paramList.add(article.getDescription());
        paramList.add(article.getContent());
        paramList.add(article.getTime());

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "INSERT INTO `skay_blog`.`blog_article` (`art_id`, `art_title`, `art_tag`, `art_description`, `art_content`, `art_time`, `art_editor`, `is_private`, `sapce1`, `space2`) VALUES (NULL, ?, ?, ?, ?, ?, 'admin', '0', NULL, NULL);", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //todo 待测试
    public boolean changearticle(Article article) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(article.getTitle());
        paramList.add(article.getTag());
        paramList.add(article.getDescription());
        paramList.add(article.getContent());
        paramList.add(article.getId());

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_article` SET `art_title` = ?,`art_tag` = ?,`art_description` = ?,`art_content` = ? WHERE `blog_article`.`art_id` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean privatearticle(Article article) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(article.getTitle());
        paramList.add(article.getTag());
        paramList.add(article.getDescription());
        paramList.add(article.getContent());
        paramList.add(article.getId());

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_article` SET `is_private` = '1' WHERE `blog_article`.`art_id` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
