package veb.seminarska.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import veb.seminarska.model.Role;
import veb.seminarska.model.exceptions.InvalidArgumentsException;
import veb.seminarska.model.exceptions.PasswordsDoNotMatchException;
import veb.seminarska.service.AuthService;
import veb.seminarska.service.UserService;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final AuthService authService;
    private final UserService userService;
    //private final JavaMailSender javaMailSender;

    @Autowired
    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
        //this.javaMailSender = javaMailSender;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           @RequestParam Role role) {
        try {
            this.userService.register(username, password, repeatedPassword, name, surname, role);
            /*SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(username);
            simpleMailMessage.setFrom("martinaradeska12@gmail.com");
            simpleMailMessage.setSubject("Registration Successful");
            simpleMailMessage.setText("Welcome to E-LIBRARY - WEB PROGRAMMING PROJECT");
            javaMailSender.send(simpleMailMessage);
            SimpleMailMessage email = new SimpleMailMessage();*/

            return "redirect:/login";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException exception) {
            return "redirect:/register?error=" + exception.getMessage();
        }
    }
}
