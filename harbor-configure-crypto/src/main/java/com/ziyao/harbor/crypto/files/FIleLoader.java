package com.ziyao.harbor.crypto.files;

import com.google.common.collect.Lists;
import com.ziyao.harbor.core.lang.NonNull;
import com.ziyao.harbor.core.utils.Strings;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public class FIleLoader {

    public static List<File> load(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            if (Files.isDirectory(path)) {
                DirectoryStream<Path> directory = Files.newDirectoryStream(path);
                return toFiles(directory);
            } else {
                return Lists.newArrayList(getFile(path));
            }
        } else
            return List.of();
    }

    /**
     * 把配置文件写入目标路径
     *
     * @param target 带写入的目标路径
     */
    public static void write(List<File> propertyFiles, String target) {

    }

    private static List<File> toFiles(@NonNull DirectoryStream<Path> directoryStream) throws IOException {
        List<File> propertyFiles = new ArrayList<>();
        // 解析符合条件的文件
        for (Path path : directoryStream) {
            if (Files.isDirectory(path))
                propertyFiles.addAll(toFiles(Files.newDirectoryStream(path)));
            else
                propertyFiles.add(getFile(path));
        }
        return propertyFiles.stream().filter(Objects::nonNull).toList();
    }

    private static File getFile(Path path) {
        File file = path.toFile();
        if (!(Strings.endsWith(file.getName(), ".yaml")
                || Strings.endsWith(file.getName(), ".yml"))) {
            System.err.println("不支持的文件格式，file：" + file.getName());
            return null;
        } else
            return file;
    }
}
