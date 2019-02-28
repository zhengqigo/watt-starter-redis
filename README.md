# watt-starter-redis

## example
```yaml
spring:
  redis:
    connection-timeout: 1000
    max-redirects: 3
    so-timeout: 1000
    max-attempts: 3
    max-total: 20
    max-idle: 3
    max-wait-millis: 300
    test-on-borrow: true
    password: 0Y0j8Zeu8iis1qIYHJUoUy2OsUD3u57JGeKosvfRKFxm75hpLa
    cluster-mode: false
    viewable: true
    host: 127.0.0.1
    port: 7000
    nodes:
    - 127.0.0.1:7000
    - 127.0.0.1:7001
    - 127.0.0.1:7002
    - 127.0.0.1:7003
    - 127.0.0.1:7004
    - 127.0.0.1:7005
```
