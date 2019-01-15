package com.example.captcha.config.components.font;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author by Carrey on 2018-05-03
 * Captcha Font 설정
 */
@Configuration
@Slf4j
public class Fonts {

	private static GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();

	@Bean
	public Font[] captchaFonts() {
		return new Font[]{
				getFont("Cinzel-Regular.otf"),
				getFont("EVILDEAD.TTF"),
				getFont("HelveticaOutBd.ttf"),
				getFont("Longhaul.ttf"),
				getFont("Roboto-Medium_0.ttf")
		};
	}

	public Font getFont(String fontFileName) {

		try (InputStream stream = getClass().getResourceAsStream("ttf/" + fontFileName)) {
			Font font = Font.createFont(Font.TRUETYPE_FONT, stream);
			font = font.deriveFont(30);

			//ttf 파일 -> GraphicsEnvironment에 폰트 등록 (시스템 폰트가 아니기 때문에 폰트를 등록해줘야 렌더링이 됨)
			graphicsEnvironment.registerFont(font);
			return font;
		} catch (FontFormatException e) {
			log.error("[Captcha.Fonts] FontFormatException : " + e.getMessage());
		} catch (IOException e) {
			log.error("[Captcha.Fonts] IOException : " + e.getMessage());
		}

		return new Font("Courier", Font.BOLD, 30);
	}
}
