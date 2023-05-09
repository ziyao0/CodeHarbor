package com.cfx.generator.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.cfx.generator.config.GeneratorConfig;

import java.util.*;

/**
 * @author Eason
 * @since 2023/4/26
 */
public abstract class GeneratorConfigManager {


    public static <T> T getInstance(GeneratorConfig config, Class<T> beanClass) throws InstantiationException {

        Object instance = null;

        if (beanClass.isAssignableFrom(GlobalConfig.class)) {
            instance = getGlobalConfig(config);
        }
        if (beanClass.isAssignableFrom(DataSourceConfig.class)) {
            instance = getDataSourceConfig(config);
        }
        if (beanClass.isAssignableFrom(PackageConfig.class)) {
            instance = getPackageConfig(config);
        }
        if (beanClass.isAssignableFrom(InjectionConfig.class)) {
            instance = getInjectionConfig(config);
        }
        if (beanClass.isAssignableFrom(TemplateConfig.class)) {
            instance = getTemplateConfig(config);
        }
        if (beanClass.isAssignableFrom(StrategyConfig.class)) {
            instance = getStrategyConfig(config);
        }
        if (null == instance) {
            throw new InstantiationException(beanClass.getName());
        }

        return beanClass.cast(instance);
    }

    private static StrategyConfig getStrategyConfig(GeneratorConfig config) {

        // 策略配置
        StrategyConfig strategy = new StrategyConfig.Builder


                .setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);

        if (StringUtils.isNotBlank(config.getSuperEntityClass())) {
            strategy.setSuperEntityClass(config.getSuperEntityClass());
        }


        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);


        if (StringUtils.isNotBlank(config.getSuperControllerClass())) {
            strategy.setSuperControllerClass(config.getSuperControllerClass());
        }
        if (StringUtils.isNotBlank(config.getSuperEntityClass())) {
            strategy.setSuperEntityClass(config.getSuperEntityClass());

            // 写于父类中的公共字段
            strategy.setSuperEntityColumns(config.getSuperEntityColumns().split(","));

        }

        // 公共父类
        strategy.setInclude(config.getInclude().split(","));


        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix(config.getModuleName() + "_");

        //
        //逻辑删除字段
        strategy.setLogicDeleteFieldName("DELETED");

        List<TableFill> tableFills = new ArrayList<>();

        TableFill createdBy = new TableFill("CREATED_BY", FieldFill.INSERT);
        TableFill createdAt = new TableFill("CREATED_AT", FieldFill.INSERT);
        TableFill modifiedBy = new TableFill("MODIFIED_BY", FieldFill.UPDATE);
        TableFill modifiedAt = new TableFill("MODIFIED_AT", FieldFill.UPDATE);
        tableFills.add(createdBy);
        tableFills.add(createdAt);
        tableFills.add(modifiedBy);
        tableFills.add(modifiedAt);
        strategy.setTableFillList(tableFills);

        return strategy;

        return new StrategyConfig.Builder().entityBuilder()
//                .
                .addTableFills(new Column("CREATED_BY", FieldFill.INSERT))
                .addTableFills(new Column("CREATED_AT", FieldFill.INSERT))
                .addTableFills(new Column("MODIFIED_BY", FieldFill.UPDATE))
                .addTableFills(new Column("MODIFIED_AT", FieldFill.UPDATE))
                .build();

    }

    private static TemplateConfig getTemplateConfig(GeneratorConfig config) {
        // 配置模板
        return new TemplateConfig.Builder().build();
    }

    private static InjectionConfig getInjectionConfig(GeneratorConfig config) {
        Map<String, Object> map = new HashMap<>();
        map.put("dto", "com.cfx.usercenter.dto");
        return new InjectionConfig.Builder().customMap(map).customFile(CustomFileBuilder.createDto()).build();


    }

    private static PackageConfig getPackageConfig(GeneratorConfig config) {
        return new PackageConfig.Builder()
                .moduleName(config.getModuleName())
                .parent(config.getParent())
//                .other("model.dto")
                .pathInfo(Collections.singletonMap(OutputFile.xml, config.getProjectDir() + "/src/main/resources/mapper"))
                .build();
    }

    private static DataSourceConfig getDataSourceConfig(GeneratorConfig config) {

        // 数据源配置
        return new DataSourceConfig
                .Builder(config.getUrl(), config.getUserName(), config.getPassword()).build();
    }


    private static GlobalConfig getGlobalConfig(GeneratorConfig config) {

        return new GlobalConfig.Builder()
                .outputDir(config.getProjectDir() + "/src/main/java")
                .author(config.getAuthor())
                .enableKotlin()
//                .enableSwagger()
                .dateType(DateType.TIME_PACK)
//                .commentDate("yyyy-MM-dd")
//                .ser
                .build();

    }

}
