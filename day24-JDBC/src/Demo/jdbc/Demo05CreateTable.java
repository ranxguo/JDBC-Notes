package Demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 创建teacher张表
 */

public class Demo05CreateTable {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            connection = DriverManager.getConnection("jdbc.properties:mysql://127.0.0.1:3306/db1?serverTimezone=UTC", "root", "root");
            //获取执行sql对象
            statement = connection.createStatement();
            //定义sql
            String sql = "CREATE TABLE teacher (id INT, name VARCHAR(20))";
            //执行sql
            int count = statement.executeUpdate(sql);
            //处理结果
            System.out.println(count);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                if (statement != null) {
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
