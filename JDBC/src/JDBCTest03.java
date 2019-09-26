import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by  �߽���   2019/9/24 19:57
 * Description ʹ�� Statement ����һ������
 * 1. Statement ������ִ�� SQL ���Ķ���
 * 2. ͨ�� Connection �� createStatement() ��������ȡ
 * 3. ͨ�� executeUpdate(sql) ������ִ�� SQL ���
 * 4. ����� SQL ������ insert , Update , delete �������� select
 * 5. Statement �� Connection ����Ӧ�ó�������ݿ��������������Դ��ʹ��֮��Ҫ�ر�
 * * Version 1.0
 */
public class JDBCTest03 {

    /*
     * ResultSet:��װ�� JDBC ��ѯ�Ľ��
     * 1.���� Statement ����� executeQuery(sql)���Եõ������
     * 2.ResultSet ���ص�ʵ������һ�����ݱ���һ��ָ��ָ�����ݱ�ĵ�һ�е�ǰ��
     * ���Ե��� next() ���������һ���Ƿ���Ч������Ч�÷�������true,��ָ�����ƣ��൱�� Iterator ����� hasNext() �� next() �����Ľ����
     * 3.��ָ���Ӧ��һ�У����Ե���getInt(index); getString(columName);��ȡÿһ�е�ֵ
     * 4.�ر���Դ
     * */
    @Test
    public void testResultSet() {
        //��ȡ person ���ݱ�ļ�¼������ӡ
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            //1.��ȡ Connection
            connection = JDBCTools.getConnection();
            //2.��ȡ Statement
            statement = connection.createStatement();
            //3.׼�� SQL
            String sql = "select * from person;";
            //4.ִ�в�ѯ���õ� ResultSet
            resultSet = statement.executeQuery(sql);
            //5.���� ResultSet
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
            //6.�ر����ݿ���Դ
            JDBCTools.release(resultSet, statement, connection);
        }
    }

    /*
     * ͨ�õĸ��·��� ���� insert updata delete
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
