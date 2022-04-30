# 操作系统{#first-class}

!!! abstract "导言"

    操作系统是计算机科学中非常重要的学科，我们一般的工作都是在操作系统之上完成的，它就相当于系统级的软件，充当一层中间件，我们借助操作系统软件提供的接口就能够完成硬件的调用。由于Linux是开发的常用系统，本章以Linux为例，详细介绍Linux操作系统的进程管理、文件管理、用户管理等

## 计算机与操作系统 {#computer-and-os}

!!! note "现代个人计算机操作系统典型功能"

    通常来说，一个现代个人计算机操作系统会包含以下的功能[^1]：
    
    * 进程管理：操作系统之上运行的各类程序都以进程的形式存在，而通常计算机的中央处理器只有几个。为了能让许多进程并发执行，需要操作系统进行调度。
    
    * 内存管理：内存是操作系统和进程用来临时存储数据的介质。计算机的内存是有限的，而且通常还是多层次的，因此操作系统需要合理分配和回收内存。
    
    * 文件管理：为了管理计算机上的文件和数据，操作系统需要建立一个合适的数据结构来存储它们，即文件系统。
    
    * 网络通信：为了能和互联网上的其它计算机进行有效的联络，一个公认的通信协议（如当今互联网主流的 TCP/IP）是必要的。操作系统需要有能力实现各种必需的网络通信方式。
    
    * 安全机制：很多个人计算机和商业服务器上的数据都很敏感，因此操作系统必须配备一个安全机制用于保护数据不被未授权的人士获取，以及保护计算机免于各类计算机病毒的攻击。
    
    * 用户界面：现代的个人计算机操作系统通常都会包含一个图形化的用户界面，从而方便用户与计算机打交道，并且提升用户体验。
    
    * 驱动程序：驱动程序是直接与硬件交互的软件，使用驱动程序才能利用好计算机的硬件，因此操作系统要有能力与驱动程序对接以发挥硬件的功能。

## 进程管理 {#process-linux}
```shell
$ sudo yum -y install htop
$ htop
```
[htop](https://htop.dev/) 是一款强大的进程管理工具，关于这款工具的详细使用，可以参考[这篇文章](https://www.cnblogs.com/programmer-tlh/p/11726016.html)。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112111551849.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">htop软件显示的进程</div>
</center>

### htop 各参数含义
|  进程属性  | 意义/目的                                                                                                                                          |
| :--------: | -------------------------------------------------------------------------------------------------------------------------------------------------- |
|    PID     | Process ID，标识进程的唯一性。                                                                                                                     |
|    USER    | 进程的用户权限                                                                                                               |
|    PRI    | realtime **priority**，进程调度的优先级，值越大，优先级越高。                                                                                                     |
|   NI    | NICE，越nice越对其他进程越好，意味着其值越大，优先级越低，非实时调度Nice值就是0                                                                                           |
|    VIRT     | virtual memory usage，进程所占用的虚拟内存。                                      |
| RES | resident memory usage 常驻内存，不包换swap out，进程当前的内存大小                                                                                                                                   |
|  SHR  | shared memory，共享内存，。                                                         |                                                                                                  |
|   S   | State，标识进程的状态：能不能运行 (Running / sleep)，能不能投入运行 (interruptible / uninterruptible)，让不让运行 (stop / trace)，程序还在不在 (Zombie)，大写的R、S、Z分别表示运行、睡眠（等待唤醒）、僵尸状态。




!!! note "进程与线程"
    
    进程是**系统资源分配的基本单位**，而线程是**独立调度的基本单位**，也就是说**一个进程里面有多个线程**。

    比如说浏览器进程，里面包含了HTTP请求、服务器响应、前端渲染等线程等等，一个进程里面包含可以包含多个线程。

!!! question 计算机如何同时运行多个线程?  
哪怕是多核CPU在很短的时间中，只能执行几个程序，那么计算机是如何并行跑几十个、乃至几百个程序呢？

实际上，确实很短的时间内，CPU只能执行一个程序。严格意义上说，CPU在一个时钟周期执行一个程序，另一个时钟周期就开始执行另一个程序，由于CPU时钟周期很短，站在用户的角度讲，这些程序处于并行状态。
例如：CPU主频为3.0GHZ，则一个时钟周期 = 1 / 3GHZ = 1 / 3*10^9HZ ≈ 0.33 * 10^-9 秒

### 进程状态
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112121335771.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">进程的多种状态</div>
</center>
如上图所示，Main memory表示程序读入内存运行的主空间，Secondary memory表示因阻塞或者其他原因导致程序存入交换空间。

程序进程的大致过程为：首先创建进程（运行程序），程序读入内存，进入就绪状态，调入进程，使得进程使用CPU进入运行状态。如果出现 Timeout，耗尽CPU时间片(time slice)，重新回到就绪状态；如果运行中出现外部设备IO或者其他事件（Event Wait），进入事件等待状态，也就是阻塞（Blocked）。期间，如果发生内存不足的情况，程序将进入交换区挂起，等待下一次调用。

### 进程控制

用户如何控制进程？进程间不共享内存，共享内存地址在进程外。因此，操作进程必须借助操作系统。**信号**是Unix系统中进程之间相互通信的一种机制。

```shell
─(03:08:27)──> ping localhost                                                                                                                  1 ↵ ──(Sun,Dec12)─┘ 
PING localhost (127.0.0.1) 56(84) bytes of data.
64 bytes from localhost (127.0.0.1): icmp_seq=1 ttl=64 time=0.049 ms
64 bytes from localhost (127.0.0.1): icmp_seq=2 ttl=64 time=0.032 ms
64 bytes from localhost (127.0.0.1): icmp_seq=3 ttl=64 time=0.030 ms
^Z
[1]  + 6768 suspended  ping localhost
(base) ┌─(~)───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(george@-c:pts/1)─┐
└─(03:10:03)──> jobs  #可以查看到前面用CRTL+Z，挂起的程序                                                                                                                         20 ↵ ──(Sun,Dec12)─┘ 
[1]  + suspended  ping localhost
(base) ┌─(~)───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(george@-c:pts/1)─┐
└─(03:10:20)──> bg  %1           #后台运行 [1]号Job                                                                                                                      ──(Sun,Dec12)─┘ 
[1]  + 6768 continued  ping localhost
64 bytes from localhost (127.0.0.1): icmp_seq=4 ttl=64 time=0.036 ms
(base) ┌─(~)───────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────────(george@-c:pts/1)─┐
└─(03:10:47)──> 64 bytes from localhost (127.0.0.1): icmp_seq=5 ttl=64 time=0.035 ms                                                                ──(Sun,Dec12)─┘ 
64 bytes from localhost (127.0.0.1): icmp_seq=6 ttl=64 time=0.036 ms
64 bytes from localhost (127.0.0.1): icmp_seq=7 ttl=64 time=0.030 ms
64 bytes from localhost (127.0.0.1): icmp_seq=8 ttl=64 time=0.031 ms
64 bytes from localhost (127.0.0.1): icmp_seq=9 ttl=64 time=0.032 ms
```

```shell
(base) {21-12-12 3:14}-c:~ george% kill -l #查看kill命令,第九个是Kill 信号的命令
HUP INT QUIT ILL TRAP ABRT BUS FPE KILL USR1 SEGV USR2 PIPE ALRM TERM STKFLT CHLD CONT STOP TSTP TTIN TTOU URG XCPU XFSZ VTALRM PROF WINCH POLL PWR SYS
```
所以我们通过```kill -9 pid(进程号)```就可以结束某个进程。

!!! tip "脱离终端"
注意到一但我们结束一个会话（session），所开启的服务或者进程会随之关闭，这是因为终端关闭时会发送（singal hangup），该信号会广播到会话下每一个进程，默认退出程序运行。如果我们不想退出该怎么办？如何我们开启了一个jupyter notebook服务，想让他一直在后台进行，可以在命令前加上```nohup```，来保证进程不会信号影响。

!!! tip "守护进程（daemon）"
无论是Web服务、数据库服务、Dokcer服务，这些都是运行在服务器上的后台服务，它们时刻处于监听状态，一旦有请求，就会响应服务，这些一直默默工作于后台的进程被称为**守护进程**。

    

## 引用来源 {#references .no-underline}

[^1]: [中科大Linux讲义](https://101.lug.ustc.edu.cn/)

