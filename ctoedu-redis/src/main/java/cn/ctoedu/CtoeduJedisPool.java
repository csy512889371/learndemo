package cn.ctoedu;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CtoeduJedisPool {
    private static final Log log = LogFactory.getLog(CtoeduJedisPool.class);
    JedisPool pool = null;
    Jedis jedis = null;
    ClassPathXmlApplicationContext context = null;

    /**
     * 构造函数，初始化jedis连接池
     */
    public CtoeduJedisPool() {
        try {
            context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();
            pool = (JedisPool) context.getBean("jedisPool");
            jedis = pool.getResource();

        } catch (Exception e) {
            log.error("==>edisCluster context start error:", e);
            context.stop();
            System.exit(0);
        }
    }

    public Jedis getJedis() {
        return jedis;
    }

    public static void main(String[] args) {
        Jedis jedis = new CtoeduJedisPool().getJedis();
        jedis.set("bigdata1", "bigdata is very good");
        System.out.println("数据存储完毕，请检查！");
    }
}