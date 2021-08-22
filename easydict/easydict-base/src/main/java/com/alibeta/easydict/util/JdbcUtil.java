package com.alibeta.easydict.util;

import com.alibeta.easydict.annotation.DictLoader;

import java.lang.reflect.Method;
import java.sql.*;

/**
 * 数据库工具类,封装了JDBC的通用操作
 *
 * @author huojg
 */
public class JdbcUtil {

    private static String className;
    private static String url;
    private static String user;
    private static String password;

    public JdbcUtil(Method method) {
        this.className = method.getAnnotation(DictLoader.class).className();
        this.url = method.getAnnotation(DictLoader.class).url();
        this.user = method.getAnnotation(DictLoader.class).username();
        this.password = method.getAnnotation(DictLoader.class).password();
    }

    /**
     * 获取数据库连接对象
     *
     * @return
     * @throws ClassNotFoundException,SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(className);
        return DriverManager.getConnection(url, user, password);
    }

    /**
     * 关闭数据库的相关连接
     *
     * @param conn
     * @param stat
     * @param res
     */
    public static void closeDb(Connection conn, Statement stat, ResultSet res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (stat != null) {
                stat.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
