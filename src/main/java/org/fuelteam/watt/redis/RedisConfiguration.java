package org.fuelteam.watt.redis;

import java.io.Serializable;
import java.time.Duration;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;

@Configuration
public class RedisConfiguration {

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate() {
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory());
        return template;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        if (RedisType.CLUSTER.getMode().equalsIgnoreCase(redisProperties.getMode())) {
            RedisClusterConfiguration rcc = new RedisClusterConfiguration();
            Set<RedisNode> nodes = Sets.newHashSet();
            for (String ipPort : redisProperties.getNodes()) {
                String[] ipAndPort = ipPort.split(":");
                nodes.add(new RedisNode(ipAndPort[0].trim(), Integer.valueOf(ipAndPort[1])));
            }
            rcc.setClusterNodes(nodes);
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                rcc.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            rcc.setMaxRedirects(redisProperties.getMaxRedirects());

            LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                    .commandTimeout(Duration.ofMillis(redisProperties.getCommandTimeoutMillis()))
                    .poolConfig(genericObjectPoolConfig()).build();
            return new LettuceConnectionFactory(rcc, clientConfig);
        } else {
            RedisStandaloneConfiguration rsc = new RedisStandaloneConfiguration();
            rsc.setDatabase(redisProperties.getDatabase());
            rsc.setHostName(redisProperties.getHost());
            rsc.setPort(redisProperties.getPort());
            if (!StringUtils.isEmpty(redisProperties.getPassword())) {
                rsc.setPassword(RedisPassword.of(redisProperties.getPassword()));
            }
            LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                    .commandTimeout(Duration.ofMillis(redisProperties.getCommandTimeoutMillis()))
                    .poolConfig(genericObjectPoolConfig()).build();
            return new LettuceConnectionFactory(rsc, clientConfig);
        }
    }

    @SuppressWarnings("rawtypes")
    private GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setBlockWhenExhausted(redisProperties.isBlockWhenExhausted());
        genericObjectPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        genericObjectPoolConfig.setMaxTotal(redisProperties.getMaxTotal());
        genericObjectPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(redisProperties.getMinEvictableIdleTimeMillis());
        genericObjectPoolConfig.setMinIdle(redisProperties.getMinIdle());
        genericObjectPoolConfig.setNumTestsPerEvictionRun(redisProperties.getNumTestsPerEvictionRun());
        genericObjectPoolConfig.setSoftMinEvictableIdleTimeMillis(redisProperties.getSoftMinEvictableIdleTimeMillis());
        genericObjectPoolConfig.setTestOnBorrow(redisProperties.isTestOnBorrow());
        genericObjectPoolConfig.setTestOnCreate(redisProperties.isTestOnCreate());
        genericObjectPoolConfig.setTestOnReturn(redisProperties.isTestOnReturn());
        genericObjectPoolConfig.setTestWhileIdle(redisProperties.isTestWhileIdle());
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getTimeBetweenEvictionRunsMillis());
        return genericObjectPoolConfig;
    }
}