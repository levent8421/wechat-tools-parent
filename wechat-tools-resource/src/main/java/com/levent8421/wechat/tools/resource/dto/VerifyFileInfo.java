package com.levent8421.wechat.tools.resource.dto;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Create By Levent8421
 * Create Time: 2020/9/12 20:43
 * Class Name: VerifyFileInfo
 * Author: Levent8421
 * Description:
 * 校验文件信息
 *
 * @author Levent8421
 */
@Slf4j
public class VerifyFileInfo {
    /**
     * 过期时间 10分钟
     */
    private final long TTL = 10 * 60 * 1000;
    private final File file;
    private final long copyTime;

    public VerifyFileInfo(File file, long copyTime) {
        this.file = file;
        this.copyTime = copyTime;
    }

    public boolean isExpired(long now) {
        return copyTime + TTL < now;
    }

    public boolean isExpired() {
        final long now = System.currentTimeMillis();
        return copyTime + TTL < now;
    }

    public void delete() {
        if (!file.delete()) {
            log.warn("Error on deleted file: [{}]", file.getAbsolutePath());
        }
        log.debug("Delete file [{}]", file.getAbsolutePath());
    }
}
