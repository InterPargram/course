import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by  �߽���   2019/10/9 21:38
 * Description ����DBUtils���߰���ʹ��
 * Version 1.0
 */
public class TestDBUtils {
    QueryRunner queryRunner = new QueryRunner();

    class MyResultSetHandler implements ResultSetHandler {
        @Override
        public Object handle(ResultSet resultSet) throws SQLException {

            List<Customer> customers = new ArrayList<>();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                Integer sex = resultSet.getInt(3);
                Customer customer = new Customer(id, name, sex);
                customers.add(customer);
            }
            return customers;
        }
    }

    /*
     * ������ֵ��װ
     * */
    @Test
    public void testScalarHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "select name from customer where id=?";
            String sql1 = "select count(id) from customer";
            Object results = queryRunner.query(connection, sql, new ScalarHandler(), 5);
            Object resultss = queryRunner.query(connection, sql1, new ScalarHandler());
            System.out.println(results);
            System.out.println(resultss);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    /*
     *  �������е���¼
     *  MapListHandler �������תΪһ�� Map �� list
     *  Map��ʵ���ǲ�ѯ��һ����¼�������ڵײ�����н����е�Map����ŵ��� List��
     *  ����SQL��ѯ�����������Ǳ�����ֵ���е�ֵ
     *  MapListHandler ���ص��Ƕ�����¼��Ӧ�� Map �ļ���
     * */
    @Test
    public void testMapListHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "select id fo_id,name,sex fo_sex from customer";
            List<Map<String, Object>> results = queryRunner.query(connection, sql, new MapListHandler());
            System.out.println(results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    /*
     *   ����SQL����ж�Ӧ��Map�����еĵ�һ����¼
     *   ����SQL��ѯ�����������Ǳ�����ֵ���е�ֵ
     * */
    @Test
    public void testMapHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "select id fo_id,name,sex fo_sex from customer";
            Map<String, Object> result = queryRunner.query(connection, sql, new MapHandler());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    /*
     * �ѽ����תΪһ�� List,��List��Ϊ null ������Ϊ�ռ��ϣ�size() ��������Ϊ0��
     * �� sql ����ܹ���ѯ����¼��List�д�Ŵ��� BeanListHandler ����� Class �����Ӧ�Ķ���
     * */
    @Test
    public void testBeanListHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "select id fo_id,name,sex fo_sex from customer";
            List<Customer> customers = (List<Customer>) queryRunner.query(connection, sql, new BeanListHandler(Customer.class));
            System.out.println(customers);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    @Test
    public void testBeanHandler() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql = "select id fo_id,name,sex fo_sex from customer where id = ?";
            Customer customer = (Customer) queryRunner.query(connection, sql, new BeanHandler(Customer.class), 1);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    /*
     * ��ѯ
     *
     * */

    @Test
    public void testQuery() {

        Connection connection = null;
        String sql = "select id,name,sex from customer where id in(1,2);";
        try {
            connection = JDBCTools.getConnection();
            Object object = queryRunner.query(connection, sql, new MyResultSetHandler());
            System.out.println(object);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }


    /*
     *   ���� QueryRunner �� update ����
     *   �÷��������� ��ɾ��
     * */
    @Test
    public void testQueryRunnerUpdate() {
        //���� QueryRunner ����
        QueryRunner queryRunner = new QueryRunner();
        //ʹ�� Update ����
        String sql = "Delete from customer where id in (?,?)";

        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            queryRunner.update(connection, sql, 7, 8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }

    }

}
