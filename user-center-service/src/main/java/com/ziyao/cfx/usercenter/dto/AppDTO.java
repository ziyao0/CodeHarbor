package com.ziyao.cfx.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.cfx.common.dto.EntityDTO;
import com.ziyao.cfx.usercenter.entity.App;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 应用系统
 * </p>
 *
 * @author zhangziyao
 * @since 2023-05-09
 */
@Data
public class AppDTO implements EntityDTO<App>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;
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

    /**
     * 组装查询条件，可根据具体情况做出修改
     *
     * @see LambdaQueryWrapper
     */
    public LambdaQueryWrapper<App> initWrapper() {

        return Wrappers.lambdaQuery(App.class)
                // 系统名称
                .likeRight(StringUtils.hasLength(appName), App::getAppName, appName)
                // 系统访问路径
                .likeRight(StringUtils.hasLength(url), App::getUrl, url)
                // 系统介绍
                .likeRight(StringUtils.hasLength(introduce), App::getIntroduce, introduce)
                // 删除状态 0正常 1 删除
                .eq(!ObjectUtils.isEmpty(deleted), App::getDeleted, deleted)
                ;
    }

    @Override
    public App getEntity() {
        return new App();
    }
}
