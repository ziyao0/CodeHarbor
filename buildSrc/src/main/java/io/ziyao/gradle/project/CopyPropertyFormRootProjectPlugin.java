package io.ziyao.gradle.project;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class CopyPropertyFormRootProjectPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        Project rootProject = project.getRootProject();
        copyPropertyFromRootProjectTo("group", rootProject);
        copyPropertyFromRootProjectTo("version", rootProject);
        copyPropertyFromRootProjectTo("description", rootProject);
    }

    private void copyPropertyFromRootProjectTo(String propertyName, Project project) {

        Object property = project.findProperty(propertyName);
        if (property != null) {
            project.setProperty(propertyName, property);
        }
    }
}
