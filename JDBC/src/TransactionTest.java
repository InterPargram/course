import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by  �߽���   2019/9/29 20:24
 * Description
 * Version 1.0
 */
public class TransactionTest {
    /*
     * Tom �� gao ��� 500Ԫ
     * ��������
     * 1.������������ÿ������ʹ�õ��ǵ��������ӣ����޷���֤����ʹ��ͬһ�����ӣ�
     * 2.������岽��
     *      ����ʼ֮ǰ����ʼ����ȡ��Connection��Ĭ���ύ
     *      �������Ĳ���û���⣬���ύ����
     *      �������Ĳ������쳣����ع�����
     * */
    @Test
    public void testTransaction() {

        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            //��ʼ����;ȡ��Ĭ���ύ
            connection.setAutoCommit(false);
            String sql = "update user set many= many - ? where name='Tom';";
            update(connection, sql, 500);

            int i = 10 / 0;
            System.out.println(i);

            String sql1 = "update user set many = many + ? where name='gao';";
            update(connection, sql1);
            //�ύ����
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //�ع�����
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
