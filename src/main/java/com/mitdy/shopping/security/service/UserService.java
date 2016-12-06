package com.mitdy.shopping.security.service;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;

import com.mitdy.shopping.security.domain.User;

public interface UserService {

    // be careful!!! the write caching should use discreetly, because when updating the any fields, the cache data may not update consistently
//    @CachePut(value = "user", key = "'id_'+#user.getId()")
    public User saveUser(User user);

    public List<User> findAllUsers();
    
    @Cacheable(value = "user", key = "'id_'+#userId") //,key="'user_id_'+#id"
    public User findUserById(Long userId);

    @Cacheable(value = "user", key = "'username_'+#username")
    public User findUserByUsername(String username);
    
    public User findUserByUsernameNotUseCache(String username);
    
}
