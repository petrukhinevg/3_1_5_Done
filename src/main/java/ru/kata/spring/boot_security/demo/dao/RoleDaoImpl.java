package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAll() {
        return entityManager.createQuery("FROM Role", Role.class).getResultList();
    }

    @Override
    @Transactional
    public void save(Role role) {
        // Проверяем наличие роли с таким же именем
        Role existingRole = entityManager
                .createQuery("SELECT r FROM Role r WHERE r.roleName = :name", Role.class)
                .setParameter("name", role.getRoleName())
                .setMaxResults(1)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (existingRole == null) {
            // Роли с таким именем еще нет, добавляем новую
            entityManager.persist(role);
        }
    }

    @Override
    public Role findById(Long id) {
        return null;
    }
}
