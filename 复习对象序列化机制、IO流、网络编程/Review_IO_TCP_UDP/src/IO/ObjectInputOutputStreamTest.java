package IO;

import org.junit.Test;

import java.io.*;

/**
 * Created by  高金明   2019/9/20 18:55
 * Description  对象流的使用
 * 1、ObjectInputStream 和 ObjectOutputStream
 * 2、将内存中的对象写到数据流中，也能将对象还原回来
 * 3、自定义序列化对象需要满足一些条件。见（Person类）
 * Version 1.0
 */
public class ObjectInputOutputStreamTest {
    /*
     * 序列化过程
     * 将java中的对象保存到磁盘中或者是通过网络传输出去
     * 使用 ObjectOutputStream 来实现
     * */
    @Test
    public void testObjectOutputStream() {
        ObjectOutputStream objectOutputStream = null;
        try {
            //创建序列化输出流并创建 Object.dat 文件
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("Object.dat"));
            //创建的 String 对象将这个对象持久化保存到 Object.dat 文件中
            objectOutputStream.writeObject(new String("我是中国人"));
            objectOutputStream.flush();//刷新操作

            //自定义一个序列化对象
            objectOutputStream.writeObject(new Person("高金明", 22));
            objectOutputStream.flush();

            //自定义一个序列化对象
            objectOutputStream.writeObject(new Person("杨虎城", 22, new Account(5000)));
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();//关闭
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * 反序列化过程
     * */
    @Test
    public void testObjectInputStream() {

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("Object.dat"));
            //将 Object.dat 文件的内容还原为 java 对象
            Object object = objectInputStream.readObject();
            String str = (String) object;
            System.out.println(str);

            //读取自定义的序列化对象
            Person person = (Person) objectInputStream.readObject();
            System.out.println(person);

            //读取自定义的序列化对象
            Person person1 = (Person) objectInputStream.readObject();
            System.out.println(person1);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
