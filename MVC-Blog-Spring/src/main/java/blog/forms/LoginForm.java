package blog.forms;


import javax.validation.constraints.Size;

public class LoginForm {
    @Size(min=2, max=30,message="Please enter user between 2 and 30 symbols")
    private String username;
    @Size(min=3)
    private String password;

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
