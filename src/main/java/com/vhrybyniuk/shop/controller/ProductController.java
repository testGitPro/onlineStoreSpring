package com.vhrybyniuk.shop.controller;

import com.vhrybyniuk.shop.entity.Product;
import com.vhrybyniuk.shop.service.ProductService;
import com.vhrybyniuk.shop.service.SecurityService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Controller
public class ProductController {
    private ProductService productService;
    private SecurityService securityService;

    public ProductController(ProductService productService, SecurityService securityService) {
        this.productService = productService;
        this.securityService = securityService;
    }

    @GetMapping("/")
    protected String showAllProducts(HttpServletRequest request, Model model, @CookieValue(value = "user-token", defaultValue = "") String usertoken) throws SQLException {
        if (securityService.isAuth(usertoken)) {
            data(request, model);
            return "all";
        } else {
            return "redirect:" + "/login";
        }

    }

    @GetMapping("/add")
    protected String getAddProductPage(@CookieValue(value = "user-token", defaultValue = "") String usertoken) {
        if (securityService.isAuth(usertoken)) {
            return "add";
        } else {
            return "redirect:" + "/login";
        }
    }

    @PostMapping("/add")
    protected String addProduct(@RequestParam String name, @RequestParam String price, Model model, @CookieValue(value = "user-token", defaultValue = "") String usertoken) throws IOException {
        if (securityService.isAuth(usertoken)) {
            Product product = Product.builder()
                    .name(name)
                    .price(Double.parseDouble(price))
                    .build();
            productService.add(product);
            return "redirect:" + "/";
        } else {
            return "redirect:" + "/login";
        }
    }

    @GetMapping("/edit")
    protected String getEditProduct(@RequestParam String id, Model model, @CookieValue(value = "user-token", defaultValue = "") String usertoken) throws ServletException, IOException {
        if (securityService.isAuth(usertoken)) {
            Product product = productService.get(Integer.parseInt(id));
            model.addAttribute("product", product);
            return "edit";

        } else {
            return "redirect:" + "/login";
        }
    }

    @PostMapping("/edit")
    protected String editProduct(@RequestParam String id, @RequestParam String name, @RequestParam String price, Model model, @CookieValue(value = "user-token", defaultValue = "") String usertoken) throws IOException {
        if (securityService.isAuth(usertoken)) {
            Product product = Product.builder().
                    name(name)
                    .price(Double.parseDouble(price))
                    .build();

            product.setId(Integer.parseInt(id));
            model.addAttribute("product", product);
            productService.update(product);
            return "redirect:" + "/";
        } else {
            return "redirect:" + "/login";
        }
    }

    @GetMapping("/delete")
    protected String deleteProduct(@RequestParam String id, Model model, @CookieValue(value = "user-token", defaultValue = "") String usertoken) throws IOException, SQLException {
        if (securityService.isAuth(usertoken)) {
            productService.delete(Integer.parseInt(id));
            return "redirect:" + "/";
        } else {
            return "redirect:" + "/login";
        }
    }


    private void data(HttpServletRequest req, Model model) throws SQLException {
        List<Product> products = productService.findAll();
        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");
        model.addAttribute("products", products);
        model.addAttribute("email", email);

    }


}
