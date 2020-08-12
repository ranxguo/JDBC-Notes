package Demo.jdbc.EXJDBC;

import Demo.jdbc.util.EXJDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EXTransaction {
    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement1 = null;
        PreparedStatement preparedStatement2 = null;
        try {
            //获取连接
            connection = EXJDBCUtils.getConnection();
            //开启事务
            connection.setAutoCommit(false);
            //定义sql
            String sql1 = "UPDATE account SET balance = balance - ? WHERE id = ?";
            String sql2 = "UPDATE account SET balance = balance + ? WHERE id = ?";
            //获取执行sql对象
            preparedStatement1 = connection.prepareStatement(sql1);
            preparedStatement2 = connection.prepareStatement(sql2);
            //设置参数
            preparedStatement1.setDouble(1, 100);
            preparedStatement1.setInt(2,1);
            preparedStatement2.setDouble(1, 100);
            preparedStatement2.setInt(2,2);
            //执行sql
            preparedStatement1.executeUpdate();
            //手动制造异常
            int i = 9 / 0;
            preparedStatement2.executeUpdate();
            //提交事务
            connection.commit();

        } catch (Exception e) {
            //事务的回滚
            try {
                if(connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            EXJDBCUtils.close(preparedStatement1, connection);
            EXJDBCUtils.close(preparedStatement2, null);
        }
    }
}
