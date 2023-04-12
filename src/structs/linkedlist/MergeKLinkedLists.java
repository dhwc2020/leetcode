package structs.linkedlist;

import java.util.*;

/**
 * leecode: 23.合并K个升序链表
 *
 * 给你一个链表数组，每个链表都已经按升序排列。
 *
 * 请你将所有链表合并到一个升序链表中，返回合并后的链表。
 */
public class MergeKLinkedLists {

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>(Comparator.comparingInt(Integer::intValue), true);
        heap.heapInsert(5);
        heap.heapInsert(3);
        heap.heapInsert(1);
        heap.heapInsert(9);

        heap.remove(2);
        System.out.println(heap);

//        MergeKLinkedLists mergeKLinkedLists = new MergeKLinkedLists();
//        List<ListNode> lists = new ArrayList<>();
//        ListNode first = new ListNode(1);
//        first.next = new ListNode(4);
//        first.next.next = new ListNode(5);
//        ListNode second = new ListNode(1);
//        second.next = new ListNode(3);
//        second.next.next = new ListNode(4);
//        ListNode third = new ListNode(2);
//        third.next = new ListNode(6);
//        lists.add(first);
//        lists.add(null);
//        lists.add(second);
//        lists.add(third);
//        ListNode listNode = mergeKLinkedLists.mergeKLists(lists);
    }

    /**
     *  思路: 1.需要k个指针指向各自链头
     *       2.从k个指针中找到最小值, 放入新链表中
     *       3.最小值指针后移一位(走到末尾不再后移和比较)
     *       4.所有链都走完, 返回结果
     */
    public ListNode mergeKLists(List<ListNode> lists){
        if(lists.size() < 1) return null;
        if(lists.size() == 1) return lists.get(0);

        ListNode head = new ListNode();
        ListNode tail = head;
        Heap<ListNode> heap = new Heap<>(Comparator.comparingInt(ListNode::getVal), true);
        heap.buildHeap(lists);

        while(heap.size() > 0){
            ListNode top = heap.getHeapTop();

            if(Objects.isNull(top.next)){
                heap.remove(0);
            }else{
                heap.heapify(0, top.next);
            }

            tail.next = top;
            tail = tail.next;
        }

        return head.next;
    }
}

class Heap<T>{
    private List<T> heapContent = new ArrayList<>();
    private Comparator<T> comparator;
    private boolean minHeap;

    public Heap(Comparator<T> comparator, boolean minHeap) {
        this.comparator = comparator;
        this.minHeap = minHeap;
    }

    //时间复杂度为O(NlogN)(可以通过heapify优化为O(N))
    public void buildHeap(List<T> list){
        Objects.requireNonNull(list, "参数不能为空");

        list.stream()
                .filter(Objects::nonNull)
                .forEach(this::heapInsert);
    }

    //   1        0         i = 3(层数)  2^(i - 1)(每层节点数)   x = 6(某一个节点下标, 右节点)
    // 1   1    1   2       x - 2^(i - 1) = parentIndex(父节点下标)
    //1 1 1 1  3 4 5 6      x = (2^i - 1)(总节点数) - 1    parentIndex = 2^(i - 1) - 2 = x / 2 - 1
    //heapInsert的时间复杂度只与完全二叉树的深度有关, 故为O(logN)
    public void heapInsert(T t){
        Objects.requireNonNull(t, "参数不能为空");

        int curIndex = this.size();
        //左右节点下标加一, 使其都满足parentIndex = 2^(i - 1) - 2 = x / 2 - 1
        int parentIndex = (curIndex + 1) / 2 - 1;

        this.heapContent.add(t);

        while(parentIndex >= 0){
            T parent = this.heapContent.get(parentIndex);

            //节点与父节点比对, 无需交换直接退出
            if(this.minHeap && this.compareGe(t, parent) || !this.minHeap && this.compareGe(parent, t)){
                return;
            }

            this.heapContent.set(parentIndex, t);
            this.heapContent.set(curIndex, parent);
            curIndex = parentIndex;
            parentIndex = (curIndex + 1) / 2 - 1;
        }
    }

    //first大于等于second
    private boolean compareGe(T first, T second){
        return this.comparator.compare(first, second) >= 0;
    }

    public void heapify(int index, T target){
        this.checkRange(index);
        Objects.requireNonNull(target, "参数不能为空");

        this.heapContent.set(index, target);
        this.heapify(index);
    }

    //思想: 以index为堆的根节点, 但只有index处的节点可能不满足该堆定义
    //     那么只需要将index节点向下调整, 同时路径长度只与堆的深度有关
    //     时间复杂度为O(logN)
    public void heapify(int index){
        this.checkRange(index);

        //parentIndex = (leftChildIndex + 1) / 2 - 1
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = leftChildIndex + 1;

        //左孩子下标越界, 直接退出
        while(leftChildIndex < this.size()){
            //右孩子下标越界, 则用左孩子下标替换
            rightChildIndex = rightChildIndex < this.size() ? rightChildIndex : leftChildIndex;

            T leftChild = this.heapContent.get(leftChildIndex);
            T rightChild = this.heapContent.get(rightChildIndex);
            T parent = this.heapContent.get(index);

            //小根堆且左孩子不大于右孩子 或 大根堆左孩子不小于右孩子时, 选择左孩子下标
            int targetIndex = this.minHeap && this.compareGe(rightChild, leftChild) ||
                    !this.minHeap && this.compareGe(leftChild, rightChild) ?
                    leftChildIndex : rightChildIndex;

            //选中节点与父节点进行比较, 当为小根堆且选中节点不小于父节点 或 为大根堆且选中节点不大于父节点时, 直接退出
            T target = this.heapContent.get(targetIndex);
            if (this.minHeap && this.compareGe(target, parent) ||
                    !this.minHeap && this.compareGe(parent, target)){
                break;
            }

            //交换目标节点与父节点的位置
            this.heapContent.set(targetIndex, parent);
            this.heapContent.set(index, target);

            //更新各指针信息, 为原始父节点找到合适的位置
            index = targetIndex;
            leftChildIndex = 2 * index + 1;
            rightChildIndex = leftChildIndex + 1;
        }

    }

    //时间复杂度为O(logN)
    public void remove(int index){
        this.checkRange(index);

        //将最后一个节点移动到index处, 然后调整堆结构
        T last = this.removeLast();

        if (this.size() < 1) return;

        this.heapContent.set(index, last);
        this.heapify(index);
    }

    //移除最后一个不需要调整堆结构, 时间复杂度为O(1)(没有数组扩容和元素移动)
    public T removeLast(){
        return this.heapContent.remove(this.size() - 1);
    }

    private void checkRange(int index){
        if(index < 0 || this.size() <= index)
            throw new IndexOutOfBoundsException("下标越界index: " + index);
    }

    public T getHeapTop(){
        return this.heapContent.get(0);
    }

    public int size(){
        return this.heapContent.size();
    }

    @Override
    public String toString() {
        return "Heap" + this.heapContent.toString();
    }
}