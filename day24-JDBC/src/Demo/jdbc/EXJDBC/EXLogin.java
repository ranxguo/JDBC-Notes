package Demo.jdbc.EXJDBC;

import Demo.jdbc.util.EXJDBCUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *  练习：
 *     1. 通过键盘录入用户名密码
 *     2. 判断用户登陆是否成功
 */

public class EXLogin {
    /**
     * 登陆方法
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //连接数据库判断登陆是否成功
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            connection = EXJDBCUtils.getConnection();
            //sql语句
            String sql = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";
            //获取执行sql的对象
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //判断
            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            EXJDBCUtils.close(resultSet,statement,connection);
        }
        return false;
    }

    public static void main(String[] args) {
        //键盘录入，接收用户名和密码
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Username: ");
        String username = scanner.next();
        System.out.print("Enter Password: ");
        String password = scanner.next();
        //调用方法
        boolean login = new EXLogin().login(username, password);
        // 判断结果
        if (login) {
            System.out.println("登陆成功！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }
}
