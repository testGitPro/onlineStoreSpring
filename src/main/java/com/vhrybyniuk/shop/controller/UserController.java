package com.vhrybyniuk.shop.controller;

import com.vhrybyniuk.shop.entity.User;
import com.vhrybyniuk.shop.service.SecurityService;
import com.vhrybyniuk.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Controller
public class UserController {
    private UserService userService;
    private SecurityService securityService;

    @Autowired
    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @GetMapping("/login")
    protected String getlogin() {
        return "login";
    }

    @PostMapping("/login")
    protected String login(@RequestParam String email, @RequestParam String password,
                           HttpSession session, HttpServletResponse response, Model model) throws SQLException {

        if (userService.isEmailExistsInDB(email)) {

            if (userService.isUserExistsInDB(email, password)) {

                String userToken = UUID.randomUUID().toString();
                securityService.getUserTokens().add(userToken);
                session.setAttribute("email", email);
                session.setMaxInactiveInterval(-1);

                Cookie cookie = new Cookie("user-token", userToken);
                response.addCookie(cookie);
                return "redirect:/";

            } else {
                String errorMessage = "Incorect password!";
                model.addAttribute("errorMessage", errorMessage);
                return "login";
            }

        } else {
            String errorMessage = "Incorect email!" + email;
            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }

    }

    @GetMapping("/registration")
    protected String getRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    protected String register(@RequestParam String email, @RequestParam String password,
                              @RequestParam String password2, @RequestParam String user_name, Model model) {

        if (!password2.equals(password)) {
            String errorMessage = "Password mismatch";
            model.addAttribute("errorMessage", errorMessage);
            return "registration";
        }

        User user = User.builder()
                .user_name(user_name)
                .email(email)
                .password(password)
                .build();

        try {
            if (!userService.isEmailExistsInDB(email)) {
                userService.addUser(user);
            } else {
                String errorMessage = "A user with the same email address already exists";
                model.addAttribute("errorMessage", errorMessage);
                return "registration";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/login";
    }

    @GetMapping("/logout")
    protected String logout(HttpServletRequest req, HttpServletResponse resp) {
        String userToken = securityService.getUserToken(req);
        Cookie cookie = new Cookie("user-token", userToken);
        cookie.setValue(null);
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
        req.getSession().removeAttribute("name");
        return "redirect:/login";
    }
}