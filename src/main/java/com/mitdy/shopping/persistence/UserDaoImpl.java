package com.mitdy.shopping.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.shopping.domain.User;

@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public User save(User user) {
        return em.merge(user);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<User> findAll() {
        return em.createQuery("select e from User e").getResultList();
    }

}
