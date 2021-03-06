package nnhl.project.ebank.Counsellor;

public class CounsellorModel {


    private String email;
    private String name;
    private String password;
    private String phone;

    public CounsellorModel()
    {

    }
    public CounsellorModel( String email, String name, String password, String phone)
    {

        this.email = email;
        this.name = name;
        this.password = password;
        this.phone = phone;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
