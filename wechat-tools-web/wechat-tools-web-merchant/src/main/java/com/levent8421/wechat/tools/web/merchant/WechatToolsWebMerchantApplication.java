package com.levent8421.wechat.tools.web.merchant;

import com.levent8421.wechat.tools.commons.context.ContextPackage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 23:57
 * Class Name: WechatToolsWebMerchantApplication
 * Author: Levent8421
 * Description:
 * 商家端用用程序入口
 *
 * @author Levent8421
 */
@SpringBootApplication(scanBasePackages = ContextPackage.BASE_PACKAGE)
@MapperScan(basePackages = ContextPackage.MAPPER_PACKAGE)
public class WechatToolsWebMerchantApplication {
    /**
     * 主方法入口
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        SpringApplication.run(WechatToolsWebMerchantApplication.class, args);
    }
}
