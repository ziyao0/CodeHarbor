package com.ziyao.harbor.usercenter.dto;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.usercenter.entity.App;
import com.ziyao.harbor.web.orm.EntityDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

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
                .likeRight(Strings.hasLength(appName), App::getAppName, appName)
                // 系统访问路径
                .likeRight(Strings.hasLength(url), App::getUrl, url)
                // 系统介绍
                .likeRight(Strings.hasLength(introduce), App::getIntroduce, introduce)
                // 删除状态 0正常 1 删除
                .eq(Objects.nonNull(deleted), App::getDeleted, deleted)
                ;
    }

    @Override
    public App getEntity() {
        return new App();
    }
}
