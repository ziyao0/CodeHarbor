package com.cfx.generator.core;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class CustomFileBuilder {


    public static CustomFile createDto() {

        return new CustomFile.Builder().fileName("DTO" + StringPool.DOT_JAVA)
//                .filePath(generatorConfig.getProjectDir() + "/src/main/java/com/cfx/" + generatorConfig.getModuleName() + "/dto/")
                .templatePath("templates/entityDTO.java.ftl").build();

    }
}
