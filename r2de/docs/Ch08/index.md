# 面试专题{#first-class}

!!! abstract "导言"

    本章主要汇总目前互联网知名企业的算法题，涵盖数据结构与算法、数据库、操作系统、机器学习、编程语言基础（主要是JAVA与Python）、大数据开发生态系统等等。
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

## 进程控制

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

!!! tip "脱离终端"
注意到一但我们结束一个会话（session），所开启的服务或者进程会随之关闭，这是因为终端关闭时会发送（singal hangup），该信号会广播到会话下每一个进程，默认退出程序运行。如果我们不想退出该怎么办？如何我们开启了一个jupyter notebook服务，想让他一直在后台进行，可以在命令前加上```nohup```，来保证进程不会信号影响。

!!! tip "守护进程（daemon）"
无论是Web服务、数据库服务、
    
```shell
(base) {21-12-12 3:14}-c:~ george% kill -l #查看kill命令,第九个是Kill 信号的命令
HUP INT QUIT ILL TRAP ABRT BUS FPE KILL USR1 SEGV USR2 PIPE ALRM TERM STKFLT CHLD CONT STOP TSTP TTIN TTOU URG XCPU XFSZ VTALRM PROF WINCH POLL PWR SYS
```
所以我们通过```kill -9 pid(进程号)```就可以结束某个进程。
### Linux 发行版 {#linux-distributions}

一个典型的 Linux 发行版除了 Linux 内核以外，通常还会包括一系列 GNU 工具和库、一些附带的软件、说明文档、一个桌面系统、一个窗口管理器和一个桌面环境。不同的发行版之间除了 Linux 内核以外的其它部分都有可能不一样，因此有的时候我们对比某两种发行版的时候会觉得它们看起来像是完全不一样的操作系统，然而实质上它们却拥有着相同的核心，即 Linux 内核。

这里给读者介绍若干桌面和服务器环境中主流的发行版分支：

#### Debian 分支 {#debian-branch}

Debian 是一个完全由自由软件构成的类 UNIX 操作系统，第一个版本发布于 1993 年 9 月 15 日，迄今仍在维护，是最早的发行版之一。其以坚持自由软件精神和生态环境优良而出名，拥有庞大的用户群体，甚至自己也成为了一个主流的子框架，称为“Debian GNU/Linux”。

![](images/Debian-Logo.png)

<p class="caption">Debian 图标</p>

Debian GNU/Linux 也派生了很多发行版，其中最为著名的便是 Ubuntu（官方译名“友邦拓”）。Ubuntu 由英国的 Canonical 公司主导创立，是一个主打桌面应用的操作系统。其为一般用户提供了一个时新且稳定的由自由软件构成的操作系统，且拥有庞大的社群力量和资源，十分适合普通用户使用。

![](images/Ubuntu-Logo.png)

<p class="caption">Ubuntu 图标</p>

#### Red Hat 分支 {#red-hat-branch}

Red Hat Linux 是美国的 Red Hat 公司发行的一个发行版，第一个版本发布于 1994 年 11 月 3 日，也是一个历史悠久的发行版。它曾经也广为使用，但在 2003 年 Red Hat 公司停止了对它的维护，转而将精力都投身于其企业版 Red Hat Enterprise Linux（简称 RHEL）上，Red Hat Linux 自此完结，而商业市场导向的 RHEL 维护至今。

![](images/Red-Hat-Logo.png)

<p class="caption">Red Hat 公司商标，RHEL 是其旗下产品</p>

在 Red Hat Linux 在停止官方更新后，由社群启动的 Fedora 项目接管了其源代码并构筑了自己的更新，演变成了如今的 Fedora 发行版。Fedora 是一套功能完备且更新迅速的系统，且本身计划也受到了 Red Hat 公司的赞助，成为了公司测试新技术的平台。

![](images/Fedora-Logo.png)

<p class="caption">Fedora 图标</p>

虽然 RHEL 是一个收费的、商业化的系统，但是其遵循 GNU 通用公共许可证，因此会开放源代码。编译这些源代码可以重新得到一个可以使用的操作系统，即一个新的发行版：CentOS（Community Enterprise Operating System，社区版企业操作系统）。因为 CentOS 几乎完全编译自 RHEL 的代码，所以其也像 RHEL 一样具有企业级别的稳定性，适合在要求高度稳定的服务器上运行。但在 2020 年 12 月，CentOS 社区在其博客中宣布未来的重点转向 CentOS Stream，这是一个全新的滚动发行版，和往日注重稳定性的 CentOS 形成鲜明对比。

![](images/CentOS-Logo.png)

<p class="caption">CentOS 图标</p>

#### Arch Linux 分支 {#arch-linux-branch}

Arch Linux 是一个基于 x86-64 架构的 Linux 发行版，不过因为其内核默认就包含了部分非自由的模块，所以其未受到 GNU 计划的官方支持。即便如此，Arch Linux 也因其“简单、现代、实在、人本、万能”的宗旨赢得了 Linux 中坚用户的广泛青睐。不过，Arch Linux 对这个宗旨的定义和其它发行版有所区别。通常的操作系统为了方便用户快速上手，都是尽可能隐藏底层细节，从而避免用户了解操作系统的运行知识即可直接使用。但是 Arch Linux 则是重在构建优雅、极简的代码结构，这方便了使用者去理解系统，但不可避免地要求使用者自身愿意去了解操作系统的运作方式。某种程度上说，它的“简单”和“人本”注重的是方便用户通过了解而去最大化地利用它，而不是采取屏蔽工作原理的方式来降低使用门槛。因此，本书不建议初学者直接上手 Arch Linux，但十分推荐在读者对 Linux 有进一步了解之后去探索它。

![](images/Arch-Linux-Logo.png)

<p class="caption">Arch Linux 图标</p>

Arch Linux 拥有强大的功能，但因其特殊的理念使得用户不易使用。为了能让一般用户也能用上 Arch Linux 的强大功能，它的变种 Manjaro 发行版于 2011 年问世。Manjaro 发行版基于 Arch Linux，但更注重易用，因而更适合一般用户。

![](images/Manjaro-Logo.png)

<p class="caption">Manjaro 图标</p>

以上是若干个常见的 Linux 发行版系列，由上文可见，Linux 的发行版非常丰富，不同的发行版有其各自的特性，因而可以面向不同的用户满足独特的需求。对于新手来说，一个拥有丰富的图形界面的发行版更加适合初步探索和后续使用。**本书推荐初次接触 Linux 的读者优先采用 Ubuntu 发行版或者它的子发行版（Lubuntu, Xubuntu 等）** 作为自己接触和探索 Linux 的平台，在以后可以自行上手其它发行版。

!!! note "Linux 总结"

    这一章或许对于一些读者来说不太容易一次读懂，因为不同于 Windows 或者 macOS 这种受到一家商业公司完全控制和规划的系统那样有着比较单一和线性的发展轨迹，Linux 其独特的自由、开源的特性注定了它的发展进程必定是一个去中心化的、非线性的形式。因此，Linux 的历史虽然只有短短三十年，却充满着复杂的脉络和丰富的细节。但是正是因为 Linux 在如此繁茂的分枝之上结出诸多硕果，我们才能从某种角度感受到开源社区文化的强大活力，反过来也正是这种活力才能让 Linux 有着与其它闭源商业操作系统不一样的叙事。
    
    回到本章的标题：什么是 Linux？这个问题在不同的语境下有不同的答案：它可以指代 Linux 内核，也可能指代一个或者多个 Linux 发行版。在日常领域或是作为新手接触到的情境来看，这个词通常都是指代后者，而且往往指的是 GNU/Linux 的发行版。

## 我们身边的 Linux {#linux-around-us}

虽然在个人电脑上不太常见，但 Linux 的各类发行版其实早已深入我们的日常生活。下面是几个例子：

### 智能手机 {#smartphones}

智能手机目前有两个主流的操作系统：苹果公司研发的 iOS 操作系统和谷歌公司研发的 Android 操作系统，而 Android 正是 Linux 的一个知名的发行版。与通常安装在通用计算机上的 GNU/Linux 分支不同，Android 属于 Android/Linux 分支，这个分支通常活跃在智能手机和嵌入式设备的舞台上。

由谷歌公司推出的 Android 叫做 Android 原生系统，而基于该原生系统诞生出来的各类独特的操作系统就是 Android/Linux 系下的子发行版。Android/Linux 下的子发行版很多，如华为公司的 EMUI 操作系统和小米公司的 MIUI 操作系统等。

![](images/Android-10-Native.png)

<p class="caption">Android 10 原生界面</p>

### 服务器 {#servers}

现代人的生活已经很难离开互联网了，在互联网上，我们可以访问各式各样的网站、利用在线社交平台分享自己的生活、或者是使用联机办公工具和同事协同工作。通常来说这些网站和软件的提供商都需要设立他们自己的计算机来完成计算、存储和通信的功能，这种计算机就被称为服务器。和个人计算机不同，服务器通常都不会使用 Windows 或者 macOS 这种个人计算机操作系统，事实上绝大部分的服务器维护人员都愿意选择一些 Linux 发行版作为它们的操作系统，因为许多 Linux 发行版界面简洁，功能强大，而且某些发行版也是受到专业计算机企业的服务支持的（如前文提到的 RHEL 和 CentOS）。

另一类有名的服务器操作系统是微软公司的 Window Server 系列，不过其流行程度比不上各类 Linux 发行版。

![](images/Windows-Server.png)

<p class="caption">Windows Server 图标</p>

### 电视机顶盒 {#digital-tv}

比起十几年前采用传统线路的电视，现在国内很多家庭里的电视都换成了智能数字电视，这些电视通常会配备一个机顶盒来控制电视播放的内容。实际上，电视机顶盒就是一个嵌入式设备，而 Android/Linux 分支下的各类发行版正是主流的嵌入式操作系统，如谷歌公司为数字电视专门推出的 Android TV 操作系统。

![](images/Android-TV.png)

<p class="caption">Android TV 图标</p>

## 让自己的计算机用上 Linux {#use-linux}

有很多尚未接触过 Linux 的读者看到这里可能已经在期待或者计划让自己尽快开始使用 Linux 了。事实上，如果把 Linux 看作一个领域，那它的确是一个重视实践的领域。而且出于学习目的，在阅读本书未来的章节时在手头准备一个随时可用的 Linux 发行版是十分关键和有益的。因此，本书**强烈建议各位读者在本机安装一个属于自己的 Linux 发行版**，以供随时实践。

在本机上安装一个 Linux 发行版有很多种选择，如：安装方法可以选择实机安装或虚拟机安装；发行版则可以在诸多选项中任意抉择。然而，对于新手来说，本书**不建议直接采用实机安装 Linux**，因为这样做会有以下缺点：

- 在安装过程中不理解关键的选项（如：磁盘分区、挂载、交换空间分配等）的意义，很容易做出错误的决定；
- 错误的配置可能导致自己原先本机上的操作系统和数据遭到不可逆转的损坏；
- 部分硬件可能对安装的发行版缺少兼容，从而导致意外安装失败。
- 如果安装的过程中选择下载附加工具，可能会因为默认镜像在国外而导致下载十分缓慢，从而让安装流程变得很漫长。

鉴于以上问题对于新手来说十分常见，本书的编写组为各位读者专门设计了另外一种更为安全高效的方法：在虚拟机上运行 Linux 发行版镜像。虚拟机简单来说可以视作一个安全可靠的沙盒，它受到虚拟机管理软件的管理，而管理软件是直接安装在自己目前常用的操作系统上的。本书**推荐使用虚拟机运行安装完毕的 Linux 镜像**，因为这样会有如下优点：

- 读者仍然可以安心地使用自己当前的操作系统，因为虚拟机不干涉当前电脑操作系统的配置。
- 无需考虑底层硬件的兼容性问题，稳定性大幅提升。
- 系统已经安装完毕，使用虚拟机打开时相当于直接开机，无需经历安装流程。
- 如果在虚拟机中发生任何错误，可以通过重置、回溯虚拟机镜像的方法无痛修复，而不会伤害到读者计算机上的操作系统和数据。

因此，本书讲主要讲解如何为自己搭建一个安全高效的 Linux 虚拟机。

### 获取虚拟机管理软件 {#get-vm-softwares}

现在在 Windows / macOS 上主流的虚拟机管理软件有：

- [VMware Workstation Player](https://www.vmware.com/cn/products/workstation-player/workstation-player-evaluation.html) 是 VMware 公司推出的一款 Windows 上专业的虚拟机管理软件。

- [VirtualBox](https://www.virtualbox.org/wiki/Downloads) 是甲骨文公司发行的通用虚拟机管理系统，支持 Windows 和 macOS，且遵循 GPLv2 开源。

以上两款软件都是免费的，且支持中文。点击上面对应的链接进入官方下载页面获取安装包，获取完毕后，直接双击打开安装程序，根据安装步骤完成安装即可。

### 获取 Xubuntu 虚拟机镜像 {#get-vm-images}

Xubuntu 是 Ubuntu 的一个子发行版，它与 Ubuntu 非常类似，但其体积更小，性能需求更少，因此十分适合各种不同性能的电脑安装使用。本书的编写组已经制作了 Xubuntu 的虚拟机镜像，供读者按需求下载使用。

[VMware Xubuntu 18.04 32 位](https://ftp.lug.ustc.edu.cn/101/vm/VMware-XUbuntu-18.04-32bit.ova)

[VMware Xubuntu 18.04 64 位](https://ftp.lug.ustc.edu.cn/101/vm/VMware-XUbuntu-18.04-64bit.ova)

[VirtualBox Xubuntu 18.04 32 位](https://ftp.lug.ustc.edu.cn/101/vm/VirtualBox-XUbuntu-18.04-32bit.ova)

[VirtualBox Xubuntu 18.04 64 位](https://ftp.lug.ustc.edu.cn/101/vm/VirtualBox-XUbuntu-18.04-64bit.ova)

推荐选用和自己系统字长相匹配的镜像。如果不确定自己的电脑是 32 位还是 64 位，可选择 32 位版。

??? tip "Xubuntu"

    Xubuntu 的设计理念是创造一个易用且更轻量的 Ubuntu。与 Ubuntu 不同的是，它采用的是更轻量和兼容性更强的 Xfce 桌面系统，并基于 GTK 运行。Xubuntu 和 Ubuntu 使用完全一致的软件源，因此 Xubuntu 能运行绝大部分 Ubuntu 下的软件。
    
    ![](images/Xubuntu-Logo.png)
    <p class="caption">Xubuntu 图标</p>

如果读者想要自己安装 Ubuntu 操作系统的话，以下两篇博客也可以参考：

- [在 Windows 下使用 VMware Workstation 安装 Ubuntu](https://ibug.io/p/15-cn)（另有[英文版](https://ibug.io/p/15)）
- [在 macOS 下使用 VMware Fusion 和 VirtualBox 安装 Ubuntu](https://blog.taoky.moe/2019-02-23/installing-os-on-vm.html)

对于日常惯用 Windows 10 用户来说，还有另一种更为便捷的安装 Linux 的方法。自 1607 版本起，Windows 10 支持适用于 Linux 的 Windows 子系统，可以在该子系统下安装若干主流的 Linux 发行版。详情可以参考拓展阅读：[适用于 Linux 的 Windows 子系统](supplement.md#wsl)。

### 启动虚拟机 {#start-vm}

若已经安装了上述虚拟机管理软件，则可以直接双击打开虚拟机镜像，管理软件会打开并导入该镜像，导入完毕后可直接点击开始按钮启动。

![](images/VWP-Xubuntu-32bit-Login.png)

<p class="caption">VMware Workstation 成功启动 Xubuntu 32 位虚拟机</p>

如果读者采用了上面列出的虚拟机之一，其默认登录密码为 `ustc`，输入密码即可登录虚拟机系统桌面。

![](images/VWP-Xubuntu-32bit-Desktop.png)

<p class="caption">Xubuntu 32 位虚拟机桌面</p>

## 中科大开源社群：LUG@USTC {#lug-ustc}

LUG@USTC 是中国科学技术大学主流的开源社群，也是校内最大的技术性社团。其现今拥有数百名热爱开源文化的成员，并受益于他们而正在蓬勃发展。LUG@USTC 维护了中国最大的开源镜像之一 [USTC Mirrors](https://mirrors.ustc.edu.cn/)，其作为本土的软件源为国内许多开源软件用户提供了镜像服务，是本社群对社会作出的一项重要贡献。

![](images/LUG@USTC-Logo.png)

<p class="caption">LUG@USTC 图标</p>

### 了解与加入 LUG@USTC {#join-lug-ustc}

你可以从 [LUG@USTC 官方网站](https://lug.ustc.edu.cn/wiki/)中了解我们。官方网站中包括了我们在校内开展的各类流行活动和面向校内外提供的诸多网络服务。

LUG@USTC 欢迎校内外的朋友加入社群交流。如果你是中国科学技术大学在读学生，你可以通过致邮 lug AT ustc.edu.cn 附上姓名与学号申请加入本社群；如果你是校外人士，也可以致邮获取进一步的沟通交流方式。

## 思考题 {#questions}

!!! question "计算机性能的增长"

    计算机的性能在过去几十年内一直呈现指数级增长，而如今其增速却已经放缓，转而开始偏向多核。在指数增长时代，计算机实际上经历过了几个不同的阶段，每个阶段都是通过不同的因素让计算机的性能快速增长。自行动手在线查阅资料，思考如下问题：
    
    (1) 指数增长时代经历了哪几个阶段？每个阶段让计算机性能大幅提升的机制都是什么？
    
    (2) 导致指数增长时代的终结的因素有哪些？
    
    (3) 在多核时代，为了充分利用硬件性能，操作系统需要支持什么样的新特性？

!!! question "免费软件和自由软件"

    在英文里，“免费软件”和“自由软件”都叫做“free software”，看起来很一致。事实上免费的软件是否一定自由？自由的软件是否也一定免费？

!!! question "著作传"

    著作传（英文：copyleft）源于自由软件运动，是一种利用现有著作权的法律体制巧妙地保证用户自由使用软件的权利的许可方式。著作传一般包含哪些规则？它和常见的著作权有什么区别？它和完全放弃权利的“公有领域”又有什么不同？
    
    ![](images/Copyleft.png)
    <p class="caption">著作传的标志，注意到它与常见的著作权标志左右颠倒</p>

## 引用来源 {#references .no-underline}

[^1]: [中科大Linux讲义](https://101.lug.ustc.edu.cn/)

