package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.Extractor;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Property;

import java.io.File;
import java.io.FileInputStream;
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
    public static List<Property> extractYamlProperties(File propertiesFile) throws IOException {
        if (propertiesFile == null) {
            return null;
        }
        YamlPropertySourceLoader yamlPropertySourceLoader = new YamlPropertySourceLoader();
        return yamlPropertySourceLoader.load(
                "EnvironmentExtractor", new FileInputStream(propertiesFile));
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
