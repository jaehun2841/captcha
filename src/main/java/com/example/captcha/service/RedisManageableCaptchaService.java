package com.example.captcha.service;

import com.example.captcha.redis.CaptchaJedisTemplate;
import com.octo.captcha.Captcha;
import com.octo.captcha.engine.CaptchaEngine;
import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.multitype.GenericManageableCaptchaService;

import java.awt.image.BufferedImage;
import java.util.Locale;

public class RedisManageableCaptchaService extends GenericManageableCaptchaService {

	private CaptchaJedisTemplate jedisTemplate;

	public RedisManageableCaptchaService(CaptchaEngine captchaEngine,
			int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize) {
		super(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize);
	}

	public RedisManageableCaptchaService(CaptchaEngine captchaEngine,
			int minGuarantedStorageDelayInSeconds, int maxCaptchaStoreSize, CaptchaJedisTemplate jedisTemplate) {
		this(captchaEngine, minGuarantedStorageDelayInSeconds, maxCaptchaStoreSize);
		this.jedisTemplate = jedisTemplate;
	}

	@Override
	public BufferedImage getImageChallengeForID(String ID, Locale locale) throws CaptchaServiceException {
		Captcha captcha;
		if (!this.store.hasCaptcha(ID)) {
			captcha = this.generateAndStoreCaptcha(locale, ID);
		} else {
			captcha = this.store.getCaptcha(ID);
			if (captcha == null) {
				captcha = this.generateAndStoreCaptcha(locale, ID);
			} else if (captcha.hasGetChalengeBeenCalled()) {
				captcha = this.generateAndStoreCaptcha(locale, ID);
			}
		}
		jedisTemplate.set(ID, captcha);
		Object challenge = this.getChallengeClone(captcha);
		captcha.disposeChallenge();
		return (BufferedImage)challenge;
	}

	@Override
	public Boolean validateResponseForID(String ID, Object response) throws CaptchaServiceException {

 		Captcha captcha = jedisTemplate.get(ID);
 		if (captcha == null) {
			throw new CaptchaServiceException("Invalid ID, could not validate unexisting or already validated captcha");
		} else {
			Boolean valid = captcha.validateResponse(response);
			this.store.removeCaptcha(ID);
			return valid;
		}
	}
}
