package IO;

import org.junit.Test;

import java.io.*;

/**
 * Created by  �߽���   2019/9/20 18:55
 * Description  ��������ʹ��
 * 1��ObjectInputStream �� ObjectOutputStream
 * 2�����ڴ��еĶ���д���������У�Ҳ�ܽ�����ԭ����
 * 3���Զ������л�������Ҫ����һЩ����������Person�ࣩ
 * Version 1.0
 */
public class ObjectInputOutputStreamTest {
    /*
     * ���л�����
     * ��java�еĶ��󱣴浽�����л�����ͨ�����紫���ȥ
     * ʹ�� ObjectOutputStream ��ʵ��
     * */
    @Test
    public void testObjectOutputStream() {
        ObjectOutputStream objectOutputStream = null;
        try {
            //�������л������������ Object.dat �ļ�
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("Object.dat"));
            //������ String �����������־û����浽 Object.dat �ļ���
            objectOutputStream.writeObject(new String("�����й���"));
            objectOutputStream.flush();//ˢ�²���

            //�Զ���һ�����л�����
            objectOutputStream.writeObject(new Person("�߽���", 22));
            objectOutputStream.flush();

            //�Զ���һ�����л�����
            objectOutputStream.writeObject(new Person("���", 22, new Account(5000)));
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();//�ر�
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * �����л�����
     * */
    @Test
    public void testObjectInputStream() {

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream("Object.dat"));
            //�� Object.dat �ļ������ݻ�ԭΪ java ����
            Object object = objectInputStream.readObject();
            String str = (String) object;
            System.out.println(str);

            //��ȡ�Զ�������л�����
            Person person = (Person) objectInputStream.readObject();
            System.out.println(person);

            //��ȡ�Զ�������л�����
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
