import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Created by  �߽���   2019/9/24 20:55
 * Description JDBC�Ĺ����� ��װ��
 * ��ȡ���ӵķ���
 * ͨ����ȡ�����ļ��е����ݿ���Ϣ�������ݿ�
 * �ر����ӵķ���
 * �ж������Ƿ�Ϊ�գ�Ȼ����йر���Դ
 * <p>
 * Version 1.0
 */
public class JDBCTools {


    /*
     * ��ǿ��updata ʹ��PreparedStatement
     * ִ�� sql ; obj ��ռλ���Ŀɱ����
     * */
    public static void updata(String sql, Object... obj) {
        Connection connection=null;
        PreparedStatement ps=null;
        try{
            connection=JDBCTools.getConnection();
            ps=connection.prepareStatement(sql);

            for (int i=0;i<obj.length;i++){
                ps.setObject(i+1,obj[i]);
            }

            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(ps,connection);
        }
    }

    /*
     * ����ִ�� sql ���ķ��� insert delete updata
     * */
    public static void updata(String sql) {

        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, connection);
        }

    }

    public static void release(ResultSet rs, Statement statement, Connection connection) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void release(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {
        String driverClass = null;
        String jdbcUrl = null;
        String user = null;
        String password = null;

        //��ȡ���ļ��µ�jdbc.properties�ļ�
        InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties properties = new Properties();
        properties.load(in);
        driverClass = properties.getProperty("driver");
        jdbcUrl = properties.getProperty("jdbcUrl");
        user = properties.getProperty("user");
        password = properties.getProperty("password");

        Properties info = new Properties();
        info.put("user", user);
        info.put("password", password);
        //ͨ�����䴴�� driver ����
        Driver driver = (Driver) Class.forName(driverClass).newInstance();
        //ͨ�� driver �� connect ������ȡ���ݿ������
        Connection connection = driver.connect(jdbcUrl, info);
        return connection;
    }
}
