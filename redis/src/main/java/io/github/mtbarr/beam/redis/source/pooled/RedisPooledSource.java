package io.github.mtbarr.beam.redis.source.pooled;

import io.github.mtbarr.beam.redis.source.AbstractRedisSource;
import io.github.mtbarr.beam.redis.source.credentials.RedisCredentials;
import org.jetbrains.annotations.NotNull;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisPooledSource extends AbstractRedisSource {

    private final JedisPool pool;

    public RedisPooledSource(@NotNull RedisCredentials credentials) {
        super(credentials);

        this.pool = (credentials.hasUser() && credentials.hasPassword())
          ? new JedisPool(credentials.getHost(), credentials.getPort())
          : new JedisPool(credentials.getHost(), credentials.getPort(), credentials.getUser(), credentials.getPassword());
    }

    public RedisPooledSource() {
        this(RedisCredentials.DEFAULT_CREDENTIALS);
    }

    @Override
    public Jedis getJedisInstance() {
        return pool.getResource();
    }
}
