package cn.ctoedu;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PipelineRedis {
    public static void main(String[] args) {
        Jedis jedis = new CtoeduJedisPool().getJedis();
        Map<String, String> data = new HashMap<String, String>();

        //不用pipeline存储数据
        //选择redis的库
        jedis.select(4);
        jedis.flushDB();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            jedis.hmset("k_" + i, data);
        }
        long end = System.currentTimeMillis();
        System.out.println("datasize=" + jedis.dbSize());
        System.out.println("hmset without pipeline used=" + (end - start) / 1000 + "seconds!");

        //用pipeline存储数据
        jedis.select(4);
        jedis.flushDB();
        Pipeline pipeline = jedis.pipelined();
        start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            data.clear();
            data.put("k_" + i, "v_" + i);
            pipeline.hmset("k_" + i, data);
        }
        pipeline.sync();
        end = System.currentTimeMillis();
        System.out.println("datasize=" + jedis.dbSize());
        System.out.println("hmset with pipeline used=" + (end - start) / 1000 + "seconds!");


        Set<String> keys = jedis.keys("*");
        // 直接使用Jedis hgetall
        start = System.currentTimeMillis();
        Map<String, Map<String, String>> result = new HashMap<String, Map<String, String>>();
        for (String key : keys) {
            result.put(key, jedis.hgetAll(key));
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll without pipeline used [" + (end - start) / 1000 + "] seconds ..");
        // 使用pipeline hgetall
        Map<String, Response<Map<String, String>>> responses =
                new HashMap<String, Response<Map<String, String>>>(
                        keys.size());
        result.clear();
        start = System.currentTimeMillis();
        for (String key : keys) {
            responses.put(key, pipeline.hgetAll(key));
        }
        pipeline.sync();
        for (String k : responses.keySet()) {
            result.put(k, responses.get(k).get());
        }
        end = System.currentTimeMillis();
        System.out.println("result size:[" + result.size() + "] ..");
        System.out.println("hgetAll with pipeline used [" + (end - start) / 1000 + "] seconds ..");
        jedis.disconnect();
    }
}
