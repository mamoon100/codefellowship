package com.example.codefellowship.Controllers;

import com.example.codefellowship.Models.ApplicationUser;
import com.example.codefellowship.Models.Post;
import com.example.codefellowship.Repositories.ApplicationUserRepository;
import com.example.codefellowship.Repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;

@Controller
public class ApplicationUserController {
    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    PostRepository postRepository;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("post", new Post());
        return "index";
    }

    @GetMapping("/users/{id}")
    public String getUser(Authentication authentication, Model model, @PathVariable Long id) {
        try {
            ApplicationUser user = applicationUserRepository.findUserById(id);
            if (user == null) throw new Exception("User not found");
            boolean isHimSelf = Objects.equals(authentication.getName(), user.getUsername());
            model.addAttribute("isHimSelf", isHimSelf);
            model.addAttribute("user", user);
            return "user";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    @GetMapping("/my_profile")
    public String profile(Authentication authentication, Model model) {
        try {
            if (authentication == null) throw new Exception("You are not logged in");
            ApplicationUser user = applicationUserRepository.findUserByUsername(authentication.getName());
            Post[] posts = postRepository.findPostByApplicationUser(user);
            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
            return "profile";
        } catch (Exception e) {
            e.printStackTrace();
            return "index";
        }

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
    public RedirectView signup(@ModelAttribute ApplicationUser applicationUser, HttpServletRequest request) {
        // try and catch for only if the user has duplicate username.
        try {
            String password = applicationUser.getPassword();
            applicationUser.setPassword(encoder.encode(applicationUser.getPassword()));
            // Just to get the age in years.
//            System.out.println(Period.between(applicationUser.getDOB().toLocalDate(), LocalDate.now()).getYears());
            applicationUserRepository.save(applicationUser);
            request.login(applicationUser.getUsername(), password);
            return new RedirectView("/");
        } catch (Exception e) {
            e.printStackTrace();
            return new RedirectView("/");
        }
    }

    @PostMapping("/post")
    public RedirectView postPost(@ModelAttribute Post post, Authentication authentication) {
        try {
            ApplicationUser applicationUser = applicationUserRepository.findUserByUsername(authentication.getName());
            post.setApplicationUser(applicationUser);
            postRepository.save(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new RedirectView("/");

    }
}
