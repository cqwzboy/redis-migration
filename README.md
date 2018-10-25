# 介绍
实现两台单机Redis之间的数据迁移
# 版本
## v1.0
初始化版本，定制化较多
## v1.1
通用的稳定版本，可以运用于不同单机Redis之间的数据迁移，且可以自定义正则表达式用于过滤
# 使用
## 导入jar
    <dependency>  
     <groupId>com.qc.itaojin</groupId>  
     <artifactId>redis-migration</artifactId>  
     <version>1.1</version>  
     <exclusions> 
      <exclusion> 
       <groupId>redis.clients</groupId>  
       <artifactId>jedis</artifactId>  
      </exclusion>
     </exclusions>
    </dependency>  
    <dependency>  
     <groupId>redis.clients</groupId>  
     <artifactId>jedis</artifactId>  
     <version>2.7.3</version>  
    </dependency>

## redis-migration.properties
在classpath下创建redis-migration.properties文件，配置内容如下

    # 数据源    
    redis.from.host=192.168.0.1    
    redis.from.port=6379    
    redis.from.password=password123    
    redis.from.dbIndex=9    
    redis.from.charset=utf-8    
    # 数据目的地    
    redis.to.host=192.168.0.2   
    redis.to.port=6379    
    redis.to.password=password2    
    redis.to.dbIndex=0    
    redis.to.charset=utf-8    
    redis.maxTotal=100    
    redis.maxIdle=20    
    redis.maxWaitMillis=2000    
    redis.testOnBorrow=false    
    redis.timeout=5000    
    # Scan全部Keys的正则    
    redis.migration.pattern=*    
    # Scan某个Hash结构中全部数据的正则    
    redis.migration.hash.pattern=*    
    # Scan某个Set结构中全部数据的正则    
    redis.migration.set.pattern=*    
    # Scan某个ZSet结构中全部数据的正则    
    redis.migration.zset.pattern=*    
    # Scan的批扫描数量    
    redis.migration.batchSize=1000
    
## java代码
    RedisMigrator redisMigrator = new RedisMigrator();
    long time = redisMigrator.excute();
    System.out.println("耗时："+time/1000+" 秒");
