package Demo.DataSourcePool.DataSource_C3P0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * C3P0演示 连接对象
 */

public class Demo01C3P0 {
    public static void main(String[] args) throws SQLException {
        //创建数据库连接池对象
        DataSource ds = new ComboPooledDataSource();
        //获取连接对象
        Connection connection = ds.getConnection();
        //打印
        System.out.println(connection);

    }
}
