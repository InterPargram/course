import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by  高金明   2019/9/24 19:57
 * Description 使用 Statement 增加一条数据
 * 1. Statement 是用于执行 SQL 语句的对象
 * 2. 通过 Connection 的 createStatement() 方法来获取
 * 3. 通过 executeUpdate(sql) 方法来执行 SQL 语句
 * 4. 传入的 SQL 可以是 insert , Update , delete 但不能是 select
 * 5. Statement 和 Connection 都是应用程序和数据库服务器的连接资源，使用之后要关闭
 * * Version 1.0
 */
public class JDBCTest03 {

    /*
     * ResultSet:封装了 JDBC 查询的结果
     * 1.调用 Statement 对象的 executeQuery(sql)可以得到结果集
     * 2.ResultSet 返回的实际上是一张数据表，有一个指针指向数据表的第一行的前面
     * 可以调用 next() 方法检测下一行是否有效，若有效该方法返回true,且指针下移，相当于 Iterator 对象的 hasNext() 和 next() 方法的结合体
     * 3.当指针对应的一行，可以调用getInt(index); getString(columName);获取每一列的值
     * 4.关闭资源
     * */
    @Test
    public void testResultSet() {
        //获取 person 数据表的记录，并打印
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.获取 Connection
            connection = JDBCTools.getConnection();
            //2.获取 Statement
            statement = connection.createStatement();
            //3.准备 SQL
            String sql = "select * from person;";
            //4.执行查询，得到 ResultSet
            resultSet = statement.executeQuery(sql);
            //5.处理 ResultSet
            while (resultSet.next()) {
                int id = resultSet.getInt(1);
                String name = resultSet.getString("name");
                System.out.println(id);
                System.out.println(name);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //6.关闭数据库资源
            JDBCTools.release(resultSet, statement, connection);
        }
    }

    /*
     * 通用的更新方法 包括 insert updata delete
     * version 1.0
     * */

//    String sql = "insert into person(id,name) values(2,'gao')";
//    String sql = "delete from person where id =1";
//    String sql = "updata person set name='Red' where id=1";

    public void updata(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, connection);
        }
    }
}
