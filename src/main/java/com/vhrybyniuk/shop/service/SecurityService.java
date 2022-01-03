package com.vhrybyniuk.shop.service;

import com.vhrybyniuk.shop.dao.jdbc.JdbcUser;
import com.vhrybyniuk.shop.entity.User;
import org.apache.commons.codec.binary.Hex;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class SecurityService {

    private ArrayList<String> userTokens = new ArrayList<>();
    private final UserService userService;

    public List<String> getUserTokens() {
        return userTokens;
    }


    public SecurityService(UserService userService) {
        this.userService = userService;
    }


    public boolean isAuth(String token) {
        if (token != "") {
            if (userTokens.contains(token)) {
                return true;
            }
        }

        return false;
    }


    public static String getUserToken(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }


}
