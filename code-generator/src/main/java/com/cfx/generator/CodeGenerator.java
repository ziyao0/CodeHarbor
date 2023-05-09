package com.cfx.generator;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.cfx.generator.config.GeneratorConfig;
import com.cfx.generator.core.GeneratorConfigManager;

import java.sql.Types;
import java.util.Collections;
import java.util.function.Consumer;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public class CodeGenerator {

    public static void main(String[] args) throws InstantiationException {

        GeneratorConfig config = generatorConfig();
        // 代码生成器
        FastAutoGenerator.create(GeneratorConfigManager.getInstance(config, DataSourceConfig.Builder.class))
                .globalConfig(builder -> builder
                        .outputDir(config.getProjectDir() + "/src/main/java")
                        .author(config.getAuthor())
                        .enableKotlin()
//                .enableSwagger()
                        .dateType(DateType.TIME_PACK))
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }

                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.moduleName(config.getModuleName())// 设置父包模块名
                            .parent(config.getParent()) // 设置父包名
//                .other("model.dto")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, config.getProjectDir() + "/src/main/resources/mapper"))// 设置mapperXml生成路径
                    ;
                })
                .strategyConfig(builder -> {
                    builder.addInclude(config.getInclude().split(","))
                            .entityBuilder()
                            .addTableFills(new Column("CREATED_BY", FieldFill.INSERT))
                            .addTableFills(new Column("CREATED_AT", FieldFill.INSERT))
                            .addTableFills(new Column("MODIFIED_BY", FieldFill.UPDATE))
                            .addTableFills(new Column("MODIFIED_AT", FieldFill.UPDATE))
                            //逻辑删除字段
                            .logicDeleteColumnName("DELETED")
                            .enableLombok()
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .controllerBuilder()
                            .superClass(config.getSuperControllerClass())
                            .enableRestStyle();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }


    private static GeneratorConfig generatorConfig() {
        GeneratorConfig gc = new GeneratorConfig();

        gc.setServiceName("%sService");

//        gc.setUrl("jdbc:mysql://localhost:3306/cfx");
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
//        gc.setInclude("role_menu");
        gc.setInclude("app,user,department,menu,role,user_role,role_menu");

        gc.setProjectDir(System.getProperty("user.dir") + "/user-center-service");


        return gc;
    }
}
