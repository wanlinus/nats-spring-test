package cn.wanlinus.nats;

import cn.wanlinus.nats.config.Subscribe;
import io.nats.client.Message;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author wanli
 * @date 2018-11-20 14:09
 */
@Service
public class NatsService {

    @Subscribe("aaa")
    public void asd(Message message) {
        System.out.println("收到消息:" + new String(message.getData(), StandardCharsets.UTF_8));
    }
}
