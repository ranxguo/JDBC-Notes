package Demo.DataSourcePool.DataSource_C3P0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * c3p0演示 最大连接数与多个config
 */

public class Demo02C3P0 {
    public static void main(String[] args) throws SQLException {
        //获取DataSource使用默认配置
        DataSource ds = new ComboPooledDataSource();
        //获取DataSource使用config02配置
        DataSource da_config02 = new ComboPooledDataSource("config02");

        //获取连接
        for (int i = 0; i < 11; i++) {
            Connection connection = ds.getConnection();
            System.out.println(i + ": " + connection);
            if (i == 5) {
                connection.close(); //归还连接

            }
        }

        for (int i = 0; i < 8; i++) {
            Connection connection = da_config02.getConnection();
            System.out.println(i + ".config02: " + connection);
        }


    }
}
