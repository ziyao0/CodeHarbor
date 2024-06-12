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


    @Query("select roleId from user_role where userId=:userId")
    Set<String> findByUserId(Long userId);

}
