import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by  高金明   2019/9/29 21:46
 * Description
 * Version 1.0
 */
public class JDBCTest {
    /*
     *  批处理提高语句处理速度
     *  向数据库的 perosn 表插入 10万条记录
     *  测试如何插入用时最短
     * 1.使用 Statement
     * 2.使用PrepareStatement
     * 3.
     * */
    @Test
    public void testBatchProcessing() {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = null;
        try {
            connection = JDBCTools.getConnection();
            sql = "insert into customer(name,email,type) values(?,?,?);";
            statement = connection.prepareStatement(sql);

            long begin = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {
                statement.setString(1, "name_" + i);
                statement.setString(2, "9872313");
                statement.setString(3, "22");
                //积攒sql
                statement.addBatch();
                //当积攒到一定程度，就统一执行一次，清空之前的SQL
                if ((i + 1) % 300 == 0) {
                    //执行SQL
                    statement.executeBatch();
                    //清空
                    statement.clearBatch();
                }
            }
            //如果总数不是整数倍，需要额外执行一次
            if ((100000 % 300) != 0) {
                statement.executeBatch();
                statement.clearBatch();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, connection);
        }
    }

    @Test
    public void testBatchWith() {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = null;
        try {
            connection = JDBCTools.getConnection();
            sql = "insert into customer(name,email,type) values(?,?,?);";
            statement = connection.prepareStatement(sql);

            long begin = System.currentTimeMillis();

            for (int i = 0; i < 100000; i++) {
                statement.setString(1, "name_" + i);
                statement.setString(2, "9872313");
                statement.setString(3, "22");
                statement.executeUpdate();
            }
            long end = System.currentTimeMillis();
            System.out.println(end - begin);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, connection);
        }
    }

}
