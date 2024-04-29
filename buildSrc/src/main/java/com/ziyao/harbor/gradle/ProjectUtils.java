package com.ziyao.harbor.gradle;

import org.gradle.api.Project;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public abstract class ProjectUtils {

    private ProjectUtils() {
    }

    public static String getProjectName(Project project) {
        String projectName = project.getRootProject().getName();
        if (projectName.endsWith("-build")) {
            projectName = projectName.substring(0, projectName.length() - "-build".length());
        }
        return projectName;
    }

    public static boolean isSnapshot(Project project) {
        String projectVersion = projectVersion(project);
        return projectVersion.matches("^.*([.-]BUILD)?-SNAPSHOT$");
    }

    public static boolean isMilestone(Project project) {
        String projectVersion = projectVersion(project);
        return projectVersion.matches("^.*[.-]M\\d+$") || projectVersion.matches("^.*[.-]RC\\d+$");
    }

    public static boolean isRelease(Project project) {
        return !(isSnapshot(project) || isMilestone(project));
    }

    public static String projectVersion(Project project) {
        return String.valueOf(project.getVersion());
    }

    public static String findProperty(String propertyName, Project project) {
        Project rootProject = project.getRootProject();
        Object property = rootProject.findProperty(propertyName);
        if (property != null) {
            System.out.println(property);
            return property.toString();
        }
        return null;
    }
}
