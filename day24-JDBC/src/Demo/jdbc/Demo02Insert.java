package Demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * student表添加一条记录insert语句
 */

public class Demo02Insert {
    public static void main(String[] args){
        //1. 导入驱动jar包 可省略
        //2. 注册驱动
        Connection connection = null;
        Statement statement = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //3.定义SQL
            String sql = "insert into student values(2, 'Amy',28, 86.0, '1992-03-29', null)";
            //4. 获取Connection对象
            connection = DriverManager.getConnection("jdbc.properties:mysql:///db1?serverTimezone=UTC", "root", "root");
            //获取执行sql的对象Statement
            statement = connection.createStatement();
            //执行sql
            int count = statement.executeUpdate(sql);
            //处理结果
            System.out.println(count);
            if (count > 0) {
                System.out.println("添加成功！");
            } else {
                System.out.println("添加失败！");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //避免空指针异常
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    }
}