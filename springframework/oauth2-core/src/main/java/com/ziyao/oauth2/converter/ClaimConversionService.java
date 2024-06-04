package com.ziyao.oauth2.converter;

import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.core.convert.support.GenericConversionService;

/**
 * @author ziyao zhang
 * @time 2024/6/4
 */
public class ClaimConversionService extends GenericConversionService {

    private static volatile ClaimConversionService sharedInstance;

    private ClaimConversionService() {
        addConverters(this);
    }

    /**
     * Returns a shared instance of {@code ClaimConversionService}.
     *
     * @return a shared instance of {@code ClaimConversionService}
     */
    public static ClaimConversionService getSharedInstance() {
        ClaimConversionService sharedInstance = ClaimConversionService.sharedInstance;
        if (sharedInstance == null) {
            synchronized (ClaimConversionService.class) {
                sharedInstance = ClaimConversionService.sharedInstance;
                if (sharedInstance == null) {
                    sharedInstance = new ClaimConversionService();
                    ClaimConversionService.sharedInstance = sharedInstance;
                }
            }
        }
        return sharedInstance;
    }

    /**
     * Adds the converters that provide type conversion for claim values to the provided
     * {@link ConverterRegistry}.
     *
     * @param converterRegistry the registry of converters to add to
     */
    public static void addConverters(ConverterRegistry converterRegistry) {
        converterRegistry.addConverter(new ObjectToStringConverter());
        converterRegistry.addConverter(new ObjectToBooleanConverter());
        converterRegistry.addConverter(new ObjectToInstantConverter());
        converterRegistry.addConverter(new ObjectToURLConverter());
        converterRegistry.addConverter(new ObjectToListStringConverter());
        converterRegistry.addConverter(new ObjectToMapStringObjectConverter());
    }

}
