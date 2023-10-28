package com.ziyao.harbor.crypto;


import com.ziyao.harbor.crypto.core.YamlResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public class CodecMain {

    public static void main(String[] args) throws IOException {

        String filePath = "/Users/zhangziyao/workspace/project/CodeHarbor/resources/config/bootstrap-register.yml";
        List<Property> properties = YamlResolver.extractYamlProperties(new File(filePath));
        for (Property propertySource : properties) {
            System.out.println(propertySource);
        }
        OutputStream outputStream = new FileOutputStream("/Users/zhangziyao/workspace/project/CodeHarbor/resources/config/bootstrap-register.properties");

    }


}
