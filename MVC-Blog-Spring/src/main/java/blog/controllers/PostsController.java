package blog.controllers;

import blog.forms.CreateForm;
import blog.forms.EditForm;
import blog.models.Post;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.PostService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static blog.controllers.AccountController.USER_LOGIN;

@Controller
public class PostsController {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notifyService;
    @RequestMapping("/posts/view/{id}")
    public String view(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/index";  // v primera e view.html
    }
    @RequestMapping("/posts")
    public String view_all(Model model) {
        List<Post> postall = postService.findAll();
        model.addAttribute("postall", postall);
        return "posts/index_all";  // v primera e view.html
    }
    @RequestMapping(value ="/posts/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        postService.deleteById(id);
        List<Post> postall = postService.findAll();
        model.addAttribute("postall", postall);
        return "posts/index_all";  // v primera e view.html
    }
    @RequestMapping(value ="/posts/edit/{id}", method = RequestMethod.GET)
    public String editPage(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }
        model.addAttribute("post", post);
        return "posts/edit";
    }
    @RequestMapping(value ="/posts/edit/{id}", method = RequestMethod.POST)
    public String edit(@Valid EditForm editForm, @PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            notifyService.addErrorMessage("Cannot find post #" + id);
            return "redirect:/";
        }

        post.setTitle(editForm.getTitle());
        post.setBody(editForm.getBody());
        postService.edit(post);
        return "redirect:/posts";
    }
    @RequestMapping(value ="/posts/create", method = RequestMethod.GET)
    public String createPage(CreateForm createForm) {
           return "posts/create";
    }
    @RequestMapping(value ="/posts/create", method = RequestMethod.POST)
    public String create(CreateForm createForm) {
        Post post=new Post();
        Object usernameObject = httpSession.getAttribute(USER_LOGIN);
        if (usernameObject!=null)
        {
            String username = usernameObject.toString();
            List<User> userall = userService.findAll();
            Stream<User> userstream = userall.stream().filter(u->u.getUsername().equals(username));
            List<User> userslist = userstream.collect(Collectors.toList());
            User user = userslist.get(0);
            post.setAuthor(user);
        }
        post.setTitle(createForm.getTitle());
        post.setBody(createForm.getBody());
        postService.create(post);
        return "redirect:/";
    }
}
