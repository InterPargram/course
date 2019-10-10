

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by  �߽���   2019/10/10 18:17
 * Description �������ݿ⵽ Dao �ӿ�
 * ����÷������ݱ����ַ���
 * T��Dao�����ʵ���������
 * Version 1.0
 */
public interface DaoV2<T> {


    /*
     * ��������ķ���
     * args:���ռλ���� Object[] ���͵Ŀɱ����
     * */
    void batch(Connection connection, String sql, Object[] ... args);

    /*
     * ����һ�������ֵ
     * */
    <E> E getForValue(Connection connection, String sql, Object... args);

    /*
     * ����һ�� T �ļ���
     * */
    List<T> getForList(Connection connection, String sql, Object ... args);

    /*
     * ��ѯһ����¼���ض�Ӧ�Ķ���
     * */
    T get(Connection connection, String sql, int args) throws SQLException;

    /*
     * INSRET,UPDATE,DELETE
     * Connection ���ݿ�����
     * sql sql���
     * args ���ռλ�����ɱ����
     * */
    void updata(Connection connection, String sql, Object ... args);
}
