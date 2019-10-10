import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by  高金明   2019/9/26 8:00
 * Description SQL注入 以及解决方法
 * Version 1.0
 */
public class JDBCTest05 {

    /*
     * SQL 注入 恶意代码登录
     * 限制条件为 执行 SQL 语句的对象是 statement SQL语句是拼写的
     * 规避 SQL 注入的方法为 ：使用 statement的子类 PrepareStatement来执行sql语句
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
                System.out.println("用户登录成功");
            } else {
                System.out.println("登录失败，用户名或密码不匹配");
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
                System.out.println("用户登录成功");
            } else {
                System.out.println("登录失败，用户名或密码不匹配");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, statement, connection);
        }
    }

    /*
     *使用 PrepareStatement 可以有效的解决 SQL注入 问题
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
                System.out.println("用户登录成功");
            } else {
                System.out.println("登录失败，用户名或密码不匹配");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, statement, connection);
        }
    }


    /*
     * 向数据表中添加一条数据
     * */
    public void addNewPerson(Person person) {
        String sql = "insert into person(id,name) values(?,?);";
        JDBCTools.updata(sql, person.getId(), person.getName());
    }

    public static void main(String[] args) {
        JDBCTest05 jdbc = new JDBCTest05();
        Person person = new Person();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入ID：");
        person.setId(scanner.nextInt());
        System.out.println("请输入name：");
        person.setName(scanner.next());
        jdbc.addNewPerson(person);

    }
}
