package Demo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * JDBC 快速入门
 */

public class Demo01QuikLook {
    public static void main(String[] args) throws Exception {
        //1. 导入驱动jar包
        //2. 注册驱动
        // Class.forName("com.mysql.cj.jdbc.properties.Driver"); //注册驱动可以省略
        //3. 获取数据库的连接对象
        Connection connection = DriverManager.getConnection("jdbc.properties:mysql://127.0.0.1:3306/db1?serverTimezone=UTC", "root", "root");
        //4. 定义sql语句
        String sql = "UPDATE student SET score = 99.0 WHERE id = 0001";
        //5. 获取执行sql的对象 Statement
        Statement statement = connection.createStatement();
        //6. 执行sql
        int count = statement.executeUpdate(sql);
        //7. 处理结果
        System.out.println(count);
        //8. 释放资源
        statement.close();
        connection.close();
    }
}
