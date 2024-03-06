package com.harbor.boot.autoconfigure.redis.support;

import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author ziyao zhang
 * @since 2024/2/29
 */
@Getter
final class LazyStreamable<T> implements Streamable<T> {

    private final Supplier<? extends Stream<T>> stream;

    private LazyStreamable(Supplier<? extends Stream<T>> stream) {
        this.stream = stream;
    }

    public static <T> LazyStreamable<T> of(Supplier<? extends Stream<T>> stream) {
        return new LazyStreamable<T>(stream);
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public @NonNull Iterator<T> iterator() {
        return stream().iterator();
    }

    /*
     * (non-Javadoc)
     * @see Streamable#stream()
     */
    @Override
    public Stream<T> stream() {
        return stream.get();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LazyStreamable(stream=" + this.getStream() + ")";
    }
}