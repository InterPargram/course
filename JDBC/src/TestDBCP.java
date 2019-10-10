import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by  高金明   2019/10/9 19:31
 * Description
 * Version 1.0
 */
public class TestDBCP {


    /*
     *  加载 DBCP 到 properties 配置文件 配置文件中到键需要来自BasicDataSource到属性
     *  调用 BasicDataSourceFactory 到 createDataSource 方法创建数据库连接
     *  从 DataSource 实例中获取数据库连接
     * */
    @Test
    public void testDBCPSource() throws Exception {
        Properties properties = new Properties();
        InputStream inStream = TestDBCP.class.getClassLoader().getResourceAsStream("dbcp.properties");
        properties.load(inStream);
        DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
        System.out.println(dataSource.getConnection());

//
//        new Thread() {
//            public void run() {
//                Connection connection = null;
//                try {
//                    connection = dataSource.getConnection();
//                    System.out.println(connection.getClass());
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        try {
//            Thread.sleep(3000);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /*
     * 使用DBCP数据库连接池
     * 1、加入 jar 包 依赖于 pool.jar
     * 2、创建数据库连接池
     * 3、设置常用属性
     * 4、从数据源中获取数据库连接
     * */
    @Test
    public void testDBCP() throws SQLException {
        BasicDataSource dataSource = null;
        //创建DBCP数据源实例
        dataSource = new BasicDataSource();
        //为数据源实例指定必须到属性
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql:///test");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        //给数据源指定一些可选到属性
        //指定数据库连接池中初始化连接数到个数
        dataSource.setInitialSize(10);

        //指定数据库连接池中最大到连接数：同一时间同时向数据库申请到连接数
        dataSource.setMaxIdle(50);

        //指定数据库连接池最小连接数：在连接池中保存的最少空闲连接数
        dataSource.setMinIdle(5);

        //最长的等待时间
        dataSource.setMaxWaitMillis(1000 * 5);


        //从数据源中获取数据库连接
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getClass());
    }
}
