package com.cfx.usercenter.dto;

import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * <p>
 *菜单资源表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-05
 */
@Data
public class MenuDTO implements EntityDTO<Menu>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private Integer id;
    /**
     * 系统id
     */
    private Integer appId;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 菜单编码
     */
    private String code;
    /**
     * 资源URL
     */
    private String url;
    /**
     * 资源图标
     */
    private String icon;
    /**
     * 上级资源ID
     */
    private Integer parentId;
    /**
     * 资源级别
     */
    private Integer level;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建人ID
     */
    private Integer createdBy;
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 更新人ID
     */
    private Integer updatedBy;
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;

    @Override
    public Menu getEntity() {
        return new Menu();
    }
}
