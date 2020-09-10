package com.levent8421.wechat.tools.web.user.view;

import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.model.service.config.WebsiteConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Create By leven ont 2020/9/11 0:26
 * Class Name :[ViewNameGenerator]
 * <p>
 * 试图名称生成器
 *
 * @author leven
 */
@Component
public class ViewNameGenerator {
    private static final String CHARSET = "UTF-8";
    private static final String ERROR_PAGE = "/error";
    private final WebsiteConfigurationProperties websiteConfigurationProperties;

    public ViewNameGenerator(WebsiteConfigurationProperties websiteConfigurationProperties) {
        this.websiteConfigurationProperties = websiteConfigurationProperties;
    }

    /**
     * 错误页面视图名称
     *
     * @param message 错误信息
     * @return view name
     */
    public String errorPage(String message) {
        return String.format("%s/index.html#%s?message=%s",
                websiteConfigurationProperties.getBaseUrl(), ERROR_PAGE, urlEncode(message));
    }

    /**
     * 主页
     *
     * @param hash hash router
     * @return view name
     */
    public String home(String hash) {
        return String.format("%s/#%s", websiteConfigurationProperties.getBaseUrl(), hash);
    }

    /**
     * 主页
     *
     * @return view name
     */
    public String home() {
        return websiteConfigurationProperties.getBaseUrl();
    }

    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
