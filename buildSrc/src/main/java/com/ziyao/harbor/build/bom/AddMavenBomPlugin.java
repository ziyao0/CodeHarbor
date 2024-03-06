package com.ziyao.harbor.build.bom;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author ziyao zhang
 * @since 2024/3/6
 */
public class AddMavenBomPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
//        project.getConfigurations().all(configuration ->
//                configuration.withDependencies(dependencies ->
//                        dependencies.add(project.getDependencies().create("org.springframework.cloud:spring-cloud-dependencies:${springCloudAlibabaVersion}@pom"))
//                )
//        );
    }
}
