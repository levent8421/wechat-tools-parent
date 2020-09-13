package com.levent8421.wechat.tools.web.user.view;

import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.utils.CollectionUtils;
import com.levent8421.wechat.tools.model.service.config.WebsiteConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 跳转到主页
     *
     * @param params 参数
     * @return view name
     */
    public String home(Map<String, String> params) {
        final List<String> pairs = new ArrayList<>();
        for (Map.Entry<String, String> param : params.entrySet()) {
            pairs.add(param.getKey() + "=" + param.getValue());
        }
        final String paramsStr = CollectionUtils.joinAsString(pairs.stream(), "&");
        return String.format("%s?%s", websiteConfigurationProperties.getBaseUrl(), paramsStr);
    }

    /**
     * 进入主页 完成token验证后跳转到next页面
     *
     * @param next  next
     * @param token web token
     * @return view name
     */
    public String to(String next, String token) {
        final Map<String, String> params = new HashMap<>(16);
        params.put("next", next);
        params.put("token", token);
        return home(params);
    }

    private String urlEncode(String str) {
        try {
            return URLEncoder.encode(str, CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new InternalServerErrorException(e);
        }
    }
}
