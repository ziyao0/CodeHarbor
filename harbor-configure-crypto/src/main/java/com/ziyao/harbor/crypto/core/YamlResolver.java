package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Property;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public final class YamlResolver
        implements Extractor<String, List<Property>> {


    @Override
    public List<Property> extract(String location) {
        File propertiesFile = getPropertiesFile(location);
//        extractYamlProperties(propertiesFile)
        return null;
    }

    /**
     * 从文件中提取环境变量
     *
     * @param propertiesFile 配置文件
     * @return 返回 {@link org.springframework.core.env.Environment}
     * @throws IOException 读取配置文件失败后所抛出的流异常
     */
    private static StandardEnvironment extractYamlProperties(File propertiesFile) throws IOException {
        if (propertiesFile == null) {
            return null;
        }
        StandardEnvironment environment = new StandardEnvironment();
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        yamlPropertySourceLoader.load("EnvironmentExtractor", new FileSystemResource(propertiesFile))
                .forEach(e -> {
                    environment.getPropertySources().addFirst(e);
                });
        return environment;
    }

    private static File getPropertiesFile(String configPath) {
        if (Strings.hasText(configPath))
            return getPropertiesFileForConfigPath(configPath);
        else
            return null;
    }

    private static File getPropertiesFileForConfigPath(String configPath) {
        File file = new File(configPath);
        if (file.exists() && file.isFile())
            return file;
        else
            return null;
    }
}
