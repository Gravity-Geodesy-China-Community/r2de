# 概述

!!! abstract "导言"

    本章主要介绍网络安全的相关知识，随着互联网的迅速发展，特别是大数据与云计算技术的飞速发展，给我们带来便利的同时，也会带来一定的 Internet 安全隐患。一般的软件开发都涉及到网络，如何保证系统的网络安全，是一件值得重视的事情。

## 什么是网络安全？
网络安全是一种保护计算机、服务器、移动设备、电子系统、网络和数据免受恶意攻击的技术，这种技术也称为信息技术安全或电子信息安全。该术语适用于从业务到移动计算的各种环境，可以分为几个常见类别[^1]。

### 网络攻击的常用手段

1、**恶意软件**：这种方式是最常见的网络威胁之一，常见于不明的邮件链接、盗版软件或钓鱼网站，以木马 (Rootkit) 程序的方式欺骗用户下载到计算机上，并破坏和收集计算机上的程序。常见的有各种带恶意广告的软件（疯狂下载各种小软件到电脑上）、勒索软件（锁定用户资料，威胁用户支付赎金，如比特币、可能支付了他也不会给你密码）；  

2、**SQL注入**：SQL(结构化查询语言)注入是一种用于控制和盗取数据库数据的网络攻击。网络罪犯会利用数据驱动的应用程序的漏洞，通过恶意SQL语句将恶意代码插入到数据库中，从未威胁数据库的安全；**通俗的讲，就是用户输入的时候，输入SQL语句，更改后台SQL语句的预定的执行命令**[^2]。有关SQL注入的实验请[看这里](../Ch07/index.md)。  

3、**“中间人”攻击**：“中间人”攻击是指通过拦截两个人之间的通信数据来窃取数据，例如，攻击者可以在不安全的WIFI网络中获取设备与网络的通信数据。

4、**拒绝服务攻击（Dos）**：Deny of service，是黑客攻击的常用手段之一，攻击者通过对网络带宽进行消耗性攻击，目的是使服务器因为巨大请求而挂掉，让服务器停止服务。服务器的主要表现为：1、迫使服务器的缓存区爆满，让用户不接受新的请求；2、使用 IP 欺诈，迫使服务器把非法用户的连接复位，影响合法用户的连接[^3]。

!!! note "Syn Flood攻击"
    
    SYN Flood攻击是当今网络常见的DDos(分布式拒绝服务攻击)，其通过利用 TCP 协议实现上的一个缺陷，发送向网络服务所在端口发送大量伪造源地址的攻击报文（如注册机器人，疯狂向服务器请求注册大量账号，导致服务器崩溃）。SYN是序列号，Flood是洪水的意思，意味着攻击者会发送大量的SYN请求，让服务器响应，服务器返回SYN+ACK，完成第二次握手，但此时，攻击者不返回ACK（确认接收），没有完成三次握手。由于TCP是可靠协议，服务器会等待ACK消息，并不断重试连接，这会导致服务器中出现大量半连接请求，足以撑爆服务器的半连接队列。

    **关于 Syn flood 防御**：目前主要的防护手段为 cookie 身份认证、Reset认证、TCP首包丢弃，感兴趣的可以自行了解。

5、**root访问**：获取Root权限，意味着攻击者可以执行系统最高级别的指令，做出毁灭性的操作。通常入侵者进入系统后，会上传一个 rootkit 工具，用于对系统的破坏，比如篡改标准网络工具(netstat)，消除系统日志文件来隐藏自己的行踪，Key loggers 能够捕获和记录键盘的输入。

### 如何保障网络安全
 
#### 防火墙

**防火墙**这个词想必大家应该不陌生，它就是目前保护网络安全的主要手段。防火墙就是一个放置在网络路径上，用于检查、接受或拒绝打算进入网络的数据包。理论上我们只要对防火墙作适当的配置，只开放必要的服务端口，过滤异常流量，就能基本上保护服务器免受 Internet 上的攻击。

其中防火墙的配置采用了一系列命令或规则来描述，这就是**防火墙规则**。我们可以配置服务器在Internet上开放的端口，配置参数来过滤异常流量等。常见的配置包括：1、资源地址与地址范围；2、目的地址范围；3、服务；4、行为。比如说我们关闭所有去往服务器内某个地址的流量，也可以关闭特定服务[^4]。

#### 代理服务

原理很简单，就是在服务器前面布设代理服务器，分担主服务器处理连接请求的压力，通过代表客户端发送和接收 Internet 请求，代理可以使客户端免于直接与恶意网站相连接。然而多数情况下，代理服务器主要用于提升服务器性能，比如存储被频繁访问的网络资源，下次请求时，直接用本地的拷贝响应，这要比通过 Internet 响应服务要快很多。

##### 反向代理

关于正向代理或反向代理，我在个人博客中有完整说明，具体可以参考我的[个人博客](https://goujianing.ml/blog/post/how-to-use-nginx-realise-reverse-proxy/)。上文提到的向外发送 Internet 请求实际上是正向代理，反向代理就是它接受来自外部资源的请求，将这些请求转发给内部网络，如果有多台服务器的话，还能够根据需求，转发到具体服务器上进行响应，因此其还可以实现负载均衡。

### 通信加密

对于信息安全，一个自然的想法就是进行信息加密。我们回忆一下，当时内战的时候，国共两方的特务在传送信息都是加密的，主要通过密码本来翻译信息，如果敌方拿到密码本，就有破译的风险。

现在互联网上传输的信息都是经过加密算法加工的，即使黑客截取相关信息，但是无法破译。那么作为工程师如何涉及加密算法呢？我们可能会遇到两个主要问题：（1）如果使用同一套加密法则，窃听者获取一个程序副本就能完成整个网络计算机通信数据的解密；（2）不同使用不同的加密算法，那每台计算机的软件都不一样，这样管理或开发的成本会增加。

那么如何解决这个问题呢？

#### 对称加密

对称加密（Symmetric encryption），就是发送方和接收方所用的秘钥相同，发送方通过加密算法与秘钥加密数据，进行网络传送，接收方用相同的秘钥与解密算法对信息进行解密。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/对称加密.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">对称加密示意图</div>
</center>
但是秘钥一旦泄露，就会造成信息泄露的风险，一般系统都会定期更新秘钥，以保证安全。

#### 非对称加密

非对称加密之所以被称为非对称，是因为用来加密的秘钥，与用来解密数据的秘钥不同。举例来说，如果计算机A要和计算机B通信，

1、首先计算机A设法与B建立一个连接，可以是HTTPs或者其他连接；

2、计算机B上的加密软件生成一个私有公钥和一个公开秘钥，顾名思义，私有公钥自己私有，公有秘钥可以分享给需要通信的计算机使用；

3、计算机A使用从计算机B接收到的公开秘钥加密数据并传输数据；

4、计算机B接受来自计算机A发送来的数据，并使用相应的私有秘钥进行解密。
<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112261511057.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">非对称加密示意图</div>
</center>

私钥和公钥是特定加密软件自动生成的，必须成对使用。加密算法是一系列数学算法，涉及到密码学，有兴趣可以深入了解，如非对称加密算法 [RSA](https://usemynotes.com/what-is-rsa-algorithm/#:~:text=The%20RSA%20algorithm%20is%20a%20public%20key%20cryptography,for%20encrypting%20and%20decrypting%20the%20message%20or%20data.) 等[^5]。

### Web加密通信

安全套接层（Secure Sockets layer）和 IPSec 都是 TCP/IP 安全协议，目的是保障 TCP/IP 网络上的通信安全。

#### SSl 协议

 前面介绍过，HTTP 是超文本传输协议，位于应用层，常用于 Web 服务器和服务期间的通信，但是这种传输方式是明文通信，如果通信数据包被拦截，通信数据将会暴露[^6]。HTTPs 就是在其基础上增加了安全套接层（ssl），ssl 是美国 Netscape 公司于1995年为保护 Web 通信所引入的一个 TCP/IP 安全协议集。SSL 协议主要包含三个部分：（1）SSL 握手协议(Handshake Protocol)、（2）SSL 更改密文规范协议（Change Cipher Spec Protocol）、（3）SSL 警告协议（Alert Protocol）
 <center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112261722393.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">ssl 协议工作示意图</div>
</center>
SSL 具体工作原理如下：

（1）客户端发送一个 SSL 会话

（2）服务器响应，发送自己的[数字证书](https://www.zhihu.com/question/24294477?sort=created), 确认通信双方身份 （数字证书产生的原因是为了保证 Public Key 的真实性，因为公钥是在网络上交换的，有泄露的风险，如果中间人获取了公钥，无法保证 public key 的真实性，基于[CA体系](https://www.ssl.com/faqs/what-is-a-certificate-authority/)交换证书的方式交换 public key，能基本保证安全），数字证书的本质就是公钥的加密副本。


（3）客户端用服务器公钥加密数据，服务器用私钥解密数据。


#### IPSec 协议

IPSec 是另一种在 TCP/IP 网络上使用的安全协议，其在传输层之下实现，因此在传输层之上运行的程序就不需要有安全上的配置。也正是因为它属于 TCP/IP 较为底层的协议栈，相互通信的两台计算机都需要支持 IPSec 协议才能够正常通信，IPSec 非常适合为路由器或防火墙等网络设备提供安全服务。

IPSec 是一种支持安全网络传输的协议，而 VPN（虚拟专用网络）就是连接本身，通过 VPN，在 Internet 中建立一条专用通信隧道，采用一系列加密算法加密数据报，
 <center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/202112261726641.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">IPSec 协议工作示意图</div>
</center>

## 网络安全专家的建议

* 正确配置防火墙
* 使用尽可能安全的密码（大小写字母、数字、特殊符号）
* 不要在不安全的地方存储密码（浏览器、字条等）
* 不要点击可疑的连接
* 使用系统最低权限来操作
* 关闭所有不必要的服务
* 使用VPN来加密访问内部网络
* 在沙箱环境下运行网络服务，如docker
* 安装最新的系统补丁



## 引用来源 {#references .no-underline}

[^1]: [https://www.kaspersky.com.cn/resource-center/definitions/what-is-cyber-security](https://www.kaspersky.com.cn/resource-center/definitions/what-is-cyber-security)   
[^2]: [https://zhuanlan.zhihu.com/p/41857396](https://zhuanlan.zhihu.com/p/41857396)

[^3]: [https://baike.baidu.com/item/%E6%8B%92%E7%BB%9D%E6%9C%8D%E5%8A%A1%E6%94%BB%E5%87%BB/421896?fr=aladdin](https://baike.baidu.com/item/%E6%8B%92%E7%BB%9D%E6%9C%8D%E5%8A%A1%E6%94%BB%E5%87%BB/421896?fr=aladdin)  

[^4]: 《TCP/IP入门经典-第六版》----Joe Casad  

[^5]: [http://www.ruanyifeng.com/blog/2013/06/rsa_algorithm_part_one.html](http://www.ruanyifeng.com/blog/2013/06/rsa_algorithm_part_one.html)


[^6]: [https://blog.csdn.net/qq_38265137/article/details/90112705](https://blog.csdn.net/qq_38265137/article/details/90112705)
  
