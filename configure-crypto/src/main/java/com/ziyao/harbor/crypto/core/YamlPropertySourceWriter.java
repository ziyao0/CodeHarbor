package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Property;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class YamlPropertySourceWriter implements PropertySourceWriter {


    @Override
    public void write(List<Property> properties, String location) throws IOException {

        String yamlStr = new OriginTrackedYamlProcessor(properties).resolve();
        Files.write(Paths.get(location), Strings.toBytesOrEmpty(yamlStr));
    }
}
