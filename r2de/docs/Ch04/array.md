# 数组

!!! abstract "导言"

    数组是一个非常重要且常见的数据结构，无论是在科研工作还是互联网应用中都发挥了重要作用。

## 数组定义

数组就是存储在连续内存中的数据集合，其中存储的都是相同类型的数据（String、Int）。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202110131032401.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">数组示意</div>
</center>

## 数组特点

1. 一般来说，数组的索引都是从 0 开始的。也有从 1 开始的（比如 Matlab）

??? tip "索引为什么要从零开始？"

```
a[j] = base adress + [j] * data_type_byte, 比如字符“C”的地址为 200 + 6*1 = 206，
如果下标从1开始的话，上述公式就变为 a[j] = base adress + [j-1] * data_type_byte，
这样就会多一次减法运算。
```

2、数组能够实现随机访问，即通过下标即可访问相应地址的数据，这种机制有利有弊。数组随机访问就是一个简单的寻址过程，时间复杂度为 O(1)。但是要想实现数组的插入和删除就变得稍许复杂，因为数组是在内存上连续存储的，对某个数的删除与插入操作势必会产生大量的平移操作。

3、**警惕数组访问越界问题**，切记在编程过程中进行边界检查。

## 数组容器

在大多数面向对象语言，如C++[^1]，Java中都定义了数组容器供开发者使用。比如C++ STL 里面的 array、vector，java里面的Arraylist，array 数组长度一旦定义不能变化，vector、Arraylist根据实际需求可以动态增加或者减少元素[^2]，数组的扩容原理很简单，就是从新开辟一个更大的数组，再把原来的数据复制过去，再插入新的数组，每次扩容到原来数组大小的1.5倍。

!!! note "数组扩容"

    实际开发工作中，不追求系统的极致性能的话，直接使用数据容器完成开发即可，容器内部提供了常用的方法。但是对于某些特殊的情况，用数组效率会更高，比如知道数据的类型和大小，比如int、long，在Java里会封装成Integer、Long，而 [装箱与拆箱] 的过程本身会损耗效率。
 
## 参考文献
 
[^1]: http://c.biancheng.net/view/6688.html  
[^2]: https://blog.csdn.net/lan266548_ning/article/details/72565272