import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by  �߽���   2019/10/9 19:57
 * Description
 * Version 1.0
 */
public class TestC3P0 {

    @Test
    public void testJDBCTools() throws ClassNotFoundException, SQLException, InstantiationException, IOException, IllegalAccessException {
        Connection connection=JDBCTools.getConnection();
        System.out.println(connection);
    }
    /*
     * 1������c3p0-config.xml�ļ�
     * 2���ļ����ݲ���Ҫ�Ķ�
     * 3������ ComboPooledDataSource ʵ��
     * 4���� DataSource ʵ���л�ȡ���ݿ�����
     *
     * */
    @Test
    public void testc3p0WithConfigfile() throws SQLException {
        DataSource dataSource = new ComboPooledDataSource("helloC3p0");
        System.out.println(dataSource.getConnection());
        ComboPooledDataSource comboPooledDataSource = (ComboPooledDataSource) dataSource;
        System.out.println(comboPooledDataSource.getMaxStatements());
    }

    @Test
    public void testc3p0() throws SQLException, PropertyVetoException {
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql:///test");
        cpds.setUser("root");
        cpds.setPassword("123456");
        System.out.println(cpds.getConnection());
    }
}
