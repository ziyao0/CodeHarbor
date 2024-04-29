package io.ziyao.gradle.maven;

import io.ziyao.gradle.ProjectUtils;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

import java.net.URI;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class NexusPublishingPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);

        publishing.publications(
                publications -> publications.create(
                        "mavenJava", MavenPublication.class, publication -> {
                            publication.from(project.getComponents().getByName("java"));
                        }));

        publishing.getRepositories().maven(repos -> {
            repos.setAllowInsecureProtocol(true);

            String url = ProjectUtils.isRelease(project)
                    ? ProjectUtils.findProperty("releaseUrl", project)
                    : ProjectUtils.findProperty("snapshotUrl", project);

            if (url != null) {
                URI uri = URI.create(url);

                repos.setUrl(uri);
                // 设置凭据
                repos.credentials(credentials -> {
                    credentials.setUsername(ProjectUtils.findProperty("nexusUsername", project));
                    credentials.setPassword(ProjectUtils.findProperty("nexusPassword", project));
                });
            }
        });
    }
}
