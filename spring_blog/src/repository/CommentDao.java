package repository;


import entity.Comment;
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
 * @date 2020/10/20 15:33
 * @description
 */

@Repository
public class CommentDao {
    public Comment[] get_comments(String which_article) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result1 = null;
        paramList.add(which_article);
        try {
            result1 = jdbcUtil.findResult(
                    "select * from blog_comment where art_id = ?", paramList);
            Comment[] comments = new Comment[result1.size()];
            int i= 0;
            for (Map<String, Object> n : result1) {
                comments[i] = new Comment();
                comments[i].setWhich_user((String) n.get("which_user"));
                comments[i].setcontent((String) n.get("content"));
                comments[i].setId(String.valueOf(n.get("id")));
                i++;
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Comment[0];
        }
    }

    public boolean add_comment(Comment comment) {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);

        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(comment.getWhich_art());
        paramList.add(comment.getWhich_user());
        paramList.add(comment.getContent());
        paramList.add(comment.getContent());
        paramList.add(dateNowStr);

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "INSERT INTO `skay_blog`.`blog_comment` (`id`, `art_id`, `which_user`, `content`, `overview_content`, `time`, `is_see`, `space1`, `space2`, `space3`) VALUES (NULL, ?, ?, ?, ?, ?, '1', NULL, NULL, NULL);", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result){
            return true;
        }else {
            return false;
        }

    }

    public Comment[] get_all_comments(int page) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result1 = null;
        String result_num = String.valueOf(page * 10);
        try {
            result1 = jdbcUtil.findResult(
                    "SELECT * FROM `blog_comment` LIMIT "+ String.valueOf(page * 10-10)+" , "+result_num + ";", null);
            Comment[] comments = new Comment[result1.size()];
            int i= 0;
            for (Map<String, Object> n : result1) {
                comments[i] = new Comment();
                comments[i].setWhich_user((String) n.get("which_user"));
                comments[i].setcontent((String) n.get("content"));
                comments[i].setId(String.valueOf(n.get("id")));
                comments[i].setWhich_art(String.valueOf(n.get("which_art")));
                i++;
            }
            return comments;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Comment[0];
        }
    }

    public boolean del_comments(Comment comment) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(comment.getId());

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_comment` SET `is_see` = '0' WHERE `blog_comment`.`id` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
