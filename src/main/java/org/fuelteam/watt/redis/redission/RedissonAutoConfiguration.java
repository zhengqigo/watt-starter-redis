package org.fuelteam.watt.redis.redission;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import org.fuelteam.watt.redis.redission.RedissonConditions.RedissonClientCondition;
import org.fuelteam.watt.redis.redission.RedissonConditions.RedissonCondition;
import org.fuelteam.watt.redis.redission.RedissonConditions.RedissonReactiveClientCondition;
import org.fuelteam.watt.redis.redission.RedissonProperties.ClusterServersConfig;
import org.fuelteam.watt.redis.redission.RedissonProperties.SingleServerConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass(Redisson.class)
@Conditional(RedissonCondition.class)
@AutoConfigureBefore(CacheAutoConfiguration.class)
@EnableConfigurationProperties(RedissonProperties.class)
@Import(RedissonCustomizer.class)
public class RedissonAutoConfiguration implements ResourceLoaderAware {

    private final RedissonProperties redissonProperties;

    private final List<Customizer<Config>> customizers;

    private ResourceLoader resourceLoader;

    public RedissonAutoConfiguration(RedissonProperties redissonProperties,
            ObjectProvider<List<Customizer<Config>>> customizersProvider) {
        this.redissonProperties = redissonProperties;
        this.customizers = customizersProvider.getIfAvailable(Collections::emptyList);
    }

    @Bean(destroyMethod = "shutdown")
    @Conditional(RedissonClientCondition.class)
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient(Config config) {
        return Redisson.create(config);
    }

    @Bean(destroyMethod = "shutdown")
    @Conditional(RedissonReactiveClientCondition.class)
    @ConditionalOnMissingBean(RedissonReactiveClient.class)
    public RedissonReactiveClient redissonReactiveClient(Config config) {
        return Redisson.createReactive(config);
    }

    @Bean
    @ConditionalOnMissingBean(Config.class)
    public Config config() {
        Config temp = configFromResource();
        Config config = temp != null ? temp : configFromSpring();
        customizers.forEach(customizer -> customizer.customize(config));
        return config;
    }

    private Config configFromResource() {
        String location = redissonProperties.getConfig().getLocation();
        if (StringUtils.isEmpty(location)) return null;
        Resource resource = ResourcePatternUtils.getResourcePatternResolver(resourceLoader).getResource(location);
        try {
            InputStream inputStream = resource.getInputStream();
            if (StringUtils.endsWithIgnoreCase(location, "json")) {
                return Config.fromJSON(inputStream);
            } else if (Stream.of("yml", "yaml").anyMatch(ext -> StringUtils.endsWithIgnoreCase(location, ext))) {
                return Config.fromYAML(inputStream);
            }
        } catch (IOException ioe) {}
        return null;
    }

    private Config configFromSpring() {
        Config config = new Config();
        configGlobal(config);
        switch (redissonProperties.getType()) {
            case SINGLE:
                configSingle(config);
                break;
            case CLUSTER:
                configCluster(config);
                break;
            default:
                throw new IllegalArgumentException("illegal redisson type: " + redissonProperties.getType());
        }
        return config;
    }

    private void configGlobal(Config config) {
        config.setCodec(redissonProperties.getCodec().getInstance()).setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads()).setExecutor(redissonProperties.getExecutor())
                .setKeepPubSubOrder(redissonProperties.isKeepPubSubOrder())
                .setTransportMode(redissonProperties.getTransportMode()).setEventLoopGroup(redissonProperties.getEventLoopGroup())
                .setLockWatchdogTimeout(redissonProperties.getLockWatchdogTimeout())
                .setAddressResolverGroupFactory(redissonProperties.getAddressResolverGroupFactory().getInstance())
                .setReferenceEnabled(redissonProperties.isReferenceEnabled());
    }

    private void configSingle(Config config) {
        SingleServerConfig properties = redissonProperties.getSingle();
        config.useSingleServer()
                // BaseConfig
                .setPassword(properties.getPassword()).setSubscriptionsPerConnection(properties.getSubscriptionsPerConnection())
                .setRetryAttempts(properties.getRetryAttempts()).setRetryInterval(properties.getRetryInterval())
                .setTimeout(properties.getTimeout()).setClientName(properties.getClientName())
                .setConnectTimeout(properties.getConnectTimeout()).setIdleConnectionTimeout(properties.getIdleConnectionTimeout())
                .setSslEnableEndpointIdentification(properties.isSslEnableEndpointIdentification())
                .setSslProvider(properties.getSslProvider()).setSslTruststore(properties.getSslTrustStore())
                .setSslTruststorePassword(properties.getSslKeystorePassword()).setSslKeystore(properties.getSslKeystore())
                .setSslKeystorePassword(properties.getSslKeystorePassword())
                .setPingConnectionInterval(properties.getPingConnectionInterval()).setKeepAlive(properties.isKeepAlive())
                .setTcpNoDelay(properties.isTcpNoDelay())
                // SingleServerConfig
                .setAddress(properties.getAddress()).setDatabase(properties.getDatabase())
                .setConnectionMinimumIdleSize(properties.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(properties.getConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(properties.getSubscriptionConnectionPoolSize())
                .setDnsMonitoringInterval(properties.getDnsMonitoringInterval());
    }

    private void configCluster(Config config) {
        ClusterServersConfig properties = redissonProperties.getCluster();
        config.useClusterServers()
                // BaseConfig
                .setPassword(properties.getPassword()).setSubscriptionsPerConnection(properties.getSubscriptionsPerConnection())
                .setRetryAttempts(properties.getRetryAttempts()).setRetryInterval(properties.getRetryInterval())
                .setTimeout(properties.getTimeout()).setClientName(properties.getClientName())
                .setConnectTimeout(properties.getConnectTimeout()).setIdleConnectionTimeout(properties.getIdleConnectionTimeout())
                .setSslEnableEndpointIdentification(properties.isSslEnableEndpointIdentification())
                .setSslProvider(properties.getSslProvider()).setSslTruststore(properties.getSslTrustStore())
                .setSslTruststorePassword(properties.getSslKeystorePassword()).setSslKeystore(properties.getSslKeystore())
                .setSslKeystorePassword(properties.getSslKeystorePassword())
                .setPingConnectionInterval(properties.getPingConnectionInterval()).setKeepAlive(properties.isKeepAlive())
                .setTcpNoDelay(properties.isTcpNoDelay())
                // BaseMasterSlaveServersConfig
                .setLoadBalancer(properties.getLoadBalancer().getInstance())
                .setMasterConnectionMinimumIdleSize(properties.getMasterConnectionMinimumIdleSize())
                .setMasterConnectionPoolSize(properties.getMasterConnectionPoolSize())
                .setSlaveConnectionMinimumIdleSize(properties.getSlaveConnectionMinimumIdleSize())
                .setSlaveConnectionPoolSize(properties.getSlaveConnectionPoolSize())
                .setSubscriptionConnectionMinimumIdleSize(properties.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(properties.getSubscriptionConnectionPoolSize())
                .setFailedSlaveCheckInterval(properties.getFailedSlaveCheckInterval())
                .setFailedSlaveReconnectionInterval(properties.getFailedSlaveReconnectionInterval())
                .setReadMode(properties.getReadMode()).setSubscriptionMode(properties.getSubscriptionMode())
                .setDnsMonitoringInterval(properties.getDnsMonitoringInterval())
                // ClusterServersConfig
                .addNodeAddress(properties.getAddresses()).setScanInterval(properties.getInterval());
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}