package com.ziyao.harbor.crypto.core;

import com.ziyao.harbor.crypto.Property;

import java.io.IOException;
import java.util.List;

/**
 * @author ziyao
 * @since 2023/4/23
 */
public interface PropertySourceWriter {


    void write(List<Property> properties, String location) throws IOException;
}
