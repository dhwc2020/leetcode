package topic;

import java.util.HashMap;
import java.util.Map;

/**
 *leetcode: 3.无重复字符的最长子串
 *
 * 给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。
 */
public class LongestSubstring {

    /**
     * 思路一:
     *      1.采用map记录遍历的字符及其下标
     *      2.通过变量start来记录左侧有效下标
     *      3.通过双指针进行处理, start先不动, i指针向前走
     *        直到i下标的字符在map中, 且map中记录的下标大于等于start
     *        此时, 计算[start, i)的子串长度, 更新之前最大的子串长度
     *
     *        更新start, 如果start与map中该字符的下标相等, 则start的值增一
     *        如果不相等, 则更新为该字符下标加一
     *
     *      4.循环结束, start不等于字符串长度减一时, 说明[start, s.length() - 1]
     *        范围不存在重复字符, 更新最长子串长度
     */
    public int lengthOfLongestSubstring(String s){
        if(s.length() == 0) return 0;
        //key为字符, value为该字符的下标
        Map<Character, Integer> charIndexMap = new HashMap<>();
        int start = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            //当前子串中存在该元素, 且map中记录的不是别的子串记录
            if(charIndexMap.containsKey(ch) && charIndexMap.get(ch) >= start){
                maxLen = Math.max(maxLen, i - start);
                //更新子串起始下标
                start = charIndexMap.get(ch) + 1;
            }
            charIndexMap.put(ch, i);
        }

        return Math.max(maxLen, s.length() - start);
    }

    /**
     * 思路一:
     *      优化, 采用数组替换掉map
     */
    public int lengthOfLongestSubstring1(String s){
        if(s.length() == 0) return 0;
        //把字符作为数组下标, 字符在字符串中的下标加一作为数组中的值(数组默认值与字符存在冲突, 故加一)
        //128是acsll码所标识的字符数量(字母, 数字, 下划线等字符)
        int[] charIndexs = new int[128];
        int start = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            //当前子串中存在该元素, 且map中记录的不是别的子串记录
            if(charIndexs[ch] != 0 && charIndexs[ch] > start){
                maxLen = Math.max(maxLen, i - start);
                //更新子串起始下标
                start = charIndexs[ch] + 1;
            }
            charIndexs[ch] = i + 1;
        }

        return Math.max(maxLen, s.length() - start);
    }

    /**
     *  思路二:
     *          1.采用滑动窗口来解决
     *
     */
    public int lengthOfLongestSubstring2(String s){
        if(s.length() == 0) return 0;

        //窗口左边界
        int left = 0;
        //窗口右边界
        int right = 0;
        //最大窗口长度
        int maxLen = 0;
        //存储窗口内容的容器(acsll码)
        int[] windowInfos = new int[128];

        while(right < s.length()){
            //右窗口右移一位
            char ch = s.charAt(right);
            right++;

            //窗口中存在该字符, 更新窗口最大长度, 左窗口右移
            if(windowInfos[ch] != 0 && windowInfos[ch] > left){
                maxLen = Math.max(maxLen, right - left);
                //左窗口右移, 移至重复元素右边一位
                left = windowInfos[ch];
            }

            //新加入窗口的元素下标加一, 与默认值0错开
            windowInfos[ch] = right;
        }

        return Math.max(maxLen, s.length() - left);
    }
}
