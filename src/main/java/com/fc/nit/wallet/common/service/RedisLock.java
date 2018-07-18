package com.fc.nit.wallet.common.service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisLock implements AutoCloseable {
	
	private static final Logger log = Logger.getLogger(RedisLock.class);
    
    private static final long DEFAULT_EXPIRE = 80;//80s 有慢sql，超时时间设置长一点
    
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 
     * 等待锁的时间,单位为s
     *
     * @param key
     * @param timeout s
     * @param seconds
     */
    public boolean lock(String key, long timeout, TimeUnit seconds) {
        long nanoWaitForLock = seconds.toNanos(timeout);
        long start = System.nanoTime();

        try {
            while ((System.nanoTime() - start) < nanoWaitForLock) {
                if (redisTemplate.getConnectionFactory().getConnection().setNX(key.getBytes(), new byte[0])) {
                    redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);//暂设置为80s过期，防止异常中断锁未释放
                    return true;
                }
                TimeUnit.MILLISECONDS.sleep(1000 + new Random().nextInt(100));//加随机时间防止活锁
            }
        } catch (Exception e) {
           log.error(e.getMessage(), e);
            unlock(key);
        }
        return false;
    }

    public void unlock(String key) {
        try {
            RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
            connection.del(key.getBytes());
            connection.close();
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
    }

	@Override
	public void close() throws Exception {
		
	}

}