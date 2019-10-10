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
 * Created by  �߽���   2019/10/9 19:31
 * Description
 * Version 1.0
 */
public class TestDBCP {


    /*
     *  ���� DBCP �� properties �����ļ� �����ļ��е�����Ҫ����BasicDataSource������
     *  ���� BasicDataSourceFactory �� createDataSource �����������ݿ�����
     *  �� DataSource ʵ���л�ȡ���ݿ�����
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
     * ʹ��DBCP���ݿ����ӳ�
     * 1������ jar �� ������ pool.jar
     * 2���������ݿ����ӳ�
     * 3�����ó�������
     * 4��������Դ�л�ȡ���ݿ�����
     * */
    @Test
    public void testDBCP() throws SQLException {
        BasicDataSource dataSource = null;
        //����DBCP����Դʵ��
        dataSource = new BasicDataSource();
        //Ϊ����Դʵ��ָ�����뵽����
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        dataSource.setUrl("jdbc:mysql:///test");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");

        //������Դָ��һЩ��ѡ������
        //ָ�����ݿ����ӳ��г�ʼ��������������
        dataSource.setInitialSize(10);

        //ָ�����ݿ����ӳ��������������ͬһʱ��ͬʱ�����ݿ����뵽������
        dataSource.setMaxIdle(50);

        //ָ�����ݿ����ӳ���С�������������ӳ��б�������ٿ���������
        dataSource.setMinIdle(5);

        //��ĵȴ�ʱ��
        dataSource.setMaxWaitMillis(1000 * 5);


        //������Դ�л�ȡ���ݿ�����
        Connection connection = dataSource.getConnection();
        System.out.println(connection.getClass());
    }
}
