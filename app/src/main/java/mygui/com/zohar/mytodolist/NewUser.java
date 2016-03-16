package mygui.com.zohar.mytodolist;

/**
 * Created by danielshamama on 12/03/2016.
 */
public class NewUser {
    private String email;
    private String phone;

    public NewUser(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
