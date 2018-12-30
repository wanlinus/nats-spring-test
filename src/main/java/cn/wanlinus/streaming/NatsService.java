package cn.wanlinus.streaming;

import cn.wanlinus.streaming.config.Subscribe;
import io.nats.streaming.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author wanli
 * @date 2018-11-16 11:21
 */
@Service
public class NatsService {

    @Subscribe(value = "bbb", durableName = "asd")
    public void asd(Message message) {
        String s = new String(message.getData(), StandardCharsets.UTF_8);
        System.out.println("收到消息: " + s);
    }
}
