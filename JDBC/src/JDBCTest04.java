import java.util.Scanner;

/**
 * Created by  高金明   2019/9/24 21:41
 * Description 插入一条信息
 * Version 1.0
 */
public class JDBCTest04 {
    /*
     * 测试添加用户信息到数据表 person 中
     * */
    public static void main(String[] args) {
        JDBCTest04 jdbcTest04=new JDBCTest04();
        jdbcTest04.testaddNewPerson();
    }

    public void testaddNewPerson() {
        Person person = getPersonFromConsole();
        addNewPerson(person);
    }

    /*
     *从控制台输入学生信息
     * */
    private Person getPersonFromConsole() {
        Scanner scanner=new Scanner(System.in);
        Person person=new Person();
        System.out.println("请输入ID：");
        person.setId(scanner.nextInt());
        System.out.println("请输入名字：");
        person.setName(scanner.next());
        System.out.println(person.toString());
        return person;
    }


    public void addNewPerson(Person person) {
        //准备一条 sql语句
        String sql = "insert into person(id,name) values("+person.getId()+",'"+person.getName()+"')";
        JDBCTools.updata(sql);
    }
}
