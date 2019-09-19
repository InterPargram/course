import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Java提供了一个更好用的类来获取数据库连接 DriverManger 是驱动的管理类
public class JDBCTest02 {
    /*
     * DriverManger 可以管理多个驱动程序
     *
     * */
    @Test
    public void testDriverManger() throws SQLException, IOException, ClassNotFoundException {
        //1.准备连接数据库的字符串。
        //驱动的全类名
        String driverClass = null;
        String driverClass2 = "com.mysql.jdbc.Driver";
        //JDBC URL
        String jdbcUrl = null;
        String jdbcUrl2 = "jdbc:mysql://127.0.0.1:3306/test2";
        //数据库的用户名
        String user = null;
        String user2 = "root";
        //数据库的密码
        String password = null;
        String password2 = "123456";
        //读取类文件下的jdbc.properties文件
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        //2.加载数据库驱动程序
        Class.forName(driverClass);
        //加载第二个数据库驱动程序
        Class.forName(driverClass2);
        //3.通过 DriverManager 的 getConnection 方法获取数据库的连接
        Connection connection = DriverManager.getConnection(jdbcUrl2, user2, password2);

        System.out.println(connection);
    }
}
