package structs.linkedlist;

/**
 * leetcode: 86.分隔链表
 *
 * 给你一个链表的头节点 head 和一个特定值 x ，请你对链表进行分隔，使得所有 小于 x 的节点都出现在 大于或等于 x 的节点之前。
 *
 * 你应当 保留 两个分区中每个节点的初始相对位置。
 */
public class PartitionLinkedList {
    /**
     *  思路: 1.先划分两个空链表
     *       2.遍历链表, 小的连到第一个链表, 其他的放入另一个链表
     *       3.合并两个链表并返回
     */
    public ListNode partition(ListNode head, int x){
        ListNode firstHead = new ListNode();
        ListNode firstTail = firstHead;

        ListNode secondHead = new ListNode();
        ListNode secondTail = secondHead;

        while(head != null){
            if(head.val < x){
                firstTail.next = head;
                firstTail = firstTail.next;
            }else {
                secondTail.next = head;
                secondTail = secondTail.next;
            }

            head = head.next;
        }

        firstTail.next = secondHead.next;
        return firstHead.next;
    }
}
