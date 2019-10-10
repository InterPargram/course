import org.junit.Test;

import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

/**
 * Created by  高金明   2019/10/10 19:49
 * Description
 * Version 1.0
 */
public class JDBCFunction {
    /*
     * 使用JDBC调用数据库中的函数或存储过程
     * */
    @Test
    public void testCallableStatment() {
        //通过 Connection 对象调用 CallableStatement 对象的实例；在使用 Connection 对象的 preparedCall()方法时
        //需要传入一个 String 类型的字符串，该字符串指明如何调用存储过程
        Connection connection=null;
        CallableStatement callableStatement=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="{?=call quantitly }";
            callableStatement=connection.prepareCall(sql);
            //通过 CallableStatement 对象的 reisterOutParameter() 方法注册 OUT 参数
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
