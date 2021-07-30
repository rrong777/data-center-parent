package com.slzh.client;

/**
 * <p>文件名称:${FILE_NAME}</p>
 * <p>文件描述:</p>
 * <p>版权所有: 版权所有(C)2013-2099</p>
 * <p>公 司:  </p>
 * <p>内容摘要: </p>
 * <p>其他说明: </p>
 *
 * @version 1.0
 * @author lanbin
 * @since 2019/8/22 下午3:28
 */


import com.slzh.utils.ObjectTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @param <T>
 * @author Zhangyf
 */
@Component
public class RedisClient<T> {

    @Autowired
    private JedisPool jedisPool;

    private final static Logger logger = LoggerFactory.getLogger(RedisClient.class);

    public void set(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * expireTime单位秒
     *
     * @param key
     * @param value
     * @param expireTime
     * @throws Exception
     */
    public void set(String key, String value, Integer expireTime) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            if (expireTime != null) {
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * expireTime单位秒
     *
     * @param key
     * @param expireTime
     * @throws Exception
     */
    public void setExpireTime(String key, Integer expireTime) {
        Jedis jedis = null;
        try {
            if (expireTime != null) {
                jedis = jedisPool.getResource();
                jedis.expire(key, expireTime);
            }
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * @param key
     * @throws Exception
     */
    public void deleteByKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    /**
     * @param key
     * @throws Exception
     */
    public boolean existsKey(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.exists(key);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
        return false;
    }

    public String get(String key) {

        Jedis jedis = null;
        String value = null;
        try {
            jedis = jedisPool.getResource();
            value = jedis.get(key);
        } catch (Exception e) {
            logger.error(e.toString());

        } finally {
            //返还到连接
            jedis.close();
            return value;
        }
    }

    public void setobj(String key, T value) {
        Jedis jedis = null;
        try {
            Set<T> set = new HashSet<T>();
            set.add(value);
            jedis = jedisPool.getResource();
            jedis.sadd(key, String.valueOf(set));
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public void setObjForList(String key, T value) {
        Jedis jedis = null;
        try {
            List<T> list = new ArrayList<>();
            list.add(value);
            jedis = jedisPool.getResource();
            jedis.set(key.getBytes(), ObjectTranscoder.serialize(list));
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
        }
    }

    public <T> List<T> getObjForList(String key) {
        Jedis jedis = null;
        List<T> list = null;
        try {
            jedis = jedisPool.getResource();
            if (!jedis.exists(key.getBytes())) {
                return null;
            }
            byte[] in = jedis.get(key.getBytes());
            list = (List<T>) ObjectTranscoder.deserialize(in);


        } catch (Exception e) {
            logger.error("get value error : {}", e);
        } finally {
            //返还到连接池
            jedis.close();
            return list;
        }

    }


    //---------------------存储set集合-----------------------------------------
    public long sadd(String key, String value) {
        Jedis jedis = null;
        long item = 0;
        try {

            jedis = jedisPool.getResource();
            item = jedis.sadd(key, value);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return item;
        }
    }


    public long sadd(String key, String... values) {

        Jedis jedis = null;
        long item = 0;
        try {

            jedis = jedisPool.getResource();
            item = jedis.sadd(key, values);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return item;
        }
    }


    /**
     * 判断元素是否存在于集合中
     *
     * @param key
     * @param value
     * @return
     */
    public boolean sismember(String key, String value) {

        Jedis jedis = null;
        boolean check = false;
        try {

            jedis = jedisPool.getResource();
            check = jedis.sismember(key, value);
            jedis.close();
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            return check;
        }
    }

    /**
     * 移除集合中的某个元素
     *
     * @param key
     * @param value
     * @return
     */
    public long sremove(String key, String value) {

        Jedis jedis = null;
        long count = 0;
        try {

            jedis = jedisPool.getResource();
            count = jedis.srem(key, value);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return count;
        }
    }

    /**
     * 返回集合全部成员
     *
     * @param key
     * @return
     */
    public Set<String> smembers(String key) {

        Jedis jedis = null;
        Set<String> sets = null;
        try {

            jedis = jedisPool.getResource();
            sets = jedis.smembers(key);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return sets;
        }
    }


    public long scard(String key) {

        Jedis jedis = null;
        long size = 0;
        try {

            jedis = jedisPool.getResource();
            size = jedis.scard(key);
        } catch (Exception e) {

            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return size;
        }
    }


    public long hset(String key, String filed, String value) {

        Jedis jedis = null;
        long coun = 0;
        try {

            jedis = jedisPool.getResource();
            coun = jedis.hset(key, filed, value);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return coun;
        }

    }


    public long hdel(String key, String filed) {

        Jedis jedis = null;
        long coun = 0;
        try {

            jedis = jedisPool.getResource();
            coun = jedis.hdel(key, filed);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return coun;
        }

    }


    public String hget(String key, String filed) {

        Jedis jedis = null;
        String value = null;
        try {

            jedis = jedisPool.getResource();
            value = jedis.hget(key, filed);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return value;
        }

    }

    public boolean hexist(String key, String filed) {

        Jedis jedis = null;
        boolean value = false;
        try {

            jedis = jedisPool.getResource();
            value = jedis.hexists(key, filed);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return value;
        }

    }

    public long hlen(String key) {

        Jedis jedis = null;
        long value = 0;
        try {

            jedis = jedisPool.getResource();
            value = jedis.hlen(key);
        } catch (Exception e) {
            logger.error(e.toString());
        } finally {
            //返还到连接池
            jedis.close();
            return value;
        }

    }


}
