package com.ziyao.harbor.gradle.dependency;

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

    private static final String LIBS = "dependencies/dependency.libs";

    private static final String Comments = "#";

    @Override
    public void apply(Project project) {
        try {
            File file = new File(LIBS);

            if (file.exists()) {
                List<String> libs = Files.readAllLines(file.toPath());
                for (String lib : libs) {

                    if (lib == null || lib.trim().isEmpty()) continue;

                    // 排除掉注释
                    if (lib.startsWith(Comments)) continue;

                    project.getDependencies().getConstraints().add("api", lib.trim());
                }
            }
        } catch (Exception e) {
            log.error("Failed to read dependencies file", e);
        }
    }
}