package topic;

import java.util.HashMap;
import java.util.Map;

/**
 * leetcode: 1.两数之和
 *
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出
 * 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 */
public class TwoNumSum {

    /**
     * 思路:
     *     1.根据题意推导 i,j为[0, nums.length - 1] (提示i不等于j), 求target - nums[i] == nums[j]
     */
    public int[] twoNum(int[] nums, int target){
        //1.提供一个map, 保存已经处理过的元素(key是数组元素, value是数组下标)
        Map<Integer, Integer> valIndexMap = new HashMap<>();

        //2.遍历nums, 通过target - nums[i], 来查找nums[j]
        //使用map使该算法的时间复杂度降低到了O(N)
        for (int i = 0; i < nums.length; i++) {
            int key = target - nums[i];

            //存在, 则代表之前没有符合条件的两个数, 而现在恰好符合条件
            if(valIndexMap.containsKey(key)){
                //返回两数下标, map中的数是已经判断过的数, 即比i下标小
                return new int[]{valIndexMap.get(key), i};
            }

            //不存在, 直接将元素和下标放入map中
            valIndexMap.put(nums[i], i);
        }

        //代表没有符合该条件的两个数
        return new int[]{};
    }

}
