package io.github.mtbarr.beam.redis.source.credentials;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


public class RedisCredentials {

    public static final RedisCredentials DEFAULT_CREDENTIALS = new RedisCredentials("localhost", 6379, null, null);

    private final String host;
    private final int port;
    private final String user;
    private final String password;

    public RedisCredentials(@NotNull String host, int port, @Nullable String user, @Nullable String password) {
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        StringBuilder url = new StringBuilder("redis://");
        if (user != null) {
            url.append(user);
            if (password != null) {
                url.append(":").append(password);
            }
            url.append("@");
        }

        url.append(host).append(":").append(port);
        return url.toString();
    }

    @NotNull
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Nullable
    public String getUser() {
        return user;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public boolean hasUser() {
        return user != null;
    }

    public boolean hasPassword() {
        return password != null;
    }
}
