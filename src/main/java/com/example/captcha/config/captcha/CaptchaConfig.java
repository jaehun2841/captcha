package com.example.captcha.config.captcha;

import com.example.captcha.redis.CaptchaJedisTemplate;
import com.example.captcha.service.RedisManageableCaptchaService;
import com.example.captcha.servlet.CaptchaServlet;
import com.jhlabs.image.*;
import com.octo.captcha.CaptchaFactory;
import com.octo.captcha.component.image.backgroundgenerator.BackgroundGenerator;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.deformation.ImageDeformationByFilters;
import com.octo.captcha.component.image.fontgenerator.FontGenerator;
import com.octo.captcha.component.image.fontgenerator.RandomFontGenerator;
import com.octo.captcha.component.image.textpaster.DecoratedRandomTextPaster;
import com.octo.captcha.component.image.textpaster.TextPaster;
import com.octo.captcha.component.image.textpaster.textdecorator.BaffleTextDecorator;
import com.octo.captcha.component.image.textpaster.textdecorator.TextDecorator;
import com.octo.captcha.component.image.wordtoimage.DeformedComposedWordToImage;
import com.octo.captcha.component.image.wordtoimage.WordToImage;
import com.octo.captcha.component.word.wordgenerator.RandomWordGenerator;
import com.octo.captcha.component.word.wordgenerator.WordGenerator;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.engine.GenericCaptchaEngine;
import com.octo.captcha.engine.bufferedengine.ContainerConfiguration;
import com.octo.captcha.engine.bufferedengine.SimpleBufferedEngineContainer;
import com.octo.captcha.engine.bufferedengine.buffer.DiskCaptchaBuffer;
import com.octo.captcha.engine.bufferedengine.buffer.MemoryCaptchaBuffer;
import com.octo.captcha.image.gimpy.GimpyFactory;
import com.octo.captcha.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.awt.*;
import java.awt.image.ImageFilter;

@Configuration
@ComponentScan(basePackages = {"com.example.captcha.config.*", "com.example.captcha.redis"})
public class CaptchaConfig {

    private static final String ACCEPT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int FEED_PERIOD_MILLISEC = 1000;
    private static final int SWAP_PRERIOD_MILLISEC = 1000;
    private static final int MIN_GUARANTED_STORAGE_DELAY_IN_SECOND = 10;
    private static final int MAX_CAPTCHA_STORE_SIZE = 180000;
    private static final int MIN_QUESTION_WORD_LENGTH = 5;
    private static final int MAX_QUESTION_WORD_LENGTH = 5;
    private static final int QUESTION_FONT_SIZE = 30;

    @Autowired
    private Font[] captchaFonts;
    @Autowired
    private ColorGenerator blackFontColorGenerator;
    @Autowired
    private BackgroundGenerator funkyBackgroundGenerator;
	@Autowired
	private BackgroundGenerator multipleShapeBackgroundGenerator;
    @Autowired
    private ContainerConfiguration containerConfiguration;
    @Autowired
    private MemoryCaptchaBuffer memoryCaptchaBuffer;
    @Autowired
    private DiskCaptchaBuffer diskCaptchaBuffer;
    @Autowired
    private CaptchaJedisTemplate jedisTemplate;

    /*@Bean
    public CaptchaService captchaService() {

        WordGenerator wordGenerator = new RandomWordGenerator(ACCEPT_CHARS);
        FontGenerator fontGenerator = new RandomFontGenerator(QUESTION_FONT_SIZE, QUESTION_FONT_SIZE, captchaFonts);

        TextPaster textPaster = new RandomTextPaster(MIN_QUESTION_WORD_LENGTH, MAX_QUESTION_WORD_LENGTH, fontRangeColorGenerator, true);
        WordToImage wordToImage = new DeformedComposedWordToImage(fontGenerator, funkyBackgroundGenerator, textPaster,
                new ImageDeformationByFilters(new ImageFilter[]{}),
                new ImageDeformationByFilters(new ImageFilter[]{}),
                new ImageDeformationByFilters(new ImageFilter[]{})
        );

        GimpyFactory captchaFactory = new GimpyFactory(wordGenerator, wordToImage);
        CaptchaEngine genericCaptchaEngine = new GenericCaptchaEngine(new CaptchaFactory[]{captchaFactory});

        CaptchaEngine captchaEngine = new SimpleBufferedEngineContainer(genericCaptchaEngine, memoryCaptchaBuffer, diskCaptchaBuffer, containerConfiguration, FEED_PERIOD_MILLISEC, SWAP_PRERIOD_MILLISEC);
        return new RedisManageableCaptchaService(captchaEngine, MIN_GUARANTED_STORAGE_DELAY_IN_SECOND, MAX_CAPTCHA_STORE_SIZE, jedisTemplate);
    }

*/
    @Bean
    public CaptchaService simpleCaptchaService() {

        WordGenerator wordGenerator = new RandomWordGenerator(ACCEPT_CHARS);
        FontGenerator fontGenerator = new RandomFontGenerator(QUESTION_FONT_SIZE, QUESTION_FONT_SIZE, captchaFonts);

        TextPaster textPaster = new DecoratedRandomTextPaster(MIN_QUESTION_WORD_LENGTH, MAX_QUESTION_WORD_LENGTH, blackFontColorGenerator, new TextDecorator[]{new BaffleTextDecorator(0, Color.WHITE)});
        WordToImage wordToImage = new DeformedComposedWordToImage(fontGenerator, multipleShapeBackgroundGenerator, textPaster,
                new ImageDeformationByFilters(new ImageFilter[]{}),
                new ImageDeformationByFilters(new ImageFilter[]{}),
                new ImageDeformationByFilters(new ImageFilter[]{})
        );

        GimpyFactory captchaFactory = new GimpyFactory(wordGenerator, wordToImage);
        CaptchaEngine genericCaptchaEngine = new GenericCaptchaEngine(new CaptchaFactory[]{captchaFactory});

        CaptchaEngine captchaEngine = new SimpleBufferedEngineContainer(genericCaptchaEngine, memoryCaptchaBuffer, diskCaptchaBuffer, containerConfiguration, FEED_PERIOD_MILLISEC, SWAP_PRERIOD_MILLISEC);
        return new RedisManageableCaptchaService(captchaEngine, MIN_GUARANTED_STORAGE_DELAY_IN_SECOND, MAX_CAPTCHA_STORE_SIZE, jedisTemplate);
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CaptchaServlet());
        registrationBean.addUrlMappings("/captcha/view");
        return registrationBean;
    }
}
