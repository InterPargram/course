import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  高金明   2019/9/26 18:41
 * Description 利用反射及JDBC元数据编写通用的查询方法
 * Version 1.0
 */
public class JDBCTest06 {
    /*
     * 1.利用 SQL 进行查询获取结果集
     * 2.获取结果集的列的别名：idCard
     * 3.利用反射创建实体类的对象：创建对象
     * 4.在获取结果集的每一列的值，结合 3 得到一个 Map 键值对，键：列的别名 值：列的值
     * {id:5,name:tom}
     * 5.再利用反射对 2 的属性赋值 属性：Map的键 值：Map的值
     * */
    public <T> T get(Class<T> classz, String sql, Object... obj) {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.得到 ResultSet 对象
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //2.得到 ResultSetMetaData 对象
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //3。创建一个 Map<String,Object> 对象，键：SQL 查询的列的别名 值：列
            Map<String, Object> vlaues = new HashMap<String, Object>();
            //4.处理结果集，填充3.对应的Map对象
            //通过解析SQL语句来判断到底选择了那些列以及给entity对象的那些属性赋值
            if (resultSet.next()) {
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    //获取列的名
                    String columnLable = rsmd.getColumnLabel(i + 1);
                    Object colunmName = resultSet.getObject(columnLable);
                    vlaues.put(columnLable, colunmName);
                }
            }
            //5.Map 不为空 利用反射创建 Class 对象
            if (vlaues.size()>0){
                //利用反射创建对象
                entity = classz.newInstance();
                //6.遍历Map对象,利用 Class 对象的对应的属性赋值
                for (Map.Entry<String,Object> entry:vlaues.entrySet()){
                    String filedName = entry.getKey();
                    Object filedValues = entry.getValue();
                    ReflectionUtils.setFieldValue(entity, filedName, filedValues);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, preparedStatement, connection);
        }
        return entity;
    }

    /*
     * ResultSetMetaData 是描述 ResultSet 的元数据对象，可以从中获取到结果集中有多少列，列名是什么
     * 常用的方法：
     * int getColumnCount() 返回此 ResultSet 对象中的列数。
     * String getColumnLabel(int column)  获取用于打印输出和显示的指定列的建议标题。
     * */
    @Test
    public void testGet(){
        String sql="select id fo_id,name fo_name,type fo_type,email fo_email from Customer where id=?";
        Customer customer=get(Customer.class,sql,2);
        System.out.println(customer);
        String sql2="select id,name from person where Id=?";
        Person person=get(Person.class,sql2,2);
        System.out.println(person);
    }
}
