import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by  ¸ß½ðÃ÷   2019/9/29 18:27
 * Description
 * Version 1.0
 */
public class JDBC_Primary_key {
    @Test
    public void testGetPrimarykey(){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        String sql="insert into customer(name ,type ,email) value (?,?,?);";
        try {
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,"gao");
            preparedStatement.setString(2,"321");
            preparedStatement.setString(3,"7895213");

            preparedStatement.executeUpdate();

            resultSet=preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                System.out.println(resultSet.getObject(1));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
    }
}
