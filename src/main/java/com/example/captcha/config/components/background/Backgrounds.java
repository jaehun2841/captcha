package com.example.captcha.config.components.background;

import com.example.captcha.config.components.size.Size;
import com.octo.captcha.component.image.backgroundgenerator.*;
import com.octo.captcha.component.image.color.ColorGenerator;
import com.octo.captcha.component.image.color.SingleColorGenerator;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

@Configuration
@ComponentScan(basePackages = "com.example.captcha.config.components.color")
public class Backgrounds {

	@Autowired
	private ColorGenerator randomRangeColorGenerator;
	@Autowired
	private ColorGenerator redRangeColorGenerator;
	@Autowired
	private ColorGenerator greenRangeColorGenerator;
	@Autowired
	private ColorGenerator blueRangeColorGenerator;

	@Bean(autowire = Autowire.BY_NAME)
	public UniColorBackgroundGenerator uniColorBackgroundGenerator() {
		return new UniColorBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public FunkyBackgroundGenerator funkyBackgroundGenerator() {
		return new FunkyBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT,
				redRangeColorGenerator,
				greenRangeColorGenerator,
				blueRangeColorGenerator,
				randomRangeColorGenerator,
				0.2f
		);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public EllipseBackgroundGenerator eclipseBackgroundGenerator() {
		return new EllipseBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT);
	}

	@Bean(autowire = Autowire.BY_NAME)
	public GradientBackgroundGenerator gradientBackgroundGenerator() {
		return new GradientBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT, Color.WHITE, Color.WHITE);
	}


	@Bean(autowire = Autowire.BY_NAME)
	public MultipleShapeBackgroundGenerator multipleShapeBackgroundGenerator() {
		//return new MultipleShapeBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT);
		Color firstEllipseColorGenerator = new Color(255, 255, 255);
		Color secondEllipseColorGenerator = new Color(255, 255, 255);
		Color firstRectangleColorGenerator = new Color(210, 210, 210);
		Color secondRectangleColorGenerator = new Color(255, 255, 255);
		Integer spaceBetweenLine = new Integer(8);
		Integer spaceBetweenCircle = new Integer(8);
		Integer ellipseHeight = new Integer(8);
		Integer ellipseWidth = new Integer(8);
		Integer rectangleWidth = new Integer(3);
		return new MultipleShapeBackgroundGenerator(Size.IMAGE_WIDTH, Size.IMAGE_HEIGHT, firstEllipseColorGenerator, secondEllipseColorGenerator, spaceBetweenLine, spaceBetweenCircle, ellipseHeight, ellipseWidth, firstRectangleColorGenerator, secondRectangleColorGenerator, rectangleWidth);
	}
}
