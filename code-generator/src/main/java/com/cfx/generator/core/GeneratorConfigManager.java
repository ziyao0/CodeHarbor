package com.cfx.generator.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.cfx.generator.config.GeneratorConfig;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Eason
 * @since 2023/4/26
 */
public abstract class GeneratorConfigManager {


    public static <T> T getInstance(GeneratorConfig config, Class<T> beanClass) throws InstantiationException {

        Object instance = null;

        if (beanClass.isAssignableFrom(GlobalConfig.Builder.class)) {
            instance = getGlobalConfig(config);
        }
        if (beanClass.isAssignableFrom(DataSourceConfig.Builder.class)) {
            instance = getDataSourceConfig(config);
        }
        if (beanClass.isAssignableFrom(PackageConfig.Builder.class)) {
            instance = getPackageConfig(config);
        }
        if (beanClass.isAssignableFrom(InjectionConfig.Builder.class)) {
            instance = getInjectionConfig(config);
        }
        if (beanClass.isAssignableFrom(TemplateConfig.Builder.class)) {
            instance = getTemplateConfig(config);
        }

        if (null == instance) {
            throw new InstantiationException(beanClass.getName());
        }

        return beanClass.cast(instance);
    }


    private static TemplateConfig.Builder getTemplateConfig(GeneratorConfig config) {
        // 配置模板
        return new TemplateConfig.Builder();
    }

    private static InjectionConfig.Builder getInjectionConfig(GeneratorConfig config) {
        Map<String, Object> map = new HashMap<>();
        map.put("dto", "com.cfx.usercenter.dto");
        return new InjectionConfig.Builder().customMap(map).customFile(CustomFileBuilder.createDto());


    }

    private static PackageConfig.Builder getPackageConfig(GeneratorConfig config) {
        return new PackageConfig.Builder()
                .moduleName(config.getModuleName())
                .parent(config.getParent())
//                .other("model.dto")
                .pathInfo(Collections.singletonMap(OutputFile.xml, config.getProjectDir() + "/src/main/resources/mapper"))
                ;
    }

    private static DataSourceConfig.Builder getDataSourceConfig(GeneratorConfig config) {

        // 数据源配置
        return new DataSourceConfig
                .Builder(config.getUrl(), config.getUserName(), config.getPassword());
    }


    private static GlobalConfig.Builder getGlobalConfig(GeneratorConfig config) {

        return new GlobalConfig.Builder()
                .outputDir(config.getProjectDir() + "/src/main/java")
                .author(config.getAuthor())
                .enableKotlin()
//                .enableSwagger()
                .dateType(DateType.TIME_PACK)
//                .commentDate("yyyy-MM-dd")
//                .ser
                ;

    }

}
