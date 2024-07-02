package com.ziyao.harbor.data.redis.repository;

import com.ziyao.harbor.data.redis.core.RedisHashEntity;
import com.ziyao.harbor.data.redis.core.RedisRepository;

import java.util.Map;
import java.util.Optional;

/**
 * @author ziyao
 * @since 2024/06/25 09:10:50
 */
public interface RedisHashRepository<T extends RedisHashEntity, ID> extends RedisRepository<T, ID> {

    /**
     * 通过ID 获取存储的hash数据
     */
    Optional<Map<Object, Object>> findById(ID id);

    /**
     * 通过hash key获取数据
     *
     * @param id      redis key
     * @param hashKey hash 键
     * @return 返回 hash 值
     */
    Optional<Object> findByHashKey(ID id, Object hashKey);

    /**
     * 通过hk删除
     */
    Long deleteByHashKey(String id, Object... hashKey);

    /**
     * 保存
     */
    void save(Object hashKey, Object hashValue);

    /**
     * 保存全部
     */
    void saveAll(Map<Object, Object> map);
}
