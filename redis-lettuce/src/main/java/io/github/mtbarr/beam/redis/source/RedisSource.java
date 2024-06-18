package io.github.mtbarr.beam.redis.source;

import io.lettuce.core.RedisClient;
import org.jetbrains.annotations.NotNull;

public class RedisSource {

    public static RedisSource create(String url) {
        return new RedisSource(url);
    }

    private final String url;
    private final RedisClient client;

    private RedisSource(@NotNull String url) {
        this.url = url;
        this.client = RedisClient.create(url);
    }

    public RedisClient getClient() {
        return client;
    }

    public String getUrl() {
        return url;
    }
}