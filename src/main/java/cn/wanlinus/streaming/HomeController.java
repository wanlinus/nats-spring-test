package cn.wanlinus.streaming;

import io.nats.streaming.StreamingConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author wanli
 * @date 2018-11-16 18:11
 */
@RestController
public class HomeController {

    @Autowired
    private StreamingConnection connection;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/send")
    public String asd(@RequestParam String str) throws InterruptedException, TimeoutException, IOException {
        System.out.println("发送消息: " + str);
        connection.publish("bbb", str.getBytes(StandardCharsets.UTF_8));
        return "asd";
    }

    @GetMapping("/shutdown")
    public void shutdown(@RequestParam(defaultValue = "0") String type) {
        if ("0".equals(type)) {
            System.out.println("System exit");
            System.exit(1);
        } else if ("1".equals(type)) {
            System.out.println("Spring exit");
            SpringApplication.exit(context);
        } else if ("2".equals(type)) {
            System.out.println("System exit Spring");
            System.exit(SpringApplication.exit(context));
        } else {
            System.out.println("Thread");
            Thread.currentThread().interrupt();
        }
    }

}
