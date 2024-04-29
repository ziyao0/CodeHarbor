package io.ziyao.gradle.maven;

import groovy.namespace.QName;
import groovy.util.Node;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;

/**
 * @author ziyao zhang
 * @since 2024/3/5
 */
public class MavenPublishingPlugin implements Plugin<Project> {

    private static final String DEPENDENCY_MANAGEMENT = "dependencyManagement";

    @Override
    public void apply(Project project) {
        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);
        publishing.getPublications().withType(MavenPublication.class).all(
                publication -> publication.pom(
                        pom -> pom.withXml(
                                xml -> {
                                    Node projectNode = xml.asNode();
                                    // 查找到特定的节点并删除
                                    Node dependencyManagement = findChild(projectNode, DEPENDENCY_MANAGEMENT);
                                    if (dependencyManagement != null) {
                                        projectNode.remove(dependencyManagement);
                                    }
                                })));
    }

    private Node findChild(Node parent, String name) {
        for (Object child : parent.children()) {
            if (child instanceof Node) {
                Node node = (Node) child;
                if (node.name() instanceof QName) {
                    QName qName = (QName) node.name();
                    if (name.equals(qName.getLocalPart())) {
                        return node;
                    }
                }
                if (name.equals(node.name())) {
                    return node;
                }
            }
        }
        return null;
    }

}
