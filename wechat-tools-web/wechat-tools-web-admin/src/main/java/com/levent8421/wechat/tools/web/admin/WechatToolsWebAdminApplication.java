package com.levent8421.wechat.tools.web.admin;

import com.levent8421.wechat.tools.commons.context.ContextPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 0:05
 * Class Name: WechatToolsWebAdminApplication
 * Author: Levent8421
 * Description:
 * 管理员后台用用程序启动入口
 *
 * @author Levent8421
 */
@SpringBootApplication(scanBasePackages = ContextPackage.BASE_PACKAGE)
@MapperScan(basePackages = ContextPackage.MAPPER_PACKAGE)
public class WechatToolsWebAdminApplication {
    /**
     * Entry method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        SpringApplication.run(WechatToolsWebAdminApplication.class, args);
    }
}
