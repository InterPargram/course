import java.util.Scanner;

/**
 * Created by  �߽���   2019/9/24 21:41
 * Description ����һ����Ϣ
 * Version 1.0
 */
public class JDBCTest04 {
    /*
     * ��������û���Ϣ�����ݱ� person ��
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
     *�ӿ���̨����ѧ����Ϣ
     * */
    private Person getPersonFromConsole() {
        Scanner scanner=new Scanner(System.in);
        Person person=new Person();
        System.out.println("������ID��");
        person.setId(scanner.nextInt());
        System.out.println("���������֣�");
        person.setName(scanner.next());
        System.out.println(person.toString());
        return person;
    }


    public void addNewPerson(Person person) {
        //׼��һ�� sql���
        String sql = "insert into person(id,name) values("+person.getId()+",'"+person.getName()+"')";
        JDBCTools.updata(sql);
    }
}
