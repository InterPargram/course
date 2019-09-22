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
            //
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
            //
            os = socket.getOutputStream();
            //
            fil = new FileInputStream(new File("3.jpg"));
            //
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fil.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            socket.shutdownOutput();

            is = socket.getInputStream();
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            byte[] byte1 = new byte[20];
            int len1;
            while ((len1 = is.read(byte1)) != -1) {
                boas.write(byte1, 0, len1);
            }
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
            //
            ss = new ServerSocket(9090);
            //
            socket = ss.accept();
            //
            inputStream = socket.getInputStream();
            //
            fileOutputStream = new FileOutputStream(new File("33.jpg"));
            //
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            os = socket.getOutputStream();
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
