package com.harbor.boot.autoconfigure.redis;

import com.ziyao.harbor.data.redis.core.Repository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ziyao zhang
 * @since 2024/2/5
 */
public class RepositoryScanningComponentProvider extends ClassPathScanningCandidateComponentProvider {

    /**
     * -- GETTER --
     * <p>
     * <p>
     * -- SETTER --
     * Controls whether nested inner-class
     * interface definitions should be considered for automatic
     * discovery. This defaults to
     * .
     */
    @Setter
    @Getter
    private boolean considerNestedRepositoryInterfaces;
    private final BeanDefinitionRegistry registry;

    /**
     * Creates a new {@link RepositoryScanningComponentProvider} using the given {@link TypeFilter} to include components to be
     * picked up.
     *
     * @param includeFilters the {@link TypeFilter}s to select repository interfaces to consider, must not be
     *                       {@literal null}.
     */
    public RepositoryScanningComponentProvider(Iterable<? extends TypeFilter> includeFilters, BeanDefinitionRegistry registry) {

        super(false);

        Assert.notNull(includeFilters, "Include filters must not be null");
        Assert.notNull(registry, "BeanDefinitionRegistry must not be null");

        this.registry = registry;

        if (includeFilters.iterator().hasNext()) {
            for (TypeFilter filter : includeFilters) {
                addIncludeFilter(filter);
            }
        } else {
            super.addIncludeFilter(new RepositoryScanningComponentProvider.InterfaceTypeFilter(Repository.class));
            super.addIncludeFilter(new AnnotationTypeFilter(RepositoryDefinition.class, true, true));
        }

        addExcludeFilter(new AnnotationTypeFilter(NoRepositoryBean.class));
    }

    /**
     * Custom extension of {@code #addIncludeFilter(TypeFilter)} to extend the added {@link TypeFilter}. For the
     * {@link TypeFilter} handed we'll have two filters registered: one additionally enforcing the
     * {@link RepositoryDefinition} annotation, the other one forcing the extension of {@link org.springframework.data.repository.Repository}.
     *
     * @see ClassPathScanningCandidateComponentProvider#addIncludeFilter(TypeFilter)
     */
    @Override
    public void addIncludeFilter(@NonNull TypeFilter includeFilter) {

        List<TypeFilter> filterPlusInterface = new ArrayList<>(2);
        filterPlusInterface.add(includeFilter);
        filterPlusInterface.add(new RepositoryScanningComponentProvider.InterfaceTypeFilter(Repository.class));

        super.addIncludeFilter(new RepositoryScanningComponentProvider.AllTypeFilter(filterPlusInterface));

        List<TypeFilter> filterPlusAnnotation = new ArrayList<>(2);
        filterPlusAnnotation.add(includeFilter);
        filterPlusAnnotation.add(new AnnotationTypeFilter(RepositoryDefinition.class, true, true));

        super.addIncludeFilter(new RepositoryScanningComponentProvider.AllTypeFilter(filterPlusAnnotation));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {

        boolean isNonRepositoryInterface = !Repository.class.getName().equals(beanDefinition.getBeanClassName());
        boolean isTopLevelType = !beanDefinition.getMetadata().hasEnclosingClass();
        boolean isConsiderNestedRepositories = isConsiderNestedRepositoryInterfaces();

        return isNonRepositoryInterface && (isTopLevelType || isConsiderNestedRepositories);
    }

    /**
     * Customizes the repository interface detection and triggers annotation detection on them.
     */
    @Override
    public @NonNull Set<BeanDefinition> findCandidateComponents(@NonNull String basePackage) {

        Set<BeanDefinition> candidates = super.findCandidateComponents(basePackage);

        for (BeanDefinition candidate : candidates) {
            if (candidate instanceof AnnotatedBeanDefinition) {
                AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
            }
        }

        return candidates;
    }

    @NonNull
    @Override
    protected BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    /**
     * {@link org.springframework.core.type.filter.TypeFilter} that only matches interfaces. Thus setting this up makes
     * only sense providing an interface type as {@code targetType}.
     *
     * @author Oliver Gierke
     */
    private static class InterfaceTypeFilter extends AssignableTypeFilter {

        public InterfaceTypeFilter(Class<?> targetType) {
            super(targetType);
        }

        @Override
        public boolean match(MetadataReader metadataReader, @NonNull MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            return metadataReader.getClassMetadata().isInterface() && super.match(metadataReader, metadataReaderFactory);
        }
    }

    /**
     * Helper class to create a {@link TypeFilter} that matches if all the delegates match.
     *
     * @author Oliver Gierke
     */
    private record AllTypeFilter(List<TypeFilter> delegates) implements TypeFilter {

        private AllTypeFilter {

            Assert.notNull(delegates, "TypeFilter deleages must not be null");
        }

        public boolean match(@NonNull MetadataReader metadataReader, @NonNull MetadataReaderFactory metadataReaderFactory)
                throws IOException {

            for (TypeFilter filter : delegates) {
                if (!filter.match(metadataReader, metadataReaderFactory)) {
                    return false;
                }
            }

            return true;
        }
    }
}
