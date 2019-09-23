package Socket;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by  �߽���   2019/9/21 11:59
 * Description �ͻ��˷����ļ�������ˣ�����˽��ܱ��浽����֮�󣬸��ͻ��˷��ء����ͳɹ������ر���Ӧ����
 * Version 1.0
 */
public class Test3TCP {
    //�ͻ���
    @Test
    public void client() {
        Socket socket = null;
        OutputStream os = null;
        FileInputStream fil = null;
        InputStream is = null;
        try {
            //����Socket����
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9090);
            //��ȡ�����
            os = socket.getOutputStream();
            //�����ļ�������
            fil = new FileInputStream(new File("3.jpg"));
            //���ļ����ͳ�ȥ
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fil.read(bytes)) != -1) {
                os.write(bytes, 0, len);
            }
            //���߷�����ļ��Ѿ��������
            socket.shutdownOutput();
            //���շ���˵ķ�����Ϣ
            is = socket.getInputStream();
            ByteArrayOutputStream boas = new ByteArrayOutputStream();
            byte[] byte1 = new byte[20];
            int len1;
            while ((len1 = is.read(byte1)) != -1) {
                boas.write(byte1, 0, len1);
            }
            //��ӡ����˷��͹�������Ϣ
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

    //�����
    @Test
    public void server() {
        ServerSocket ss = null;
        Socket socket = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        OutputStream os = null;
        try {
            //���������Socket����������9090�˿�
            ss = new ServerSocket(9090);
            //��ȡ�ͻ��˵�Socket
            socket = ss.accept();
            //��ȡ�ͻ��˵�������
            inputStream = socket.getInputStream();
            //�����ļ������
            fileOutputStream = new FileOutputStream(new File("33.jpg"));
            //���ļ���ȡ����
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                fileOutputStream.write(bytes, 0, len);
            }
            //��ȡ�ͻ��˵������
            os = socket.getOutputStream();
            //���ͻ��˷�����Ϣ
            os.write("�ļ��յ���".getBytes());
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
