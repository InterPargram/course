package Socket;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by  高金明   2019/9/20 21:34
 * Description 实现 TCP 的网络编程
 * 例子1：客户端给服务端发送消息，服务端将信息显示在控制台上
 * Version 1.0
 */
public class TestTCP {
    //客户端
    @Test
    public void client() {

        Socket socket = null;
        OutputStream os = null;
        try {
            //创建socket对象，指明服务器端的IP和端口号
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inetAddress, 8899);
            //获取一个输出流输出数据
            os = socket.getOutputStream();
            //写出数据
            os.write("我是客户端".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //服务端
    @Test
    public void server() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //启动服务，并且监听自己8899端口号
            serverSocket = new ServerSocket(8899);
            //等待客户端连接8899端口
            socket = serverSocket.accept();
            //可以接受来自客户端的数据
            is = socket.getInputStream();
            //读取输入流中的数据
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[5];
            int len;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            System.out.println(baos.toString());
            System.out.println("收到了来自于："+socket.getInetAddress().getHostAddress()+"的数据");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
