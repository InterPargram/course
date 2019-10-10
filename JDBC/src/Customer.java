/**
 * Created by  ¸ß½ðÃ÷   2019/9/26 18:38
 * Description
 * Version 1.0
 */
public class Customer {

    private int fo_id;
    private String fo_name;
    private String fo_email;
    private String fo_type;

    @Override
    public String toString() {
        return "Customer{" +
                "fo_id=" + fo_id +
                ", fo_name='" + fo_name + '\'' +
                ", fo_email='" + fo_email + '\'' +
                ", fo_type='" + fo_type + '\'' +
                '}';
    }

    public Customer() {
    }


    public Customer(int id, String name, String email, String type) {
        this.fo_id = id;
        this.fo_name = name;
        this.fo_email = email;
        this.fo_type = type;
    }

    public int getFo_id() {
        return fo_id;
    }

    public void setFo_id(int fo_id) {
        this.fo_id = fo_id;
    }

    public String getName() {
        return fo_name;
    }

    public void setName(String name) {
        this.fo_name = name;
    }

    public String getFo_email() {
        return fo_email;
    }

    public void setFo_email(String fo_email) {
        this.fo_email = fo_email;
    }

    public String getFo_type() {
        return fo_type;
    }

    public void setFo_type(String fo_type) {
        this.fo_type = fo_type;
    }


}
