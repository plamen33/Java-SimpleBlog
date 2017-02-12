package blog.controllers;


import blog.forms.LoginForm;
import blog.repositories.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import blog.forms.RegisterForm;
import blog.models.User;
import blog.services.NotificationService;
import blog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;

import static blog.controllers.AccountController.USER_LOGIN;

@Controller
public class RegisterController {
    @Autowired
    private HttpSession httpSession;
    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notifyService;

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/users/register")
    public String register(RegisterForm registerForm) {
        return "users/register";
    }

    @RequestMapping(value = "/users/register", method = RequestMethod.POST)
    public String registerPage(@Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            notifyService.addErrorMessage("Please fill the form correctly!");
            return "users/register";
        }

        User user=new User();

        user.setUsername(registerForm.getUsername());

        user.setFullName(registerForm.getFull_name());
        user.setPasswordHash(registerForm.getPassword());

        userService.create(user);


        notifyService.addInfoMessage("Register successful");
        httpSession.setAttribute(USER_LOGIN, registerForm.getUsername());
        return "redirect:/";
    }
    private static String bCrypt(String password) {
        String result = BCrypt.hashpw(password,BCrypt.gensalt());
        return result;
    }
}
