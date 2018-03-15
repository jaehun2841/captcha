package com.example.captcha.config.components.font;

import com.example.captcha.config.components.size.Size;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class Fonts {

	@Bean
	public Font[] captchaFonts() {
		return new Font[] {
			new Font("Arial", Font.BOLD, Size.FONT_SIZE),
			new Font("Times New Roman", Font.ROMAN_BASELINE, Size.FONT_SIZE),
			new Font("Georgia", Font.ITALIC, Size.FONT_SIZE),
			new Font("Courier", Font.PLAIN, Size.FONT_SIZE),
			new Font("Verdana", Font.LAYOUT_NO_LIMIT_CONTEXT, Size.FONT_SIZE),
			new Font("Helvetica", Font.HANGING_BASELINE, Size.FONT_SIZE),
			new Font("Comic Sans Serif", Font.TYPE1_FONT, Size.FONT_SIZE)
		};
	}
}
