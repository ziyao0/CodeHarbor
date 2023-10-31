package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.core.io.IOUtils;
import com.ziyao.harbor.core.lang.Nullable;
import com.ziyao.harbor.core.utils.Assert;
import com.ziyao.harbor.core.utils.Strings;
import com.ziyao.harbor.crypto.Property;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.representer.Representer;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ziyao zhang
 * @since 2023/10/27
 */
public abstract class YamlProcessor {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ResolutionMethod resolutionMethod = ResolutionMethod.OVERRIDE;
    private InputStream[] streams = new InputStream[0];
    private List<Property> properties = new ArrayList<>();
    private List<DocumentMatcher> documentMatchers = Collections.emptyList();

    private boolean matchDefault = true;

    private Set<String> supportedTypes = Collections.emptySet();


    /**
     * A map of document matchers allowing callers to selectively use only
     * some of the documents in a YAML resource. In YAML documents are
     * separated by {@code ---} lines, and each document is converted
     * to properties before the match is made. E.g.
     * <pre class="code">
     * environment: dev
     * url: https://dev.bar.com
     * name: Developer Setup
     * ---
     * environment: prod
     * url:https://foo.bar.com
     * name: My Cool App
     * </pre>
     * when mapped with
     * <pre class="code">
     * setDocumentMatchers(properties -&gt;
     *     ("prod".equals(properties.getProperty("environment")) ? MatchStatus.FOUND : MatchStatus.NOT_FOUND));
     * </pre>
     * would end up as
     * <pre class="code">
     * environment=prod
     * url=https://foo.bar.com
     * name=My Cool App
     * </pre>
     */
    public void setDocumentMatchers(DocumentMatcher... matchers) {
        this.documentMatchers = List.of(matchers);
    }

    /**
     * Flag indicating that a document for which all the
     * {@link #setDocumentMatchers(DocumentMatcher...) document matchers} abstain will
     * nevertheless match. Default is {@code true}.
     */
    public void setMatchDefault(boolean matchDefault) {
        this.matchDefault = matchDefault;
    }

    /**
     * Method to use for resolving resources. Each resource will be converted to a Map,
     * so this property is used to decide which map entries to keep in the final output
     * from this factory. Default is {@link ResolutionMethod#OVERRIDE}.
     */
    public void setResolutionMethod(ResolutionMethod resolutionMethod) {
        Assert.notNull(resolutionMethod, "ResolutionMethod must not be null");
        this.resolutionMethod = resolutionMethod;
    }

    public void setStreams(InputStream... streams) {
        this.streams = streams;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    /**
     * Set the supported types that can be loaded from YAML documents.
     * <p>If no supported types are configured, only Java standard classes
     * (as defined in {@link org.yaml.snakeyaml.constructor.SafeConstructor})
     * encountered in YAML documents will be supported.
     * If an unsupported type is encountered, an {@link IllegalStateException}
     * will be thrown when the corresponding YAML node is processed.
     *
     * @param supportedTypes the supported types, or an empty array to clear the
     *                       supported types
     * @see #createYaml()
     * @since 5.1.16
     */
    public void setSupportedTypes(Class<?>... supportedTypes) {
        if (Objects.isNull(supportedTypes)) {
            this.supportedTypes = Collections.emptySet();
        } else {
            Assert.noNullElements(supportedTypes, "'supportedTypes' must not contain null elements");
            this.supportedTypes = Arrays.stream(supportedTypes).map(Class::getName)
                    .collect(Collectors.toUnmodifiableSet());
        }
    }

    /**
     * Provide an opportunity for subclasses to process the Yaml parsed from the supplied
     * resources. Each resource is parsed in turn and the documents inside checked against
     * the {@link #setDocumentMatchers(DocumentMatcher...) matchers}. If a document
     * matches it is passed into the callback, along with its representation as Properties.
     * Depending on the {@link #setResolutionMethod(ResolutionMethod)} not all the
     * documents will be parsed.
     *
     * @param callback a callback to delegate to once matching documents are found
     * @see #createYaml()
     */
    protected void process(MatchCallback callback) {
        Yaml yaml = createYaml();
        for (InputStream inputStream : this.streams) {
            boolean found = process(callback, yaml, inputStream);
            if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND && found) {
                return;
            }
        }
    }

    protected String process() {
        Yaml yaml = createYamlBlockFlowStyle();
        Map<String, Object> yamlMap = createYamlMap(this.properties);
        return yaml.dump(yamlMap);
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> createYamlMap(List<Property> properties) {
        Map<String, Object> yamlMap = new LinkedHashMap<>();
        for (Property property : properties) {
            String key = property.getKey();
            Object value = property.getValue();
            Map<String, Object> currentMap = yamlMap;
            String[] keys = Strings.split(key, "\\.");

            for (int i = 0; i < keys.length; i++) {
                String currentKey = keys[i];
                if (i == keys.length - 1) {
                    currentMap.put(currentKey, value);
                } else {
                    currentMap = (Map<String, Object>) currentMap.computeIfAbsent(currentKey, k -> new HashMap<>());
                }
            }
        }
        return yamlMap;
    }

    /**
     * 转换类型
     */
    public Object convert(Object value) {
        if (value instanceof String) {
            return value.toString();
        }
        return null;
    }

    /**
     * Create the {@link Yaml} instance to use.
     * <p>The default implementation sets the "allowDuplicateKeys" flag to {@code false},
     * enabling built-in duplicate key handling in SnakeYAML 1.18+.
     * <p>As of Spring Framework 5.1.16, if custom {@linkplain #setSupportedTypes
     * supported types} have been configured, the default implementation creates
     * a {@code Yaml} instance that filters out unsupported types encountered in
     * YAML documents. If an unsupported type is encountered, an
     * {@link IllegalStateException} will be thrown when the node is processed.
     *
     * @see LoaderOptions#setAllowDuplicateKeys(boolean)
     */
    protected Yaml createYaml() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        DumperOptions dumperOptions = new DumperOptions();
        return new Yaml(new FilteringConstructor(loaderOptions), new Representer(dumperOptions),
                dumperOptions, loaderOptions);
    }

    protected Yaml createYamlBlockFlowStyle() {
        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setAllowDuplicateKeys(false);
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(new FilteringConstructor(loaderOptions), new Representer(dumperOptions),
                dumperOptions, loaderOptions);
    }

    private boolean process(MatchCallback callback, Yaml yaml, InputStream inputStream) {
        int count = 0;
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("Loading from YAML: " + inputStream);
            }
            try (Reader reader = new UnicodeReader(inputStream)) {
                for (Object object : yaml.loadAll(reader)) {
                    if (object != null && process(asMap(object), callback)) {
                        count++;
                        if (this.resolutionMethod == ResolutionMethod.FIRST_FOUND) {
                            break;
                        }
                    }
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Loaded " + count + " document" + (count > 1 ? "s" : "") +
                            " from YAML resource: " + inputStream);
                }
            }
        } catch (IOException ex) {
            handleProcessError(inputStream, ex);
        } finally {
            IOUtils.close(inputStream);
        }
        return (count > 0);
    }

    private void handleProcessError(InputStream inputStream, IOException ex) {
        if (this.resolutionMethod != ResolutionMethod.FIRST_FOUND &&
                this.resolutionMethod != ResolutionMethod.OVERRIDE_AND_IGNORE) {
            throw new IllegalStateException(ex);
        }
        if (logger.isWarnEnabled()) {
            logger.warn("Could not load map from " + inputStream + ": " + ex.getMessage());
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private Map<String, Object> asMap(Object object) {
        // YAML can have numbers as keys
        Map<String, Object> result = new LinkedHashMap<>();
        if (!(object instanceof Map map)) {
            // A document can be a text literal
            result.put("document", object);
            return result;
        }

        map.forEach((key, value) -> {
            if (value instanceof Map) {
                value = asMap(value);
            }
            if (key instanceof CharSequence) {
                result.put(key.toString(), value);
            } else {
                // It has to be a map key in this case
                result.put("[" + key.toString() + "]", value);
            }
        });
        return result;
    }

    private boolean process(Map<String, Object> map, MatchCallback callback) {
        Properties properties = this.createStringAdaptingProperties();
        properties.putAll(getFlattenedMap(map));

        if (this.documentMatchers.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Merging document (no matchers set): " + map);
            }
            callback.process(properties, map);
            return true;
        }

        MatchStatus result = MatchStatus.ABSTAIN;
        for (DocumentMatcher matcher : this.documentMatchers) {
            MatchStatus match = matcher.matches(properties);
            result = MatchStatus.getMostSpecific(match, result);
            if (match == MatchStatus.FOUND) {
                if (logger.isDebugEnabled()) {
                    logger.debug("Matched document with document matcher: " + properties);
                }
                callback.process(properties, map);
                return true;
            }
        }

        if (result == MatchStatus.ABSTAIN && this.matchDefault) {
            if (logger.isDebugEnabled()) {
                logger.debug("Matched document with default matcher: " + map);
            }
            callback.process(properties, map);
            return true;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Unmatched document: " + map);
        }
        return false;
    }

    private Properties createStringAdaptingProperties() {
        return new SortedProperties(false) {
            @Override
            @Nullable
            public String getProperty(String key) {
                Object value = get(key);
                return (value != null ? value.toString() : null);
            }
        };
    }

    /**
     * Return a flattened version of the given map, recursively following any nested Map
     * or Collection values. Entries from the resulting map retain the same order as the
     * source. When called with the Map from a {@link MatchCallback} the result will
     * contain the same values as the {@link MatchCallback} Properties.
     *
     * @param source the source map
     * @return a flattened map
     * @since 4.1.3
     */
    protected final Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, @Nullable String path) {
        source.forEach((key, value) -> {
            if (Strings.hasText(path)) {
                if (key.startsWith("[")) {
                    key = path + key;
                } else {
                    key = path + '.' + key;
                }
            }
            if (value instanceof String) {
                result.put(key, value);
            } else if (value instanceof Map map) {
                // Need a compound key
                buildFlattenedMap(result, map, key);
            } else if (value instanceof Collection collection) {
                // Need a compound key
                if (collection.isEmpty()) {
                    result.put(key, "");
                } else {
                    int count = 0;
                    for (Object object : collection) {
                        buildFlattenedMap(result, Collections.singletonMap(
                                "[" + (count++) + "]", object), key);
                    }
                }
            } else {
                result.put(key, (value != null ? value : ""));
            }
        });
    }


    /**
     * Callback interface used to process the YAML parsing results.
     */
    @FunctionalInterface
    public interface MatchCallback {

        /**
         * Process the given representation of the parsing results.
         *
         * @param properties the properties to process (as a flattened
         *                   representation with indexed keys in case of a collection or map)
         * @param map        the result map (preserving the original value structure
         *                   in the YAML document)
         */
        void process(Properties properties, Map<String, Object> map);
    }


    /**
     * Strategy interface used to test if properties match.
     */
    @FunctionalInterface
    public interface DocumentMatcher {

        /**
         * Test if the given properties match.
         *
         * @param properties the properties to test
         * @return the status of the match
         */
        MatchStatus matches(Properties properties);
    }


    /**
     * Status returned from {@link DocumentMatcher#matches(java.util.Properties)}.
     */
    public enum MatchStatus {

        /**
         * A match was found.
         */
        FOUND,

        /**
         * No match was found.
         */
        NOT_FOUND,

        /**
         * The matcher should not be considered.
         */
        ABSTAIN;

        /**
         * Compare two {@link MatchStatus} items, returning the most specific status.
         */
        public static MatchStatus getMostSpecific(MatchStatus a, MatchStatus b) {
            return (a.ordinal() < b.ordinal() ? a : b);
        }
    }


    /**
     * Method to use for resolving resources.
     */
    public enum ResolutionMethod {

        /**
         * Replace values from earlier in the list.
         */
        OVERRIDE,

        /**
         * Replace values from earlier in the list, ignoring any failures.
         */
        OVERRIDE_AND_IGNORE,

        /**
         * Take the first resource in the list that exists and use just that.
         */
        FIRST_FOUND
    }


    /**
     * {@link Constructor} that supports filtering of unsupported types.
     * <p>If an unsupported type is encountered in a YAML document, an
     * {@link IllegalStateException} will be thrown from {@link #getClassForName}.
     */
    private class FilteringConstructor extends Constructor {

        FilteringConstructor(LoaderOptions loaderOptions) {
            super(loaderOptions);
        }

        @Override
        protected Class<?> getClassForName(String name) throws ClassNotFoundException {
            Assert.state(YamlProcessor.this.supportedTypes.contains(name),
                    () -> "Unsupported type encountered in YAML document: " + name);
            return super.getClassForName(name);
        }
    }

}
