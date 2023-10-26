package com.ziyao.harbor.usercenter;

import com.ziyao.harbor.crypto.Codebook;
import com.ziyao.harbor.crypto.TextCipher;
import com.ziyao.harbor.crypto.utils.SmUtils;
import com.ziyao.harbor.usercenter.security.PrimaryProviderManager;
import com.ziyao.harbor.usercenter.security.core.PrimaryProvider;
import com.ziyao.harbor.web.ApplicationContextUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @author zhangziyao
 * @since 2023/4/23
 */
@Configuration
public class SecurityAutoConfiguration implements ApplicationContextAware {

    @Bean
    public PrimaryProviderManager primaryAuthProviderManager() {
        List<PrimaryProvider> beans = ApplicationContextUtils.getBeansOfType(PrimaryProvider.class);
        return new PrimaryProviderManager(beans);
    }

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.setApplicationContext(applicationContext);
    }

    public static void main(String[] args) {
        Codebook.KeyIv keyIv = SmUtils.generateSm4KeyIv();
        System.out.println(keyIv);
        Codebook.KeyPair keyPair = SmUtils.generateSm2KeyPair();
        System.out.println(keyPair);
        TextCipher textCipher = SmUtils.createSm2TextCipher("4486ffa055031faac9491313611591bec7b3b79271b5afbc6e9c9f1a254a2248", "0435b7038b6a7c30ec3b38fca5497083212988d212fd9edd76172ceab14ae8dc77d61c177f9c783358a16761dec951efefa032ae3346af69a206189a2dcfee5b52");
        System.out.println(textCipher.encrypt("张子尧"));

    }
}
