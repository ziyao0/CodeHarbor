package com.ziyao.harbor.generator.config;

import com.baomidou.mybatisplus.generator.config.rules.DateType;
import lombok.Data;

/**
 * @author ziyao zhang
 * @since 2023/4/26
 */
@Data
public class GeneratorConfig {
    /**
     * 生成文件的输出目录【默认 D 盘根目录】
     */
    private String projectDir;
    /**
     * 是否覆盖已有文件
     */
    private boolean fileOverride = false;
    /**
     * 开发人员
     */
    private String author = "zhangziyao";

    private boolean open = false;

    /**
     * 是否在xml中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 开启 ActiveRecord 模式
     */
    private boolean activeRecord = false;

    /**
     * 开启 BaseResultMap
     */
    private boolean baseResultMap = false;

    /**
     * 时间类型对应策略
     */
    private DateType dateType = DateType.TIME_PACK;
    /**
     * 开启 baseColumnList
     */
    private boolean baseColumnList = false;

    private String serviceName = "%sService";


    private String userName;

    private String url;

    private String password;

    //PackageConfig

    private String moduleName;
    private String parent;


    // StrategyConfig

    private String superEntityClass;
    private String superControllerClass;
    /**
     * 表名，多个英文逗号分割
     */
    private String include;

    private String superEntityColumns;

}
