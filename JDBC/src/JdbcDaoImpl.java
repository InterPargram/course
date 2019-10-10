
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by  高金明   2019/10/10 18:34
 * Description
 * 使用 QueryRunner 提供具体的实现方法
 * <T>:子类需传入的泛型类型
 * Version 1.0
 */
public class JdbcDaoImpl<T> implements DaoV2<T> {

    private QueryRunner queryRunner = null;
    private Class<T> type;

    public JdbcDaoImpl() {
        queryRunner = new QueryRunner();
        type = ReflectionUtils.getSuperClassGenricType(getClass());
    }

    @Override
    public void batch(Connection connection, String sql, Object[]... args) {

    }

    @Override
    public <E> E getForValue(Connection connection, String sql, Object... args) {
        return null;
    }

    @Override
    public List<T> getForList(Connection connection, String sql, Object... args) {
        return null;
    }

    @Override
    public T get(Connection connection, String sql, int args) throws SQLException {
        return queryRunner.query(connection, sql, new BeanHandler<>(type), args);
    }


// return

    @Override
    public void updata(Connection connection, String sql, Object... args) {

    }
}
