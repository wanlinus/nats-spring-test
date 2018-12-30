package cn.wanlinus.nats.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author wanli
 * @date 2018-11-20 11:38
 */
@ConfigurationProperties(prefix = "spring.nats")
public class NatsProperties {
    private String[] natsUrls = {"nats://127.0.0.1:4200"};
    private String token = null;
    /**
     * Default maximum number of reconnect attempts
     */
    private int maxReconnect = 60;
    /**
     * Default wait time before attempting reconnection to the same server
     */
    private int reconnectWait = 2;
    /**
     * Default connection timeout
     */
    private int connectionTimeout = 2;

    public String[] getNatsUrls() {
        return natsUrls;
    }

    public void setNatsUrls(String[] natsUrls) {
        this.natsUrls = natsUrls;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getMaxReconnect() {
        return maxReconnect;
    }

    public void setMaxReconnect(int maxReconnect) {
        this.maxReconnect = maxReconnect;
    }

    public int getReconnectWait() {
        return reconnectWait;
    }

    public void setReconnectWait(int reconnectWait) {
        this.reconnectWait = reconnectWait;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }
}
