package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.common.dto.EntityDTO;
import com.ziyao.harbor.common.utils.Strings;
import com.ziyao.harbor.usercenter.entity.Menu;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>
 * 菜单资源表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
public class MenuDTO implements EntityDTO<Menu>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 资源ID
     */
    private Long id;
    /**
     * 系统id
     */
    private Long appId;
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
    private Long parentId;
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

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<Menu> initWrapper() {

        return Wrappers.lambdaQuery(Menu.class)
                // 系统id
                .eq(Objects.nonNull(appId), Menu::getAppId, appId)
                // 资源名称
                .likeRight(Strings.hasLength(name), Menu::getName, name)
                // 菜单编码
                .likeRight(Strings.hasLength(code), Menu::getCode, code)
                // 资源URL
                .likeRight(Strings.hasLength(url), Menu::getUrl, url)
                // 资源图标
                .likeRight(Strings.hasLength(icon), Menu::getIcon, icon)
                // 上级资源ID
                .eq(Objects.nonNull(parentId), Menu::getParentId, parentId)
                // 资源级别
                .eq(Objects.nonNull(level), Menu::getLevel, level)
                // 排序
                .eq(Objects.nonNull(sort), Menu::getSort, sort)
                // 更新人ID
                .eq(Objects.nonNull(updatedBy), Menu::getUpdatedBy, updatedBy)
                // 更新时间
                .eq(Objects.nonNull(updatedAt), Menu::getUpdatedAt, updatedAt)
                // 排序
                .orderByAsc(Menu::getSort)
                ;
    }

    @Override
    public Menu getEntity() {
        return new Menu();
    }
}
