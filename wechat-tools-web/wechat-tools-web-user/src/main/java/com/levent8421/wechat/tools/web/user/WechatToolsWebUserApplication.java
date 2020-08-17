package com.levent8421.wechat.tools.web.user;

import com.levent8421.wechat.tools.commons.context.ContextPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 20:02
 * Class Name: WechatToolsWebUserApplication
 * Author: Levent8421
 * Description:
 * Spring Boot Application (User End)
 *
 * @author Levent8421
 */
@SpringBootApplication(scanBasePackages = ContextPackage.BASE_PACKAGE)
@MapperScan(basePackages = ContextPackage.MAPPER_PACKAGE)
public class WechatToolsWebUserApplication {
    /**
     * Entry Main Method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(WechatToolsWebUserApplication.class, args);
    }
}
