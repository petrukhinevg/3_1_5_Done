package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

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
    public String showUsersInfo(Model model, Model role, Principal principal){
        User authenticatedUser = userService.getUserByUsername(principal.getName()); //Нашли в БД юзера, который аутентифицировался


        model.addAttribute ("authenticatedUser", authenticatedUser); //Добавили самого юзера из БД
        model.addAttribute ("roleOfAuthenticatedUser", authenticatedUser.getRoles()); //Добавили его роли
        role.addAttribute("AllRoles", roleService.getRolesList());
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
        role.addAttribute("roles", roleService.getRolesList());
        model.addAttribute("user", new User());
        return "/admin/usersinfo";
    }


    @PostMapping("/create")
    public String add(@ModelAttribute("user") User user){
        userService.addUser(user);
        return "redirect:/admin/usersinfo";
    }

    @GetMapping("/user-profile/{id}")
    public String findUser(@PathVariable("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("AllRoles", user.getRoles());
        return "user";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("AllRoles", roleService.getRolesList());
        return "redirect:/admin/usersinfo";
    }

    @Transactional
    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User updateUser, @PathVariable("id") Long id) {
        userService.settingRoles(updateUser);
        userService.editUser(id, updateUser);

        return "redirect:/admin/usersinfo";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);

        return "redirect:/admin/usersinfo";
    }

}