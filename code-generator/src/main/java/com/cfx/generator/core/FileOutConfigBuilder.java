package com.cfx.generator.core;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.cfx.generator.config.GeneratorConfig;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class FileOutConfigBuilder {


    public List<FileOutConfig> getFileOutConfigs(GeneratorConfig config) {
        // 创建dto
        FileOutConfig dtoFoc = createDto(config);
        FileOutConfig mapperXmlFoc = createMapperXml(config);
        return Lists.newArrayList(dtoFoc,mapperXmlFoc);
    }

    private FileOutConfig createDto(GeneratorConfig generatorConfig) {
        return new FileOutConfig("templates/entityDTO.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                System.out.println(tableInfo.getEntityPath());
                return generatorConfig.getProjectDir() + "/src/main/java/com/cfx/" + generatorConfig.getModuleName()
                        + "/dto/" + tableInfo.getEntityName() + "DTO" + StringPool.DOT_JAVA;
            }
        };
    }private FileOutConfig createMapperXml(GeneratorConfig config) {
        return new FileOutConfig("/templates/mapper.xml.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return config.getProjectDir() + "/src/main/resources/mapper/" + config.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        };
    }
}
