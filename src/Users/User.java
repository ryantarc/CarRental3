package Users;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    protected String id;
    private String email;
    private String password;
    private String phoneNo;

    public User (String name,String email, String password, String phoneNo){
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return String.format(
                "%-5s | %-15s | %-20s | %-15s",
                id,
                name,
                email,
                phoneNo
        );
    }
}
