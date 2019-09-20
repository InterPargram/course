package Socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by  �߽���   2019/9/20 20:04
 * Description ����ͨ�ŵ�Ҫ��
 * 1��IP �� �˿ں�
 * 2������ͨ��Э�� TCP/IP �ο�ģ�ͣ�Ӧ�ò㡢����㡢����㡢������·�㣩
 * 3���� java �� InetAddress ����ʾ IP ��ַ
 * 4��������  www.baidu.com  www.github.com
 * 5��ʵ���� InetAddress ����ķ����� ʵ�� getByName() ��  getLocalHost()
 * 6���������÷��� getHostName() getHostAddress()
 * 7���˿ںź�IP��ַ��ϳ�һ���׽��� Socket
 * Version 1.0
 */
public class InetAddressTest {

    public static void main(String[] args) {
        try {
            InetAddress inet1 = InetAddress.getByName("192.168.123.141");
            System.out.println(inet1);

            //ͨ��������ȡ IP ��ַ
            InetAddress inet2 = InetAddress.getByName("www.baidu.com");
            System.out.println(inet2);

            //��ȡ���ص�ַ
            InetAddress inet3 = InetAddress.getLocalHost();
            System.out.println(inet3);

            //��ȡ����������
            System.out.println(inet3.getHostName());
            //��ȡ����IP��ַ
            System.out.println(inet3.getHostAddress());


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
