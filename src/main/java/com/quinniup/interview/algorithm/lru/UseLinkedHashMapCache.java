package com.quinniup.interview.algorithm.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: UseLinkedHashMapCache
 * @Description: ToDo
 * @Author: CheneyIn
 * @Date: 2021/1/11
 */
public class UseLinkedHashMapCache<K, V> extends LinkedHashMap {
    private int cacheSize;

    public UseLinkedHashMapCache(int cacheSize) {
        super(16, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > cacheSize;
    }
}
