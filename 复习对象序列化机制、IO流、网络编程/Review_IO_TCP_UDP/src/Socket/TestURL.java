package Socket;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by  �߽���   2019/9/23 20:20
 * Description
 * 1��URL��ͳһ��Դ��λ������Ӧ�Ż�������ĳһ��Դ��ַ
 * 2����ʽ��http://localhost:8080/examples/hello.txt?username=Tom
 *          Э��    ������  �˿ں�      ��Դ��ַ        �����б�
 * Version 1.0
 */
public class TestURL {
    public static void main(String[] args) {
        //���� URL ����Ļ�������
        URL url= null;
        try {
            url = new URL("http://localhost:8080/examples/hello.txt?username=Tom");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url.getProtocol());  //��ȡ����Э��
        System.out.println(url.getHost());      //��ȡ������
        System.out.println(url.getPort());      //��ȡ�˿ں�
        System.out.println(url.getPath());      //��ȡ·��
        System.out.println(url.getFile());      //��ȡ�ļ�·��
        System.out.println(url.getQuery());     //��ȡ����
    }
}
