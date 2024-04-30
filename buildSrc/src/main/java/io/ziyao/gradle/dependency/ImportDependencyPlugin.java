package io.ziyao.gradle.dependency;

import io.ziyao.gradle.CommConstants;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;
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

        project.getPluginManager().apply(CommConstants.GRADLE_PLUGIN_platform);

        try {
            File file = new File(project.getRootDir().getPath() + CommConstants.LIBS);

            if (file.exists()) {
                List<String> libs = Files.readAllLines(file.toPath());
                if (!libs.isEmpty()) {
                    libs.stream().distinct().filter(lib -> {
                        // Remove comments and spaces
                        if (lib == null || lib.isEmpty()) return false;
                        return !lib.startsWith(CommConstants.WELL_NUMBER);
                    }).forEach(lib -> {
                        // Add dependencies
                        project.getDependencies().getConstraints().add(CommConstants.GRADLE_API, lib.trim());
                    });
                }
            }
        } catch (Exception e) {
            log.error("Failed to read dependencies file", e);
        }
    }
}