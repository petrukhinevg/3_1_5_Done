package ru.kata.spring.boot_security.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserDaoImpl userDao, RoleService roleService, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public List<User> showAllUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(settingRoles(user));
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public void editUser(Long id, User updatedUser) {
        User user = userDao.findById(id);
            user.setId(updatedUser.getId());
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setRoles(updatedUser.getRoles());
            if (!user.getPassword().equals(updatedUser.getPassword())) {
                user.setPassword(encoder.encode(updatedUser.getPassword()));

            userDao.save(settingRoles(user));
        }
    }
    private User settingRoles(User user) {
        var roles = user.getRoles();
        var roleList = roleService.getRolesList();
        var list = new ArrayList<Role>();
        for (Role role : roleList) {
            for (Role userRole : roles) {
                if (role.getRoleName().equals(userRole.getRoleName())) {
                    list.add(role);
                }
            }
        }
        user.setRoles(list);
        return user;
    }
}