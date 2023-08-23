package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/adminpage")
    public String adminPage() {
        return "/adminpage";
    }

    @GetMapping("/usersinfo")
    public String showUsersInfo(Model model, Model role){
        role.addAttribute("rolesList", roleService.getRolesList());
        model.addAttribute("users", userService.showAllUsers());
        return "/usersinfo";
    }

    @GetMapping("/user/{id}")
    public String userPage(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "/user";
    }

    @GetMapping("/new")
    public String addUser(Model model, Model role) {
        role.addAttribute("rolesList", roleService.getRolesList());
        model.addAttribute("user", new User());
        return "/new";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/admin/usersinfo";
    }

    @GetMapping("/{id}/edit")
    public String updateUser(Model model, @PathVariable("id") Long id, Model role) {
        role.addAttribute("rolesList", roleService.getRolesList());
        var user = userService.getUserById(id);
        user.setPassword(null);
        model.addAttribute("user", user);

        return "/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user,
                         @PathVariable("id") Long id) {
        userService.editUser(id, user);

        return "redirect:/admin/usersinfo";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return "redirect:/admin/usersinfo";
    }

}
