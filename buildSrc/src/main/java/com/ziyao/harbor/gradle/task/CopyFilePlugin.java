package com.ziyao.harbor.gradle.task;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2024/3/6
 */
public class CopyFilePlugin implements Plugin<Project> {

    // 需要拷贝的项目资源路径
    private static final String SUBPROJECTS_SUFFIX = "/resources/subprojects.txt";
    // 源文件资源路径
    private static final List<String> SOURCE_FILES = List.of("resources/config/bootstrap-register.yml");
    // 目标资源路径
    private static final String TARGET_PATH = "/src/main/resources";

    @Override
    public void apply(Project project) {
        String input = project.getRootDir().getPath() + SUBPROJECTS_SUFFIX;
        project.getTasks().register("copyFile", Copy.class, task -> {
            File inputFile = new File(input);
            if (inputFile.exists()) {
                try {
                    List<String> subprojects = new ArrayList<>(Files.readAllLines(inputFile.toPath()));
                    // 拷贝文件
                    subprojects.forEach(dir -> SOURCE_FILES.forEach(sourceFile -> {
                        String destDir = dir + TARGET_PATH;
                        task.from(sourceFile);
                        task.into(destDir);
                        System.out.println("copy properties from【" + sourceFile + "】into【" + destDir + "】");
                    }));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to read subprojects file", e);
                }
            }
        });
    }
}
