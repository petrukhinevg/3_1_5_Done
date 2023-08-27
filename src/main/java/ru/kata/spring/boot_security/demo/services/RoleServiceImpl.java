package ru.kata.spring.boot_security.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @PostConstruct
    @Transactional(rollbackFor=Exception.class)
    public void init() {
        roleDao.save(new Role("ROLE_USER"));
        roleDao.save(new Role("ROLE_ADMIN"));
    }

    @Override
    public List<Role> getRolesList() {
        return roleDao.findAll();
    }

    @Override
    public boolean isRoleInList(Role roleToCheck, List<Role> roleList) {
        for (Role role : roleList) {
            if (role.getRoleName().equals(roleToCheck.getRoleName())) {
                return true;
            }
        }
        return false;
    }
}