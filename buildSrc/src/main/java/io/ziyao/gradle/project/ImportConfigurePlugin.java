package io.ziyao.gradle.project;

import io.ziyao.gradle.GradleConstantPool;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.Copy;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class ImportConfigurePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        // root project path
        String rootPath = project.getRootDir().getPath();
        // project path
        String destDir = project.getProjectDir().getPath() + GradleConstantPool.TARGET_PATH;
        //注册任务
        project.getTasks().register("importConfig", Copy.class, task -> {

            GradleConstantPool.SOURCES.forEach(source -> {
                // 拷贝文件
                task.from(rootPath + source);
                task.into(destDir);
                System.out.println("copy properties from[" + source + "]into[" + destDir + "]");
            });
        });
    }
}
