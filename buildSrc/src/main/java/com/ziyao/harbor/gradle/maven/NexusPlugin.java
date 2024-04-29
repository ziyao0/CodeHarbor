package com.ziyao.harbor.gradle.maven;

import com.ziyao.harbor.gradle.ProjectUtils;
import io.github.gradlenexus.publishplugin.NexusPublishExtension;
import io.github.gradlenexus.publishplugin.NexusPublishPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

import java.net.URI;
import java.time.Duration;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class NexusPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        boolean release = ProjectUtils.isRelease(project);

        // Apply nexus publish plugin
        project.getPlugins().apply(NexusPublishPlugin.class);

        NexusPublishExtension nexusPublishing = project.getExtensions().getByType(NexusPublishExtension.class);

        nexusPublishing.getRepositories().create("nexusRepos", repos -> {

            repos.getAllowInsecureProtocol().set(true);
//            repos.getNexusUrl().set(URI.create("http://192.168.20.247:8081"));

            String url = release ? ProjectUtils.findProperty("releaseUrl", project)
                    : ProjectUtils.findProperty("snapshotUrl", project);

            if (url != null) {
                URI uri = URI.create(url);

                if (release) repos.getNexusUrl().set(uri);
                else
                    repos.getSnapshotRepositoryUrl().set(uri);
                repos.getUsername().set(ProjectUtils.findProperty("username", project));
                repos.getPassword().set(ProjectUtils.findProperty("password", project));
            }
        });


        // Configure timeouts
        nexusPublishing.getConnectTimeout().set(Duration.ofMinutes(3));
        nexusPublishing.getClientTimeout().set(Duration.ofMinutes(3));

        project.getTasks().register("publishToNexus", task -> {
            task.dependsOn("publish");
            task.doLast(action -> {
                // 在此处执行发布到 Nexus 的操作
                System.out.println("Publishing to Nexus...");
            });
        });

        project.getExtensions().configure(PublishingExtension.class, publish -> {

            publish.getPublications().create(
                    "mavenJava", MavenPublication.class, publication -> {

                        publication.from(project.getComponents().getByName("java"));

                        // 将源码发布到 Maven 仓库
                        publication.artifact(project.getTasks().getByName("jar"));
                    });
        });
    }
}
