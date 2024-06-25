package com.ziyao.harbor.usercenter.repository.jpa;

import com.ziyao.harbor.usercenter.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ziyao
 * @since 2024/06/08 16:45:31
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
