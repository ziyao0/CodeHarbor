package com.ziyao.harbor.gradle.project;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class CopyPropertyFormRootProjectPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        copyPropertyFromRootProjectTo("group", project);
        copyPropertyFromRootProjectTo("version", project);
        copyPropertyFromRootProjectTo("description", project);
    }

    private void copyPropertyFromRootProjectTo(String propertyName, Project project) {
        Project rootProject = project.getRootProject();
        Object property = rootProject.findProperty(propertyName);
        if (property != null) {
            System.out.println(property);
            project.setProperty(propertyName, property);
        }
    }
}
