package Socket;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by  高金明   2019/9/23 20:32
 * Description 从Tomcat服务器上下载文件
 * Version 1.0
 */
public class Test1URL {
    public static void main(String[] args) {

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            URL url = new URL("http://localhost:8080/examples/hello.txt");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            fileOutputStream = new FileOutputStream("hello2.txt");
            byte[] buffer=new byte[1024];
            int len;
            while ((len=inputStream.read(buffer))!=-1){
                fileOutputStream.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
        }
    }
}
