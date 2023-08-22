package com.ziyao.harbor.generator.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ziyao zhang
 * @since 2023/4/26
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("ID")
    private Integer id;
    /**
     * 创建人id
     */
    @TableField("CREATED_BY")
    private Integer createdBy;

    /**
     * 创建时间
     */
    @TableField("CREATED_AT")
    private LocalDateTime createdAt;

    /**
     * 修改人id
     */
    @TableField("MODIFIED_BY")
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField("MODIFIED_AT")
    private LocalDateTime modifiedAt;

}
