package com.ziyao.harbor.usercenter.repository.redis2;

import com.ziyao.harbor.usercenter.entity.Application;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ziyao
 * @see <a href="https://blog.zziyao.cn">https://blog.zziyao.cn</a>
 * @since 2024/07/02 15:00:41
 */
@Repository
public interface ApplicationRepositoryRedis extends KeyValueRepository<Application, Long> {
}
