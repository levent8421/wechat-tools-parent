package com.levent8421.wechat.tools.model.service.wechat;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.model.service.config.WebsiteConfigurationProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Create By leven ont 2020/9/10 22:33
 * Class Name :[WechatResourceGenerator]
 * <p>
 * 微信资源生成器
 *
 * @author leven
 */
@Component
public class WechatResourceGenerator {
    private static final String WECHAT_AUTH_URL_TEMPLATE =
            "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_userinfo&state=%s#wechat_redirect";
    private static final String WECHAT_AUTH_REDIRECT_URL_TEMPLATE = "%s/api/w/auth/%s";
    private static final String CHARSET = "UTF-8";
    private final WebsiteConfigurationProperties websiteConfigurationProperties;

    public WechatResourceGenerator(WebsiteConfigurationProperties websiteConfigurationProperties) {
        this.websiteConfigurationProperties = websiteConfigurationProperties;
    }

    /**
     * 生成微信授权链接
     *
     * @param merchant 商户
     * @return 链接
     * @throws WechatResourceException error
     */
    public String generateWechatAuthUrl(Merchant merchant, String state) throws WechatResourceException {
        checkWechatConfiguration(merchant);
        final String appId = merchant.getWechatAppId();
        final String redirectUri = generateWechatAuthRedirectUrl(merchant);
        final String encodedRedirectUri;
        try {
            encodedRedirectUri = URLEncoder.encode(redirectUri, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new WechatResourceException("Error on encode redirect uri:" + redirectUri, e);
        }
        return String.format(WECHAT_AUTH_URL_TEMPLATE, appId, encodedRedirectUri, state);
    }

    /**
     * 生成微信授权链接中的RedirectUri
     *
     * @param merchant 商户信息
     * @return redirect uri
     */
    private String generateWechatAuthRedirectUrl(Merchant merchant) {
        final String websiteBaseUrl = websiteConfigurationProperties.getBaseUrl();
        return String.format(WECHAT_AUTH_REDIRECT_URL_TEMPLATE, websiteBaseUrl, merchant.getSn());
    }

    private void checkWechatConfiguration(Merchant merchant) throws WechatResourceException {
        final String appId = merchant.getWechatAppId();
        final String secret = merchant.getWechatSecret();
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(secret)) {
            throw new WechatResourceException("商户微信公众号未配置");
        }
    }
}
