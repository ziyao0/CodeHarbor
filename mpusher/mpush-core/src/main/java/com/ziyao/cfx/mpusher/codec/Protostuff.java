package com.ziyao.cfx.mpusher.codec;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyao zhang
 * @since 2023/6/29
 */
public abstract class Protostuff {

    private static final Map<Class<?>, Schema<?>> CACHED_SCHEMA = new ConcurrentHashMap<>();

    private static <T> Schema<T> getSchema(Class<T> clazz) {
        @SuppressWarnings("unchecked")
        Schema<T> schema = (Schema<T>) CACHED_SCHEMA.get(clazz);
        if (schema == null) {
            schema = RuntimeSchema.getSchema(clazz);
            if (schema != null) {
                CACHED_SCHEMA.put(clazz, schema);
            }
        }
        return schema;
    }


    /**
     * 序列化
     *
     * @param obj 序列化对象
     * @return 返回 {@link ByteBuf}
     */
    public static <T> ByteBuf serializer(T obj) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return Unpooled.copiedBuffer(ProtostuffIOUtil.toByteArray(obj, schema, buffer));
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        } finally {
            buffer.clear();
        }
    }

    public static <T> byte[] serialize(T obj) {
        @SuppressWarnings("unchecked")
        Class<T> clazz = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(clazz);
            return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        } finally {
            buffer.clear();
        }
    }


    /**
     * 反序列化
     *
     * @param bytes {@link byte[]}
     * @param clazz 对象class
     * @return 返回反序列化对象
     */
    public static <T> T deserializer(byte[] bytes, Class<T> clazz) {
        try {
            T obj = clazz.getDeclaredConstructor().newInstance();
            Schema<T> schema = getSchema(clazz);
            ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
            return obj;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * byteBuf case byte[]
     *
     * @param byteBuf {@link ByteBuf}
     * @return byte[]
     */
    public static byte[] byteBufToBytes(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        return bytes;
    }
}
