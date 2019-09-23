package Socket;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by  高金明   2019/9/23 20:20
 * Description
 * 1、URL：统一资源定位符，对应着互联网的某一资源地址
 * 2、格式：http://localhost:8080/examples/hello.txt?username=Tom
 *          协议    主机名  端口号      资源地址        参数列表
 * Version 1.0
 */
public class TestURL {
    public static void main(String[] args) {
        //关于 URL 对象的基本方法
        URL url= null;
        try {
            url = new URL("http://localhost:8080/examples/hello.txt?username=Tom");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        System.out.println(url.getProtocol());
        System.out.println(url.getHost());
        System.out.println(url.getPort());
        System.out.println(url.getPath());
        System.out.println(url.getFile());
        System.out.println(url.getQuery());
    }
}
