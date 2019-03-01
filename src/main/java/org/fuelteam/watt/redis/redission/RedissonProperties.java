package org.fuelteam.watt.redis.redission;

import java.net.URI;
import java.util.concurrent.ExecutorService;

import org.fuelteam.watt.redis.RedisType;
import org.redisson.config.ReadMode;
import org.redisson.config.SslProvider;
import org.redisson.config.SubscriptionMode;
import org.redisson.config.TransportMode;
import org.springframework.boot.context.properties.ConfigurationProperties;

import io.netty.channel.EventLoopGroup;

@ConfigurationProperties(prefix = "spring.redisson")
public class RedissonProperties {

    // 客户端模式
    private ClientMode clientMode = ClientMode.DEFAULT;

    // 线程池数量
    private int threads = 0;

    // Netty线程池数量
    private int nettyThreads = 0;

    // 序列化和反序列化的类型
    private CodecType codec = CodecType.JACKSON;

    // 单独提供一个线程池实例
    private ExecutorService executor;

    // 参考功能的配置选项
    private boolean referenceEnabled = true;

    // TransportMode, 默认 linux=epoll/other=nio
    private TransportMode transportMode;

    // 单独指定EventLoopGroup
    private EventLoopGroup eventLoopGroup;

    // 锁监视器的超时时间(单位:秒)
    private long lockWatchdogTimeout = 10 * 1000;

    // 是否顺序处理或并发处理PubSub消息
    private boolean keepPubSubOrder = true;

    // 服务端模式
    private RedisType type = RedisType.SINGLE;

    // 地址解析器
    private AddressResolverGroupFactoryType addressResolverGroupFactory = AddressResolverGroupFactoryType.DEFAULT;

    // 基础配置
    private Config config = new Config();

    // 单节点模式
    private SingleServerConfig single = new SingleServerConfig();

    // 集群模式
    private ClusterServersConfig cluster = new ClusterServersConfig();

    public static class Config {

        private String location = null;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    private static class BaseConfig {

        // 连接空闲超时时间(单位:秒)
        private int idleConnectionTimeout = 10 * 1000;

        // PING命令的超时时间(单位:秒)
        private int pingTimeout = 1 * 1000;

        // PING命令的发送时间间隔(单位:秒)
        private int pingConnectionInterval = 3 * 1000;

        // 连接超时时间(单位:秒)
        private int connectTimeout = 10 * 1000;

        // 命令等待超时时间(单位:秒)
        private int timeout = 3 * 1000;

        // 命令失败重试次数
        private int retryAttempts = 0;

        // 命令重试发送时间间隔(单位:秒)
        private int retryInterval = 3 * 1000;

        // Redis实例密码
        private String password = null;

        // 单个连接最大订阅数量
        private int subscriptionsPerConnection = 5;

        // 客户端名称
        private String clientName = "client";

        // 启用SSL终端识别
        private boolean sslEnableEndpointIdentification = true;

        // SSL实现方式
        private SslProvider sslProvider = SslProvider.JDK;

        // SSL信任证书库路径
        private URI sslTrustStore = null;

        // SSL信任证书库密码
        private String sslTrustStorePassword = null;

        // SSL钥匙库路径
        private URI sslKeystore = null;

        // SSL钥匙库密码
        private String sslKeystorePassword = null;

        // 开启连接TCP KeepAlive特性
        private boolean keepAlive = false;

        // 开启连接TCP NoDelay特性
        private boolean tcpNoDelay = false;

        public int getIdleConnectionTimeout() {
            return idleConnectionTimeout;
        }

        public void setIdleConnectionTimeout(int idleConnectionTimeout) {
            this.idleConnectionTimeout = idleConnectionTimeout;
        }

        public int getPingTimeout() {
            return pingTimeout;
        }

        public void setPingTimeout(int pingTimeout) {
            this.pingTimeout = pingTimeout;
        }

        public int getPingConnectionInterval() {
            return pingConnectionInterval;
        }

        public void setPingConnectionInterval(int pingConnectionInterval) {
            this.pingConnectionInterval = pingConnectionInterval;
        }

        public int getConnectTimeout() {
            return connectTimeout;
        }

        public void setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public int getRetryAttempts() {
            return retryAttempts;
        }

        public void setRetryAttempts(int retryAttempts) {
            this.retryAttempts = retryAttempts;
        }

        public int getRetryInterval() {
            return retryInterval;
        }

        public void setRetryInterval(int retryInterval) {
            this.retryInterval = retryInterval;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getSubscriptionsPerConnection() {
            return subscriptionsPerConnection;
        }

        public void setSubscriptionsPerConnection(int subscriptionsPerConnection) {
            this.subscriptionsPerConnection = subscriptionsPerConnection;
        }

        public String getClientName() {
            return clientName;
        }

        public void setClientName(String clientName) {
            this.clientName = clientName;
        }

        public boolean isSslEnableEndpointIdentification() {
            return sslEnableEndpointIdentification;
        }

        public void setSslEnableEndpointIdentification(boolean sslEnableEndpointIdentification) {
            this.sslEnableEndpointIdentification = sslEnableEndpointIdentification;
        }

        public SslProvider getSslProvider() {
            return sslProvider;
        }

        public void setSslProvider(SslProvider sslProvider) {
            this.sslProvider = sslProvider;
        }

        public URI getSslTrustStore() {
            return sslTrustStore;
        }

        public void setSslTrustStore(URI sslTrustStore) {
            this.sslTrustStore = sslTrustStore;
        }

        public String getSslTrustStorePassword() {
            return sslTrustStorePassword;
        }

        public void setSslTrustStorePassword(String sslTrustStorePassword) {
            this.sslTrustStorePassword = sslTrustStorePassword;
        }

        public URI getSslKeystore() {
            return sslKeystore;
        }

        public void setSslKeystore(URI sslKeystore) {
            this.sslKeystore = sslKeystore;
        }

        public String getSslKeystorePassword() {
            return sslKeystorePassword;
        }

        public void setSslKeystorePassword(String sslKeystorePassword) {
            this.sslKeystorePassword = sslKeystorePassword;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public boolean isTcpNoDelay() {
            return tcpNoDelay;
        }

        public void setTcpNoDelay(boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
        }
    }

    private static class ServersConfig extends BaseConfig {

        // 负载均衡算法
        private LoadBalancerType loadBalancer = LoadBalancerType.ROUND_ROBIN;

        // 主节点最小空闲连接数
        private int masterConnectionMinimumIdleSize = 32;

        // 主节点连接池大小
        private int masterConnectionPoolSize = 64;

        // 从节点最小空闲连接数
        private int slaveConnectionMinimumIdleSize = 32;

        // 从节点连接池大小
        private int slaveConnectionPoolSize = 64;

        // 当第一个Redis命令执行失败的时间间隔到达该值时, 从节点将被排除在可用节点的内部列表中(单位:秒)
        private int failedSlaveCheckInterval = 30 * 1000;

        // 当节点被排除在可用服务器的内部列表中时, 从节点重新连接尝试的间隔(单位:秒)
        private int failedSlaveReconnectionInterval = 3 * 1000;

        // 读取操作的负载均衡模式
        private ReadMode readMode = ReadMode.SLAVE;

        // 订阅操作的负载均衡模式
        private SubscriptionMode subscriptionMode = SubscriptionMode.MASTER;

        // 从节点发布和订阅连接的最小空闲连接数
        private int subscriptionConnectionMinimumIdleSize = 1;

        // 从节点发布和订阅连接池大小
        private int subscriptionConnectionPoolSize = 50;

        // DNS 监测时间间隔(单位:秒)
        private long dnsMonitoringInterval = 5 * 1000L;

        public LoadBalancerType getLoadBalancer() {
            return loadBalancer;
        }

        public void setLoadBalancer(LoadBalancerType loadBalancer) {
            this.loadBalancer = loadBalancer;
        }

        public int getMasterConnectionMinimumIdleSize() {
            return masterConnectionMinimumIdleSize;
        }

        public void setMasterConnectionMinimumIdleSize(int masterConnectionMinimumIdleSize) {
            this.masterConnectionMinimumIdleSize = masterConnectionMinimumIdleSize;
        }

        public int getMasterConnectionPoolSize() {
            return masterConnectionPoolSize;
        }

        public void setMasterConnectionPoolSize(int masterConnectionPoolSize) {
            this.masterConnectionPoolSize = masterConnectionPoolSize;
        }

        public int getSlaveConnectionMinimumIdleSize() {
            return slaveConnectionMinimumIdleSize;
        }

        public void setSlaveConnectionMinimumIdleSize(int slaveConnectionMinimumIdleSize) {
            this.slaveConnectionMinimumIdleSize = slaveConnectionMinimumIdleSize;
        }

        public int getSlaveConnectionPoolSize() {
            return slaveConnectionPoolSize;
        }

        public void setSlaveConnectionPoolSize(int slaveConnectionPoolSize) {
            this.slaveConnectionPoolSize = slaveConnectionPoolSize;
        }

        public int getFailedSlaveCheckInterval() {
            return failedSlaveCheckInterval;
        }

        public void setFailedSlaveCheckInterval(int failedSlaveCheckInterval) {
            this.failedSlaveCheckInterval = failedSlaveCheckInterval;
        }

        public int getFailedSlaveReconnectionInterval() {
            return failedSlaveReconnectionInterval;
        }

        public void setFailedSlaveReconnectionInterval(int failedSlaveReconnectionInterval) {
            this.failedSlaveReconnectionInterval = failedSlaveReconnectionInterval;
        }

        public ReadMode getReadMode() {
            return readMode;
        }

        public void setReadMode(ReadMode readMode) {
            this.readMode = readMode;
        }

        public SubscriptionMode getSubscriptionMode() {
            return subscriptionMode;
        }

        public void setSubscriptionMode(SubscriptionMode subscriptionMode) {
            this.subscriptionMode = subscriptionMode;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public long getDnsMonitoringInterval() {
            return dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }
    }

    public static class SingleServerConfig extends BaseConfig {

        // 节点地址, 格式为 redis://host:port
        private String address = "redis://127.0.0.1:6379";

        // 数据库编号
        private int database = 0;

        // 最小空闲连接数
        private int connectionMinimumIdleSize = 32;

        // 连接池大小
        private int connectionPoolSize = 64;

        // 发布和订阅连接的最小空闲连接数
        private int subscriptionConnectionMinimumIdleSize = 1;

        // 发布和订阅连接池大小
        private int subscriptionConnectionPoolSize = 50;

        // DNS 监测时间间隔(单位:秒)
        private long dnsMonitoringInterval = 5 * 1000L;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }

        public int getConnectionMinimumIdleSize() {
            return connectionMinimumIdleSize;
        }

        public void setConnectionMinimumIdleSize(int connectionMinimumIdleSize) {
            this.connectionMinimumIdleSize = connectionMinimumIdleSize;
        }

        public int getConnectionPoolSize() {
            return connectionPoolSize;
        }

        public void setConnectionPoolSize(int connectionPoolSize) {
            this.connectionPoolSize = connectionPoolSize;
        }

        public int getSubscriptionConnectionMinimumIdleSize() {
            return subscriptionConnectionMinimumIdleSize;
        }

        public void setSubscriptionConnectionMinimumIdleSize(int subscriptionConnectionMinimumIdleSize) {
            this.subscriptionConnectionMinimumIdleSize = subscriptionConnectionMinimumIdleSize;
        }

        public int getSubscriptionConnectionPoolSize() {
            return subscriptionConnectionPoolSize;
        }

        public void setSubscriptionConnectionPoolSize(int subscriptionConnectionPoolSize) {
            this.subscriptionConnectionPoolSize = subscriptionConnectionPoolSize;
        }

        public long getDnsMonitoringInterval() {
            return dnsMonitoringInterval;
        }

        public void setDnsMonitoringInterval(long dnsMonitoringInterval) {
            this.dnsMonitoringInterval = dnsMonitoringInterval;
        }
    }

    public static class ClusterServersConfig extends ServersConfig {

        // 集群节点地址, 格式为 redis://host:port
        private String[] addresses;

        // 集群扫描间隔时间(单位:秒)
        private int interval = 1 * 1000;

        public String[] getAddresses() {
            return addresses;
        }

        public void setAddresses(String[] addresses) {
            this.addresses = addresses;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }
    }

    public static class SentinelServersConfig extends ServersConfig {

        // 哨兵节点地址, 格式为 redis://host:port
        private String[] addresses;

        // 主服务器名称
        private String master = "master";

        // 哨兵扫描间隔时间(单位:秒)
        private int interval = 1000;

        // 数据库编号
        private int database = 0;

        public String[] getAddresses() {
            return addresses;
        }

        public void setAddresses(String[] addresses) {
            this.addresses = addresses;
        }

        public String getMaster() {
            return master;
        }

        public void setMaster(String master) {
            this.master = master;
        }

        public int getInterval() {
            return interval;
        }

        public void setInterval(int interval) {
            this.interval = interval;
        }

        public int getDatabase() {
            return database;
        }

        public void setDatabase(int database) {
            this.database = database;
        }
    }

    public ClientMode getClientMode() {
        return clientMode;
    }

    public void setClientMode(ClientMode clientMode) {
        this.clientMode = clientMode;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

    public int getNettyThreads() {
        return nettyThreads;
    }

    public void setNettyThreads(int nettyThreads) {
        this.nettyThreads = nettyThreads;
    }

    public CodecType getCodec() {
        return codec;
    }

    public void setCodec(CodecType codec) {
        this.codec = codec;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public boolean isReferenceEnabled() {
        return referenceEnabled;
    }

    public void setReferenceEnabled(boolean referenceEnabled) {
        this.referenceEnabled = referenceEnabled;
    }

    public TransportMode getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(TransportMode transportMode) {
        this.transportMode = transportMode;
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public void setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        this.eventLoopGroup = eventLoopGroup;
    }

    public long getLockWatchdogTimeout() {
        return lockWatchdogTimeout;
    }

    public void setLockWatchdogTimeout(long lockWatchdogTimeout) {
        this.lockWatchdogTimeout = lockWatchdogTimeout;
    }

    public boolean isKeepPubSubOrder() {
        return keepPubSubOrder;
    }

    public void setKeepPubSubOrder(boolean keepPubSubOrder) {
        this.keepPubSubOrder = keepPubSubOrder;
    }

    public RedisType getType() {
        return type;
    }

    public void setType(RedisType type) {
        this.type = type;
    }

    public AddressResolverGroupFactoryType getAddressResolverGroupFactory() {
        return addressResolverGroupFactory;
    }

    public void setAddressResolverGroupFactory(AddressResolverGroupFactoryType addressResolverGroupFactory) {
        this.addressResolverGroupFactory = addressResolverGroupFactory;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public SingleServerConfig getSingle() {
        return single;
    }

    public void setSingle(SingleServerConfig single) {
        this.single = single;
    }

    public ClusterServersConfig getCluster() {
        return cluster;
    }

    public void setCluster(ClusterServersConfig cluster) {
        this.cluster = cluster;
    }
}