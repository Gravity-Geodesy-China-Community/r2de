!!! abstract "导言" 

在系统学习数据结构与算法之前，需要先了解今后会常用到的概念和工具

## 时间、空间复杂度

算法描述了解决一个具体问题的计算过程，那如何去评价一个算法呢？首先第一前提是**正确性**，如果都不能完成算法本身目标，剩余的工作也无从谈起。其次是评价算法所消耗的内存和时间，我们一般称之为**空间、时间复杂度**。每次算法执行的时间在不同输入测试结果下也不尽相同，而且程序执行的时间非常依赖硬件性能与运行环境，所以说绝对的运行时间和空间没有太大意义。

于是我们需要一个不需要具体例子测试，就可以估计出算法大致的执行效率情况的方法，这就是**时间、空间复杂度分析法**。

## 如为优秀的算法

有的人追求快，就用大量内存空间来换取时间上的减少，有的人则想尽可能用到更少的内存就能执行完算法。在计算机资源足够的情况下，我们可以用空间去换取一定的时间，但针对桌面用户或者笔记本电脑来说，追求**时间/空间 复杂度的平衡**至关重要，也就是我们在设计算法的时候，能够做好时间和空间分辨率能够尽可能的小。

## 复杂度分析

由于算法执行时间依赖于输入与硬件设备，于是 **渐进分析（asymptotic analysis）**逐渐成为复杂度分析的主要方法，一般我们用 **大O 表示法（Big O notation）**来表示分析结果。我们可以不通过具体测试，就可以用“肉眼”观察出程序的复杂度。

试想以下场景：

如果我们想从一个数组中查找某个值，直观的有两种思路: 

#### 1、依次遍历整个数组，再进行匹配，知道找到目标值为止。

![img](https://gitee.com/georgegou/gravitychina/raw/picture/202109181119973.png)

```python
def search(arr, n, x): 
  
    for i in range (0, n): 
        if (arr[i] == x): 
            return i; 
    return -1; 
```

这就是一个简单的线性搜索，在我们分析算法的时候，我们主要关注循环最多的那一段代码即可。当问题的规模无限增大时，其他位置的代码复杂度接近于无穷小，可以忽略，这就是所谓的 **渐进分析**。上述算法只需执行一层循环，次数为 `n`，这样其时间复杂度就为 `O(n)`。当有两层循环的时候，时间复杂度就是 `O(n^2)`。需要注意的是，如果循环里面有子函数也有循环的话，这种情况就是嵌套循环，应遵循**乘法准则**，即将两种循环的复杂度相乘。如果只是并列的关系，非嵌套，只需找到复杂度最高的一段代码即可。

#### 2、二分查找

```python
# 返回 x 在 arr 中的索引，如果不存在返回 -1
def binarySearch (arr, l, r, x): 
  
    # 基本判断
    if r >= l: 
  
        mid = int(l + (r - l)/2)
  
        # 元素整好的中间位置
        if arr[mid] == x: 
            return mid 
          
        # 元素小于中间位置的元素，只需要再比较左边的元素
        elif arr[mid] > x: 
            return binarySearch(arr, l, mid-1, x) 
  
        # 元素大于中间位置的元素，只需要再比较右边的元素
        else: 
            return binarySearch(arr, mid+1, r, x) 
  
    else: 
        # 不存在
        return -1
  
# 测试数组
arr = [ 2, 3, 4, 10, 40 ] 
x = 10

result = binarySearch(arr, 0, len(arr)-1, x) 
  
if result != -1: 
    print ("元素在数组中的索引为 %d" % result )
else: 
    print ("元素不在数组中")
```

​	元素在数组中的索引为 3

上述代码就是递归实现的二分查找算法

## 各种时间复杂度实例

### O(1)

```c
 int i = 8;
 int j = 6;
 int mean = (i + j) / 2;
```

这种情况是最简单的，即代码中没有递归、循环等操作。

### O(logn)、O(nlogn)

1、二分查找的时间复杂度就是 O(logn)

2、快速排序的时间复杂度就是O(nlogn)

以下是快速排序的 C 语言源代码：

```c
#include<stdio.h>
/* Logic: This is divide and conquer algorithm like Merge Sort. Unlike Merge Sort this does not require
          extra space. So it sorts in place. Here dividing step is chose a pivot and parition the array
          such that all elements less than or equal to pivot are to the left of it andd all the elements  
          which  are greater than or equal to the pivot are to the right of it. Recursivley sort the left 
          and right parts.
*/

void QuickSort(int *array, int from, int to)
{
        if(from>=to)return;
        int pivot = array[from]; /*Pivot I am chosing is the starting element */
        /*Here i and j are such that in the array all elemnts a[from+1...i] are less than pivot,
          all elements a[i+1...j] are greater than pivot and the elements a[j+1...to] are which 
          we have not seen which is like shown below.
          ________________________i_________________j___________
          |pivot|....<=pivot......|....>=pivot......|.........|
          If the new element we encounter than >=pivot the above variant is still satisfied.
          If it is less than pivot we swap a[j] with a[i+1].
         */
        int i = from, j, temp;
        for(j = from + 1;j <= to;j++)
        {
                if(array[j] < pivot) 
                {
                        i = i + 1;
                        temp = array[i];
                        array[i] = array[j];
                        array[j] = temp;
                }
        }
        /* Finally The picture is like shown below
          _______________________i____________________
          |pivot|....<=pivot......|....>=pivot......|
        */
        temp = array[i];
        array[i] = array[from];
        array[from] = temp;
        /* So we the array is now 
          ____________________i______________________
          |...<=pivot......|pivot|....>=pivot......|
        */
        /*Recursively sort the two sub arrays */
        QuickSort(array,from,i-1);
        QuickSort(array,i+1,to);
}
int main()
{
        int number_of_elements;
        scanf("%d",&number_of_elements);
        int array[number_of_elements]; 
        int iter;
        for(iter = 0;iter < number_of_elements;iter++)
        {
                scanf("%d",&array[iter]);
        }
        /* Calling this functions sorts the array */
        QuickSort(array,0,number_of_elements-1); 
        for(iter = 0;iter < number_of_elements;iter++)
        {
                printf("%d ",array[iter]);
        }
        printf("\n");
        return 0;
}
```

  

## 参考文献

[^1]: https://www.runoob.com/python3/python-linear-search.html

[^2]: https://www.thelearningpoint.net/computer-science/arrays-and-sorting-quick-sort--with-c-program-source-code

