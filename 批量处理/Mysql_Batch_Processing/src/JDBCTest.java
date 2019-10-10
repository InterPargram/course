import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

/**
 * Created by  �߽���   2019/9/29 21:46
 * Description
 * Version 1.0
 */
public class JDBCTest {
    /*
     *  �����������䴦���ٶ�
     *  �����ݿ�� perosn ����� 10������¼
     *  ������β�����ʱ���
     * 1.ʹ�� Statement
     * 2.ʹ��PrepareStatement
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
                //����sql
                statement.addBatch();
                //�����ܵ�һ���̶ȣ���ͳһִ��һ�Σ����֮ǰ��SQL
                if ((i + 1) % 300 == 0) {
                    //ִ��SQL
                    statement.executeBatch();
                    //���
                    statement.clearBatch();
                }
            }
            //���������������������Ҫ����ִ��һ��
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
