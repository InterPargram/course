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
 * Created by  高金明   2019/10/9 21:38
 * Description 测试DBUtils工具包到使用
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
     * 将单个值封装
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
     *  返回所有到记录
     *  MapListHandler 将结果集转为一个 Map 的 list
     *  Map其实还是查询了一条记录，但是在底层代码中将所有的Map都存放到了 List中
     *  键：SQL查询的列名（不是别名）值：列的值
     *  MapListHandler 返回的是多条记录对应的 Map 的集合
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
     *   返回SQL语句中对应的Map对象中的第一条记录
     *   键：SQL查询的列名（不是别名）值：列的值
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
     * 把结果集转为一个 List,该List不为 null 但可能为空集合（size() 方法返回为0）
     * 若 sql 语句能够查询到记录，List中存放创建 BeanListHandler 传入的 Class 对象对应的对象
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
     * 查询
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
     *   测试 QueryRunner 类 update 方法
     *   该方法可用于 增删改
     * */
    @Test
    public void testQueryRunnerUpdate() {
        //创建 QueryRunner 方法
        QueryRunner queryRunner = new QueryRunner();
        //使用 Update 方法
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
