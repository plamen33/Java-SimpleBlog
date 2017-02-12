package blog.controllers;

import blog.forms.LoginForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;
import org.omg.CosNaming.Binding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.metadata.IIOMetadataNode;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@Controller
public class AccountController {
    public static final String USER_LOGIN = "siteUserLogin";
    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    private HttpSession httpSession;

    @RequestMapping("/users/login")
    public String login(LoginForm loginForm) {
        return "users/login";
    }

   @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public String loginPage(@Valid LoginForm loginForm, BindingResult bindingResult) throws SQLException {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "users/login";
        }

        if (!userService.authenticate(
                loginForm.getUsername(), loginForm.getPassword())) {
            notifyService.addErrorMessage("Invalid login!");
            httpSession.removeAttribute(USER_LOGIN);
            return "users/login";
        }

        notifyService.addInfoMessage("Login successful");
        httpSession.setAttribute(USER_LOGIN, loginForm.getUsername());



        httpSession.setAttribute("userLogin",loginForm.getUsername());
        return "redirect:/";
    }
    @RequestMapping(value = "/users/logout", method = RequestMethod.POST)
    public String logoutPage(@Valid LoginForm loginForm, BindingResult bindingResult) throws SQLException {
            httpSession.removeAttribute(USER_LOGIN);
            return "redirect:/";
    }
    @RequestMapping("/users")
    public String view_all(Model model) {
        List<User> usersall = userService.findAll();
        model.addAttribute("usersall", usersall);
        return "users/users_all";  // v primera e view.html
    }
}
