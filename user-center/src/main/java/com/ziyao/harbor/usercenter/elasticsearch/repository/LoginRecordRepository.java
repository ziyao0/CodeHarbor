package com.ziyao.harbor.usercenter.elasticsearch.repository;

import com.ziyao.harbor.elasticsearch.repository.ElasticsearchRepository;
import com.ziyao.harbor.usercenter.elasticsearch.entity.LoginRecord;
import org.springframework.stereotype.Repository;

/**
 * @author ziyao zhang
 * @since 2023/12/25
 */
@Repository
public interface LoginRecordRepository extends ElasticsearchRepository<LoginRecord, Long> {
}
