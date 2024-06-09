package com.ziyao.harbor.usercenter.repository.jpa;

import com.ziyao.harbor.usercenter.entity.Authorization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/08 11:00:40
 */
@Repository
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

    Optional<Authorization> findByState(Integer state);

    Optional<Authorization> findByAuthorizationCodeValue(String authorizationCode);

    Optional<Authorization> findByAccessTokenValue(String accessToken);

    Optional<Authorization> findByRefreshTokenValue(String refreshToken);
}
