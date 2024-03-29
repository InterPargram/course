
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
 * Created by  高金明   2019/9/24 20:55
 * Description JDBC的工具类 封装了
 * 获取连接的方法
 * 通过读取配置文件中的数据库信息连接数据库
 * 关闭连接的方法
 * 判断连接是否为空，然后进行关闭资源
 * <p>
 * Version 1.0
 */
public class JDBCTools {


    /*
     * 加强版updata 使用PreparedStatement
     * 执行 sql ; obj 是占位符的可变参数
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
     * 用来执行 sql 语句的方法 insert delete updata
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
                //数据库连接池中的Connection对象进行close时并不是将连接进行关闭
                //而是将数据库连接换回到连接池中
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

    //数据库连接池应只被初始化一次
    private static DataSource dataSource = null;

    static {
        dataSource = new ComboPooledDataSource("helloc3p0");

    }

    public static Connection getConnection() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException, IOException {

        return dataSource.getConnection();
    }
}
