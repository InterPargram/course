package Socket;

import org.junit.Test;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by  高金明   2019/9/23 19:52
 * Description UDP 网络编程代码实现
 * Version 1.0
 */
public class TestUDP {
    @Test
    public void send() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
            String str="我是UDP我发送了一个数据包";
            byte[] bytes = str.getBytes();
            InetAddress iea = InetAddress.getLocalHost();
            DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, iea, 9090);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }

    @Test
    public void receiver() {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(9090);
            byte[] data = new byte[100];
            DatagramPacket packet = new DatagramPacket(data, 0, data.length);
            socket.receive(packet);
            System.out.println(new String(packet.getData(),0,packet.getLength()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
