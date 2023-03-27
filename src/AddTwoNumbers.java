import java.util.ArrayList;
import java.util.List;

/**
 * leetcode: 2.两数相加
 *
 * 给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，
 * 并且每个节点只能存储 一位 数字。请你将两个数相加，并以相同形式返回一个表示和的链表。
 * 你可以假设除了数字 0 之外，这两个数都不会以 0 开头。
 */
public class AddTwoNumbers {

    /**
     * 思路:
     *      1.两个链表合成一个新的链表, 存在进位处理
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        //1.创建一个头节点
        ListNode head = new ListNode();
        //指向尾节点的指针
        ListNode tail = head;
        //进位标志(false代表不进位)
        boolean carry = false;

        //l1,l2其中一个到达尾端, 退出循环
        while(l1 != null && l2 != null){
            //2.l1和l2指向的节点求和, 并进行是否进位处理
            int curVal = carry ? l1.val + l2.val + 1 : l1.val + l2.val;

            //3.求和结果大于9, 设置进位标志为true, 并将结果减十生成新节点, 链接到新链表尾端
            //4.求和结果小于等于9, 设置进位标志为false, 将结果生成新节点, 链接到新链表尾端
            tail.next = curVal > 9 ? new ListNode(curVal - 10) : new ListNode(curVal);
            carry = curVal > 9;

            //5.l1,l2向后移一位
            l1 = l1.next;
            l2 = l2.next;
            tail = tail.next;
        }

        //6.未到尾端的单独处理, 思路与上述相似, 区别在于单个链表进行处理
        List<ListNode> saveTail = new ArrayList<>();
        carry = singleLinkSum(l1 != null ? l1 : l2, tail, carry, saveTail);
        //外部指针修改指向
        tail = saveTail.get(0);

        if(carry){
            tail.next = new ListNode(1);
        }
        return head.next;
    }

    /**
     * 单个链表进行处理
     * @param link 未走到末尾的链表指针
     * @param tail 新链表尾指针
     * @param carry 是否进位
     * @param saveTail 处理完成后, 存储链尾节点
     * @return 是否进位
     */
    private boolean singleLinkSum(ListNode link, ListNode tail, boolean carry, List<ListNode> saveTail){
        while(link != null){
            int curVal = carry ? link.val + 1 : link.val;

            tail.next = curVal > 9 ? new ListNode(curVal - 10) : new ListNode(curVal);
            carry = curVal > 9;

            link = link.next;
            tail = tail.next;
        }

        saveTail.add(tail);
        return carry;
    }

    private class ListNode{
        int val;
        ListNode next;

        ListNode(){};
        ListNode(int val){
            this.val = val;
        }

        ListNode(int val, ListNode next){
            this.val = val;
            this.next = next;
        }
    }
}
