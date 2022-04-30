!!! abstract "导言"
    本节主要介绍计算机中常用的排序算法，包括冒泡排序、选择排序、插入排序、快速排序、归并排序、计数排序、基数排序、桶排序、梳排序、希尔排序。

!!! question "为什么需要排序算法 ？"

    将一个数组中的整数进行排序，试想有多少种方法？谷歌给出将近40多种排序方法。各种方法在不同的应用场景中发挥着重要作用。试想2022年北京冬奥会，谷爱凌的比赛中，裁判打出比分，需要实时进行排序，在电视上给出目前的排名结果，这里用到的就是**快速排序**。在数据库中，对于大量数据的排序，受限于内存的容量，可以将数据分成几块，分别排好序后就Merge。

# 时间复杂度为 nlogn 的排序算法

## 快速排序（Quich Sort）

快速排序的基本思想是分而知之（[Divide and Conquer](https://www.geeksforgeeks.org/divide-and-conquer/)）。所谓分治法，就是将一个大问题分为若干个小问题，然后使用递归来解决问题。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/quicksort.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">快速排序示例图</div>
</center>
快速排序算法大概分为三个步骤；

 1）选择一个基准值（Pivot），上图选择的是数组中最后一个数作为Pivot，也可以选择第一个数，也可以随机选择。

2）分区，将所有小于Pivot的数据放入左数组，将所有大于Pivot的数据放入右数组。

3）递归调用Quichsort（递归停止的条件是数组双指针begin>end，即已经完整遍历完一遍数组了）


```python
# -- coding : utf-8 --
# @Time:2022/2/28 17:51
# @Author: Jianing Gou(goujianing19@mails.ucas.ac.cn)

'''Python3 implementation of QuickSort
Main idea: 1、find a pivot(Last number in a array);
2、partition, split the original array into two subarray, one is the number below the pivot,
one is over the pivot; 3、recursive the QuickSort algorithm
'''
def partition(arraylist, begin, end):
    # the aim of this method is to partition the array into two subarray that satisfy constrains above
    pivot_index = end
    pivot = arraylist[end]
    # double pointer method to traverse the array
    while begin < end:
        # to find and swap number below and upper the pivot
        while begin < len(arraylist) and arraylist[begin] < pivot:
            # when break this statement means there is a num
            # that bigger than pivot
            begin += 1
        while arraylist[end] >= pivot:
            # when break this statement means there is a num smaller than pivot
            end -= 1
        # swap value if we do not traverse the array completely
        if begin < end:
            array[begin], array[end] = array[end], array[begin]
        # put the pivot number in right place and return the new index
    arraylist[pivot_index], arraylist[begin] = arraylist[begin], arraylist[pivot_index]
    return begin


def quickSort(arraylist, begin, end):
    # always define the last number as the pivot：
    if begin < end:
        p = partition(arraylist, begin, end)  # return new pivot index and partition the array in two subarray
        # recursive
        quickSort(arraylist, begin, p - 1)  # left subarray equal to number lower than pivot
        quickSort(arraylist, p + 1, end)  # right subarray equal to number upper than pivot
    # Recursive ends when begin >= end means they are only one or two number in each subarray


if __name__ == '__main__':
    array = [10, 80, 30, 90, 70, 100, 50, 70]
    print('Original array: ', array)

    quickSort(array, 0, len(array) - 1)
    print('Sorted array: ', array)

```
主函数输出

```shell
Original array:  [10, 80, 30, 90, 70, 100, 50, 70]
Sorted array:  [10, 30, 50, 70, 70, 80, 90, 100]

Process finished with exit code 0
```

### 复杂度分析

对于快速排序来说，主要讨论分区函数的复杂度。对于完全有序的数组（如1、2、3、4、5），假如选择1为基点的话，则第一次分区需要比较（n-1）个元素，第二次分区需要比较（n-2)个元素，依次类推，第n-1次分区需要比较1个元素，则总次数为$n(n+1)/2$，即复杂度为$O(n^2)$。

在数组完全无序的情况下，每次基点都基本能选择在子列表的中央，这就和二叉搜索的时间复杂度一般，时间复杂度就是$O(nlog_2n)$


## 归并排序（Merge Sort）

归并排序同样参考了递归和分而治之的思想，因此其时间复杂也是$O(nlog_2n)$。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202203092240111.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">归并排序示例图</div>
</center>
如上图所示，归并排序的主要思想如下:

（1）：找到数组中间位置，将数组分割为两个子序列
（2）：然后递归地进行（1）步骤，直到数组被分为长度为 1 的数组
（3）：随后再进行上述过程的逆过程，将短数组进行重新组合（Merge），组合的阶段
对数组进行排序。

### 代码实现

