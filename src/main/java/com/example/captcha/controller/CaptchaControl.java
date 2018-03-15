package com.example.captcha.controller;

import com.example.captcha.service.RedisManageableCaptchaService;
import com.octo.captcha.engine.bufferedengine.BufferedEngineContainer;
import com.octo.captcha.engine.bufferedengine.buffer.CaptchaBuffer;
import com.octo.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/captcha")
public class CaptchaControl {

    @Autowired
    private CaptchaService captchaService;

    @RequestMapping(value = "/auth")
    public String captchView(Model model) {

        String captchaId = UUID.randomUUID().toString();
        model.addAttribute("uuid", captchaId);
        return "/captcha";
    }

    @RequestMapping(value = "/status")
    @ResponseBody
    public Map<String, Integer> getStatuses() {

        BufferedEngineContainer engine = (BufferedEngineContainer)((RedisManageableCaptchaService)captchaService).getEngine();
        CaptchaBuffer diskBuffer = engine.getPersistentBuffer();
        CaptchaBuffer memoryBuffer = engine.getVolatileBuffer();

        int diskBufferSize = diskBuffer.size();
        int memoryBufferSize = memoryBuffer.size();

        Map<String, Integer> status = new HashMap<>();
        status.put("DiskBufferSize", diskBufferSize);
        status.put("MemoryBufferSize", memoryBufferSize);

        return status;
    }
}
