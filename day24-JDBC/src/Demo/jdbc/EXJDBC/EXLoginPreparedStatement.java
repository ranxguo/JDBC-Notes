package Demo.jdbc.EXJDBC;

import Demo.jdbc.util.EXJDBCUtils;

import java.sql.*;
import java.util.Scanner;

public class EXLoginPreparedStatement {
    /**
     * 登陆方法,使用PreparedStatement来实现
     */
    public boolean login(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        //连接数据库判断登陆是否成功
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //获取连接
            connection = EXJDBCUtils.getConnection();
            //sql语句
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            //获取执行sql的对象
            preparedStatement = connection.prepareStatement(sql);
            //给？赋值
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            //判断
            return resultSet.next();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            EXJDBCUtils.close(resultSet,preparedStatement,connection);
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
        boolean login = new EXLoginPreparedStatement().login(username, password);
        // 判断结果
        if (login) {
            System.out.println("登陆成功！");
        } else {
            System.out.println("用户名或密码错误！");
        }
    }
}
