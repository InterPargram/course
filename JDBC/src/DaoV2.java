

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by  高金明   2019/10/10 18:17
 * Description 访问数据库到 Dao 接口
 * 定义好访问数据表到各种方法
 * T：Dao处理的实体类的类型
 * Version 1.0
 */
public interface DaoV2<T> {


    /*
     * 批量处理的方法
     * args:填充占位符的 Object[] 类型的可变参数
     * */
    void batch(Connection connection, String sql, Object[] ... args);

    /*
     * 返回一个具体的值
     * */
    <E> E getForValue(Connection connection, String sql, Object... args);

    /*
     * 返回一个 T 的集合
     * */
    List<T> getForList(Connection connection, String sql, Object ... args);

    /*
     * 查询一条记录返回对应的对象
     * */
    T get(Connection connection, String sql, int args) throws SQLException;

    /*
     * INSRET,UPDATE,DELETE
     * Connection 数据库连接
     * sql sql语句
     * args 填充占位符到可变参数
     * */
    void updata(Connection connection, String sql, Object ... args);
}
