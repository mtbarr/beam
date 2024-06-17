package io.github.mtbarr.beam.redis.source;

import io.github.mtbarr.beam.redis.source.credentials.RedisCredentials;

public abstract class AbstractRedisSource implements RedisSource {

    private final RedisCredentials credentials;

    public AbstractRedisSource(RedisCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public RedisCredentials getCredentials() {
        return credentials;
    }
}
