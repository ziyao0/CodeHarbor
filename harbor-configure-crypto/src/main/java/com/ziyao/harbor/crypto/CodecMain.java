package com.ziyao.harbor.crypto;


import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.core.YamlResolver;
import com.ziyao.harbor.crypto.files.FIleLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public class CodecMain {

    public static void main(String[] args) throws Exception {
        String projectDir = System.getProperty("user.dir");
        // 向系统环境变量中写入配置信息
        writeEnvironment();
        // 加载解析配置文件的上下文对象
        CodecContext context = CodecContextFactory.getInstance().createContext();

        List<File> files = FIleLoader.load(projectDir + System.getProperty(ConstantPool.input_path));

        EncryptCallback encryptCallback = context.getEncryptCallback();

        String included_keys = System.getProperty(ConstantPool.included_keys);
        String[] includes = new String[0];
        if (Strings.hasText(included_keys)) {
            includes = included_keys.split(",");
        }

        if (!Collections.isEmpty(files)) {
            for (File file : files) {
                List<Property> properties = YamlResolver.loadYaml(file);
                if (Collections.isEmpty(properties)) {
                    continue;
                }
                for (Property property : properties) {
                    if (Arrays.stream(includes).toList().contains(property.getKey())) {
                        property.setValue(encryptCallback.encrypt(property.getValue().toString()));
                    }
                }
                YamlResolver.writeYaml(properties, projectDir + System.getProperty(ConstantPool.output_path) + "/" + file.getName());
            }
        }
    }

    private static void writeEnvironment() throws IOException {
        String projectDir = System.getProperty("user.dir");
        // 加载环境变量 加载密钥配置文件
        Path path = Paths.get(projectDir + "/resources/config/crypto.yml");
        List<Property> properties = YamlResolver.loadYaml(path.toFile());
        Assert.notNull(properties, "配置信息不能为空");
        StringBuilder included_keys = new StringBuilder();
        for (Property property : properties) {
            if (Strings.startsWith(property.getKey(), ConstantPool.included_keys)) {
                included_keys.append(property.getValue()).append(",");
            } else
                System.setProperty(property.getKey(), property.getValue().toString());
        }
        if (Strings.hasLength(included_keys)) {
            System.setProperty(ConstantPool.included_keys, included_keys.toString());
        }
    }

}
