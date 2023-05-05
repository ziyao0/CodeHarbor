package com.cfx.generator;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.cfx.generator.config.GeneratorConfig;
import com.cfx.generator.core.GeneratorConfigManager;

import java.util.List;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public class CodeGenerator {

    public static void main(String[] args) throws InstantiationException {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        GeneratorConfig generatorConfig = generatorConfig();
        // 全局配置
        GlobalConfig gc = GeneratorConfigManager.getInstance(generatorConfig, GlobalConfig.class);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = GeneratorConfigManager.getInstance(generatorConfig, DataSourceConfig.class);
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = GeneratorConfigManager.getInstance(generatorConfig, PackageConfig.class);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = GeneratorConfigManager.getInstance(generatorConfig, InjectionConfig.class);

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 允许生成模板文件
                return true;
            }
        });


        FileOutConfig etConfig = new FileOutConfig("templates/entityDTO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return generatorConfig.getProjectDir() + "/src/main/java/com/cfx/" + generatorConfig.getModuleName()
                        + "/dto/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        };
        List<FileOutConfig> list = cfg.getFileOutConfigList();
        list.add(etConfig);
        cfg.setFileOutConfigList(list);

        mpg.setCfg(cfg);

        // 配置模板
//        TemplateConfig templateConfig = GeneratorConfigManager.getInstance(generatorConfig, TemplateConfig.class);
//
//        templateConfig.setXml(null);
//        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = GeneratorConfigManager.getInstance(generatorConfig, StrategyConfig.class);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


    private static GeneratorConfig generatorConfig() {
        GeneratorConfig gc = new GeneratorConfig();

        gc.setServiceName("%sService");

        gc.setUrl("jdbc:mysql://192.168.206.200:33306/cfx?useUnicode=true&useSSL=false&characterEncoding=utf8");
        gc.setDriverName("com.mysql.cj.jdbc.Driver");
        gc.setUserName("root");
        gc.setPassword("1qaz@WSX");

        gc.setModuleName("usercenter");

        gc.setParent("com.cfx");

//        gc.setSuperEntityClass("com.cfx.web.orm.BaseEntity");
//
//        gc.setSuperEntityColumns("id,CREATED_BY,CREATED_AT,MODIFIED_BY,MODIFIED_AT");

        gc.setSuperControllerClass("com.cfx.web.mvc.BaseController");
//        gc.setInclude("app");
        gc.setInclude("app,user,department,menu,role,user_role,role_menu");

        gc.setProjectDir(System.getProperty("user.dir") + "/user-center-service");


        return gc;
    }
}
