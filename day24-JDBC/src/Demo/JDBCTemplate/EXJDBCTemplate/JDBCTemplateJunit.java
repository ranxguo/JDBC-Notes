package Demo.JDBCTemplate.EXJDBCTemplate;

import Demo.DataSourcePool.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 练习：
 *       * 修改Book_code = 3350 书的price为12.50；
 *       * 添加一本新书
 *       * 删除刚才添加的新书
 *       * 查询BOOK_CODE 为 3350的书，将其封装为Map集合
 *       * 查询Type为fiction的书，将其封装为List集合
 *       * 查询所有书的记录，将其封装为Book对象的List集合
 *       * 查询一共多少本不同的书
 */

public class JDBCTemplateJunit {
    //Junit单元测试，可以让方法独立执行

    //获取JDBCTemplate对象
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDatasource());


    /**
     * 修改Book_code = 3350 书的price为12.50；
     */
    @Test
    public void test1() {
        //定义sql
        String sql = "UPDATE book SET price = ? WHERE book_code = ?";
        int count = jdbcTemplate.update(sql, 12.50, "3350");
        if (count != 0) {
            System.out.println("修改成功！" + count + "条记录被修改！");
            System.out.println();
        } else {
            System.out.println("删除失败");
        }
    }

    //添加一本新书
    @Test
    public void test2() {
        //定义sql
        String sql = "INSERT INTO book VALUES(?, ?, ?, ?, ?, ?)";
        int count = jdbcTemplate.update(sql, "8888", "演员的自我修养", "CC", "PSY", 99.99, "Y");
        if (count != 0) {
            System.out.println("添加成功！" + count + "条记录被添加！");
            System.out.println();
        } else {
            System.out.println("删除失败");
        }
    }


    //删除刚才添加的新书
    @Test
    public void test3() {
        String sql = "DELETE FROM book WHERE book_code = ?";
        int count = jdbcTemplate.update(sql, "8888");
        if (count != 0) {
            System.out.println("删除成功！" + count + "条记录被删除！");
            System.out.println();
        } else {
            System.out.println("删除失败");
        }
    }

    //查询BOOK_CODE 为 3350的书，将其封装为Map集合
    //注意：这个方法查询的结果集长度只能是1。
    @Test
    public void test4() {
        String sql = "SELECT * FROM book WHERE book_code = ?";
        Map<String, Object> book = jdbcTemplate.queryForMap(sql, "3350");
        System.out.println(book);
    }

    //查询Type为fiction的书，将其封装为List集合
    @Test
    public void test5() {
        String sql = "SELECT * FROM book WHERE type= ?";
        List<Map<String, Object>> type = jdbcTemplate.queryForList(sql, "FIC");
        for (Map<String, Object> book : type) {
            System.out.println(book);
        }
    }

    //查询所有书的记录，将其封装为Book对象的List集合
    @Test
    public void test6() {
        String sql = "SELECT * FROM book";
        List<Book> books = jdbcTemplate.query(sql, new RowMapper<Book>() {
            @Override
            public Book mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Book(
                        resultSet.getString("book_code"),
                        resultSet.getString("title"),
                        resultSet.getString("publisher_code"),
                        resultSet.getString("type"),
                        resultSet.getDouble("price"),
                        resultSet.getString("paperback")
                );
            }
        });

        for (Book book : books) {
            System.out.println(book);
        }
    }

    //简化test6
    @Test
    public void test6_1() {
        String sql = "SELECT * FROM book";
        List<Book> books = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Book>(Book.class));
        for (Book book : books) {
            System.out.println(book);
        }
    }

    //查询一共多少本不同的书
    @Test
    public void test7() {
        String sql = "SELECT COUNT(book_code) FROM book";
        Integer total = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(total);
    }
}

