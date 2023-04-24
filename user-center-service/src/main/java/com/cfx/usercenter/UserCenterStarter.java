package com.cfx.usercenter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Eason
 * @date 2023/4/23
 */
@Slf4j
@SpringBootApplication
public class UserCenterStarter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("user-center-service is started!");
    }
}
