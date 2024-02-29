package com.harbor.boot.autoconfigure.redis.support;

import org.springframework.data.util.CloseableIterator;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.*;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
public interface StreamUtils {

    /**
     * Returns a {@link Stream} backed by the given {@link Iterator}
     *
     * @param iterator must not be {@literal null}.
     */
    public static <T> Stream<T> createStreamFromIterator(Iterator<T> iterator) {

        Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false);
    }

    /**
     * Returns a {@link Stream} backed by the given {@link CloseableIterator} and forwarding calls to
     * {@link Stream#close()} to the iterator.
     *
     * @param iterator must not be {@literal null}.
     */
    public static <T> Stream<T> createStreamFromIterator(CloseableIterator<T> iterator) {

        Assert.notNull(iterator, "Iterator must not be null");

        return createStreamFromIterator((Iterator<T>) iterator).onClose(() -> iterator.close());
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link List}.
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, List<T>> toUnmodifiableList() {
        return collectingAndThen(toList(), Collections::unmodifiableList);
    }

    /**
     * Returns a {@link Collector} to create an unmodifiable {@link Set}.
     *
     * @return will never be {@literal null}.
     */
    public static <T> Collector<T, ?, Set<T>> toUnmodifiableSet() {
        return collectingAndThen(toSet(), Collections::unmodifiableSet);
    }

}
