import org.junit.Test;

import java.sql.SQLOutput;
import java.util.Date;
import java.util.List;

/**
 * Created by  ¸ß½ðÃ÷   2019/9/26 22:46
 * Description
 * Version 1.0
 */
public class DAOTest {
    DAO dao=new DAO();
    @Test
    public void testUpdata(){
        String sql="insert into customer(id,name,type,email) values(?,?,?,?)";
        dao.update(sql,"5","xiaoming,","456","7891325");

    }
    @Test
    public void testGet(){
        String sql="select id fo_id,name fo_name,type fo_type,email fo_email from customer where id = ?";
        Customer customer=dao.get(Customer.class,sql,3);
        System.out.println(customer);
    }

    @Test
    public void testGetForList(){
        String sql="select id fo_id,name fo_name,type fo_type,email fo_email from customer;";
        List<Customer> customers=dao.getForList(Customer.class,sql);
        System.out.println(customers);
    }
}
