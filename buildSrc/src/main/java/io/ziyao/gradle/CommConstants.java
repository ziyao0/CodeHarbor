package io.ziyao.gradle;

import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface CommConstants {
    public static final String LIBS = "/dependencies/libs/ziyao.dependencies";
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
    // 源文件资源路径
    public static final List<String> SOURCES = List.of("/resources/config/bootstrap-register.yml");
    // 目标资源路径
    public static final String TARGET_PATH = "/src/main/resources";

}
