package com.ziyao.harbor.gradle.project;

import com.ziyao.harbor.gradle.maven.NexusPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class SpringJavaPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        project.getPlugins().apply(CopyPropertyFormRootProjectPlugin.class);
//        project.getPlugins().apply(NexusPlugin.class);

        // 设置依赖传递
        project.getConfigurations().configureEach(
                configuration -> configuration.setTransitive(true)
        );

        // 设置字符集编码
        project.getTasks().withType(
                JavaCompile.class, javaCompile -> {
                    Object property = project.findProperty("encoding");
                    javaCompile.getOptions().setEncoding(String.valueOf(property));

                });
    }
}
