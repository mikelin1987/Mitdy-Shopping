package com.mitdy.shopping.security.web.dubbo;

import com.mitdy.shopping.security.domain.User;
import com.mitdy.shopping.security.service.UserService;

//@Component
// @Service(version = "1.0")
public class UserServiceApiImpl implements UserServiceApi {

    // @Autowired
    private UserService userService;

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userService.findUserByUsername(username);

        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setUsername(user.getUsername());
            userDTO.setNickname(user.getNickname());
            userDTO.setStatus(user.getStatus().toString());

            return userDTO;
        } else {
            return null;
        }
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
