package topic;

/**
 *  leetcode: 4.寻找两个正序数组的中位数
 *
 *  给定两个大小分别为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。请你找出并返回这两个正序数组的 中位数 。
 *  算法的时间复杂度应该为 O(log (m+n)) 。
 */
public class FindMedianNum {
    /**
     *  思路一:
     *          1.在不考虑时间复杂度的情况下, 可以先进行归并合成一个数组
     *          2.在新数组中求中位数
     *          3.时间复杂度和空间复杂度都是O(M + N)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2){
        //其中一个数组长度为0, 则另一个数组直接计算中位数
        if(nums1.length == 0)
            return singleArrayMedian(nums2);

        if(nums2.length == 0)
            return singleArrayMedian(nums1);

        //归并处理
        int[] mergerArr = mergerArrays(nums1, nums2);

        //根据奇偶, 返回中位数
        return mergerArr.length % 2 == 0 ?
                (mergerArr[mergerArr.length / 2 - 1] + mergerArr[mergerArr.length / 2]) / 2.0 :
                mergerArr[mergerArr.length / 2];
    }

    //求单个数组的中位数
    private double singleArrayMedian(int[] nums){
        int halfLen = nums.length / 2;
        boolean even = nums.length % 2 == 0;
        return even ? (nums[halfLen - 1] + nums[halfLen]) / 2.0 :
                nums[halfLen];
    }

    //归并两个升序数组
    private int[] mergerArrays(int[] nums1, int[] nums2){
        //进行归并处理
        int newLen = nums1.length + nums2.length;
        int[] mergerArr = new int[newLen];
        //指向nums1, nums2数组当前待处理的元素
        int nums1Index = 0;
        int nums2Index = 0;
        //mergerArr数组中下一个待放入元素的索引
        int mergerIndex = 0;

        //按照升序依次放入归并数组
        while(nums1Index < nums1.length && nums2Index < nums2.length){
            mergerArr[mergerIndex++] = nums1[nums1Index] <= nums2[nums2Index] ?
                    nums1[nums1Index++] :
                    nums2[nums2Index++];
        }

        //剩余部分直接放入归并数组
        while(nums1Index < nums1.length){
            mergerArr[mergerIndex++] = nums1[nums1Index++];
        }
        while(nums2Index < nums2.length){
            mergerArr[mergerIndex++] = nums2[nums2Index++];
        }

        return mergerArr;
    }

    /**
     *  思路二:
     *          1.优化思路一, 使用双指针(查找第k小的数)替换归并数组
     *          2.如果是奇数, 则获取第(M + N) / 2的数
     *          3.如果是偶数, 则获取第(M + N) / 2 - 1 和 第(M + N) / 2数的和并除以2
     *          4.时间复杂度O(M + N), 空间复杂度为O(1)
     */
    public double findMedianSortedArrays1(int[] nums1, int[] nums2){
        //仅有一个数组中不存在元素的处理方式
        if(nums1.length == 0)
            return singleArrayMedian(nums2);

        if(nums2.length == 0)
            return singleArrayMedian(nums1);

        int newLen = nums1.length + nums2.length;
        boolean even = newLen % 2 == 0;
        //总长度为偶数
        if(even){
            int median1 = findKthOnArrays(nums1, nums2, newLen / 2);
            int median2 = findKthOnArrays(nums1, nums2, newLen / 2 + 1);
            return (median1 + median2) / 2.0;
        }

        //总长度为奇数
        return findKthOnArrays(nums1, nums2, newLen / 2 + 1);
    }

    //在两个有序数组中上查找第k小的数(升序)
    private int findKthOnArrays(int[] nums1, int[] nums2, int k){
        //当前指向的值
        int curVal = 0;
        //数组nums1的当前下标
        int index1 = 0;
        //数组nums2的当前下标
        int index2 = 0;

        while(k-- > 0){
            //边界处理
            if(index1 >= nums1.length){
                return nums2[index2 + k];
            }

            if(index2 >= nums2.length){
                return nums1[index1 + k];
            }

            curVal = nums1[index1] <= nums2[index2] ?
                    nums1[index1++] :
                    nums2[index2++];
        }


        return curVal;
    }

    /**
     *  思路三:
     *          1.采用二分思想优化时间复杂度
     *          2.由思路二可知, 求中位数可以转化为求第k小的数
     *          3.如果想要使用二分, 那么每轮能够将问题划分为两个部分,
     *            同时每轮可以筛选一部分符合条件的数据
     *
     *          4.首先两个数组为升序数组, 那么每轮可以比较第k/2下标的数
     *          5.其中较小的一方, 前k/2个元素都不可能为第k小的数, 最多为k-1
     *          6.排除前k/2个元素生成新的数组, k进行变化减去排除元素的个数, 继续进行第5步操作
     *
     *          7.边界处理:
     *              i.k/2大于其中一个数组长度时, 直接取末尾值与另一个数组k/2处的数进行比较
     *             ii.k为1时, 取两个新数组第一个数进行比较
     *            iii.一个新数组长度为0时, 即指针指向原数组末尾, 直接在另一个数组上取k-1处的值返回
     *          8.时间复杂度O(log(M + N)), 空间复杂度O(1)
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2){
        int newLen = nums1.length + nums2.length;

        if(newLen % 2 == 0){
            int median1 = findKthOnArrays1(nums1, nums2, newLen / 2);
            int median2 = findKthOnArrays1(nums1, nums2, newLen / 2 + 1);
            return (median1 + median2) / 2.0;
        }

        return findKthOnArrays1(nums1, nums2, newLen / 2 + 1);
    }

    //在两个有序数组中上查找第k小的数(升序)
    private int findKthOnArrays1(int[] nums1, int[] nums2, int k){
        //当前指向k/2的值
        int pivot1 = 0;
        int pivot2 = 0;
        //新数组起始下标
        int index1 = 0;
        int index2 = 0;

        while(true){
            //边界处理(其中一个数组已经被完全排除, 直接在另一个数组上计算第k小)
            if(index1 >= nums1.length){
                return nums2[index2 + k - 1];
            }

            if(index2 >= nums2.length){
                return nums1[index1 + k - 1];
            }

            //比较新数组起点的数, 直接确定第1小是谁
            if(k == 1){
                return Math.min(nums1[index1], nums2[index2]);
            }

            //如果新数组起点加k/2超过原数组, 则直接用末尾索引
            int halfIndex1 = Math.min(index1 + k / 2 - 1, nums1.length - 1);
            int halfIndex2 = Math.min(index2 + k / 2 - 1, nums2.length - 1);
            //比较两个数组指定位置的值
            pivot1 = nums1[halfIndex1];
            pivot2 = nums2[halfIndex2];
            //舍弃较小的一部分
            if(pivot1 <= pivot2){
                //舍弃部分一定为第k小前面的数
                //此时, 舍弃边界后一位作为新数组起点
                //k减去舍弃部分的元素个数, 作为新数组比较的k
                k = k - halfIndex1 + index1 - 1;
                index1 = halfIndex1 + 1;
            }else{
                k = k - halfIndex2 + index2 - 1;
                index2 = halfIndex2 + 1;
            }
        }
    }
}
