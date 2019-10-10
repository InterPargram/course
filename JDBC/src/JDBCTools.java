
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
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
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = JDBCTools.getConnection();
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(ps, connection);
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
                //���ݿ����ӳ��е�Connection�������closeʱ�����ǽ����ӽ��йر�
                //���ǽ����ݿ����ӻ��ص����ӳ���
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

    //���ݿ����ӳ�Ӧֻ����ʼ��һ��
    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("helloc3p0");

    }

    public static Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {

        return dataSource.getConnection();
    }
}
