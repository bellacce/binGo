package com.wenhui.common.base.utils;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisCacheUtil
 * @Description: 缓存
 * @author: yhy
 * @date: 2016年12月6日下午1:32:34
 */
@Slf4j
@Service("redisUtil")
public class RedisUtil<T> {

    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();//序列化为String
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);//序列化为Json
        ObjectMapper om = new ObjectMapper();
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        this.redisTemplate = redisTemplate;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public boolean set(String key, T value) {

        boolean result = false;
        try {
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            operation.set(key, value);
            redisTemplate.expire(key, 7000, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("", e);
        }

        return result;
    }

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key        缓存的键值
     * @param value      缓存的值
     * @param expireTime 缓存有笑时间
     * @return 缓存的对象
     */
    public boolean set(String key, T value, int expireTime) {

        boolean result = false;
        try {
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            operation.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("", e);
        }

        return result;
    }

    /**
     * 修改缓存基本的对象不修改过期时间
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     * @return 缓存的对象
     */
    public boolean update(String key, T value) {

        boolean result = false;
        try {
            ValueOperations<String, T> operation = redisTemplate.opsForValue();
            operation.set(key, value, 0);
            result = true;
        } catch (Exception e) {
            log.error("", e);
        }

        return result;
    }

    public <T> ValueOperations<String, T> setCacheObjectLasting(String key, T value) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        operation.set(key, value);
        return operation;
    }

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @param
     * @return 缓存键值对应的数据
     */
    public <T> T get(String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 缓存List数据
     * 入队
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> boolean setCacheList(String key,
                                    List<T> dataList) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
            return true;
        }
        return false;
    }

    /**
     * 缓存List数据
     * 入队
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public <T> boolean setCacheList(String key, List<T> dataList, int expireTime) {
        ListOperations listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (int i = 0; i < size; i++) {
                listOperation.leftPush(key, dataList.get(i));
            }
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }


    /**
     * 获得缓存的list对象
     * 出队
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheList(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add((T) listOperation.rightPop(key));
        }
        return dataList;
    }

    /**
     * 缓存Set
     *
     * @param key     缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public <T> BoundSetOperations<String, T> setCacheSet(String key, Set<T> dataSet) {
        BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            setOperation.add(it.next());
        }
        return setOperation;
    }

    /**
     * 获得缓存的set
     *
     * @param key
     * @param
     * @return
     */
    public Set<T> getCacheSet(String key) {
        Set<T> dataSet = new HashSet<T>();
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Long size = operation.size();
        for (int i = 0; i < size; i++) {
            dataSet.add(operation.pop());
        }
        return dataSet;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, String, T> setCacheMap(String key,
                                                             Map<String, T> dataMap, Long seconds) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {

            for (Map.Entry<String, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param
     * @return
     */
    public <T> Map<String, T> getCacheMap(String key) {
        Map<String, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     * @return
     */
    public <T> HashOperations<String, Integer, T> setCacheIntegerMap(
            String key, Map<Integer, T> dataMap) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        if (null != dataMap) {
            for (Map.Entry<Integer, T> entry : dataMap.entrySet()) {
                hashOperations.put(key, entry.getKey(), entry.getValue());
            }
        }
        return hashOperations;
    }

    /**
     * 获得缓存的Map
     *
     * @param key
     * @param
     * @return
     */
    public <T> Map<Integer, T> getCacheIntegerMap(String key) {
        Map<Integer, T> map = redisTemplate.opsForHash().entries(key);
        return map;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }


    /**
     * <批量删除对应的value>
     *
     * @param keys keys
     * @throws
     */

    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * <删除对应的value>
     *
     * @param key key
     * @throws
     */

    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * <批量删除key>
     *
     * @param pattern pattern
     * @throws
     */

    public void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }


    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <T> List<T> getCacheLists(String key) {
        List<T> dataList = new ArrayList<T>();
        ListOperations<String, T> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);
        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.index(key, i));
        }
        return dataList;
    }


}
