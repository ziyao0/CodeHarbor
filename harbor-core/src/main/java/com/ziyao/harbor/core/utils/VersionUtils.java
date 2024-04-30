package com.ziyao.harbor.core.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class VersionUtils {


    public static String extractVersion(String dependencyString) {
        // 定义匹配版本号的正则表达式
        String regex ="^ d+ (?:. d +){2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dependencyString);

        if (matcher.matches()) {
            return matcher.group(1); // 返回匹配的版本号部分
        } else {
            return null; // 如果未找到匹配的版本号，则返回 null
        }

    }
//

//    public static void main(String[] args) {
//        System.out.println(extractVersion("com.baomidou:mybatis-plus-extension:3.5.3.1"));
//        System.out.println(extractVersion("com.github.jeffreyning:mybatisplus-plus:1.5.1-RELEASE"));
//        System.out.println(extractVersion("com.google.guava:guava:31.1-jre"));
//    }

    public static void main(String[] args) {
        String dependencyString = "com.baomidou:mybatis-plus-generator:3.5.3.1";
        String version = extractVersion(dependencyString);

        if (version != null) {
            System.out.println("提取的版本号为：" + version);
        } else {
            System.out.println("未找到版本号。");
        }
    }
}
