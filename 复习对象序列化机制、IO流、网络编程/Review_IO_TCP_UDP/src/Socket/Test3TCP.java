package Socket;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by  高金明   2019/9/21 11:59
 * Description 客户端发送文件给服务端，服务端接受保存到本地之后，给客户端返回“发送成功”并关闭相应连接
 * Version 1.0
 */
public class Test3TCP {
    //客户端
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        FileInputStream fil = null;
        InputStream is = null;
        try {
            //创建Socket对象
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
            //获取输出流
            os = socket.getOutputStream();
            //创建文件输入流
            fil = new FileInputStream(new File("3.jpg"));
            //将文件发送出去
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fil.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            //告诉服务端文件已经发送完毕
            socket.shutdownOutput();
            //接收服务端的反馈信息
            is = socket.getInputStream();
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            byte[] byte1 = new byte[20];
            int len1;
            while ((len1 = is.read(byte1)) != -1) {
                boas.write(byte1, 0, len1);
            }
            //打印服务端发送过来的信息
            System.out.println(boas.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fil != null) {
                try {
                    fil.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //服务端
    @Test
    public void server() {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        OutputStream os = null;
        try {
            //创建服务端Socket并启动监听9090端口
            ss = new ServerSocket(9090);
            //获取客户端的Socket
            socket = ss.accept();
            //获取客户端的输入流
            inputStream = socket.getInputStream();
            //创建文件输出流
            fileOutputStream = new FileOutputStream(new File("33.jpg"));
            //将文件读取出来
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            //获取客户端的输出流
            os = socket.getOutputStream();
            //给客户端发送消息
            os.write("文件收到了".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ss != null) {
                try {
                    ss.close();
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
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
