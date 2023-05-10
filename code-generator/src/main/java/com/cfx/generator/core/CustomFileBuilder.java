package com.cfx.generator.core;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */
public abstract class CustomFileBuilder {


    public static CustomFile createDto(String fileName, String templatePath) {
        return new CustomFile.Builder().fileName(fileName)
                .templatePath(templatePath)
                .build();

    }
}
