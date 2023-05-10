package com.cfx.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.cfx.generator.config.GeneratorConfig;
import com.cfx.generator.core.CustomFileBuilder;
import com.cfx.generator.engine.CustomTemplateEngine;
import com.google.common.collect.Lists;

import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public class CodeGenerator {

    public static void main(String[] args) {

        GeneratorConfig config = generatorConfig();
        // 代码生成器
        FastAutoGenerator.create(
                        new DataSourceConfig.Builder(config.getUrl(), config.getUserName(), config.getPassword())
                )
                // 全局配置
                .globalConfig(builder -> builder
                        .outputDir(config.getProjectDir() + "/src/main/java")
                        .author("zhangziyao")
                        .dateType(DateType.TIME_PACK)
                )
                // 数据库配置
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                // 包配置
                .packageConfig(builder -> {
                    builder.moduleName(config.getModuleName())// 设置父包模块名
                            .parent(config.getParent()) // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, config.getProjectDir() + "/src/main/resources/mapper"));// 设置mapperXml生成路径
                })

                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(config.getInclude().split(","))
                            // 实体类相关策略
                            .entityBuilder()
                            .enableFileOverride()
                            .addTableFills(new Column("CREATED_BY", FieldFill.INSERT))
                            .addTableFills(new Column("CREATED_AT", FieldFill.INSERT))
                            .addTableFills(new Column("MODIFIED_BY", FieldFill.UPDATE))
                            .addTableFills(new Column("MODIFIED_AT", FieldFill.UPDATE))
                            //逻辑删除字段
                            .logicDeleteColumnName("DELETED")
                            .enableLombok()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            // controller 相关策略
                            .controllerBuilder()
                            .superClass(config.getSuperControllerClass())
                            .enableRestStyle()
                            // service 相关策略
                            .serviceBuilder()
                            .formatServiceFileName("%sService");
                })
                // 自定义配置 可以生成自定义文件
                .injectionConfig(builder -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("dto", "com.cfx.usercenter.dto");
                    builder.customMap(map)
                            .customFile(Lists.newArrayList(
                                    CustomFileBuilder.createDto("DTO" + StringPool.DOT_JAVA, "templates/entityDTO.java.ftl"))
                            );
                })

                .templateEngine(new CustomTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    private static GeneratorConfig generatorConfig() {
        GeneratorConfig gc = new GeneratorConfig();


//        gc.setUrl("jdbc:mysql://localhost:3306/cfx");
        gc.setUrl("jdbc:mysql://192.168.206.200:33306/cfx?useUnicode=true&useSSL=false&characterEncoding=utf8");
        gc.setUserName("root");
        gc.setPassword("1qaz@WSX");

        gc.setModuleName("usercenter");

        gc.setParent("com.cfx");

//        gc.setSuperEntityClass("com.cfx.web.orm.BaseEntity");
//
//        gc.setSuperEntityColumns("id,CREATED_BY,CREATED_AT,MODIFIED_BY,MODIFIED_AT");

        gc.setSuperControllerClass("com.cfx.web.mvc.BaseController");
        gc.setInclude("app");
//        gc.setInclude("app,user,department,menu,role,user_role,role_menu");

        gc.setProjectDir(System.getProperty("user.dir") + "/user-center-service");


        return gc;
    }
}
