package Demo.jdbc;

import java.sql.*;

/**
 * Query
 */

public class Demo06Query {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //获取连接
            connection = DriverManager.getConnection("jdbc.properties:mysql://127.0.0.1:3306/db1?serverTimezone=UTC", "root", "root");
            //获取执行sql对象
            statement = connection.createStatement();
            //定义sql
            String sql = "SELECT * FROM student";
            //执行sql
            resultSet = statement.executeQuery(sql);
            //处理结果
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                System.out.println("ID: " + id + " name: " + name);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                if (statement != null) {
                    resultSet.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
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
