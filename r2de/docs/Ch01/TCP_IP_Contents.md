# 概述

!!! abstract "导言"

    本章主要介绍 TCP/IP 协议的基础知识，包括分层逻辑以及相关原理、     

## 什么是TCP/IP？

??? tip "TCP/IP 维基百科定义"

    The Internet protocol suite, commonly known as TCP/IP, is the set of communications protocols used in the Internet and similar computer networks. The current foundational protocols in the suite are the Transmission Control Protocol (TCP) and the Internet Protocol (IP).
    
    During its development, versions of it were known as the Department of Defense (DoD) model because the development of the networking method was funded by the United States Department of Defense through DARPA. Its implementation is a protocol stack.[<sup>1</sup>](#引用来源-references-no-underline)
    
    The Internet protocol suite provides end-to-end data communication specifying how data should be packetized, addressed, transmitted, routed, and received. This functionality is organized into four abstraction layers, which classify all related protocols according to each protocol's scope of networking.[2][3] From lowest to highest, the layers are the link layer, containing communication methods for data that remains within a single network segment (link); the internet layer, providing internetworking between independent networks; the transport layer, handling host-to-host communication; and the application layer, providing process-to-process data exchange for applications.
    
    The technical standards underlying the Internet protocol suite and its constituent protocols are maintained by the Internet Engineering Task Force (IETF). The Internet protocol suite predates the OSI model, a more comprehensive reference framework for general networking systems.


Internet 协议套件，通常称为 TCP/IP，是 Internet 和类似计算机网络中使用的一组通信协议。该套件中当前的基础协议是传输控制协议 (TCP) 和互联网协议 (IP)。

Internet 协议套件提供端到端数据通信规则，指定如何**打包、寻址、传输、路由和接收**数据。此过程可抽象为四层，根据每个协议的网络范围对所有相关协议进行分类。 从低到高，底层为**链路层**，包含数据的通信方式，数据保留在单个网段（链路）内；**互联网层**，提供独立网络之间的互联互通；**传输层**，处理主机到主机的通信；和**应用层**，为应用提供进程到进程的数据交换[<sup>2</sup>](#references)。

## 为什么学习 TCP/IP

只有了解了基本的通信协议，才能够了解应用程序中网络编程的基本原理。



## TCP/IP 分层逻辑 

!!! abstract "前言摘要"

    本节主要介绍TCP/IP 分层逻辑及其与 OSI 模型的区别

Internet 协议套件，通常称为 TCP/IP，是 Internet 和类似计算机网络中使用的一组通信协议。该套件中当前的基础协议是传输控制协议 (TCP) 和互联网协议 (IP)。

Internet 协议套件提供端到端数据通信规则，指定如何打包、寻址、传输、路由和接收数据。此过程可抽象为四层，根据每个协议的网络范围对所有相关协议进行分类。 从低到高，底层为**链路层**，包含数据的通信方式，数据保留在单个网段（链路）内；**互联网层**，提供独立网络之间的互联互通；**传输层**，处理主机到主机的通信；和**应用层**，为应用提供进程到进程的数据交换。而 **OSI** （**开放式系统互联通信参考模型**）模型可分为 7层，与 TCP/IP 不完全一致，但划分的层次更加具体。

![TCP_IP_and_OSI](https://gitee.com/georgegou/gravitychina/raw/picture/202109241036378.png)

注意到在 OSI 模型中，网络访问层细分为了物理层和数据链路层。顾名思义，。
## 物理层
**物理层**的主要功能是管理和同步实时传输的模拟脉冲信号，将数据帧转化为用于介质传输的 bit 流。
## 数据链路层
**数据链路层**主要完成网络中两个节点之间的数据交换，可以划分为以下两个子层：1、**介质访问控制（MAC）**：提供与网络适配器的接口，网卡在出厂前会分配一个 MAC 地址 2、**逻辑链路控制（LLC）**：对经过子网传递的帧进行错误检查，管理子网上通信设备之间的链路。
### 错误检测方法
由于传输介质的各种意外问题，在数据传输中容易出现丢包的问题，链路层就尝试进行错误检测与修正。主要的思想就是在传输信号后加入冗余码来辅助数据传输，主要的方法有下面几种：

**奇偶校验码（PCC)**：奇偶校验就是在末尾添加一位使得“1”的个数为奇数或者偶数(取决于双方使用奇校验还是偶校验)，如果接受和发送的数据奇偶数不相同，则数据出现错误。
比如在奇校验中，发送的数据为：(1 0 1 1 0 1) 1为偶数，实际发送的数据为（1 0 1 1 0 1 **1**）加了一个1，使的1为奇数，如果接收的数据为(1 0 1 1 0 **0** 1)，1变成了偶数，则数据传送错误。
这种方法的缺点很明显，如果丢失的数据未偶数，则这样的检测却没有发现异常。

**循环冗余校验（CRC）**: 
![循环冗余检验流程图](https://gitee.com/georgegou/gravitychina/raw/picture/202112091621550.png)
其也是在信号末位加上冗余码，核心思想是利用了一个divisor(除数)，把收到的每一帧数据都除以同样的数（2进制运算），得到 Remainder(余数)。具体例子可以参考[这个例子](https://blog.csdn.net/wenqiang1208/article/details/71641414)。如果传输帧没有错误的话，得到的余数是0，传输成功，如果不是0，就reject。

**CSMA/CD(多路访问/冲突检测)**:
CSMA/CD的功能是判断计算机何时把数据发送到访问介质。所谓载波侦听就是所有计算机都监听传输介质的状态，如果两台计算机同时发送数据，这样就会造成冲突。就好比**一个有很多人的房间，如果你想说话，你是不是需要判断有没有人讲话(这就是载波侦听)，如果两个人同时讲话的话，他们会发现这个问题，并停止讲话（这就是冲突检测）**。计算机会等待一个随机的时间，再开始发送数据。
    

## 网络层
本节主要介绍 IP 协议、ARP 协议、ICMP 协议
### 物理寻址

计算机是如何通过地址寻找到需要通信的另一台计算机的呢？**网络访问层中的物理寻址将完成这一部分工作，即网络访问层需要把逻辑 IP 地址与网络适配器的物理地址相关联。**硬件出厂前会有预置的物理地址，经过局域网传递的数据帧必须使用物理地址来标识源适配器与目的适配器，进而完成下一步通信。TCP/IP 协议使用**地址解析协议（ARP）**与**逆向地址解析协议 (RARP)** 把 IP 地址关联到网络适配器的物理地址。值得注意的是，以太网软件使用的地址并不是逻辑 IP 地址，但这个地址在网际层的接口上与 IP 地址有映射关系。

但是，在路由式网络中，不能利用物理地址实现数据传输，因为根据物理地址进行传输所需的过程不能跨越路由接口来运行，这就需要跨越局域网来传输数据，**因此TCP/IP 隐藏了物理地址，以一种逻辑化、层次化的寻址方式对网络进行组织**。这就需要IP 协议、ARP 协议、ICMP 协议来支撑。
### IP 协议
#### IP 地址编码方式
网络中 IP 是有一定规则的，我们可以通过 IP 地址来了解主机所在的网络。
IP协议提供了一种分层的、与硬件无关的寻址系统，能够在复杂的路由式网络中传递数据。在 TCP/IP 网络中每一台网络适配器都具有 IP 地址（可能不止一个）。
#### 分类
IP地址一般分为网络ID 和 主机ID。这样的话，组合的方式其实有很多种，究竟怎样的规则能够有效管理呢？一种就是按照一定逻辑分类：A、B、C、D、E[<sup>3</sup>](#引用来源-references-no-underline)。

其中A类地址为IP地址的前8位表示网络ID，后24位表示主机ID；
B类地址为IP地址的前16位表示网络ID，后16为表示主机ID；
C类地址为IP地址的前24为表示网络ID，后8位表示主机ID。

显而易见：A类地址提供了较少的网络ID，可以拥有大量主机ID，而C类地址只能包含少量的主机（254台，256减去全是0或者全是1这种特殊的IP地址）。D类地址用于多播，多播就是将一个消息发送到网络的子网。E类地址一般处于试验环境，不用于生产。

那么计算机如何知道一个地址究竟是哪一类呢？其实很简单，如果32位地址以0开头，就是A类；10开头就是B类；110开头就是C类。也就是说A类地址开头是0，其最多到127，B类地址的话就在128~191的范畴。
#### 子网划分
通过在主机号中拿一部分作为子网号，把两级IP地址划分为3级IP地址。这就会用到[子网掩码](https://baike.baidu.com/item/%E5%AD%90%E7%BD%91%E6%8E%A9%E7%A0%81/100207?fr=aladdin)，子网掩码是在IPv4地址资源紧缺的背景下为了解决lP地址分配而产生的虚拟lP技术，通过子网掩码将A、B、C三类地址划分为若干子网，从而显著提高了IP地址的分配效率，有效解决了IP地址资源紧张的局面。它的作用有两个，其一是屏蔽IP地址的一部分，来区别网络标识和主机标识，二是将大的IP网络划分为若干个子网络。

#### 无类别域间路由 （CIDR）
为了缓解 IPV4 地址即将用尽的局面，依赖于预定义的8位、16位、24位网络ID已经是行不通了，我们使用一个名为CIDR前缀的值指定地址中网络ID，这个前缀有时候也称为变长子网掩码（VLSM）。比如说 205.123.196.183/25中，/25表示地址中25位用于网络ID，响应的子网掩码就是255.255.255.128。

#### 如何配置计算机IP地址

实际工作中几乎不需要手动配置IP，一般使用DHCP（动态主机配置协议）来接受动态IP地址。静态IP地址虽然在小型网络中表现得很好，但是网络大了之后，需要重新配置IP地址就会非常麻烦，需要对每台计算机操作。现在有了DHCP后，我们可以只更新DHCP服务器，就可以更新网络中的IP地址分配。

**实际上每台计算机的IP地址都是有租期限制到，到了一定时间不使用后，该IP地址就会被DHCP服务器回收，这样的话就会节省地址空间，客户端的IP地址数量与网络中的数据不一定相同。**

#### NAT 网络地址转换
细心的读者可能已经发现，DHCP分配的地址可能是个局域网IP，如何与外界Internet通信呢？这里就需要用到NAT设备，它就相当于一个代理工具（网关），其能够屏蔽本地网络的所有细节，当然也能阻止外部网络攻击本地网络，这是因为本地网络的寻址模式与Internet寻址模式不一致。

### 地址解析协议 ARP
前面已经提到了ARP协议的作用，在局域网通信中，ARP协议可以将IP地址解析为MAC地址寻找物理机，进而进行通信。一般的，主机都会存储一个ARP高速缓存，是本局域网上个主机和路由器的IP地址到MAC地址的映射关系。如果不知道需要通讯主机IP地址呢？
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112092224572.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">ARP地址解析协议</div>
</center>


如图所示，Host A会向周围广播，此时如果 Host B收到了请求，就会返回其MAC地址，进而进行通信。

### Internet控制消息协议 ICMP
前面提到过，不同局域网通信需要NAT转接多个路由器来完成通信，其中可能会遇到问题，路由器就利用 ICMP 消息把问题通知给源IP。ICMP可以用于其他调试和排错功能。比如我们经常用的 ping 命令，其实就是使用ICMP中的Echo Request和Echo Reply.
```shell
(base) [george@-c ~]$ ping 192.168.1.14
PING 192.168.1.14 (192.168.1.14) 56(84) bytes of data.
64 bytes from 192.168.1.14: icmp_seq=1 ttl=64 time=0.058 ms
64 bytes from 192.168.1.14: icmp_seq=2 ttl=64 time=0.041 ms
64 bytes from 192.168.1.14: icmp_seq=3 ttl=64 time=0.032 ms
64 bytes from 192.168.1.14: icmp_seq=4 ttl=64 time=0.032 ms
64 bytes from 192.168.1.14: icmp_seq=5 ttl=64 time=0.037 ms

(base) [george@-c ~]$ ping 192.168.1.4
PING 192.168.1.4 (192.168.1.4) 56(84) bytes of data.
From 192.168.1.14 icmp_seq=1 Destination Host Unreachable
From 192.168.1.14 icmp_seq=2 Destination Host Unreachable
From 192.168.1.14 icmp_seq=3 Destination Host Unreachable
From 192.168.1.14 icmp_seq=4 Destination Host Unreachable
```
## 传输层
本节将介绍面向连接的协议和无连接的协议、端口与套接字、TCP、UDP。前面几层操作系统都帮我们封装好了，我们了解即可。网络层实际上只是寻址到需要通信的两台主机，真正通信的是主机中的进程。
**传输层为网络应用程序提供了一个借口**，传输层与网络编程密切相关，如果一个应用程序不涉及网络编程的话，它的影响力将大大降低。

!!! note "几个重要概念:"

    1、面向连接的协议和无连接的协议：面向连接的协议会在通信计算机之间**建立一个连接**，并实时监听，会确认传输的数据包，如果丢失会重发数据，直至数据传输完毕，才会关闭连接。无连接协议则**不建立连接**以一对多的方式，单向传递数据报，不需要监测信息传递的状态。

    2、端口与套接字：端口很好理解，如果把计算机比作一个酒店的话，端口就相当于房间号，**充当应用程序与传输层之间的通路**，比如我们可以通过3306端口访问 mysql 服务器。
    实际上TCP和UDP数据实际上是被发送到一个套接字上的。套接字由ip地址和端口组成，比如192.168.1.14.8888,就是ip地址为192.168.1.14的计算机的端口8888。

    3、多路复用/多路分解：套接字寻址系统使得TCP/UDP能够执行传输层另一个重要任务，就是多路复用，多路分解。实际上很好理解，多路复用就是把多个来源的数据导向一个输出，多路分解就是把一个来源的数据发送到多个输出。其关键在于套接字地址，它可以定位应用程序位置，

### TCP与UDP
TCP是一个面向连接的协议，提供了全面的错误控制和流量控制。而UDP是一个无连接的协议，错误控制也简单很多，但其传输速度更快。所以说如果应用程序需要交互就使用TCP，**一般情况下开发者更喜欢使用简单的UDP进行网络访问，在应用程序里实现一些错误检测等功能，而不是使用速度较慢的TCP协议**。

#### 什么是三次握手
这是面试经常问的问题，实际上，三次握手就是**序列号同步的过程**。换句话说计算机B必须知道计算机A的初始序列号(ISN)，计算机A也必须知道计算机B使用什么ISN开始传输数据。

第一步(SYN)：客户端向服务端发送连接请求，所以它发送 SYN(Synchronize Sequence Number)，也就是客户端序列号x，通知服务端它想建立连接;

第二步(SYN+ACk)：服务端返回它的SYN(y)，并返回 Acknowledgement（ACK）确定，确认号为 ACK=x+1;

第三步(ACK)：客户端确认与服务端建立连接（ACK=y+1），随后服务端接受ACK。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112101231513.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">TCP三次握手</div>
</center>

#### 为什么要三次握手
三次握手是为了防止失效的连接请求到达服务器，使服务器打开错误连接。试想，如果网络阻塞的话，一个请求还没到达服务器，此时客户端会重连，这两个请求最终都会到达服务器，如果不进行三次握手的话，服务器就会产生两个连接，实际上是一个连接。

#### 四次挥手
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112101526010.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">TCP四次挥手</div>
</center>
第一步(FIN From Client)：客户端请求断开连接，设置FIN=1，发送到服务器端，置于FIN_WAIT_1，等待服务器ACK确认；

第二步(ACK FROM Server)：服务器收到请求之后，发送ACK确认关闭连接，此时进入ClOSE WAIT状态，服务器可以传送没发送的数据，客户端不能发送数据；

第三步(FIN from Server)：服务器发出FIN=1的指令（为什么在ACk之后？因为服务器还要做关闭连接前的准备，比如说还有数据没有传送完，等待传输完毕再发出FIN=1指令）；

第四步：客户端ACK确认关闭连接，连接关闭。

!!! tip "attention"
    需要注意的是UDP并不是完全没有错误检验功能，其实它也能执行基本的错误检验功能。UDP数据包中包含一个校验和，接收端的计算机可以用它来检验数据的完整性。

### 应用层
应用层是TCP/IP栈的顶层，应用层很多组件就是**网络服务**。

#### 名称解析服务
域名系统（DNS）为Internet提供名称解析服务，能够将域名解析为IP地址。名称服务运行于服务器的应用层，并且与其他名称服务器交换名称解析信息。
#### Web服务
HTTP（超文本传输协议）是应用层的一个协议，通过这个协议，用户可以访问到HTML等类型的网络资源。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112102020911.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">HTTP协议</div>
</center>
!!! note "Http特点"
    
    HTTP是无连接的：HTTP 请求由浏览器（HTTP 客户端）根据用户的信息请求发起。 服务器将处理请求并返回客户端。

    HTTP是简单的：HTTP把HTTP消息封装成帧，HTTP是简单易读的，由于其明文通信的特点，它也是不安全的。HTTPS就是在此基础上添加了SSL（Secure Sockets Layer），让HTTP先和SSL进行通信，再与TCP通信，加了一层隧道，所以更加安全。

    HTTP是可扩展的：HTTP可以与新功能集成到软件中。

#### HTTP 方法中GET与POST区别
GET方法用于将表单数据以名称或者值对的形式附加到URL，而POST则是传输一个实体。WWW经常使用POST将用户生成的数据发送到Web服务或在你上传文件的时候使用。
!!! note "difference"
    
    1、在 GET 方法中，值在 URL 中可见，而在 POST 方法中，值在 URL 中不可见；  
    2、GET 对值的长度有限制，通常为 255 个字符，而 POST 对值的长度没有限制，因为它们是通过 HTTP 正文提交的；  
    3、GET 方法仅支持字符串数据类型，而 POST 方法支持不同的数据类型，如字符串、数字、二进制等；  
    4、GET 请求通常可缓存，而 POST 请求几乎不可缓存；  
    5、与 POST 相比，GET 的性能更好；  
    6、由于Get会把数据编码到URL中，所以这些变量会显示在浏览器的地址栏里，也会被记录在服务器端的日志中，因此Post方法更加安全。



## 引用来源 {#references .no-underline}
<div id="refer-anchor"></div>
 [1] [https://en.wikipedia.org/wiki/Internet_protocol_suite](https://en.wikipedia.org/wiki/Internet_protocol_suite)  
 [2] 《TCP/IP入门经典-第六版》----Joe Casad  
 [3] [https://www.meridianoutpost.com/resources/articles/IP-classes.php](https://www.meridianoutpost.com/resources/articles/IP-classes.php)  
 [4] [https://www.ionos.com/digitalguide/server/know-how/introduction-to-tcp/](https://www.ionos.com/digitalguide/server/know-how/introduction-to-tcp/)  
 [5] [https://www.geeksforgeeks.org/tcp-3-way-handshake-process/](https://www.geeksforgeeks.org/tcp-3-way-handshake-process/)  
 [6] [https://www.guru99.com/difference-get-post-http.html](https://www.guru99.com/difference-get-post-http.html)