package repository;

import entity.Config;
import entity.JDBCConnection;
import entity.User;
import org.springframework.stereotype.Repository;
import util.AESUtils;
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
public class UserDao {
    public boolean login_dao(User user) {
        String username = user.getUsername();
        String password = AESUtils.encodes(user.getPassword(), Config.getAESKEY());

        if(username != "" && password != ""){
//            JdbcUtil jdbcUtil = new JdbcUtil();
//            jdbcUtil.getConnection();
            JdbcUtil jdbcUtil = JDBCConnection.JDBC;
            List<Object> paramList = new ArrayList<Object>();
            paramList.add(username);
            paramList.add(password);
            List<Map<String, Object>> result = null;
            try {
                result = jdbcUtil.findResult(
                        "select * from blog_user where name = ? and password = ?", paramList);
                for (Map<String, Object> m : result) {
                    System.out.println(m);
                }
            } catch (SQLException e) {
                e.printStackTrace();}
//            } finally {
//                jdbcUtil.releaseConn();
//            }
            if(result.isEmpty()){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }

    public boolean register_dao(User user) {
        Date d = new Date();
        System.out.println(d);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateNowStr = sdf.format(d);

        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();

        paramList.add(user.getUsername());
        paramList.add(AESUtils.encodes(user.getPassword(), Config.getAESKEY()));
        paramList.add(dateNowStr);
        paramList.add(user.getEmail());
        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "INSERT INTO `skay_blog`.`blog_user` (`id`, `name`, `password`, `registe_date`, `is_use`, `user_blog`, `user_mail`, `space1`, `space2`) VALUES (NULL, ?, ?, ?, 1, NULL, ?, NULL, NULL);", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(result){
            return true;
        }else {
            return false;
        }
    }

    public boolean reset_dao(User user) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(AESUtils.encodes(user.getPassword(), Config.getAESKEY()));
        paramList.add(user.getEmail());
        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_user` SET `password` = ? WHERE `blog_user`.`user_mail` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result){
            return true;
        }else {
            return false;        }
    }

    public boolean del_user(User user) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(user.getId());
        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_user` SET `is_use` = '0' WHERE `blog_user`.`id` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(result){
            return true;
        }else {
            return false;        }
    }

    public boolean changeuser(User user) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        paramList.add(user.getUsername());
        paramList.add(AESUtils.encodes(user.getPassword(), Config.getAESKEY()));
        paramList.add(user.getId());

        boolean result = false;
        try {
            result = jdbcUtil.updateByPreparedStatement(
                    "UPDATE `skay_blog`.`blog_user` SET `name` = ?,`password` = ? WHERE `blog_user`.`id` = ?;", paramList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User[] get_all(int page) {
        JdbcUtil jdbcUtil = JDBCConnection.JDBC;
        List<Object> paramList = new ArrayList<Object>();
        List<Map<String, Object>> result = null;
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        User user5 = new User();
        User[] users = {user1,user2,user3,user4,user5};

        String result_num = String.valueOf(page * 10);

        try {
//            result = jdbcUtil.findResult("SELECT * FROM `blog_user` LIMIT "+String.valueOf(page * 10-10)+" , "+result_num + ";", null);
            result = jdbcUtil.findResult("SELECT * FROM `blog_user`;", null);
            int i = 0;
            for (Map<String, Object> m : result) {
                users[i].setUsername((String)m.get("name"));
                users[i].setPassword((String)m.get("password"));
                users[i].setId(String.valueOf(m.get("id")));
                users[i].setEmail((String)m.get("user_mail"));
                ++i;
                if(i == 5){
                    break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
