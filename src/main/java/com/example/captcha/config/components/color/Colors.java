package com.example.captcha.config.components.color;

import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.RandomListColorGenerator;
import com.octo.captcha.component.image.color.RandomRangeColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
public class Colors {

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator redRangeColorGenerator() {
		return new RandomRangeColorGenerator(
				new int[]{0, 255},   //red
				new int[]{100, 150}, //green
				new int[]{200, 255}, //blue
				new int[]{255, 255}  //alpha
		);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator greenRangeColorGenerator() {
		return new RandomRangeColorGenerator(
				new int[]{150, 255}, //red
				new int[]{0, 255},	 //green
				new int[]{200, 255}, //blue
				new int[]{255, 255}  //alpha
		);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator blueRangeColorGenerator() {
		return new RandomRangeColorGenerator(
				new int[]{150, 255}, //red
				new int[]{0, 50},	 //green
				new int[]{0, 255}, //blue
				new int[]{255, 255}  //alpha
		);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator randomRangeColorGenerator() {
		return new RandomRangeColorGenerator(
				new int[]{0, 255}, //red
				new int[]{0, 255},	 //green
				new int[]{0, 255}, //blue
				new int[]{0, 255} //alpha
		);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator fontRangeColorGenerator() {
		return new RandomListColorGenerator(new Color[]{
				Color.BLACK,
				Color.GREEN,
				Color.RED,
				Color.BLUE,
				Color.lightGray,
				Color.PINK
		});
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator whiteColorGenerator() {
		return new SingleColorGenerator(Color.WHITE);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public ColorGenerator blackFontColorGenerator() {
		return new SingleColorGenerator(Color.BLACK);
	}
}
