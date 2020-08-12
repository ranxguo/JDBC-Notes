package Demo.jdbc;

import Demo.jdbc.util.EXJDBCUtils;

import java.sql.*;

public class Demo07UseUtil {
    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动
            //获取连接
            connection = EXJDBCUtils.getConnection();
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //释放资源
            EXJDBCUtils.close(resultSet,statement,connection);
        }
    }
}

