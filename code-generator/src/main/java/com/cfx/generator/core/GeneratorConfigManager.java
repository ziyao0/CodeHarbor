package com.cfx.generator.core;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.cfx.generator.config.GeneratorConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (beanClass.isAssignableFrom(FileOutConfig.class)) {
            instance = getFileOutConfig(config);
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
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
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
    }

    private static TemplateConfig getTemplateConfig(GeneratorConfig config) {
        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
//        templateConfig.setEntity(config.getEntity());
//        templateConfig.setService(config.getService());
        templateConfig.setController(config.getController());

        return templateConfig;
    }

    private static FileOutConfig getFileOutConfig(GeneratorConfig config) {
        // 如果模板引擎是 freemarker
        return new FileOutConfig(config.getTemplatePath()) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return config.getProjectDir() + "/src/main/resources/mapper/" + config.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        };
    }

    private static InjectionConfig getInjectionConfig(GeneratorConfig config) {
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
                Map<String, Object> map = new HashMap<>();
                map.put("dto", "com.cfx.usercenter.dto");
                this.setMap(map);
            }
        };
        // 设置mapper路径
        FileOutConfig fileOutConfig = getFileOutConfig(config);
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(fileOutConfig);
        injectionConfig.setFileOutConfigList(focList);

        return injectionConfig;
    }

    private static PackageConfig getPackageConfig(GeneratorConfig config) {

        PackageConfig pc = new PackageConfig();
        pc.setModuleName(config.getModuleName());
        pc.setParent(config.getParent());
        return pc;
    }

    private static DataSourceConfig getDataSourceConfig(GeneratorConfig config) {

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(config.getUrl());
        // dsc.setSchemaName("public");
        dsc.setDriverName(config.getDriverName());
        dsc.setUsername(config.getUserName());
        dsc.setPassword(config.getPassword());
        return dsc;
    }


    private static GlobalConfig getGlobalConfig(GeneratorConfig config) {
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(config.getProjectDir() + "/src/main/java");
        gc.setAuthor(config.getAuthor());
        gc.setOpen(config.isOpen());
        gc.setServiceName(config.getServiceName());
        return gc;
    }

}
