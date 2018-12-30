package cn.wanlinus.nats.config;

import io.nats.client.Connection;
import io.nats.client.Dispatcher;
import io.nats.client.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * @author wanli
 * @date 2018-11-20 12:30
 */
public class NatsConfigBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NatsConfigBeanPostProcessor.class);

    private Connection connection;

    public NatsConfigBeanPostProcessor(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        final Class<?> clazz = bean.getClass();
        Arrays.stream(clazz.getMethods()).forEach(method -> {
            Optional<Subscribe> subOpt = Optional.ofNullable(AnnotationUtils.findAnnotation(method, Subscribe.class));
            subOpt.ifPresent(sub -> {
                final Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1 || !parameterTypes[0].equals(Message.class)) {
                    throw new RuntimeException(String.format(
                            "Method '%s' on bean with name '%s' must have a single parameter of type %s when using the @%s annotation.",
                            method.toGenericString(),
                            beanName,
                            Message.class.getName(),
                            Subscribe.class.getName()
                    ));
                }
                Dispatcher dispatcher = connection.createDispatcher(message -> {
                    try {
                        method.invoke(bean, message);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOGGER.error(String.format("Error for method invoke: %s", method.getName()));
                    }
                });
                dispatcher.subscribe(sub.value());
            });
        });
        return bean;
    }
}
