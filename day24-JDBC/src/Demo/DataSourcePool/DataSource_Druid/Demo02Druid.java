package Demo.DataSourcePool.DataSource_Druid;


import Demo.DataSourcePool.util.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 使用工具类
 */
public class Demo02Druid {

    public static void main(String[] args) {
        /*
            完成添加操作， 给account表添加一条记录
        */

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            //获取连接
            connection = JDBCUtils.getConnection();
            //定义sql
            String sql = "INSERT INTO account VALUE(null, ?, ?)";
            //获取pstmt对象
            preparedStatement = connection.prepareStatement(sql);
            //给？赋值
            preparedStatement.setString(1, "Jack");
            preparedStatement.setDouble(2, 500);
            //执行sql
            int count = preparedStatement.executeUpdate();
            System.out.println(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            //释放资源
            JDBCUtils.close(preparedStatement, connection);
        }

    }
}
