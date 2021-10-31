package com.example.codefellowship.Controllers;

import com.example.codefellowship.Models.ApplicationUser;
import com.example.codefellowship.Repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        model.addAttribute("user", new ApplicationUser());
        return "signup";
    }

    @PostMapping("/signup")
    public RedirectView signup(@ModelAttribute ApplicationUser applicationUser) {
        // try and catch for only if the user has duplicate username.
        try {
            applicationUser.setPassword(encoder.encode(applicationUser.getPassword()));
            // Just to get the age in years.
            System.out.println(Period.between(applicationUser.getDOB().toLocalDate(), LocalDate.now()).getYears());
            applicationUserRepository.save(applicationUser);
            return new RedirectView("/");
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/");
        }
    }
}
