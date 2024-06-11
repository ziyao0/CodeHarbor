package com.ziyao.harbor.usercenter.repository.jpa;

import com.ziyao.harbor.usercenter.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author ziyao
 * @since 2024/06/11 16:22:07
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {


    @Query("select ur.role_code from user_role ur join user u on ur.user_id = u.id where u.id=:userId")
    Set<String> findRolesByUserId(Long userId);

}
