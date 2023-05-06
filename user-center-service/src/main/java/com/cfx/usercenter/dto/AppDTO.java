package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.App;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *应用系统
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
 */
@Data
public class AppDTO implements EntityDTO<App>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;
    /**
     * 系统名称
     */
    private String appName;
    /**
     * 系统访问路径
     */
    private String url;
    /**
     * 系统介绍
     */
    private String introduce;
    /**
     * 删除状态 0正常 1 删除
     */
    private Integer deleted;
    /**
     * 创建人id
     */
    private Integer createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 修改人id
     */
    private Integer modifiedBy;
    /**
     * 修改时间
     */
    private LocalDateTime modifiedAt;

    @Override
    public App getEntity() {
        return new App();
    }
}
