package com.ziyao.harbor.gradle.convention;

import com.ziyao.harbor.gradle.management.ManagementConfigurationPlugin;
import com.ziyao.harbor.gradle.optional.OptionalDependenciesPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.plugins.PluginManager;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class RootProjectPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        // 添加maven插件
        PluginManager pluginManager = project.getPluginManager();

        pluginManager.apply(BasePlugin.class);
//        pluginManager.apply(NexusPlugin.class);
//        pluginManager.apply(CopyFilePlugin.class);
        pluginManager.apply(ManagementConfigurationPlugin.class);
        pluginManager.apply(OptionalDependenciesPlugin.class);

        // 添加默认maven仓库
        project.getRepositories().mavenLocal();
        project.getRepositories().mavenCentral();
    }
}
