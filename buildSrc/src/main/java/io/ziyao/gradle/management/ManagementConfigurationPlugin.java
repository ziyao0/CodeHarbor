package io.ziyao.gradle.management;

import io.ziyao.gradle.CommConstants;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaTestFixturesPlugin;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.VariantVersionMappingStrategy;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class ManagementConfigurationPlugin implements Plugin<Project> {


    @Override
    public void apply(Project project) {
        ConfigurationContainer configurations = project.getConfigurations();
        configurations.create(CommConstants.MANAGEMENT_CONFIGURATION_NAME, (management) -> {
            management.setVisible(false);
            management.setCanBeConsumed(false);
            management.setCanBeResolved(false);

            PluginContainer plugins = project.getPlugins();
            plugins.withType(JavaPlugin.class, (javaPlugin) -> {
                configurations.getByName(JavaPlugin.COMPILE_CLASSPATH_CONFIGURATION_NAME).extendsFrom(management);
                configurations.getByName(JavaPlugin.RUNTIME_CLASSPATH_CONFIGURATION_NAME).extendsFrom(management);
                configurations.getByName(JavaPlugin.ANNOTATION_PROCESSOR_CONFIGURATION_NAME).extendsFrom(management);
                configurations.getByName(JavaPlugin.TEST_COMPILE_CLASSPATH_CONFIGURATION_NAME).extendsFrom(management);
                configurations.getByName(JavaPlugin.TEST_RUNTIME_CLASSPATH_CONFIGURATION_NAME).extendsFrom(management);
                configurations.getByName(JavaPlugin.TEST_ANNOTATION_PROCESSOR_CONFIGURATION_NAME).extendsFrom(management);
            });
            plugins.withType(JavaTestFixturesPlugin.class, (javaTestFixturesPlugin) -> {
                configurations.getByName("testFixturesCompileClasspath").extendsFrom(management);
                configurations.getByName("testFixturesRuntimeClasspath").extendsFrom(management);
            });
            plugins.withType(MavenPublishPlugin.class, (mavenPublish) -> {
                PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);
                publishing.getPublications().withType(MavenPublication.class, (mavenPublication) ->
                        mavenPublication.versionMapping((versions) ->
                                versions.allVariants(VariantVersionMappingStrategy::fromResolutionResult)));
            });
        });
    }
}