# 算法题快速求解技巧

!!! abstract "导言"

    对于应对笔试来说，掌握几种常见的算法套路即可应对绝大多数的大厂笔试。比常见的双指针、递归与回溯

## 递归

!!! note "定义"

      **Recursion** (adjective: recursive) occurs when a thing is defined in terms of itself or of its type. Recursion is used in a variety of disciplines ranging from linguistics to logic. The most common application of recursion is in mathematics and computer science, where a function being defined is applied within its own definition.  
    
### 什么是递归？
维基百科给出的定义为：递归常常发生于对于一件事情的定义用到了本身或者其衍生的东西，递归在语言学或逻辑学中常常用到，现在经常用在数学或者计算机科学中，其中定义的函数用于自己的定义。

说了好像又什么都没说，简单点说就是**套娃**，函数调用函数本身。

学习一个新的东西，首先要了解它的定义，明白是什么？递归在什么场景下会用到呢？斐波那契数列大家应该都不陌生，斐波那契数列数学表示式有两种：
$$f(n) = 1 + 2 + 3 +……..+ n       \tag{1}$$.
$$
\begin{align}
f(n) &= 1 \quad \quad n = 1\\
f(n) &= n + f(n-1) \quad  \quad n>1\\
\end{align} \tag{2}
 $$
第二种表达方式利用了递推公式，最早可以追溯到我们高中学过的知识（递推公式），当$n>1$时，注意到，我们在$f()$中又调用了该函数，这就是**递归调用**。

看似很复杂的求和，我们通过将问题拆分为一个个子问题，调用递归函数去完成它，相当于就化繁为简了，这是计算机算法解决实际问题优势所在。但是需要注意**递归结束**的条件，否则程序将无限制地递归下去，导致程序崩溃。

在阶乘的例子中，有如下代码：
```java
int factorial(int n){
    if(n <= 1) //base case
       return 1;
    else
        return n*factorial;
}
```
所谓的 base case 就是结束递归的条件，说明程序已经把问题分解成最简单的问题了，不能够再细分下去。设想我们如果设置 `n = 100`，当输入的`n < 100`时，永远达不到100，意味着程序无法到达结束递归的条件。

### 递归程序的执行过程

我们都知道程序的执行过程，从`main`函数进入主函数，遇到子函数，将其压入栈，然后返回后依次出栈，将返回值传入上一个函数中，下面举例说明递归函数的执行过程。
```c++

// A C++ program to demonstrate working of
// recursion
#include <bits/stdc++.h>
using namespace std;
 
void printFun(int test)
{
    if (test < 1)
        return;
    else {
        cout << test << " ";
        printFun(test - 1); // statement 2
        cout << test << " ";
        return;
    }
}
 
// Driver Code
int main()
{
    int test = 3;
    printFun(test);
}
```
执行程序后，会输出什么？大家可以先思考一下：
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/recursion.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">内存栈示意图</div>
</center>

首先，赋值`test = 3`，进入`printFun(3)`函数，打印一个`3`，不满足递归结束条件，进入第一次递归`printFun(2)`，后面的`printf`函数暂时没有执行，因为进入了子函数，等待子函数返回结果，再执行该语句；所以再打印一个`2`，进入第二次递归函数`printFun(1)`，打印一个`1`；进入第三次递归`printFun(0)`，没有输出，直接返回到`printFun(1)`函数，执行第三句`cout << test << " ";`，打印一个`1`，因为上一级函数没有返回`test`，所里`test`变量的值在`printFun(1)`里面的值是`1`，依次返回到`printFun(3)`函数里面，最后回到主函数里面，完成递归。所以输出为：
```shell
3 2 1 1 2 3
```

## 回溯 {#backtrack}
!!! note "定义"

    **Backtracking** can be defined as a general algorithmic technique that considers searching every possible combination in order to solve a computational problem. 

### 什么是回溯？

维基百科给出的定义为：回溯，是一种搜索所有可能的候选解来解决计算问题的算法。

当我们在所有的过程中，所得的解不能满足题目给出的条件，就回到上一步，重新搜索结果（**这就是回溯的核心思想**）。稍后将给出著名的[解数独](#SloveShudu)例子来带大家体会回溯的过程。

一步一步解决问题，意味着需要在解中间中搜索，如何搜索呢？这里我们就需要先了解搜索的基本知识：广度优先搜索、深度优先搜索。

#### 广度优先搜索 

#### 深度优先搜索 (Depth Search First, DFS)

深度优先搜索 (Depth Search First, DFS)，
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/backtrack.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">数的深度优先遍历</div>
</center>


### 例子：解数独 {#SloveShudu}

```java
class Solution {

    public void solveSudoku(char[][] board) {
        backtrack(board, 0, 0);
    }

    /**
     * 遍历中一旦找到解 就立刻返回 因此该函数需要有返回结果
     */
    private boolean backtrack(char[][] board, int row, int col) {
        //终止条件 即所有区域填充完毕
        if(col == 9) {
            col = 0;
            row++;
            if(row == 9) {
                return true;
            }
        }

        // 跳过已经填充的区域
        if(board[row][col] != '.' ) {
            return backtrack(board, row, col + 1);
        }
        // 穷举1-9
        for(int num = 1; num <= 9; ++num) {
            if(isVaild(board, row, col, (char)(num + '0'))) {

                board[row][col] = (char)('0' + num);

                if(backtrack(board, row, col + 1)) {
                    return true;
                }
                //暂时没有找到可行解 执行回退
                board[row][col] = '.';
            }
        }
        // 当前区域(row, col)穷举完毕 没有找到可行解 则返回false 回到上一个区域继续换数字穷举
        return false;
    }

    private boolean isVaild(char[][] board, int row, int col, int targetNum) {
        for(int i = 0; i < 9; ++i) {
            //当前行是否已有该数
            if(board[row][i] == targetNum) {
                return false;
            }

            //当前列是否已有该数
            if(board[i][col] == targetNum) {
                return false;
            }

            //当前九宫格是否已有该数
            if(board[row - (row % 3) + i / 3][col - (col % 3) + i % 3] == targetNum) {
                return false;
            }
        }
        return true;
    }
}

```


## 双指针



1. 一般来说，数组的索引都是从 0 开始的。也有从 1 开始的（比如 Matlab）

??? tip "索引为什么要从零开始？"

```
a[j] = base adress + [j] * data_type_byte, 比如字符“C”的地址为 200 + 6*1 = 206，
如果下标从1开始的话，上述公式就变为 a[j] = base adress + [j-1] * data_type_byte，
这样就会多一次减法运算。
```

2、数组能够实现随机访问，即通过下标即可访问相应地址的数据，这种机制有利有弊。数组随机访问就是一个简单的寻址过程，时间复杂度为 O(1)。但是要想实现数组的插入和删除就变得稍许复杂，因为数组是在内存上连续存储的，对某个数的删除与插入操作势必会产生大量的平移操作。

3、**警惕数组访问越界问题**，切记在编程过程中进行边界检查。

## 枚举

什么是枚举？

## 数组容器

在大多数面向对象语言，如C++[^1]，Java中都定义了数组容器供开发者使用。比如C++ STL 里面的 array、vector，java里面的Arraylist，array 数组长度一旦定义不能变化，vector、Arraylist根据实际需求可以动态增加或者减少元素[^2]，数组的扩容原理很简单，就是从新开辟一个更大的数组，再把原来的数据复制过去，再插入新的数组，每次扩容到原来数组大小的1.5倍。

!!! note "数组扩容"

    实际开发工作中，不追求系统的极致性能的话，直接使用数据容器完成开发即可，容器内部提供了常用的方法。但是对于某些特殊的情况，用数组效率会更高，比如知道数据的类型和大小，比如int、long，在Java里会封装成Integer、Long，而 [装箱与拆箱] 的过程本身会损耗效率。
 
## 参考文献
 
[^1]: [https://www.geeksforgeeks.org/recursion/?ref=gcse](https://www.geeksforgeeks.org/recursion/?ref=gcse)  
[^2]: [https://www.geeksforgeeks.org/backtracking-algorithms/?ref=gcse](https://www.geeksforgeeks.org/backtracking-algorithms/?ref=gcse)
[^3]: [https://algotree.org/algorithms/tree_graph_traversal/depth_first_search/](https://algotree.org/algorithms/tree_graph_traversal/depth_first_search/)