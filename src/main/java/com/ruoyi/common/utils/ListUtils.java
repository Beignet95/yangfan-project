package com.ruoyi.common.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    /**
     * 将list分割成多个list
     * @param messagesList 分割list
     * @param groupSize 分割后list的大小
     * @param <T>
     * @return
     */
    public static  <T> List<List<T>> splitList(List<T> messagesList, int groupSize) {
        int length = messagesList.size();
        // 计算可以分成多少组
        int num = (length + groupSize - 1) / groupSize; // TODO
        List<List<T>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = (i + 1) * groupSize < length ? (i + 1) * groupSize : length;
            newList.add(messagesList.subList(fromIndex, toIndex));
        }
        return newList;
    }

    /**
     * 这个<T> T 可以传入任何类型的List
     * 关于参数T
     * 第一个 表示是泛型
     * 第二个 表示返回的是T类型的数据
     * 第三个 限制参数类型为T
     *
     * @param data
     * @return
     */
    private <T> T getListFisrt(List<T> data) {
        if (CollectionUtils.isEmpty(data)) {
            return null;
        }
        return data.get(0);
    }

}
