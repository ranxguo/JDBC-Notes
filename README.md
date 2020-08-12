# JDBC-Notes

## JDBC概念
  * Java Database Connectivity, Java 数据库连接，Java语言操作数据库。
  * 本质：其实是官方（SUN公司）定义了一套操作所有关系型数据库的规则，即接口。各个数据库厂商去实现这套接口，提供数据可驱动jar包。
  我们可以使用这套接口（JDBC）编程，真正执行的代码是驱动jar包中的实现类。
## 快速入门：
  * 步骤：
    1. 导入驱动jar包   （mysql-connector-java-8.0.21）
            * 复制 mysql-connector-java-8.0.21.jar到项目到libs目录下
            * 右键libs目录，选择Add As Library
    2. 注册驱动
    3. 获取数据库连接对象 Connection
    4. 定义SQL
    5. 获取执行SQL语句的对象 Statement
    6. 执行SQL，接收返回结果
    7. 处理结果
    8. 释放资源
  * 代码实现：
        public class Demo01JDBC {
            public static void main(String[] args) throws Exception {
              //1. 导入驱动jar包
              //2. 注册驱动
              Class.forName("com.mysql.cj.jdbc.Driver");
              //3. 获取数据库的连接对象                
              Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db1?serverTimezone=UTC", "root", "password");
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
  * 各个对象详解：
      1. DriverManager: 驱动管理对象
        * 功能：
          1. 注册驱动：告诉程序该使用
              static void registerDriver(Driver driver); 注册与给定的驱动程序 DriverManager.
              写代码使用：Class.forName("com.mysql.cj.jdbc.Driver");
              通过查看源码发现：在com.mysql.cj.jdbc.Driver类中存在静态代码块。
                  static {
                      try {
                        DriverManager.registerDriver(new Driver());
                        } catch (SQLException var1) {
                          throw new RuntimeException("Can't register driver!");
                        }
                  }
              * 注意：MySQL5之后的驱动jar包可以省略注册驱动的步骤。

          2. 获取数据库连接：
              * 方法： static Connection	getConnection​(String url, String user, String password);
              * 参数：
                * url：指定连接的路径
                  * 语法：jdbc:mysql://ip地址(域名)：端口号/数据库名称
                  * 例子："jdbc:mysql://127.0.0.1:3306/db1"
                  * 细节：如果连接的是本机的mysql服务器，并且mysql服务默认端口是3306，则URL可以简写为"jdbc:mysql:///db1"
                * user：用户名
                * password：密码

      2. Connection: 数据库连接对象
        * 功能：
          1. 获取执行sql的对象：
              * Statement createStatement();
              * PreparedStatement PreparedStatement(String sql);
          2. 管理事务：
              * 开启事务：void setAutoCommit(boolean autoCommit); //调用该方法设置参数为false，即开启事务。
              * 提交事务：void commit();
              * 回滚事务：void rollback();
      3. Statement: 执行SQL的对象
        * 执行sql
          1. boolean execute（String sql); //可以执行任意的sql 了解
          2. int exrcuteUpdate(String sql); //执行DML（insert，undate，delete）语句，DDL（create，alter，drop）语句。
            * 返回值：影响的行数，可以通过影响的行数可以判断DML语句是否执行成功。如果返回值>0则执行成功。
          3. ResultSet executeQuery(String sql); //执行DQL（select）语句。
        * 练习：
          1. student表添加一条记录。
          2. student表修改记录。
          3. student表删除一条记录。
      4. ResultSet: 结果集合对象,封装查询结果。
        * next(); //游标向下移动一行
        * getXxx(参数); //获取数据Xxx代表数据类型 例如： int getInt(); String getString(); ...
          * 参数：
            1. int: 代表列的编号，从1开始 例如：getString(1);
            2. String: 代表列表名称。 例如：getString("name");
        * 步骤：
          1. 游标向下移动一行
          2. 判断是否有数据
          3. 获取数据
        * 练习：
          * 定义一份方法，查询book表的数据将其封装非对象，然后装在集合，返回。
            1. 定义Book类
            2. 定义方法 public List<Book> findAll(){}
            3. 实现方法 SELECT * FROM book；
      5. PreparedStatement: 执行SQL的对象
        * SQL注入问题：在拼接sql时有一些sql的特殊关键字参与字符串的拼接。会造成安全问题。
          1. 输入用户随便，输入密码：a' or 'a' = 'a'
              * SELECT * FROM user WHERE username = 'Ryan' AND password = 'a' or 'a' = 'a';
        * 解决SQL注入问题：使用PreparedStatement对象来解决
        * 预编译的SQL：参数使用？作为占位符。
        * 步骤：
          1. 导入驱动jar包   （mysql-connector-java-8.0.21）
                  * 复制 mysql-connector-java-8.0.21.jar到项目到libs目录下
                  * 右键libs目录，选择Add As Library
          2. 注册驱动
          3. 获取数据库连接对象 Connection
          4. 定义SQL
              * 注意：sql的参数使用？作为占位符。 例如： SELECT * FROM user WHERE username = ？ AND password = ?;
          5. 获取执行SQL语句的对象 PreparedStatement Connection.preparedStatement(String sql);
          6. 给？赋值：
              * 方法：setXxx(参数1，参数2);
                  * 参数1: ？的位置的编号从1开始
                  * 参数2: ？值
          7. 执行SQL，接收返回结果
          8. 处理结果
          9. 释放资源
        * 注意：后期都会使用PreparedStatement来完成增删改查的所有操作。


## 抽取JDBC工具类：JDBCUtil
  * 目的：简化代码
  * 分析：
    1. 注册驱动抽取
    2. 抽取一个方法获取连接
      * 需求：不想传参数（麻烦），还要保证工具类的通用性
      * 解决：配置文件
          jdbc.properties
            url=jdbc:mysql:///HENRY?serverTimezone=UTC
            user=root
            password=root
            driver=com.mysql.cj.jdbc.Driver
    3. 抽取一个方法释放资源
  * 练习：
    1. 通过键盘录入用户名密码
    2. 判断用户登陆是否成功
        * SELECT * FROM user WHERE username = "" and password = "";
        * 如果这个sql有查询结果，则登陆成功。

## JDBC控制事务：
  1. 事务：一个包含多个步骤的业务操作。如果这个业务操作被事务管理，则这多个步骤要么同时成功，要么同时失败。
  2. 操作：
    * 开启事务
    * 提交事务
    * 回滚事务
  3. 使用的是Connection对象来管理事务
    * 开启事务：void setAutoCommit(boolean autoCommit); //调用该方法设置参数为false，即开启事务。
      * 在执行sql之前开启事务
    * 提交事务：void commit();
      * 当所有sql都执行完提交事务
    * 回滚事务：void rollback();
      * 在catch中回滚事务

## 数据库连接池
  * 概念：其实就是一个容器（集合），存放数据库连接的容器
      * 当系统初始化好后，容器被创建，容器会申请一些连接对象，当用户来访问数据时，从数据库中获取连接对象，用户访问完之后，会将连接对象归还给容器。
      * 好处：
        1. 节约资源
        2. 用户访问效率高
      * 实现：
        1. 标准接口：DataSource javax.sql包下的
          * 方法：
            * 获取连接：Connection	getConnection();
            * 归还连接：Connection.close(); 如果连接对象Connection是从连接池中获取的，那么调用Connection.close()方法，则不会再关闭连接来。而是归还连接
        2. 一般我们不去实现它，由数据库厂商来实现
          * C3P0：数据库连接技术
          * Druid：数据库连接池实现技术，由阿里巴巴提供
      * C3P0：数据库连接池技术
        * 步骤：
          1. 导入jar包 (两个包) c3p0-0.9.5.5.jar mchange-commons-java-0.2.19.jar
            * 不要忘记导入数据库驱动jar包 mysql-connector-java-8.0.21.jar
          2. 定义配置文件：
            * 名称：c3p0.properties 或者 c3p0-config.xml  （自动加载）
                    * <c3p0-config>
                          <!-- 使用默认的配置读取连接池对象 -->
                          <default-config>
                              <!-- 连接参数 -->
                              <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
                              <property name="jdbcUrl">jdbc:mysql://localhost:3306/HENRY?serverTimezone=UTC</property>
                              <property name="user">root</property>
                              <property name="password">root</property>

                              <!-- 连接池参数 -->
                              <!-- 初始化申请的连接数量 -->
                              <property name="initialPoolSize">5</property>
                              <!-- 最大的连接数量 -->
                              <property name="maxPoolSize">10</property>
                              <!-- 超时时间 -->
                              <property name="checkoutTimeout">3000</property>
                          </default-config>

                          <named-config name="otherc3p0">
                              <!-- 连接池参数 -->
                              <property name="driverClass">com.mysql.cj.jdbc.Driver</property>
                              <property name="jdbcUrl">jdbc:mysql://localhost:3306/HENRY?serverTimezone=UTC</property>
                              <property name="user">root</property>
                              <property name="password">root</property>
                              <!-- 连接池参数 -->
                              <!-- 初始化申请的连接数量 -->
                              <property name="initialPoolSize">5</property>
                              <!-- 最大的连接数量 -->
                              <property name="maxPoolSize">8</property>
                              <!-- 超时时间 -->
                              <property name="checkoutTimeout">1000</property>
                          </named-config>
                      </c3p0-config>
            * 路径：直接将文件放在src目录下即可
          3. 创建核心对象 数据库连接池对象 ComboPooledDataSource
          4. 获取连接：Connection getConnection();


    * Druid: 数据库连接池技术
      * 步骤：
        1. 导入jar包: druid-1.1.10.jar
          * 不要忘记导入数据库驱动jar包 mysql-connector-java-8.0.21.jar
        2. 定义配置文件：
          * 特点：
            1. 是properties形式的
            2. 可以叫任意名，可以放在任意目录下  （手动加载）
            * druid.properties
                    * driverClassName=com.mysql.cj.jdbc.Driver
                      url=jdbc:mysql:///db2?serverTimezone=UTC
                      username=root
                      password=root
                      initialSize=5
                      maxActive=10
                      maxWait=3000
            3. 加载配置文件 Properties
            4. 获取数据库连接池对象：通过工厂类来获取， DruidDataSourceFactory
            5. 获取连接： Connection	getConnection();

      * 定义工具类：
        1. 定义一个类 JDBCUtil
        2. 提供静态代码块加载配置文件，初始化连接池对象
        3. 提供方法
          * 获取连接方法：通过数据库连接池获取连接
          * 释放资源
          * 获取连接池的方法

## Spring JDBC Template
  * Spring 框架对JDBC对简单封装。提供了一个JDBC Template对象简化JDBC对开发
  * 步骤：
    1. 导入jar包
    2. 创建JdbcTemplate对象。依赖于数据源DateSource
            JdbcTemplate template = new JdbcTemplate(ds);
    3. 调用JdbcTemplate的方法来完成CRUD的操作
            update();           //执行DML语句。增，删，改语句
            queryForMap();      //查询结果将结果集封装为map集合
            queryForList();     //查询结果将结果集封装为list集合
            query();            //查询结果将结果集封装为javaBean对象
              * query的参数：RowMapper
                * 一般我们使用BeanPropertyRowMapper实现类。可以完成数据到JavaBean到自动封装
                * new BeanPropertyRowMapper<类型>(类型.class)
            queryForObject();   //查询结果将结果集封装为对象
              * 一般用于聚合函数的查询
    4. 练习：
      * 修改Book_code = 3350 书的price为12.50；
      * 添加一本新书
      * 删除刚才添加的新书
      * 查询BOOK_CODE 为 3350的书，将其封装为Map集合。
        * 注意：这个方法查询的结果集长度只能是1。（将列名作为key，将值作为value。将这条记录封装为一个Map集合）
      * 查询Type为fiction的书，将其封装为List集合
        * 注意：这个方法是将每一条记录封装为以Map集合，再将Map集合封装进List集合中
      * 查询所有书的记录，将其封装为Book对象的List集合
      * 查询一共多少本不同的书
