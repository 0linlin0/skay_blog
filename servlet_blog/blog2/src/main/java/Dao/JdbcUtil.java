package Dao;

import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * @auther Skay
 * @date 2020/10/20 16:03
 * @description
 */
public class JdbcUtil {
    // 表示定义数据库的用户名
    private static String USERNAME;
    // 定义数据库的密码
    private static String PASSWORD;
    // 定义数据库的驱动信息
    private static String DRIVER;
    // 定义访问数据库的地址
    private static String URL;
    private static Connection connection;

    static {
        //加载数据库配置信息，并给相关的属性赋值
        loadConfig();
    }
    // 定义sql语句的执行对象
    private PreparedStatement pstmt;
    // 定义查询返回的结果集合
    private ResultSet resultSet;


    /**
        * @Author  Skay
        * @Date  2020/10/20 16:04
        * @Param  []
        * @return  void
        * @Description  加载数据库配置数据
        * @todo
    */
    public static void loadConfig() {
        try {
//            InputStream inStream = JdbcUtil.class.getResourceAsStream("./jdbc.properties");
//            Properties prop = new Properties();
//            prop.load(inStream);
//            USERNAME = prop.getProperty("jdbc.username");
//            PASSWORD = prop.getProperty("jdbc.password");
//            DRIVER = prop.getProperty("jdbc.driver");
//            URL = prop.getProperty("jdbc.url");

            USERNAME = "root";
            PASSWORD = "root";
            DRIVER = "com.mysql.jdbc.Driver";
            URL = "jdbc:mysql://localhost:3306/skay_blog?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
        } catch (Exception e) {
            throw new RuntimeException("读取数据库配置文件异常！", e);
        }
    }

    /**
        * @Author  Skay
        * @Date  2020/10/20 16:06
        * @Param  []
        * @return  java.sql.Connection
        * @Description  获取数据库连接
        * @todo
    */
    public static Connection getConnection() {
        try {
            Class.forName(DRIVER); // 注册驱动
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); // 获取连接
        } catch (Exception e) {
            throw new RuntimeException("get connection error!", e);
        }
        return connection;
    }

    /** 
        * @Author  Skay
        * @Date  2020/10/20 18:55
        * @Param  [sql, params] 
        * @return  boolean
        * @Description  
        * @todo
    */
    public boolean updateByPreparedStatement(String sql, List<?> params)
            throws SQLException {
        boolean flag = false;
        int result = -1;// 表示当用户执行添加删除和修改的时候所影响数据库的行数
        pstmt = connection.prepareStatement(sql);
        int index = 1;
        // 填充sql语句中的占位符
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;
        return flag;
    }
    /** 
        * @Author  Skay
        * @Date  2020/10/20 18:04
        * @Param  [sql, params] 
        * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
        * @Description  
        * @todo
    */
    public List<Map<String, Object>> findResult(String sql, List<?> params)
            throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = connection.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }
    /**
     * 释放资源
     */
    public void releaseConn() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
