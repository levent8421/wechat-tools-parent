package com.levent8421.wechat.tools.resource.impl;

import com.levent8421.wechat.tools.commons.entity.Merchant;
import com.levent8421.wechat.tools.commons.exception.InternalServerErrorException;
import com.levent8421.wechat.tools.commons.qr.QrCodeEncoder;
import com.levent8421.wechat.tools.commons.qr.QrCodeEncoderFactory;
import com.levent8421.wechat.tools.commons.utils.SerialNumberGenerator;
import com.levent8421.wechat.tools.resource.MerchantResourceService;
import com.levent8421.wechat.tools.resource.ResourcePathService;
import com.levent8421.wechat.tools.resource.config.ResourceConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Create By leven ont 2020/9/14 20:39
 * Class Name :[MerchantResourceServiceImpl]
 * <p>
 * 商户静态资源处理器实现
 *
 * @author leven
 */
@Service
public class MerchantResourceServiceImpl extends AbstractEntityResourceService<Merchant> implements MerchantResourceService {
    private static final String WECHAT_AUTH_PATH = "/api/w/to-auth/";
    private static final int QR_CODE_WIDTH = 300;
    private static final int QR_CODE_HEIGHT = 300;
    private static final int QR_CODE_MARGIN = 2;
    private final ResourceConfigurationProperties resourceConfigurationProperties;
    private final SerialNumberGenerator fileNameGenerator = new SerialNumberGenerator("logo", "m", 5);
    private final QrCodeEncoder qrCodeEncoder = QrCodeEncoderFactory.getEncoder(QrCodeEncoderFactory.ZXING);
    private final ResourcePathService resourcePathService;

    protected MerchantResourceServiceImpl(ResourcePathService resourcePathService,
                                          ResourceConfigurationProperties resourceConfigurationProperties) {
        super(resourcePathService);
        this.resourcePathService = resourcePathService;
        this.resourceConfigurationProperties = resourceConfigurationProperties;
    }

    @Override
    public void resolveStaticPath(Merchant entity) {
        if (entity == null) {
            return;
        }
        if (isNotBlank(entity.getLogoPath())) {
            final String logoPath = withServer(resourceConfigurationProperties.getMerchantLogoPath(), entity.getLogoPath());
            entity.setLogoPath(logoPath);
        }
    }

    @Override
    public String saveLogo(MultipartFile logoFile) {
        return saveFile(fileNameGenerator, logoFile, resourceConfigurationProperties.getMerchantLogoPath());
    }

    @Override
    public String generateQrCode(String baseUrl, String sn) {
        final String qrCodePath = resourceConfigurationProperties.getMerchantQrCodePath();
        final String filename = String.format("%s.png", sn);
        final String targetPath = withRootPath(qrCodePath);
        createPath(targetPath);
        final String filePath = withRootPath(qrCodePath, filename);
        final File file = new File(filePath);
        if (!file.exists()) {
            generateQrCodeFile(file, baseUrl, sn);
        }
        return withServer(qrCodePath, filename);
    }

    private void createPath(String path) {
        final File file = new File(path);
        if (!file.isDirectory()) {
            if (file.exists()) {
                throw new InternalServerErrorException("Can not create dir:"
                        + file.getAbsolutePath()
                        + ", file early exists!");
            }
            if (!file.mkdirs()) {
                throw new InternalServerErrorException("Can not create dir:" + file.getAbsolutePath());
            }
        }
    }

    private void generateQrCodeFile(File file, String baseUrl, String sn) {
        final String qrCodeContent = getHomeUrl(baseUrl, sn);
        try (final FileOutputStream out = new FileOutputStream(file)) {
            qrCodeEncoder.encodeToStream(qrCodeContent, QR_CODE_WIDTH, QR_CODE_HEIGHT, QR_CODE_MARGIN, out);
        } catch (IOException e) {
            throw new InternalServerErrorException("Error on generate qrCodeFile, [" +
                    file.getAbsolutePath()
                    + "]", e);
        }
    }

    @Override
    public String getHomeUrl(String baseUrl, String sn) {
        return resourcePathService.joinPath(baseUrl, WECHAT_AUTH_PATH, sn);
    }
}
