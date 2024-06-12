package com.ziyao.harbor.usercenter.service.user;

import com.ziyao.harbor.usercenter.authentication.core.SimpleUser;
import com.ziyao.harbor.usercenter.authentication.core.UserDetails;
import com.ziyao.harbor.usercenter.authentication.core.authority.SimpleUserAuthority;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.repository.jpa.UserRepository;
import com.ziyao.harbor.usercenter.repository.jpa.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/11 15:55:17
 */
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public JpaUserDetailsService(UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> toObject(user, userRoleRepository.findByUserId(user.getId())))
                .orElse(null);
    }


    private SimpleUser toObject(User user, Set<String> roles) {
        return SimpleUser.from(user)
                .authorities(
                        authorities -> roles.forEach(
                                role -> authorities.add(new SimpleUserAuthority(role))
                        )
                )
                .build();
    }
}
