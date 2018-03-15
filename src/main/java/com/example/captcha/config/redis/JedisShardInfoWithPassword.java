package com.example.captcha.config.redis;

import redis.clients.jedis.JedisShardInfo;

public class JedisShardInfoWithPassword extends JedisShardInfo {

	public JedisShardInfoWithPassword(String host, int port, String shardName) {
		super(host, port, shardName);
	}

	public JedisShardInfoWithPassword(String host, int port, String shardName, String password) {
		this(host, port, shardName);
		setPassword(password);
	}
}
