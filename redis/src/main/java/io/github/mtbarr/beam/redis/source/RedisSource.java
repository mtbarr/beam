package io.github.mtbarr.beam.redis.source;

import io.github.mtbarr.beam.redis.source.credentials.RedisCredentials;
import redis.clients.jedis.Jedis;

public interface RedisSource {

    RedisCredentials getCredentials();

    Jedis getJedisInstance();
}
