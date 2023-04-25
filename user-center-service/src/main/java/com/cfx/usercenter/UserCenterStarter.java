package com.cfx.usercenter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangziyao
 * @date 2023/4/23
 */

@SpringBootApplication
public class UserCenterStarter implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterStarter.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("user-center-service is started!");
    }
}
