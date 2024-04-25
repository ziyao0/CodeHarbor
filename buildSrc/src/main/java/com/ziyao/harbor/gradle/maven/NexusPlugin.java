package com.ziyao.harbor.gradle.maven;

import io.github.gradlenexus.publishplugin.NexusPublishExtension;
import io.github.gradlenexus.publishplugin.NexusPublishPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.net.URI;
import java.time.Duration;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class NexusPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        // Apply nexus publish plugin
        project.getPlugins().apply(NexusPublishPlugin.class);

        NexusPublishExtension nexusPublishing = project.getExtensions().getByType(NexusPublishExtension.class);

        nexusPublishing.getRepositories().create("test", nexusRepository -> {
//            nexusRepository.getNexusUrl().set(URI.create("http://192.168.20.247:8081"));
            nexusRepository.getNexusUrl().set(URI.create("http://192.168.20.247:8081/repository/maven-release/"));
            nexusRepository.getSnapshotRepositoryUrl().set(URI.create("http://192.168.20.247:8081/repository/maven-snapshots/"));
        });


        // Configure timeouts
        nexusPublishing.getConnectTimeout().set(Duration.ofMinutes(3));
        nexusPublishing.getClientTimeout().set(Duration.ofMinutes(3));

    }
}
