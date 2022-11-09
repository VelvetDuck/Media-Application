package net.velvetduck.quotesapp.controller;

import net.velvetduck.quotesapp.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.common.ExpressionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.util.Validate;

import java.util.Optional;

@Controller
public class WebPageController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SpringUserDetailsService springUserDetailsService;

    @GetMapping("/")
    public String defaultPage(Model model){

        return "index";
    }

    @GetMapping("/homePage")
    public String homePage(Model model, @AuthenticationPrincipal SpringUserDetails springUserDetails){
        model.addAttribute("user", springUserDetails.getUser());

        System.out.println(springUserDetails.getUser().getUsername());
        return "homePage";
    }


    @GetMapping("/signup")
    public String viewSignupPage(Model model) {
        model.addAttribute("user", new User());
        return "security/signup";
    }

    @PostMapping("/signup")
    public String viewSignupPage(@ModelAttribute User user, Model model){
        model.addAttribute("user", user);

        user.setImageProfilePhotoUrl("https://cdn.pixabay.com/photo/2015/04/19/08/33/flower-729513_960_720.jpg");
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        user.setRoles("USER");
        user.setNonExpired(true);
        user.setAccountNonLocked(true);
        user.setShowGeneratedPostsOrNot(true);

        if(userRepository.findUserByUsername(user.getUsername()) == null){
            userService.save(user);
            return "homePage";
        }else {
            return "index";
        }
    }

}
