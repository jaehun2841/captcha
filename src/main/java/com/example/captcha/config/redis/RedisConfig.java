package com.example.captcha.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

@Configuration
public class RedisConfig {

	@Bean
	public ShardedJedisPool masterShardedPool() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		return new ShardedJedisPool(poolConfig, RedisProps.masterShards, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	@Bean
	public ShardedJedisPool slaveShardedPool() {

		JedisPoolConfig poolConfig = new JedisPoolConfig();
		return new ShardedJedisPool(poolConfig, RedisProps.slaveShards, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}
}
