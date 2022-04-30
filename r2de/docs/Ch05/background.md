# 机器学习基础{#first-class}

!!! abstract "导言"

  机器学习技术从上个世纪50年代起，经过了几次起起落落，终于在 2014 年左右，随着深度学习的问世，机器学习方法进入了全新的时代。

## 基本概念 {#computer-and-os}

初次进入到机器学习的世界，我们会接触到很多专有名字。本节带你迅速混入机器学习的圈子。

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
