package io.ziyao.gradle.convention;

import io.ziyao.gradle.CommConstants;
import io.ziyao.gradle.management.ManagementConfigurationPlugin;
import io.ziyao.gradle.maven.NexusPublishingPlugin;
import io.ziyao.gradle.optional.OptionalDependenciesPlugin;
import io.ziyao.gradle.project.SpringJavaPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.PluginManager;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class ModuleProjectPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        PluginManager pluginManager = project.getPluginManager();

        pluginManager.apply(CommConstants.GRADLE_PLUGIN_library);
        pluginManager.apply(CommConstants.GRADLE_PLUGIN_maven_publish);
        pluginManager.apply(SpringJavaPlugin.class);
        pluginManager.apply(ManagementConfigurationPlugin.class);
        pluginManager.apply(OptionalDependenciesPlugin.class);
        pluginManager.apply(NexusPublishingPlugin.class);
    }
}
