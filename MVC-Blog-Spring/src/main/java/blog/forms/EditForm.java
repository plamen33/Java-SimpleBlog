package blog.forms;


import javax.validation.constraints.Size;

public class EditForm {
    @Size(min=3, max=100,message="Please enter between 3 and 100 symbols")
    private String title;
    @Size(min=7)
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
