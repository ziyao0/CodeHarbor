package com.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cfx.common.dto.EntityDTO;
import com.cfx.usercenter.entity.Menu;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 菜单资源表
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-06
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

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<Menu> initWrapper() {

        return Wrappers.lambdaQuery(Menu.class)
                // 系统id
                .eq(!StringUtils.isEmpty(appId), Menu::getAppId, appId)
                // 资源名称
                .likeRight(!StringUtils.isEmpty(name), Menu::getName, name)
                // 菜单编码
                .likeRight(!StringUtils.isEmpty(code), Menu::getCode, code)
                // 资源URL
                .likeRight(!StringUtils.isEmpty(url), Menu::getUrl, url)
                // 资源图标
                .likeRight(!StringUtils.isEmpty(icon), Menu::getIcon, icon)
                // 上级资源ID
                .eq(!StringUtils.isEmpty(parentId), Menu::getParentId, parentId)
                // 资源级别
                .eq(!StringUtils.isEmpty(level), Menu::getLevel, level)
                // 排序
                .eq(!StringUtils.isEmpty(sort), Menu::getSort, sort)
                // 更新人ID
                .eq(!StringUtils.isEmpty(updatedBy), Menu::getUpdatedBy, updatedBy)
                // 更新时间
                .eq(!StringUtils.isEmpty(updatedAt), Menu::getUpdatedAt, updatedAt)
                // 排序
                .orderByAsc(Menu::getSort)
                ;
    }

    @Override
    public Menu getEntity() {
        return new Menu();
    }
}
