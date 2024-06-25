package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.Repository;

import java.util.Map;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 09:10:50
 */
public interface HashRepository<HK, HV> extends Repository {

    /**
     * 通过ID 获取存储的hash数据
     */
    Optional<Map<HK, HV>> findById(String id);

    /**
     * 通过hash key获取数据
     *
     * @param id      redis key
     * @param hashKey hash 键
     * @return 返回 hash 值
     */
    Optional<HV> findByHK(String id, Object hashKey);

    /**
     * 通过hk删除
     */
    Long deleteByHK(String id, Object... hashKey);

    /**
     * 保存
     */
    void save(String id, HK hashKey, HV hashValue);

    /**
     * 保存全部
     */
    void saveAll(String id, Map<HK, HV> map);
}
