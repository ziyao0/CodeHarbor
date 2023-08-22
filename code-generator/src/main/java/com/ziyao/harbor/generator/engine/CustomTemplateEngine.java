package com.ziyao.harbor.generator.engine;

import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author ziyao zhang
 * @since 2023/5/9
 */
public class CustomTemplateEngine extends FreemarkerTemplateEngine {

    @Override
    protected void outputCustomFile(@NotNull List<CustomFile> customFiles, @NotNull TableInfo tableInfo, @NotNull Map<String, Object> objectMap) {

        String pathInfo = getPathInfo(OutputFile.entity);
        assert pathInfo != null;
        String dtoPath = pathInfo.replace("entity", "dto");

        String entityName = tableInfo.getEntityName();
        customFiles.forEach((customFile) -> {
            String dtoFile = String.format((dtoPath + File.separator + "%s" + customFile.getFileName()), entityName);
            this.outputFile(new File(dtoFile), objectMap, customFile.getTemplatePath(), customFile.isFileOverride());
        });


    }
}
