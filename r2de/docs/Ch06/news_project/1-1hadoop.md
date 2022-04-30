## 基于Hadoop构建大数据平台

### 1、物理机准备工作

准备三台以上物理机组成集群，这里出于学习目的，用虚拟机搭建三台Ubuntu系统。

```shell
hadoop@hadoop01:~$ hostnamectl
   Static hostname: hadoop01
         Icon name: computer-vm
           Chassis: vm
        Machine ID: 82e0a322c03c4510badf38240f012739
           Boot ID: a335baaa3fa04f589020d610cab39400
    Virtualization: vmware
  Operating System: Ubuntu 20.04.2 LTS
            Kernel: Linux 5.4.0-100-generic
      Architecture: x86-64
```

生产环境中，一般采用物理集群的方式或者将大数据系统部署到云上（阿里云、腾讯云），这里使用三台虚拟机来代替三台物理机，各台机器处于同一局域网，需要先对各台机器做相关配置。

#### 配置静态IP地址

一般情况下，系统会采用DHCP(Dynamic Host Configuration Protocol)方式来获取IP地址，这种方式虽然有利于IP地址的管理，但不利于大数据集群的维护。大数据平台说到底就是用集群来完成单机的处理工作，极大地极高效率，这里面就会涉及到集群间地通信，通信就必须要知道对方地IP地址。一旦重启机器，DHCP会自动获取IP地址，导致集群里面的机器无法识别该机器。

```shell
hadoop@hadoop03:~$ cd /etc/netplan
hadoop@hadoop03:/etc/netplan$ ls
00-installer-config.yaml
hadoop@hadoop03:/etc/netplan$ cat 00-installer-config.yaml 
# This is the network config written by 'subiquity'
network:
  ethernets:
    ens33:
      dhcp4: true  //代表IP地址获取方式是DHCP
  version: 2
```

将上述配置文件修改为：

```shell
hadoop@hadoop01:/etc/netplan$ cat 00-installer-config.yaml 
# This is the network config written by 'subiquity'
network:
  ethernets:
    ens33:
      dhcp4: no
      addresses: [192.168.2.10/24]
      gateway4: 192.168.2.1
      optional: true
      nameservers:
         addresses: [8.8.8.8,114.114.114.114]
  version: 2
  hadoop@hadoop01:/etc/netplan$ sudo netplan apply #应用上述配置
```

用同样的方式对hadoop02、hadoop03机器进行处理。

#### 关闭防火墙

对于运行在内网的大数据平台而言，可以关闭linux系统的防火墙，因为这有可能阻挡集群的正常通信。

```shell
hadoop@hadoop02:/etc/netplan$ sudo ufw disable
Firewall stopped and disabled on system startup
```

#### 配置集群间免密登录

正常情况下，通过22号端口登录linux 的时候需要输入账号和密码，这对于集群之间的通信是非常繁琐的，这里需要设置免密登录。

熟悉github的同学应该非常熟悉，每次git commit 的时候会要求输入github的账号和密码，但是我们通过正确将本机的公钥加到github.com中SSH keys里面就可以实现免密登录了。

```shell
whu@hadoop01:~$ ssh-keygen -t rsa
Generating public/private rsa key pair.
Enter file in which to save the key (/home/whu/.ssh/id_rsa): 
/home/whu/.ssh/id_rsa already exists.
Overwrite (y/n)? y
Enter passphrase (empty for no passphrase): 
Enter same passphrase again: 
Your identification has been saved in /home/whu/.ssh/id_rsa.
Your public key has been saved in /home/whu/.ssh/id_rsa.pub.
The key fingerprint is:
7e:4d:a3:3a:2f:fb:be:52:cd:7c:5a:cd:20:16:ab:cd whu@hadoop01
The key's randomart image is:
+--[ RSA 2048]----+
|                 |
|            .    |
|             o   |
|            + .  |
|        S  Oo. + |
|       .  o+E.o o|
|        ..o .+   |
|        +o  .    |
|        oO=.     |
+-----------------+
whu@hadoop01:~/.ssh$ cp id_rsa.pub authorized_keys  #复制公钥到authorized_keys中
```

随后，将其他主机的公钥复制到authorized_keys中：

```shell
hadoop@hadoop01:~/.ssh$ cat authorized_keys 
ssh-rsa 
SCdfW+wiYBFh9xohcOz3uTK6tmURWF+Yd8cYv2MDaMmARJahYhWKEsNVEJD6EHgSAyjRc= hadoop@hadoop01
ssh-rsa 
cwNDms1th81bIwqlSCn+S8gw3w6gQCHBItvcq0u2tWYJid1hQ4ufzkyUfQPutZFWaOisU= hadoop@hadoop03
ssh-rsa 
b8idfD8FuavkzJ7j52rEtmDTg7I5ApRrglBCRWzvWWHYWFUpAQMlJ6RF9l2Q1mAWnHZgs= hadoop@hadoop02
```

还需要配置 Host，使得我们不用记住所有主机的 IP 地址，通过对应名字就可以访问

```shell
hadoop@hadoop01:~$ cat /etc/hosts
127.0.0.1 localhost
127.0.1.1 hadoop01

# The following lines are desirable for IPv6 capable hosts
::1     ip6-localhost ip6-loopback
fe00::0 ip6-localnet
ff00::0 ip6-mcastprefix
ff02::1 ip6-allnodes
ff02::2 ip6-allrouters
192.168.2.10 hadoop01   #以下三条是需要增加的记录
192.168.2.8 hadoop02
192.168.2.9 hadoop03
hadoop@hadoop01:~$ ssh hadoop02
Welcome to Ubuntu 20.04.2 LTS (GNU/Linux 5.4.0-100-generic x86_64)

 * Documentation:  https://help.ubuntu.com
 * Management:     https://landscape.canonical.com
 * Support:        https://ubuntu.com/advantage

  System information as of Mon 21 Feb 2022 07:45:39 PM CST

  System load:  0.05               Processes:              233
  Usage of /:   37.5% of 19.56GB   Users logged in:        1
  Memory usage: 10%                IPv4 address for ens33: 192.168.2.8
  Swap usage:   0%

 * Super-optimized for small spaces - read how we shrank the memory
   footprint of MicroK8s to make it the smallest full K8s around.

   https://ubuntu.com/blog/microk8s-memory-optimisation

163 updates can be installed immediately.
62 of these updates are security updates.
To see these additional updates run: apt list --upgradable


Last login: Mon Feb 21 15:18:01 2022 from 192.168.2.10
hadoop@hadoop02:~$           #成功登录到hadoop02
```

#### JDK安装

各类开源的大数据组件如Hadoop、Zookeeper、Spark等都是基于Java开发的，因此Java程序的基本运行环境需要安装到Linux系统上，这一步比较简单：

Oracle 官网下载 [JDK8](https://www.oracle.com/webapps/redirect/signon?nexturl=https://download.oracle.com/otn/java/jdk/8u311-b11/4d5417147a92418ea8b615e228bb6935/jdk-8u311-linux-i586.tar.gz)，随后上传到服务器：

```shell
hadoop@hadoop01:~/app$ tar -zxvf jdk-8u321-linux-x64.tar.gz  #解压文件
hadoop@hadoop01:~/app$ ln -s jdk1.8.0_321/ jdk #建立软链接
#配置环境变量
hadoop@hadoop01:~/app$ vi ~/.bashrc
hadoop@hadoop01:~/app$ cat ~/.bashrc | tail -n 4
JAVA_HOME=/home/hadoop/app/jdk
CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
PATH=$JAVA_HOME/bin:/home/hadoop/app/zookeeper/bin:$PATH
export JAVA_HOME CLASSPATH PATH
#添加如上的代码
hadoop@hadoop03:/etc/netplan$ java -version #测试JAVA环境
java version "1.8.0_321"
Java(TM) SE Runtime Environment (build 1.8.0_321-b07)
Java HotSpot(TM) 64-Bit Server VM (build 25.321-b07, mixed mode)

```

### 2、Zookeeper集群部署

Apache Zookeeper官网下载最新发布的程序，目前最新的是[3.6.3](https://dlcdn.apache.org/zookeeper/zookeeper-3.6.3/apache-zookeeper-3.6.3-bin.tar.gz )版本：

```shell
hadoop@hadoop01:~/app$ ln -s apache-zookeeper-3.6.3-bin zookeeper #建立软链接
hadoop@hadoop01:~/app$ cd zookeeper/conf
hadoop@hadoop01:~/app/zookeeper/conf$ cp zoo_sample.cfg zoo.cfg
#修改配置文件
hadoop@hadoop01:~/app/zookeeper/conf$ cat zoo.cfg
# The number of milliseconds of each tick
tickTime=2000
# The number of ticks that the initial 
# synchronization phase can take
initLimit=10
# The number of ticks that can pass between 
# sending a request and getting an acknowledgement
syncLimit=5
# the directory where the snapshot is stored.
# do not use /tmp for storage, /tmp here is just 
# example sakes.
dataDir=/home/hadoop/data/zookeeper/zkdata #需要提前建立
dataLogDir=/home/hadoop/data/zookeeper/zkdatalog #需要提前建立
 # the port at which the clients will connect
clientPort=2181
# the maximum number of client connections.
# increase this if you need to handle more clients
#maxClientCnxns=60
#
# Be sure to read the maintenance section of the 
# administrator guide before turning on autopurge.
#
# http://zookeeper.apache.org/doc/current/zookeeperAdmin.html#sc_maintenance
#
# The number of snapshots to retain in dataDir
#autopurge.snapRetainCount=3
# Purge task interval in hours
# Set to "0" to disable auto purge feature
#autopurge.purgeInterval=1

## Metrics Providers
#
# https://prometheus.io Metrics Exporter
#metricsProvider.className=org.apache.zookeeper.metrics.prometheus.PrometheusMetricsProvider
#metricsProvider.httpPort=7000
#metricsProvider.exportJvmInfo=true
server.1 = 192.168.2.10:2888:3888
server.2 = 192.168.2.8:2888:3888
server.3 = 192.168.2.9:2888:3888

#配置环境变量PATH=/home/hadoop/app/zookeeper/bin


```

其他节点做相同的配置。

#### 启动Zookeeper集群

```shell
hadoop@hadoop01:~$ zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /home/hadoop/app/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
hadoop@hadoop02:~$ zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /home/hadoop/app/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
hadoop@hadoop03:~/app/zookeeper/bin$ zkServer.sh start
ZooKeeper JMX enabled by default
Using config: /home/hadoop/app/zookeeper/bin/../conf/zoo.cfg
Starting zookeeper ... STARTED
hadoop@hadoop03:~/app/zookeeper/bin$ 
```

```shell
hadoop@hadoop01:~/app/zookeeper/conf$ zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /home/hadoop/app/zookeeper/bin/../conf/zoo.cfg
Client port found: 2181. Client address: localhost. Client SSL: false.
Error contacting service. It is probably not running.
hadoop@hadoop01:~/app/zookeeper/conf$ jps
2991 Jps
```

发现Zookeeper集群并没有启动成功
