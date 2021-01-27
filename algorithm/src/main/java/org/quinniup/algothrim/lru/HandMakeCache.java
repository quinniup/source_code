package org.quinniup.algothrim.lru;

/**
 * @ClassName: HandMakeCache
 * @Description: 自己通过数组实现LRU
 * @Author: CheneyIn
 * @Date: 2021/1/11
 */
public class HandMakeCache {
    // 添加次数计数器
    static int count = 0;
    // 数组元素计数器
    static int size = 0;
    // 最大长度
    int maxSize;
    // 对象数组
    int[] arrayList;

    public HandMakeCache(int size) {
        arrayList = new int[size];
        this.maxSize = size;
    }

    public int getSize() {
        return size;
    }

    public void add(int ele) throws Exception {
        // 用于判断是否存在
        boolean exist = false;
        // 定位位置
        int location = 0;
        for (int i = 0; i < size; i++) {
            if (ele == arrayList[i]) {
                exist = true;
                location = i;
            }
        }
        if (size < this.maxSize) {
            if (exist) {
                if (location == 0) {
                    moveArrayElements(arrayList, 0, size - 2);
                } else if (location < size - 1) {
                    moveArrayElements(arrayList, location, size - 2);
                }
                arrayList[size - 1] = ele;
            }else {
                arrayList[size] = ele;
                size++;
            }
        } else {
            if (!exist || ele == arrayList[0]) {
                moveArrayElements(arrayList, 0, maxSize - 2);
            } else if (ele != arrayList[size - 1]) {
                moveArrayElements(arrayList, location, maxSize - 2);
            }
            arrayList[maxSize - 1] = ele;
        }
        count ++;
    }

    public int get(int index){
        return arrayList[index];
    }

    private void moveArrayElements(int[] arrayList, int start, int end) {
        for (int i = start; i <= end; i++) {
            arrayList[i] = arrayList[i + 1];
        }
    }
}
