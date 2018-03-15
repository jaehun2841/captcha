package com.example.captcha.config.buffer;

import com.octo.captcha.engine.bufferedengine.ContainerConfiguration;
import com.octo.captcha.engine.bufferedengine.buffer.DiskCaptchaBuffer;
import com.octo.captcha.engine.bufferedengine.buffer.MemoryCaptchaBuffer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


@Configuration
public class Buffer {

	@Bean
	public ContainerConfiguration containerConfiguration() {

		int memorySize = 10000;			//Memory Buffer에서 유지 하려는 최소Captcha 갯수
		int persistentSize = 10000;		//Disk Buffer(ticketlink-captcha.data 파일)에 생성하는 최소 Catpcha갯수
		int swapSize = 1000;			//Disk Buffer(ticketlink-captcha.data 파일)로 부터 Memory로 Swap 하는 갯수
										// --> Memory에서 사용하면 File에서 미리 생성된 Captcha를 땡기는 구조 -> File에서 비면 배치가 돌면서 Captcha를 생성
		int feedSize = 10000;			//Memory Buffer에 생성하는 Captcha 갯수
		int feedBatchSize = 100;		//1회 배치에 생성하는 Captcha 갯수
		boolean serveOnlyLocales = true;
		Locale defaultLocale = Locale.getDefault();
		Map<Locale, Double> ratioMap = new HashMap<Locale, Double>() {
			{
				put(defaultLocale, 1.0);
			}
		};

		return new ContainerConfiguration(ratioMap, memorySize, persistentSize, swapSize,
				feedSize, feedBatchSize, serveOnlyLocales, defaultLocale);
	}

	@Bean
	public MemoryCaptchaBuffer memoryCaptchaBuffer() {

		MemoryCaptchaBuffer mBuffer = new MemoryCaptchaBuffer();
		return mBuffer;
	}

	@Bean
	public DiskCaptchaBuffer diskCaptchaBuffer() {

		//Captcha API에서 기존의 data파일과 index파일을 다시 읽는 경우
		//Exception이 발생 할 수 있다.
		//그 로직에서 파일을 지우고 다시 만드는 로직을 실행하는데,
		//FileInputStream에 대한 커넥션을 close하지 않고 지우는 로직이 있어 실제 파일이 지워지지 않는다.
		//따라서 서버 시작 시, 무조건 data파일과 index 파일을 지우고 시작한다.
		String fileName = "captcha";
		File dataFile = new File(fileName + ".data");
		File indexFile = new File(fileName + ".index");

		if(dataFile.exists()) {
			dataFile.delete();
		}
		if(indexFile.exists()) {
			indexFile.delete();
		}

		return new DiskCaptchaBuffer("captcha", true);
	}
}
