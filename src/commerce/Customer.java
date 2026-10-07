package commerce;

public class Customer {

    private String customerName;
    private String email;
    private CustomerGrade grade;

    public Customer(String customerName, String email, CustomerGrade grade){
        this.customerName = customerName;
        this.email = email;
        this.grade = grade;
    }
    public String getCustomerName(){ return customerName; }
    public String getEmail(){ return email; }
    public CustomerGrade getGrade(){ return grade; }

    public void setEmail(String email){ this.email = email; }
    public void setGrade(CustomerGrade grade){ this.grade = grade; }


}
