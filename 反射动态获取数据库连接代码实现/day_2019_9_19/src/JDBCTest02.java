import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

//Java�ṩ��һ�������õ�������ȡ���ݿ����� DriverManger �������Ĺ�����
public class JDBCTest02 {
    /*
     * DriverManger ���Թ�������������
     *
     * */
    @Test
    public void testDriverManger() throws SQLException, IOException, ClassNotFoundException {
        //1.׼���������ݿ���ַ�����
        //������ȫ����
        String driverClass = null;
        String driverClass2 = "com.mysql.jdbc.Driver";
        //JDBC URL
        String jdbcUrl = null;
        String jdbcUrl2 = "jdbc:mysql://127.0.0.1:3306/test2";
        //���ݿ���û���
        String user = null;
        String user2 = "root";
        //���ݿ������
        String password = null;
        String password2 = "123456";
        //��ȡ���ļ��µ�jdbc.properties�ļ�
        InputStream in = getClass().getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        //2.�������ݿ���������
        Class.forName(driverClass);
        //���صڶ������ݿ���������
        Class.forName(driverClass2);
        //3.ͨ�� DriverManager �� getConnection ������ȡ���ݿ������
        Connection connection = DriverManager.getConnection(jdbcUrl2, user2, password2);

        System.out.println(connection);
    }
}
