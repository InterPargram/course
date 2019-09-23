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
        System.out.println(url.getProtocol());  //获取网络协议
        System.out.println(url.getHost());      //获取主机名
        System.out.println(url.getPort());      //获取端口号
        System.out.println(url.getPath());      //获取路径
        System.out.println(url.getFile());      //获取文件路径
        System.out.println(url.getQuery());     //获取参数
    }
}
