import org.apache.commons.beanutils.BeanUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  高金明   2019/9/26 21:54
 * Description DAO设计模式 方便维护
 * Version 1.0
 */
public class DAO {
    //INSERT UPDATA DELETE 操作都可以包含在其中
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

    //查询一条记录，返回对应的对象
    public <T> T get(Class<T> classz, String sql, Object... obj) {
        T entity = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            //1.获取 Connection
            connection = JDBCTools.getConnection();
            //2.获取 PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < obj.length; i++) {
                preparedStatement.setObject(i + 1, obj[i]);
            }
            //4.进行查询，得到 ResultSet
            resultSet = preparedStatement.executeQuery();
            //5.若 ResultSet 不为空 准备一个 Map<String,Object> 键：列的别名 值：列的值
            if (resultSet.next()) {
                Map<String, Object> values = new HashMap<String, Object>();
                //6.得到 ResultSetMetaData 对象
                ResultSetMetaData rsmd = resultSet.getMetaData();
                //7.处理 ResultSet,把指针向下移动一个单位
                //8.由 ResultSetMetaData 得到结果集中有多少列
                int columnCount = rsmd.getColumnCount();
                //9.由 ResultSetMetaData 得到每一列的列名，由 ResultSet 得到具体每一列的值
                for (int i = 0; i < columnCount; i++) {
                    String columnLabel = rsmd.getColumnLabel(i + 1);
                    Object columnValues = resultSet.getObject(columnLabel);
                    //10.填充 Map
                    values.put(columnLabel, columnValues);
                }
                //11.用反射创建 Class 对应的对象
                entity = classz.newInstance();
                //12.遍历 Map 对象，用反射填充对象的属性值 属性名为 Map 中的 key,属性值为 Map 中的 Value
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

    //查询多条记录，返回对应的结果集
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
            //5.若 ResultSet 不为空 准备一个 Map<String,Object> 键：列的别名 值：列的值
            List<Map<String, Object>> values = new ArrayList<>();

            List<String> columnLabel=getColumnLabels(resultSet);
            Map<String, Object> map = null;

            //7.处理 ResultSet,使用
            while (resultSet.next()) {
                map = new HashMap<>();

                for (String columnLabel2:columnLabel) {
                    Object value = resultSet.getObject(columnLabel2);
                    map.put(columnLabel2, value);
                }
                //8.一条记录把填充好的Map 放入 在5准备的List中
                values.add(map);
            }
            //11.用反射创建 Class 对应的对象
            T bean = null;
            //12.判断 List 是否为空集合，若不为空，则遍历 List 得到一个一个的Map对象，在把Map对象转为一个 Class
            // 参数对应的 Object对象
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
     * 获取结果集ColumnLabels对应的 List
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
