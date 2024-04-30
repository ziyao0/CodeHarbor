package com.ziyao.harbor.web.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.LocalDateTime;

/**
 * 处理自动填充字段
 *
 * @author ziyao zhang
 * @since 2023/5/5
 */
public class MetaFillHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        // 新增时自动填充
        this.strictInsertFill(metaObject, "createdBy", Integer.class, 1);
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime::now, LocalDateTime.class);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 修改时自动填充
        this.strictInsertFill(metaObject, "modifiedBy", Integer.class, 1);
        this.strictInsertFill(metaObject, "modifiedAt", LocalDateTime::now, LocalDateTime.class);
    }
}
