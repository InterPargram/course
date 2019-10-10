import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by  高金明   2019/9/29 20:24
 * Description
 * Version 1.0
 */
public class TransactionTest {
    /*
     * Tom 给 gao 汇款 500元
     * 关于事务
     * 1.如果多个操作，每个操作使用的是单独的连接，则无法保证事务（使用同一个连接）
     * 2.事务具体步骤
     *      事务开始之前，开始事务：取消Connection的默认提交
     *      如果事务的操作没问题，则提交事务；
     *      如果事务的操作有异常，则回滚事务
     * */
    @Test
    public void testTransaction() {

        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            //开始事务;取消默认提交
            connection.setAutoCommit(false);
            String sql = "update user set many= many - ? where name='Tom';";
            update(connection, sql, 500);

            int i = 10 / 0;
            System.out.println(i);

            String sql1 = "update user set many = many + ? where name='gao';";
            update(connection, sql1);
            //提交事务
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚事务
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            JDBCTools.release(null, null, null);
        }

    }

    public void update(Connection connection, String sql, Object... obj) {

        PreparedStatement statement = null;
        try {

            statement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                statement.setObject(i + 1, obj[i]);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, null);
        }
    }
}
