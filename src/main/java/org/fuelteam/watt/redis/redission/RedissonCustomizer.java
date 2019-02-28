package org.fuelteam.watt.redis.redission;

import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ClassUtils;

public class RedissonCustomizer {

    private final static String className = "io.netty.channel.epoll.EpollEventLoopGroup";

    private static boolean epoll = ClassUtils.isPresent(className, RedissonCustomizer.class.getClassLoader());

    @Bean
    public Customizer<Config> transportModeCustomizer() {
        return config -> {
            if (config.getTransportMode() == null) {
                if (epoll) {
                    config.setTransportMode(TransportMode.EPOLL);
                } else {
                    config.setTransportMode(TransportMode.NIO);
                }
            }
        };
    }
}