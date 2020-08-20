package com.levent8421.wechat.tools.web.user.controller;

import com.levent8421.wechat.tools.commons.exception.BadRequestException;
import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/17 19:59
 * Class Name: IndexController
 * Author: Levent8421
 * Description:
 * Index Controller
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/")
public class IndexController extends AbstractController {
    /**
     * Index GEt Request
     *
     * @return request
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error-page")
    public String errorPage() {
        throw new BadRequestException("TestError");
    }
}
