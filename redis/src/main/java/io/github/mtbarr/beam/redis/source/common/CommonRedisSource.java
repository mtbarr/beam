package io.github.mtbarr.beam.redis.source.common;

import io.github.mtbarr.beam.redis.source.AbstractRedisSource;
import io.github.mtbarr.beam.redis.source.credentials.RedisCredentials;
import redis.clients.jedis.Jedis;

public class CommonRedisSource extends AbstractRedisSource {


    public CommonRedisSource(RedisCredentials credentials) {
        super(credentials);
    }

    public CommonRedisSource() {
        this(RedisCredentials.DEFAULT_CREDENTIALS);
    }

    @Override
    public Jedis getJedisInstance() {
        RedisCredentials credentials = getCredentials();

        Jedis jedis = new Jedis(credentials.getHost(), credentials.getPort());
        if (credentials.hasUser() && credentials.hasPassword()) {
            jedis.auth(credentials.getPassword());
        }

        return jedis;
    }
}
