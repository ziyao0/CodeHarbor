package com.cfx.generator.engine;

import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Eason
 * @since 2023/5/9
 */
public class EnhanceFreemarkerTemplateEngine extends FreemarkerTemplateEngine {

    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFiles, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {
        String entityName = tableInfo.getEntityName();
        customFiles.forEach((customFile) -> {
            String fileName = String.format(customFile.getFilePath() + entityName + customFile.getFileName());
            this.outputFile(new File(fileName), objectMap, customFile.getTemplatePath(), false);
        });


    }
}
