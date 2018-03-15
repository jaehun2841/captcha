package com.example.captcha.config.components.deformation;

import com.jhlabs.image.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Deformations {

	@Bean(autowire = Autowire.BY_NAME)
	public SphereFilter sphereFilter() {
		return new SphereFilter();
	}

	@Bean(autowire = Autowire.BY_NAME)
	public EmbossFilter embossFilter() {
		return new EmbossFilter();
	}

	@Bean(autowire = Autowire.BY_NAME)
	public WaterFilter waterFilter() {
		WaterFilter waterFilter = new WaterFilter();
		//waterFilter.setAmplitude(3.0f);
		//waterFilter.setPhase(20.0f);
		//waterFilter.setWavelength(70.0f);
		return waterFilter;
	}

	@Bean(autowire = Autowire.BY_NAME)
	public WeaveFilter weaveFilter() {
		WeaveFilter weaveFilter = new WeaveFilter();
		weaveFilter.setUseImageColors(false);
		weaveFilter.setXGap(2f);
		weaveFilter.setYGap(6f);
		weaveFilter.setXWidth(10f);
		weaveFilter.setYWidth(16f);

		return weaveFilter;
	}

	@Bean(autowire = Autowire.BY_NAME)
	public RippleFilter rippleFilter() {
		RippleFilter rippleFilter = new RippleFilter();
		rippleFilter.setWaveType(RippleFilter.NOISE);
		rippleFilter.setXAmplitude(2.0f);
		rippleFilter.setYAmplitude(2.0f);
		rippleFilter.setXWavelength(10.0f);
		rippleFilter.setYWavelength(10.0f);
		rippleFilter.setEdgeAction(1);
		return rippleFilter;
	}
}
