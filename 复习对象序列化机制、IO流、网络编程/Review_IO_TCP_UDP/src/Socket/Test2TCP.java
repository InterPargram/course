package Socket;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by  高金明   2019/9/21 11:35
 * Description 客户端给服务端发送文件，服务端将文件保存在本地
 * Version 1.0
 */
public class Test2TCP {

    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        FileInputStream fis = null;
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9000);
            os = socket.getOutputStream();
            fis = new FileInputStream(new File("1.jpg"));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os!=null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void server() {
        ServerSocket serverSocket= null;
        Socket socket= null;
        InputStream inputStream= null;
        FileOutputStream fos= null;

        try {
            //创建一个服务器端Socket并监听9000端口号
            serverSocket = new ServerSocket(9000);
            //获取客户端的Socket
            socket = serverSocket.accept();
            //获取客户端的输入流
            inputStream = socket.getInputStream();
            //因为要保存文件到本地所以创建一个文件输出流
            fos = new FileOutputStream(new File("11.jpg"));
            byte[] bytes=new byte[1024];
            int len;
            while ((len=inputStream.read(bytes))!=-1){
                fos.write(bytes,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket!=null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
