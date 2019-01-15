package com.example.captcha.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/captcha")
public class CaptchaControl {

    @RequestMapping(value = "/auth")
    public String captchView(Model model) {

        String captchaId = UUID.randomUUID().toString();
        model.addAttribute("uuid", captchaId);
        return "/captcha";
    }
}
