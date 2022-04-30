# 大数据开发环境准备{#first-class}

!!! abstract "导言"

  本节将详细介绍大数据开发所需的必要环境，包括 Java 开发环境、Linux 集群搭建。

## Linux 物理集群准备 {#Linux-cluster}

一般来说，构建一个分布式的大数据集群至少需要 3 台物理机，如果读者没有 3 台物理机的话，也可以使用虚拟机构建3台机器，当然，您也可以购买云服务器完成开发。现在我创建了三台系统版本为 CentOS Linux release 7.9.2009 (Core) 的虚拟机，它们的 IP 地址分别为：
```shell
192.168.1.8
192.168.1.12
192.168.1.14
```

### 配置静态 IP 地址 {#Static-address}

分布式大数据平台拥有多台机器，识别每台机器的标志就是 IP 地址，如果使用 DHCP 这种动态分配 IP 地址的方式，如果重启 DHCP 服务器，机器的 IP 地址就有可能改变。在实际开发过程中，有多处配置需要用到IP 地址，所以为了方便起见，采用静态配置 IP 地址的方法来解决。
```shell
BOOTPROTO="static"
IPADDR=192.168.1.8
NETMASK=255.255.255.0
GATEWAY=192.168.1.0
```

## Java 开发环境搭建 {#Development-environment}

由于大数据环境目前的开源生态如 Hadoop、Spark、Flink 等技术都是基于 Java 或 Scala 语言来开发的，所以目前几乎所有的大数据平台都是基于 Java 语言来开发的。

#### batch 

简单来说 Batch Size 就是一次向神经网络模型传输多大的数据样本，每训练完一个 Batch，模型就会更新参数。

#### epoch

一个 epoch 就是一次性传输所有数据到神经网络进行训练。举个例子，我们有 1000 张图片需要进行分类，我们设置 batch_size 为 10，即一次训练就传输 10 张图片至模型，也就是说我们需要 100 个 batch 才能完成一个 epoch。



```shell
batches in epoch = training set size / batch_size
```
!!! note "为什么要使用 batch"
通常来说，如果 batch 越大，完成整个 epoch 也就越快，训练的模型泛化能力也就越强，但是可能对没有见过的数据表现不好，也就是说过拟合。于是我们需要 tradeoff（权衡）计算机处理能力与模型表现之间的关系。batch 作为一个[超参数](https://deepai.org/machine-learning-glossary-and-terms/hyperparameter)需要传入到模型中的，具体的参数选择需要根据模型来确定。
   
#### hyperparameter

超参数是在开始模型训练前设置的参数，其参数的设置将直接影响到模型。

试想，我们在统计学中接触到的参数是什么？我们都知道正态分布模型有两个参数一个均值 $\mu$，一个方差$\sigma$，知道这两个参数就可以确定一个正态分布模型。试想我们根据数据训练出一个[高斯混合模型](https://www.geeksforgeeks.org/gaussian-mixture-model/)，根据数据得到的参数就是模型参数，而模型之外的参数为超参数。

比如常见的超参数： Learning Rate（学习率）、Number of Epochs、Momentum（动量）等等。



## 引用来源 {#references .no-underline}

[^1]: [The Linux Command Line: A Complete Introduction](http://linuxclass.heinz.cmu.edu/doc/tlcl.pdf)  
[^2]: [https://101.lug.ustc.edu.cn/](https://101.lug.ustc.edu.cn/)
