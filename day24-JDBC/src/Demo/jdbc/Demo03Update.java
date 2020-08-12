package Demo.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * student表修改记录
 */

public class Demo03Update {
    public static void main(String[] args){
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
            String sql = "UPDATE student SET name = 'Ryan' WHERE id = 1";
            //执行sql
            int count = statement.executeUpdate(sql);
            //处理结果
            System.out.println(count);
            if (count > 0) {
                System.out.println("修改成功");
            } else {
                System.out.println("修改失败");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            try {
                statement.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}