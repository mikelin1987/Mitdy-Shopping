package com.mitdy.shopping.security.persistence.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mitdy.core.persistence.JpaAbstractEntityDao;
import com.mitdy.shopping.security.domain.User;
import com.mitdy.shopping.security.persistence.UserDao;

@Transactional
@Repository("userDao")
public class UserDaoImpl extends JpaAbstractEntityDao<User> implements UserDao {

}
