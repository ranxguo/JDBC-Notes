package Demo.JDBCTemplate;

import Demo.DataSourcePool.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * JDBC Template入门
 */

public class Demo01JDBCTemplate {
    //导jar包
    public static void main(String[] args) {
        //创建JDBCTemplate对象
        JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDatasource());
        //调用方法
        String sql = "UPDATE account SET balance = ? WHERE id = ?";
        int count = jdbcTemplate.update(sql, 800, 1);
        System.out.println(count);
    }
}
