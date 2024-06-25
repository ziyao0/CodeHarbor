package com.ziyao.security.oauth2.core.settings;

import com.google.common.collect.Maps;
import lombok.Getter;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
@Getter
public abstract class AbstractSettings implements Serializable {


    private static final long serialVersionUID = -4608865879686757972L;

    /**
     * 配置信息
     */
    private final Map<String, Object> settings;

    protected AbstractSettings(Map<String, Object> settings) {
        Assert.notEmpty(settings, "settings cannot be empty");
        this.settings = Maps.newHashMap(settings);
    }

    /**
     * 返回配置
     *
     * @param name 配置名称
     * @param <T>  配置类型
     * @return 设置的值，如果不可用，则为{@code null}
     */
    @SuppressWarnings("unchecked")
    public <T> T getSetting(String name) {
        Assert.hasText(name, "name cannot be empty");
        return (T) getSettings().get(name);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AbstractSettings that = (AbstractSettings) obj;
        return this.settings.equals(that.settings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.settings);
    }

    @Override
    public String toString() {
        return "AbstractSettings {" + "settings=" + this.settings + '}';
    }

    /**
     * {@link AbstractSettings}子类的构建器。
     *
     * @param <T> 对象类型
     * @param <B> 构造器的类型
     */
    protected abstract static class AbstractBuilder<T extends AbstractSettings, B extends AbstractBuilder<T, B>> {

        private final Map<String, Object> settings = new HashMap<>();

        protected AbstractBuilder() {
        }

        /**
         * 设置配置
         */
        public B setting(String name, Object value) {
            Assert.hasText(name, "name cannot be empty");
            Assert.notNull(value, "value cannot be null");
            getSettings().put(name, value);
            return getThis();
        }

        /**
         * {@code Consumer}的配置设置{@code Map}允许添加、替换或删除的能力。
         */
        public B settings(Consumer<Map<String, Object>> settingsConsumer) {
            settingsConsumer.accept(getSettings());
            return getThis();
        }

        public abstract T build();

        protected final Map<String, Object> getSettings() {
            return this.settings;
        }

        @SuppressWarnings("unchecked")
        protected final B getThis() {
            return (B) this;
        }

    }
}
