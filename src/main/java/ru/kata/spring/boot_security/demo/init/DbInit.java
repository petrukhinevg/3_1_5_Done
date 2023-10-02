package ru.kata.spring.boot_security.demo.init;


import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.services.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DbInit {
    private final UserServiceImpl userService;
    private final RoleServiceImpl roleService;

    public DbInit(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
//    @PostConstruct
//    private void postConstruct() {
//        Role roleAdmin = new Role((long)1,"ADMIN");
//        Role roleUser = new Role( (long)2,"USER");
//        roleService.addRole(roleAdmin);
//        roleService.addRole(roleUser);
//
//        User user = new User("user", "user","user@mail.ru", 20, Set.of(roleUser));
//        User admin = new User("admin", "admin", "admin@mail.ru", 30, Set.of(roleAdmin, roleUser));
//        userService.addUser(user);
//        userService.addUser(admin);
//    }
}
