package io.ziyao.gradle;

import org.gradle.internal.impldep.com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface GradleConstantPool {
    public static final String LIBS = "/dependencies/dependency.libs";
    public static final String WELL_NUMBER = "#";
    public static final String GRADLE_API = "api";
    public static final String GRADLE_PLUGIN_platform = "java-platform";
    public static final String GRADLE_PLUGIN_library = "java-library";
    public static final String GRADLE_PLUGIN_maven_publish = "maven-publish";
    /**
     * Name of the {@code optional} configuration.
     */
    public static final String OPTIONAL_CONFIGURATION_NAME = "optional";
    public static final String MANAGEMENT_CONFIGURATION_NAME = "management";
    public static final List<String> SOURCES = Lists.newArrayList("/resources/config/bootstrap-register.yml");
    public static final String TARGET_PATH = "/src/main/resources";

}
