package structs.linkedlist;

import java.util.Objects;

/**
 * leetcode: 21.合并两个有序链表
 *
 * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class MergeOrderedLinkedLists {

    /**
     *  思路: 1.采用双指针, 分别指向两个链表头部
     *       2.依次选出小的节点, 并连接到新链链尾
     *       3.指向小节点的指针右移
     *       4.双指针中存在指向null的直接退出, 并将另一个指针连到新链链尾并返回
     */
    public ListNode mergeTwoLists(ListNode list1, ListNode list2){
        if (Objects.isNull(list1)) return list2;
        if (Objects.isNull(list2)) return list1;

        ListNode head = new ListNode();
        ListNode tail = head;

        while(list1 != null && list2 != null){
            tail.next = list1.val <= list2.val ? list1 : list2;

            if(list1.val <= list2.val){
                list1 = list1.next;
            }else {
                list2 = list2.next;
            }

            tail = tail.next;
        }

        tail.next = list1 != null ? list1 : list2;
        return head.next;
    }

}

class ListNode{
    int val;
    ListNode next;

    public int getVal() {
        return val;
    }

    ListNode(){};
    ListNode(int val){
        this.val = val;
    }

    ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }
}
