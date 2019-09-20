package Socket;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by  �߽���   2019/9/20 21:34
 * Description ʵ�� TCP ��������
 * ����1���ͻ��˸�����˷�����Ϣ������˽���Ϣ��ʾ�ڿ���̨��
 * Version 1.0
 */
public class TestTCP {
    //�ͻ���
    @Test
    public void client() {

        Socket socket = null;
        OutputStream os = null;
        try {
            //����socket����ָ���������˵�IP�Ͷ˿ں�
            InetAddress inetAddress = InetAddress.getByName("127.0.0.1");
            socket = new Socket(inetAddress, 8899);
            //��ȡһ��������������
            os = socket.getOutputStream();
            //д������
            os.write("���ǿͻ���".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //�ر���Դ
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

    //�����
    @Test
    public void server() {
        ServerSocket serverSocket = null;
        Socket socket = null;
        InputStream is = null;
        ByteArrayOutputStream baos = null;
        try {
            //�������񣬲��Ҽ����Լ�8899�˿ں�
            serverSocket = new ServerSocket(8899);
            //�ȴ��ͻ�������8899�˿�
            socket = serverSocket.accept();
            //���Խ������Կͻ��˵�����
            is = socket.getInputStream();
            //��ȡ�������е�����
            baos = new ByteArrayOutputStream();
            byte[] bytes = new byte[5];
            int len;
            while ((len = is.read(bytes)) != -1) {
                baos.write(bytes, 0, len);
            }
            System.out.println(baos.toString());
            System.out.println("�յ��������ڣ�"+socket.getInetAddress().getHostAddress()+"������");
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
