package com.mitdy.shopping.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.domain.User;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends JpaAbstractEntityDao<User> implements UserDao {

}
