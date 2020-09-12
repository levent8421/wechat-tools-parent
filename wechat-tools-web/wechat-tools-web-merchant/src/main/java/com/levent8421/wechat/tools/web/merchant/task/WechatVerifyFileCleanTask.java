package com.levent8421.wechat.tools.web.merchant.task;

import com.levent8421.wechat.tools.resource.WechatVerifyFileService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 20:54
 * Class Name: WechatVerifyFileCleanTask
 * Author: Levent8421
 * Description:
 * 微信验证文件清理任务
 *
 * @author Levent8421
 */
@Component
public class WechatVerifyFileCleanTask {
    private final WechatVerifyFileService wechatVerifyFileService;

    public WechatVerifyFileCleanTask(WechatVerifyFileService wechatVerifyFileService) {
        this.wechatVerifyFileService = wechatVerifyFileService;
    }

    /**
     * 清理文件
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void clean() {
        wechatVerifyFileService.cleanFiles();
    }
}
