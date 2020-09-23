package com.levent8421.wechat.tools.web.commons.profile;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Create By leven ont 2020/9/24 0:31
 * Class Name :[ProfileHelpher]
 * <p>
 * 配置文件帮助类
 *
 * @author leven
 */
@Component
public class ProfileHelpher implements ApplicationContextAware {
    private static final String DEV_PROFILE = "dev";
    private static final String RELEASE_PROFILE = "release";
    private List<String> activeProfiles;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        final String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
        this.activeProfiles = Arrays.asList(profiles);
    }

    public boolean isActiveProfile(String profileName) {
        return activeProfiles.contains(profileName);
    }

    public boolean isDev() {
        return isActiveProfile(DEV_PROFILE);
    }

    public boolean isRelease() {
        return isActiveProfile(RELEASE_PROFILE);
    }
}
