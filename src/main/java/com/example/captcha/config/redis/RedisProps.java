package com.example.captcha.config.redis;

import redis.clients.jedis.JedisShardInfo;

import java.util.ArrayList;
import java.util.List;

public class RedisProps {

	//Master와 Slave의 Sharding Key가 같아야 함
	public static List<JedisShardInfo> masterShards = new ArrayList<JedisShardInfo>() {
		{
			add(new JedisShardInfoWithPassword("192.168.137.103", 5001, "Master1", "password"));
			add(new JedisShardInfoWithPassword("192.168.137.103", 5003, "Master2", "password"));
			add(new JedisShardInfoWithPassword("192.168.137.103", 5005, "Master3", "password"));

		}
	};
	public static List<JedisShardInfo> slaveShards = new ArrayList<JedisShardInfo>() {
		{
			add(new JedisShardInfoWithPassword("192.168.137.103", 5002, "Master1", "password"));
			add(new JedisShardInfoWithPassword("192.168.137.103", 5004, "Master2", "password"));
			add(new JedisShardInfoWithPassword("192.168.137.103", 5006, "Master3", "password"));
		}
	};
}
