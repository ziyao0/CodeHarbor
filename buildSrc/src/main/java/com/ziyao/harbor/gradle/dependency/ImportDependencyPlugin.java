package com.ziyao.harbor.gradle.dependency;

import com.ziyao.harbor.gradle.GradleConstantPool;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class ImportDependencyPlugin implements Plugin<Project> {

    private static final Logger log = LoggerFactory.getLogger(ImportDependencyPlugin.class);

    @Override
    public void apply(Project project) {

        project.getPluginManager().apply(GradleConstantPool.GRADLE_PLUGIN_platform);

        try {
            File file = new File(GradleConstantPool.LIBS);

            if (file.exists()) {
                List<String> libs = Files.readAllLines(file.toPath());
                if (!libs.isEmpty()) {
                    libs.stream().distinct().filter(lib -> {
                        // 去掉空值和注释
                        if (lib == null || lib.isEmpty()) return false;
                        return !lib.startsWith(GradleConstantPool.WELL_NUMBER);
                    }).forEach(lib -> {
                        // 添加依赖
                        project.getDependencies().getConstraints()
                                .add(GradleConstantPool.GRADLE_API, lib.trim());
                    });
                }
            }
        } catch (Exception e) {
            log.error("Failed to read dependencies file", e);
        }
    }
}