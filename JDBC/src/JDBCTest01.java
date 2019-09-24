import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCTest01 {
    @Test
    public void testDriver(){
        try {
            Driver driver=new com.mysql.jdbc.Driver();
            String url="jdbc:mysql://127.0.0.1:3306/test";
            Properties info=new Properties();
            info.put("user","root");
            info.put("password","123456");

            Connection connection=driver.connect(url,info);
            System.out.println(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password =null;

        //��ȡ���ļ��µ�jdbc.properties�ļ�
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties=new Properties();
        properties.load(in);
        driverClass =properties.getProperty("driver");
        jdbcUrl=properties.getProperty("jdbcUrl");
        user=properties.getProperty("user");
        password=properties.getProperty("password");

        Properties info=new Properties();
        info.put("user",user);
        info.put("password",password);
        //ͨ�����䴴�� driver ����
        Driver driver= (Driver) Class.forName(driverClass).newInstance();
        //ͨ�� driver �� connect ������ȡ���ݿ������
        Connection connection=driver.connect(jdbcUrl,info);
        return  connection;
    }

    @Test
    public void testGetConnection() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException {
        System.out.println(getConnection());
    }
}
