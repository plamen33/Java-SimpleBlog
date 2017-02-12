package blog.forms;


import javax.validation.constraints.Size;

public class CreateForm {
    @Size(min=2, max=100,message="Please enter user between 2 and 100 symbols")
    private String title;
    @Size(min=10)
    private String body;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
