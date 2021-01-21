package com.quinniup.extension.algorithm.lru;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: main
 * @Description: LRU 实现
 * @Author: CheneyIn
 * @Date: 2021/1/11
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        // 基于LinkedHashMap进行实现LRU
//        UseLinkedHashMapCache<Integer, String> cache = new UseLinkedHashMapCache<>(3);
//        cache.put(1, "one");
//        cache.put(2, "two");
//        cache.put(3, "three");
//        cache.put(4, "four");
//        cache.put(2, "two");
//        cache.put(3, "three");
//        cache.put(1, "one");
//
//        Iterator<Map.Entry<Integer, String>> iterator = cache.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<Integer, String> entry = iterator.next();
//            Integer key = entry.getKey();
//            System.out.print("Key:\t" + key + "\n");
//        }
//
//        HandMakeCache handMakeCache = new HandMakeCache(3);
//        handMakeCache.add(1);
//        handMakeCache.add(2);
//        handMakeCache.add(3);
//        handMakeCache.add(4);
//        handMakeCache.add(2);
//        handMakeCache.add(3);
//        handMakeCache.add(1);
//
//        for (int i = 0; i < handMakeCache.getSize(); i++) {
//            System.out.println(handMakeCache.get(i));
//        }
//
        Map<String,Integer> map = new HashMap<>();
        int a = map.get("a");
    }
}
