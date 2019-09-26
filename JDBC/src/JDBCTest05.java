import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by  �߽���   2019/9/26 8:00
 * Description SQLע�� �Լ��������
 * Version 1.0
 */
public class JDBCTest05 {

    /*
     * SQL ע�� ��������¼
     * ��������Ϊ ִ�� SQL ���Ķ����� statement SQL�����ƴд��
     * ��� SQL ע��ķ���Ϊ ��ʹ�� statement������ PrepareStatement��ִ��sql���
     * */
    @Test
    public void testSQLInjection() {
        String name = " a' OR password =  ";
        String password = " OR '1' = '1";
        String sql = "select * from student where name ='" + name + "' and password='" + password + "'";
        System.out.println(sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("�û���¼�ɹ�");
            } else {
                System.out.println("��¼ʧ�ܣ��û��������벻ƥ��");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, statement, connection);
        }
    }

    @Test
    public void testSelect() {
        String name = "tom";
        String password = "123456";
        String sql = "select * from student where name ='" + name + "' and password='" + password + "'";
        System.out.println(sql);
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                System.out.println("�û���¼�ɹ�");
            } else {
                System.out.println("��¼ʧ�ܣ��û��������벻ƥ��");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, statement, connection);
        }
    }

    /*
     *ʹ�� PrepareStatement ������Ч�Ľ�� SQLע�� ����
     * */
    @Test
    public void testSQLInjection2() {
        String name = " a' OR password =  ";
        String password = " OR '1' = '1";
        String sql = "select * from student where name = ? and password = ?";
        System.out.println(sql);
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,password);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("�û���¼�ɹ�");
            } else {
                System.out.println("��¼ʧ�ܣ��û��������벻ƥ��");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, statement, connection);
        }
    }


    /*
     * �����ݱ������һ������
     * */
    public void addNewPerson(Person person) {
        String sql = "insert into person(id,name) values(?,?);";
        JDBCTools.updata(sql, person.getId(), person.getName());
    }

    public static void main(String[] args) {
        JDBCTest05 jdbc = new JDBCTest05();
        Person person = new Person();
        Scanner scanner = new Scanner(System.in);
        System.out.println("������ID��");
        person.setId(scanner.nextInt());
        System.out.println("������name��");
        person.setName(scanner.next());
        jdbc.addNewPerson(person);

    }
}
