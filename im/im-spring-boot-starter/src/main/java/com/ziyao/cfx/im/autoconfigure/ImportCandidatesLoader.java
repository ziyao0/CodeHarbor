package com.ziyao.cfx.im.autoconfigure;

import org.springframework.boot.context.annotation.ImportCandidates;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author ziyao zhang
 * @since 2023/7/6
 */
public class ImportCandidatesLoader implements Iterable<String> {

    private static final String LOCATION = "META-INF/im/%s.imports";

    private static final String COMMENT_START = "#";

    private final List<String> candidates;

    private ImportCandidatesLoader(List<String> candidates) {
        Assert.notNull(candidates, "'candidates' must not be null");
        this.candidates = Collections.unmodifiableList(candidates);
    }

    public static ImportCandidatesLoader load(Class<?> annotation, ClassLoader classLoader) {
        Assert.notNull(annotation, "'annotation' must not be null");
        ClassLoader classLoaderToUse = decideClassloader(classLoader);
        String location = String.format(LOCATION, annotation.getName());
        Enumeration<URL> urls = findUrlsInClasspath(classLoaderToUse, location);
        List<String> importCandidates = new ArrayList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            importCandidates.addAll(readCandidateConfigurations(url));
        }
        return new ImportCandidatesLoader(importCandidates);
    }

    private static ClassLoader decideClassloader(ClassLoader classLoader) {
        if (classLoader == null) {
            return ImportCandidates.class.getClassLoader();
        }
        return classLoader;
    }

    private static Enumeration<URL> findUrlsInClasspath(ClassLoader classLoader, String location) {
        try {
            return classLoader.getResources(location);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to load configurations from location [" + location + "]", ex);
        }
    }

    private static List<String> readCandidateConfigurations(URL url) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new UrlResource(url).getInputStream(), StandardCharsets.UTF_8))) {
            List<String> candidates = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                line = stripComment(line);
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                candidates.add(line);
            }
            return candidates;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load configurations from location [" + url + "]", ex);
        }
    }

    private static String stripComment(String line) {
        int commentStart = line.indexOf(COMMENT_START);
        if (commentStart == -1) {
            return line;
        }
        return line.substring(0, commentStart);
    }

    @Override
    public Iterator<String> iterator() {
        return this.candidates.iterator();
    }

    public List<String> getCandidates() {
        return candidates;
    }
}
