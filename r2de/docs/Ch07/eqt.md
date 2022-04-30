# EQTransformer{#first-class}

!!! abstract "导言"

   本节带你实战一个**基于地震数据做相位识别和地震监测**的深度学习应用，该成果发表于 2020 natural communication[^1] 上，目前已经广泛用于地震数据服务中。

## 环境搭建 {#environment}
```shell
#建议使用conda搭建环境，以免与其他生产环境相冲突
conda create -n eqt python=3.7

conda activate eqt

conda install -c smousavi05 eqtransformer
```


## 引用来源 {#references .no-underline}

[^1]: [Mousavi, S.M., Ellsworth, W.L., Zhu, W. et al. Earthquake transformer—an attentive deep-learning model for simultaneous earthquake detection and phase picking. Nat Commun 11, 3952 (2020). https://doi.org/10.1038/s41467-020-17591-w](https://www.nature.com/articles/s41467-020-17591-w#citeas)  
[^2]: [https://101.lug.ustc.edu.cn/](https://101.lug.ustc.edu.cn/)

