package com.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.Menu;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

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
                .eq(!ObjectUtils.isEmpty(appId), Menu::getAppId, appId)
                // 资源名称
                .likeRight(StringUtils.hasLength(name), Menu::getName, name)
                // 菜单编码
                .likeRight(StringUtils.hasLength(code), Menu::getCode, code)
                // 资源URL
                .likeRight(StringUtils.hasLength(url), Menu::getUrl, url)
                // 资源图标
                .likeRight(StringUtils.hasLength(icon), Menu::getIcon, icon)
                // 上级资源ID
                .eq(!ObjectUtils.isEmpty(parentId), Menu::getParentId, parentId)
                // 资源级别
                .eq(!ObjectUtils.isEmpty(level), Menu::getLevel, level)
                // 排序
                .eq(!ObjectUtils.isEmpty(sort), Menu::getSort, sort)
                // 更新人ID
                .eq(!ObjectUtils.isEmpty(updatedBy), Menu::getUpdatedBy, updatedBy)
                // 更新时间
                .eq(!ObjectUtils.isEmpty(updatedAt), Menu::getUpdatedAt, updatedAt)
                // 排序
                .orderByAsc(Menu::getSort)
                ;
    }

    @Override
    public Menu getEntity() {
        return new Menu();
    }
}
