package IO;

import java.io.Serializable;

/**
 * Created by  �߽���   2019/9/20 19:15
 * Description  �Զ���һ�����л��Ķ���
 * IO.Person ����Ҫ�������µ�Ҫ����ܽ������л�
 * 1��ʵ�� Serializable �ӿ�
 * 2����ǰ����Ҫ�ṩһ��ȫ�ֳ��� ����ûҪ��
 * 3�����뱣֤��������������Զ��ǿ����л��� (Ĭ�� �������� ���Ͷ��ǿ����л���) ���ӣ�IO.Account
 * ע�⣺OutputStream��InputStream�������л��� static �� transient ���εĳ�Ա
 * Version 1.0
 */
public class Person implements Serializable {

    private String name;
    private int age;
    private Account account;
    //���л��汾�� ������������д
    public static final long serialVersionUID = 465213254L;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "IO.Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", account=" + account +
                '}';
    }


    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person(String name, int age, Account account) {
        this.name = name;
        this.age = age;
        this.account = account;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}

class Account implements Serializable {

    public static final long serialVersionUID = 5163321L;
    private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "IO.Account{" +
                "balance=" + balance +
                '}';
    }
}
