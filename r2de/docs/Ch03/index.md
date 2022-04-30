# 概述

!!! abstract "导言"

    数据结构 (data structure) 是计算机中数据的存储方式，不同的应用场景使用相应的数据结构能够程序效率。算法是对某个需求的一个完整而准确的描述，处理的对象就是各种数据结构。本教程是数据结构与算法的基本教程，主要实现语言为Python。

## 为什么要学习数据结构 {#computer-and-os}

数据结构，如数组、栈、树、图等，是计算机中数据的特殊组织方式。同一种应用中，合适的数据处理方式能够提升程序的运行效率。`Word` 想必大家都再熟悉不过了吧，那微软是如何实现存储每一步的 `undo/redo` 操作，那就是利用**栈**的前进后出特性，就可以实现上述功能。 再比如，社交软件存储的社交网络信息，是一对多的关系，多个点组成**图**。在象棋游戏中，每一步的潜在下一步是一对多的关系，这可以通过**树**结构实现。图像在计算机中以二维矩阵的方式存储，这就可以通过**数组**这种数据结构进行高效的存储。针对特定问题进行高效的数据结构设计，能够显著提升效率。

## 为什么学习算法

??? tip "算法维基百科定义"

    In mathematics and computer science, an algorithm (/ˈælɡərɪðəm/ (About this soundlisten)) is a finite sequence of well-defined, computer-implementable instructions, typically to solve a class of specific problems or to perform a computation. Algorithms are always unambiguous and are used as specifications for performing calculations, data processing, automated reasoning, and other tasks. In contrast, a heuristic is a technique used in problem solving that uses practical methods and/or various estimates in order to produce solutions that may not be optimal but are sufficient given the circumstances.

总体来说，算法就是可解决某一类问题的有限指令序列，被用于执行计算、数据处理、自动推理等任务。算法与数据结构已经在各行各业发挥了巨大的作用，比如：

1. 利用地震仪观测数据快速反演震源参数，用来快速发布地震信息；
2. 加密通讯里面的公钥加密依赖于数论算法；
3. 平衡搜索树用到数据库索引中增加索引的效率；

等等应用，计算机算法已经属于百花齐放、百鸟争鸣的阶段了，但各个垂直领域的专业算法还有很大开发价值，前提是最基础的数据结构与算法。无论是再复杂的算法，其本质还是由一些基本的数据结构与算法单元构成，故学好算法与数据结果，走遍天下都不怕！

## 数据结构与算法的关系

相互之间存在关系的数据元素的集合就是数据结构，算法是解决特定问题的有限求解步骤。两者关系密切，算法是由数据结构与逻辑构成的。

## 数据结构与算法框架

![contents](https://gitee.com/georgegou/gravitychina/raw/picture/202109151110228.jpg)

数据结构与算法包含的内容大致如上图所示。掌握以上知识点，基本上能够自主实现互联网中所需算法的开发。当然，这需要一定的训练量。

## 引用来源 {#references .no-underline}

[^1]: https://www.geeksforgeeks.org/data-structures/?ref=shm

