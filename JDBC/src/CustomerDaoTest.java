import org.junit.Test;

import java.sql.Connection;


/**
 * Created by  ¸ß½ðÃ÷   2019/10/10 19:11
 * Description
 * Version 1.0
 */
public class CustomerDaoTest {
    CustomerDao customerDao=new CustomerDao();

    @Test
    public void batch() {
    }

    @Test
    public void getForValue() {
    }

    @Test
    public void getForList() {
    }

    @Test
    public void get() {
        Connection connection = null;
        try {
            connection = JDBCTools.getConnection();
            String sql="select id fo_id,name,sex fo_sex from customer where id=?";
            Customer customer=customerDao.get(connection,sql,5);
            System.out.println(customer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCTools.release(null, null, connection);
        }
    }

    @Test
    public void updata() {
    }
}