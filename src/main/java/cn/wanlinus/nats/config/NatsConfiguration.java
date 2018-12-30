package cn.wanlinus.nats.config;

import cn.wanlinus.streaming.config.NatsListener;
import io.nats.client.Connection;
import io.nats.client.Nats;
import io.nats.client.Options;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.io.IOException;
import java.time.Duration;

/**
 * @author wanli
 * @date 2018-11-20 11:40
 */
@Configuration
@EnableConfigurationProperties(NatsProperties.class)
public class NatsConfiguration {

    @Bean
    public Connection natsConnection(NatsProperties properties) throws IOException, InterruptedException {
        Options.Builder builder = new io.nats.client.Options.Builder()
                .servers(properties.getNatsUrls())
                .token(properties.getToken())
                .connectionListener(new NatsListener())
                .maxReconnects(properties.getMaxReconnect())
                .reconnectWait(Duration.ofSeconds(properties.getReconnectWait()))
                .connectionTimeout(Duration.ofSeconds(properties.getConnectionTimeout()));
        return Nats.connect(builder.build());
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public NatsConfigBeanPostProcessor configBeanPostProcessor(Connection connection) {
        return new NatsConfigBeanPostProcessor(connection);
    }

}
