package com.ziyao.harbor.crypto;


import com.ziyao.harbor.crypto.core.YamlResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public class CodecMain {

    public static void main(String[] args) throws IOException {

        // 向系统环境变量中写入配置信息
        writeEnvironment();
        // 加载解析配置文件的上下文对象
        CodecContext context = CodecContextFactory.getInstance().createContext();

        System.out.println(context);

    }

    private static void writeEnvironment() throws IOException {
        // 加载环境变量 加载密钥配置文件
        InputStream resource = CodecMain.class.getClassLoader().getResourceAsStream("config/crypto.yml");

        List<Property> properties = YamlResolver.loadYaml(resource);
        assert properties != null;
        for (Property property : properties) {
            System.setProperty(property.getKey(), property.getValue().toString());
        }
    }

}
