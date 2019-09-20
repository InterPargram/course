package Socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by  高金明   2019/9/20 20:04
 * Description 网络通信的要点
 * 1、IP 和 端口号
 * 2、网络通信协议 TCP/IP 参考模型（应用层、传输层、网络层、数据链路层）
 * 3、在 java 中 InetAddress 来表示 IP 地址
 * 4、域名：  www.baidu.com  www.github.com
 * 5、实例化 InetAddress 对象的方法有 实现 getByName() 和  getLocalHost()
 * 6、两个常用方法 getHostName() getHostAddress()
 * 7、端口号和IP地址组合成一个套接字 Socket
 * Version 1.0
 */
public class InetAddressTest {

    public static void main(String[] args) {
        try {
            InetAddress inet1 = InetAddress.getByName("192.168.123.141");
            System.out.println(inet1);

            //通过域名获取 IP 地址
            InetAddress inet2 = InetAddress.getByName("www.baidu.com");
            System.out.println(inet2);

            //获取本地地址
            InetAddress inet3 = InetAddress.getLocalHost();
            System.out.println(inet3);

            //获取本地主机名
            System.out.println(inet3.getHostName());
            //获取本地IP地址
            System.out.println(inet3.getHostAddress());


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }
}
