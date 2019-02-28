package org.fuelteam.watt.redis.jedis;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisAutoConfiguration {

    @Autowired
    private JedisProperties jedisProperties;

    private JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(jedisProperties.getMaxTotal());
        poolConfig.setMaxIdle(jedisProperties.getMaxIdle());
        poolConfig.setNumTestsPerEvictionRun(jedisProperties.getNumTestsPerEvictionRun());
        poolConfig.setTimeBetweenEvictionRunsMillis(jedisProperties.getTimeBetweenEvictionRunsMillis());
        poolConfig.setMinEvictableIdleTimeMillis(jedisProperties.getMinEvictableIdleTimeMillis());
        poolConfig.setSoftMinEvictableIdleTimeMillis(jedisProperties.getSoftMinEvictableIdleTimeMillis());
        poolConfig.setMaxWaitMillis(jedisProperties.getMaxWaitMillis());
        poolConfig.setTestOnBorrow(jedisProperties.isTestOnBorrow());
        poolConfig.setTestOnCreate(jedisProperties.isTestOnCreate());
        poolConfig.setTestOnReturn(jedisProperties.isTestOnReturn());
        poolConfig.setTestWhileIdle(jedisProperties.isTestWhileIdle());
        poolConfig.setBlockWhenExhausted(jedisProperties.isBlockWhenExhausted());
        return poolConfig;
    }

    @Bean
    public JedisCluster jedisCluster() {
        if (!jedisProperties.isClusterMode()) return null;
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        for (String str : jedisProperties.getNodes()) {
            String[] array = str.split(":");
            nodes.add(new HostAndPort(array[0], Integer.parseInt(array[1])));
        }
        JedisCluster jedisCluster = new JedisCluster(nodes, jedisProperties.getConnectionTimeout(),
                jedisProperties.getSoTimeout(), jedisProperties.getMaxAttempts(), jedisProperties.getPassword(),
                jedisPoolConfig());
        return jedisCluster;
    }

    @Bean
    public JedisPool jedisPool() {
        if (jedisProperties.isClusterMode()) return null;
        JedisPool jedisPool = new JedisPool(jedisPoolConfig(), jedisProperties.getHost(), jedisProperties.getPort(),
                jedisProperties.getTimeout(), jedisProperties.getPassword());
        return jedisPool;
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate() {
        final RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisConnectionFactory redisConnectionFactory() {
        RedisPassword redisPassword = RedisPassword.of(jedisProperties.getPassword());
        JedisConnectionFactory jcf;
        if (jedisProperties.isClusterMode()) {
            jcf = new JedisConnectionFactory(new RedisClusterConfiguration(jedisProperties.getNodes()), jedisPoolConfig());
        } else {
            RedisStandaloneConfiguration standalone = new RedisStandaloneConfiguration(jedisProperties.getHost(),
                    jedisProperties.getPort());
            standalone.setPassword(redisPassword);
            jcf = new JedisConnectionFactory(standalone);
        }
        return jcf;
    }
}
