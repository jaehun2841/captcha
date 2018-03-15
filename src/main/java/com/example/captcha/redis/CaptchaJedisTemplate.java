package com.example.captcha.redis;

import com.octo.captcha.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.PostConstruct;
import java.util.Base64;

@Component
@Slf4j
public class CaptchaJedisTemplate {

    private static final int MINUTE = 60;

	@Autowired
	private ShardedJedisPool masterShardedPool;
	@Autowired
	private ShardedJedisPool slaveShardedPool;
	private RedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();

	@PostConstruct
	public void init() {
		//Connection Pool 생성
		//IDLE 객체 미리 생성하여 첫 캡차 호출 시 로딩 타임 단축
		ShardedJedis master = masterShardedPool.getResource();
		ShardedJedis slave = slaveShardedPool.getResource();
		master.close();
		slave.close();
	}

	public void set(String key, Captcha captcha) {

		try(ShardedJedis jedis = masterShardedPool.getResource()) {
			byte[] serialize = jdkSerializer.serialize(captcha);
			String byteValue = Base64.getEncoder().encodeToString(serialize);

			ShardedJedisPipeline pipeline = jedis.pipelined();
			pipeline.set(key, byteValue);
			pipeline.expire(key, 3 * MINUTE);
			pipeline.sync();

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public Captcha get(String key) {

		Captcha captcha = null;
		try(ShardedJedis jedis = slaveShardedPool.getResource()) {
			String value = jedis.get(key);
			byte[] serializeData = Base64.getDecoder().decode(value);
			captcha = (Captcha)jdkSerializer.deserialize(serializeData);

		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return captcha;
	}
}
