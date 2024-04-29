package io.ziyao.gradle.optional;


import io.ziyao.gradle.GradleConstantPool;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSetContainer;

/**
 * @author ziyao zhang
 * @since 2024/3/4
 */
public class OptionalDependenciesPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        Configuration optional = project.getConfigurations()
                .create(GradleConstantPool.OPTIONAL_CONFIGURATION_NAME);

        optional.setCanBeConsumed(false);
        optional.setCanBeResolved(false);
        project.getPlugins().withType(JavaPlugin.class, (javaPlugin) -> {
            SourceSetContainer sourceSets = project.getExtensions()
                    .getByType(JavaPluginExtension.class)
                    .getSourceSets();
            sourceSets.all((sourceSet) -> {
                project.getConfigurations()
                        .getByName(sourceSet.getCompileClasspathConfigurationName())
                        .extendsFrom(optional);
                project.getConfigurations()
                        .getByName(sourceSet.getRuntimeClasspathConfigurationName())
                        .extendsFrom(optional);
            });
        });
    }

}

