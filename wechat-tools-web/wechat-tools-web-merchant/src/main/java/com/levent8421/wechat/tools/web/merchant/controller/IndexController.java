package com.levent8421.wechat.tools.web.merchant.controller;

import com.levent8421.wechat.tools.web.commons.controller.AbstractController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create By Levent8421
 * Create Time: 2020/8/18 0:00
 * Class Name: IndexController
 * Author: Levent8421
 * Description:
 * Indx Controller
 *
 * @author Levent8421
 */
@RestController
@RequestMapping("/")
public class IndexController extends AbstractController {
    /**
     * Jump to index page
     *
     * @return view
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
