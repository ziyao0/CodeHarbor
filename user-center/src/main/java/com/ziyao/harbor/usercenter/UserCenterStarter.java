package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.core.utils.Collections;
import com.ziyao.harbor.data.redis.core.ValueOperations;
import com.ziyao.harbor.usercenter.entity.MenuTree;
import com.ziyao.harbor.usercenter.repository.redis.MenuTreeRepository;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.List;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@MapperScan("com.ziyao.harbor.usercenter.repository.mapper")
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class UserCenterStarter implements CommandLineRunner {

    public UserCenterStarter(MenuTreeRepository menuTreeRepository) {
        this.menuTreeRepository = menuTreeRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserCenterStarter.class, args);
    }

    private final MenuTreeRepository menuTreeRepository;

    @Override
    public void run(String... args) throws Exception {
        ValueOperations<List<MenuTree>> operations = menuTreeRepository.opsForValue("wechat", "ziyao");

        System.out.println(operations.getKey());
        MenuTree parent = new MenuTree("用户中心");
        MenuTree user = new MenuTree("用户管理");
        MenuTree role = new MenuTree("角色管理");
        MenuTree menu = new MenuTree("菜单管理");

        parent.setNodes(List.of(user, role, menu));
        List<MenuTree> menuTrees = operations.get();
        if (Collections.isEmpty(menuTrees)) {
            operations.set(List.of(parent));
        }
        System.out.println(operations.get());
    }
}
