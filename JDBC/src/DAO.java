import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  �߽���   2019/9/26 21:54
 * Description DAO���ģʽ ����ά��
 * Version 1.0
 */
public class DAO {
    //INSERT UPDATA DELETE ���������԰���������
    public void update(String sql, Object... obj) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = JDBCTools.getConnection();
            statement = connection.prepareStatement(sql);
            for (int i = 0; i < obj.length; i++) {
                statement.setObject(i + 1, obj[i]);
            }
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(statement, connection);
        }
    }

    //��ѯһ����¼�����ض�Ӧ�Ķ���
    public <T> T get(Class<T> classz, String sql, Object... obj) {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.��ȡ Connection
            connection = JDBCTools.getConnection();
            //2.��ȡ PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            //3.���ռλ��
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            //4.���в�ѯ���õ� ResultSet
            resultSet = preparedStatement.executeQuery();
            //5.�� ResultSet ��Ϊ�� ׼��һ�� Map<String,Object> �����еı��� ֵ���е�ֵ
            if (resultSet.next()) {
                Map<String, Object> values = new HashMap<String, Object>();
                //6.�õ� ResultSetMetaData ����
                ResultSetMetaData rsmd = resultSet.getMetaData();
                //7.���� ResultSet,��ָ�������ƶ�һ����λ
                //8.�� ResultSetMetaData �õ���������ж�����
                int columnCount = rsmd.getColumnCount();
                //9.�� ResultSetMetaData �õ�ÿһ�е��������� ResultSet �õ�����ÿһ�е�ֵ
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValues = resultSet.getObject(columnLabel);
                    //10.��� Map
                    values.put(columnLabel, columnValues);
                }
                //11.�÷��䴴�� Class ��Ӧ�Ķ���
                entity = classz.newInstance();
                //12.���� Map �����÷��������������ֵ ������Ϊ Map �е� key,����ֵΪ Map �е� Value
                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    String propertyName = entry.getKey();
                    Object value = entry.getValue();
                    ReflectionUtils.setFieldValue(entity, propertyName, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, preparedStatement, connection);
        }

        return entity;
    }

    //��ѯ������¼�����ض�Ӧ�Ľ����
    public <T> List<T> getForList(Class<T> classz, String sql, Object... obj) {
        T entity = null;
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;

        List<T> list = new ArrayList<>();

        try {
            connection = JDBCTools.getConnection();
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < obj.length; i++) {
                ps.setObject(i + 1, obj[i]);
            }
            resultSet = ps.executeQuery();
            //5.�� ResultSet ��Ϊ�� ׼��һ�� Map<String,Object> �����еı��� ֵ���е�ֵ
            List<Map<String, Object>> values = new ArrayList<>();

            List<String> columnLabel=getColumnLabels(resultSet);
            Map<String, Object> map = null;

            //7.���� ResultSet,ʹ��
            while (resultSet.next()) {
                map = new HashMap<>();

                for (String columnLabel2:columnLabel) {
                    Object value = resultSet.getObject(columnLabel2);
                    map.put(columnLabel2, value);
                }
                //8.һ����¼�����õ�Map ���� ��5׼����List��
                values.add(map);
            }
            //11.�÷��䴴�� Class ��Ӧ�Ķ���
            T bean = null;
            //12.�ж� List �Ƿ�Ϊ�ռ��ϣ�����Ϊ�գ������ List �õ�һ��һ����Map�����ڰ�Map����תΪһ�� Class
            // ������Ӧ�� Object����
            if (values.size() > 0) {
                for (Map<String, Object> m : values) {
                    bean = classz.newInstance();
                    for (Map.Entry<String, Object> entry : m.entrySet()) {
                        String propertyName = entry.getKey();
                        Object value = entry.getValue();

                        BeanUtils.setProperty(bean, propertyName, value);
                    }
                    list.add(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(resultSet, ps, connection);
        }

        return list;
    }

    /*
     * ��ȡ�����ColumnLabels��Ӧ�� List
     * */
    private List<String> getColumnLabels(ResultSet rs) {
        List<String> labels = new ArrayList<>();
        ResultSetMetaData rsmd = null;
        try {
            rsmd = rs.getMetaData();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                labels.add(rsmd.getColumnLabel(i + 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return labels;
    }
}
