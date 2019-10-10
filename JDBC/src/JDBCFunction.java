import org.junit.Test;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * Created by  �߽���   2019/10/10 19:49
 * Description
 * Version 1.0
 */
public class JDBCFunction {
    /*
     * ʹ��JDBC�������ݿ��еĺ�����洢����
     * */
    @Test
    public void testCallableStatment() {
        //ͨ�� Connection ������� CallableStatement �����ʵ������ʹ�� Connection ����� preparedCall()����ʱ
        //��Ҫ����һ�� String ���͵��ַ��������ַ���ָ����ε��ô洢����
        Connection connection=null;
        CallableStatement callableStatement=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="{?=call quantitly }";
            callableStatement=connection.prepareCall(sql);
            //ͨ�� CallableStatement ����� reisterOutParameter() ����ע�� OUT ����
            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.execute();

            int result=callableStatement.getInt(1);
            System.out.println(result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(null,null,connection);
        }
    }
}
