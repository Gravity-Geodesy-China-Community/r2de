# Python 面试{#first-class}

!!! abstract "导言"

   本节整理了市场上关于 Python 的面试问题。

### Python函数参数传递 {#environment}

```python
string =  "Great"
def fun(string):
    string = "Great_fun"
fun(string)
print(string) #'Great'  
```

```python
set1 = {'a','a','c',4,2,1}
set1  #{1, 2, 4, 'c', 'a'} #说明set中不允许重复的元素
def fun(set1):
    set1.add(8)
fun(set1)
print(set1) #{1, 2, 4, 8, 'c', 'a'} #set类型对象是可以修改的
```
Python中一切皆对象，上述两种类型其实是对象而不是变量。而对象可以分为两种，一种是可更改（Mutable）和不可能改的（Immutable）。在Python中，Numbers、Tuples、Strings是不可更改的，List、dict、set则是可以修改的对象。

**在上述例子中，当引用传递给函数时，如果传递的是不可变的对象，函数直接复制一份引用，做相应处理，函数外的对象不会发生改变。而当函数引用传递的是可变的对象时，传递的是可变对象的地址，会在内存里修改对象的值。**

### Python中的方法

Python中有三种方法，分别是静态方法（static）、类方法（class）、实例方法（instance），这几种方法的调用方式有稍许不同：
```python

```

## 引用来源 {#references .no-underline}

[^1]: [Mousavi, S.M., Ellsworth, W.L., Zhu, W. et al. Earthquake transformer—an attentive deep-learning model for simultaneous earthquake detection and phase picking. Nat Commun 11, 3952 (2020). https://doi.org/10.1038/s41467-020-17591-w](https://www.nature.com/articles/s41467-020-17591-w#citeas)  
[^2]: [https://101.lug.ustc.edu.cn/](https://101.lug.ustc.edu.cn/)

