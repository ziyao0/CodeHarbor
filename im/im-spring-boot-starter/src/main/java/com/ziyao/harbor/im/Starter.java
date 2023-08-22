package com.ziyao.harbor.im;

import com.ziyao.harbor.im.autoconfigure.EnabledIM;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ziyao zhang
 * @since 2023/7/6
 */
@EnabledIM
@SpringBootApplication
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
