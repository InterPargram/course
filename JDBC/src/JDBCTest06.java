import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by  �߽���   2019/9/26 18:41
 * Description ���÷��估JDBCԪ���ݱ�дͨ�õĲ�ѯ����
 * Version 1.0
 */
public class JDBCTest06 {
    /*
     * 1.���� SQL ���в�ѯ��ȡ�����
     * 2.��ȡ��������еı�����idCard
     * 3.���÷��䴴��ʵ����Ķ��󣺴�������
     * 4.�ڻ�ȡ�������ÿһ�е�ֵ����� 3 �õ�һ�� Map ��ֵ�ԣ������еı��� ֵ���е�ֵ
     * {id:5,name:tom}
     * 5.�����÷���� 2 �����Ը�ֵ ���ԣ�Map�ļ� ֵ��Map��ֵ
     * */
    public <T> T get(Class<T> classz, String sql, Object... obj) {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.�õ� ResultSet ����
            connection = JDBCTools.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            resultSet = preparedStatement.executeQuery();
            //2.�õ� ResultSetMetaData ����
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //3������һ�� Map<String,Object> ���󣬼���SQL ��ѯ���еı��� ֵ����
            Map<String, Object> vlaues = new HashMap<String, Object>();
            //4.�������������3.��Ӧ��Map����
            //ͨ������SQL������жϵ���ѡ������Щ���Լ���entity�������Щ���Ը�ֵ
            if (resultSet.next()) {
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    //��ȡ�е���
                    String columnLable = rsmd.getColumnLabel(i + 1);
                    Object colunmName = resultSet.getObject(columnLable);
                    vlaues.put(columnLable, colunmName);
                }
            }
            //5.Map ��Ϊ�� ���÷��䴴�� Class ����
            if (vlaues.size()>0){
                //���÷��䴴������
                entity = classz.newInstance();
                //6.����Map����,���� Class ����Ķ�Ӧ�����Ը�ֵ
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
     * ResultSetMetaData ������ ResultSet ��Ԫ���ݶ��󣬿��Դ��л�ȡ����������ж����У�������ʲô
     * ���õķ�����
     * int getColumnCount() ���ش� ResultSet �����е�������
     * String getColumnLabel(int column)  ��ȡ���ڴ�ӡ�������ʾ��ָ���еĽ�����⡣
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
