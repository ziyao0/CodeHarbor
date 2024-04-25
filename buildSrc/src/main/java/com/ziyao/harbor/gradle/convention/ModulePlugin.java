package com.ziyao.harbor.gradle.convention;

import com.ziyao.harbor.gradle.management.ManagementConfigurationPlugin;
import com.ziyao.harbor.gradle.optional.OptionalDependenciesPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginManager;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class ModulePlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        PluginManager pluginManager = project.getPluginManager();

        pluginManager.apply("java-library");
        pluginManager.apply("maven-publish");
        pluginManager.apply(ManagementConfigurationPlugin.class);
        pluginManager.apply(OptionalDependenciesPlugin.class);

        project.getConfigurations()
                .configureEach(
                        configuration -> configuration.setTransitive(true)
                );

    }
}
