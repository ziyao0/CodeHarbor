package com.ziyao.harbor.usercenter.service.user;

import com.ziyao.harbor.usercenter.authentication.core.SimpleGrantedAuthority;
import com.ziyao.harbor.usercenter.authentication.core.UserInfo;
import com.ziyao.harbor.usercenter.entity.User;
import com.ziyao.harbor.usercenter.repository.jpa.UserRepository;
import com.ziyao.harbor.usercenter.repository.jpa.UserRoleRepository;
import com.ziyao.security.oauth2.core.UserDetails;
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


    private UserInfo toObject(User user, Set<String> roles) {
        return UserInfo.from(user)
                .authorities(
                        authorities -> roles.forEach(
                                role -> authorities.add(new SimpleGrantedAuthority(role))
                        )
                )
                .build();
    }
}
