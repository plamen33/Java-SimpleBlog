package blog.forms;


import javax.validation.constraints.Size;

public class RegisterForm {
    @Size(min=2, max=30,message="Please enter user between 2 and 30 symbols")
    private String username;
    @Size(min=3)
    private String password;

    @Size(min=5)
    private String full_name;

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
