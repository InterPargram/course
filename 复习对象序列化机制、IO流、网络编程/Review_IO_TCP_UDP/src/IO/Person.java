package IO;

import java.io.Serializable;

/**
 * Created by  高金明   2019/9/20 19:15
 * Description  自定义一个序列化的对象
 * IO.Person 类需要满足如下的要求才能进行序列化
 * 1、实现 Serializable 接口
 * 2、当前类需要提供一个全局常量 常量没要求
 * 3、必须保证所属类的所有属性都是可序列化的 (默认 基本数据 类型都是可序列化的) 例子：IO.Account
 * 注意：OutputStream和InputStream不能序列化用 static 和 transient 修饰的成员
 * Version 1.0
 */
public class Person implements Serializable {

    private String name;
    private int age;
    private Account account;
    //序列化版本号 后面的数字随便写
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
