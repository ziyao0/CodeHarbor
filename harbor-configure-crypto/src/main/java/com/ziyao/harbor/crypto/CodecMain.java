package com.ziyao.harbor.crypto;


import com.ziyao.harbor.crypto.core.YamlResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public class CodecMain {

    public static void main(String[] args) throws IOException {

        String filePath = "/Users/zhangziyao/workspace/project/CodeHarbor/resources/config/bootstrap-register.yml";
        List<Property> properties = YamlResolver.loadYaml(new File(filePath));
        for (Property property : properties) {
            property.setValue("$11111");
            System.out.println(property);
        }
//        Collections.reverse(properties);

        YamlResolver.writeYaml(properties, "output.yml");
    }

}
