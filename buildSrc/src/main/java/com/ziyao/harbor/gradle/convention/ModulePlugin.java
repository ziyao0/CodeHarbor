package com.ziyao.harbor.gradle.convention;

import com.ziyao.harbor.gradle.GradleConstantPool;
import com.ziyao.harbor.gradle.management.ManagementConfigurationPlugin;
import com.ziyao.harbor.gradle.optional.OptionalDependenciesPlugin;
import com.ziyao.harbor.gradle.project.SpringJavaPlugin;
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

        pluginManager.apply(GradleConstantPool.GRADLE_PLUGIN_library);
        pluginManager.apply(GradleConstantPool.GRADLE_PLUGIN_maven_publish);
        pluginManager.apply(ManagementConfigurationPlugin.class);
        pluginManager.apply(OptionalDependenciesPlugin.class);
        pluginManager.apply(SpringJavaPlugin.class);
    }
}
