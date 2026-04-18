package Users;

import java.util.ArrayList;

public class Admin extends User {
    public static int count = 1;
    public Admin (String name, String email, String password,String phoneNo){
        super(name,email,password,phoneNo);
        id = "A" + String.format("%03d", count++);
    }

    public static void setCount(int count) {
        Admin.count = count;
    }
}
