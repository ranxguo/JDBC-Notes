package Demo.jdbc.EXJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 定义一份方法，查询book表的数据将其封装非对象，然后装在集合，返回。
 */

public class EXSelectAllFromTable {
    private static final String USER = "root";
    private static String PASSWORD = "root";

    /**
     * 查看所有book对象
     * @param resultSet
     * @return
     * @throws SQLException
     */
    public static List<Book> findAll(ResultSet resultSet) throws SQLException {
        List<Book> book = new ArrayList<>();
        //Book(String book_code, String title, String publisher, String type, double price, char paperback)
        while (resultSet.next()) {
            book.add(new Book(
                    resultSet.getString("book_code"),
                    resultSet.getString("title"),
                    resultSet.getString("publisher_code"),
                    resultSet.getString("type"),
                    resultSet.getDouble("price"),
                    resultSet.getString("paperback")
                    ));
        }
        return book;
    }

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //注册驱动 可省略
            Class.forName("com.mysql.cj.jdbc.Driver");
            //创建连接
            connection = DriverManager.getConnection("jdbc.properties:mysql://127.0.0.1:3306/HENRY?serverTimezone=UTC", USER, PASSWORD);
            //获取执行sqlStatement
            statement = connection.createStatement();
            //sql 语句
            String sql = "SELECT * FROM book";
            //执行sql
            resultSet = statement.executeQuery(sql);
            //处理结果
            List<Book> all = findAll(resultSet);
            for (Book book : all) {
                System.out.println(book);
            }
            System.out.println("共计：" + all.size() + "条");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        }
    }
}
