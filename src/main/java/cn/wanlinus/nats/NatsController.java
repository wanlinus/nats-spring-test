package cn.wanlinus.nats;

import io.nats.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author wanli
 * @date 2018-11-20 14:11
 */
@RestController
public class NatsController {

    @Autowired
    private Connection connection;

    @GetMapping("/send")
    public String send(@RequestParam String str) {
        System.out.println("发送消息: " + str);
        connection.publish("aaa", str.getBytes(StandardCharsets.UTF_8));
        return str;
    }
}
