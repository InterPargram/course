import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by  高金明   2019/9/29 18:45
 * Description
 * Version 1.0
 */
public class MediumBoldTest {
    /*
     *  插入 Blod 类型的数据必须使用 PreparedStatament
     *
     * */
    @Test
    public void testInsertBold() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql="insert into customer(name,type,email,picture) value (?,?,?,?);";
        try {
            connection=JDBCTools.getConnection();
            preparedStatement=connection.prepareStatement(sql);

            preparedStatement.setString(1,"wang");
            preparedStatement.setString(2,"1234");
            preparedStatement.setString(3,"84623161");

            InputStream inputStream= new FileInputStream("1.jpg");
            preparedStatement.setBlob(4,inputStream);

            preparedStatement.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
    }

    /*
     * 读取 Blod 中的图片调用 IO 流输出
     * */
    @Test
    public void readBold() {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            connection=JDBCTools.getConnection();
            String sql="select id,name,type,email,picture from customer where id=8;";
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();

            if (resultSet.next()){
                int id=resultSet.getInt(1);
                String name=resultSet.getString(2);
                String type=resultSet.getString(3);
                String email=resultSet.getString(4);
                Blob picture=resultSet.getBlob(5);
                InputStream inputStream=picture.getBinaryStream();
                OutputStream outputStream=new FileOutputStream("2.jpg");
                byte[] buffer=new byte[1024];
                int len;
                while ((len=inputStream.read(buffer))!=-1){
                    outputStream.write(buffer,0,len);
                }
                inputStream.close();
                outputStream.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCTools.release(resultSet,preparedStatement,connection);
        }
    }
}
