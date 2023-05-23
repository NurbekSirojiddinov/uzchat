package com.developers.uzchat.api.view;

import com.developers.uzchat.dto.UserDto;
import com.developers.uzchat.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<UserDto> users = userService.findAllUser();
        model.addAttribute("listUsers", users);

        return "admin_page";
    }

    @GetMapping("/users/view/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findUser(id);
        model.addAttribute("user", user);
        return "view_user";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        UserDto user = userService.findUser(id);
        userService.deleteUser(id);
        model.addAttribute("user", user);
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String login() {
        return "admin_login";
    }
}