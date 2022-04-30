# Linux 基础{#first-class}

!!! abstract "导言"

   本节收集了互联网上与本人实际经历的面试问题
    

!!! question "硬链接（Hard Links）与软链接（Symbolic Links）的区别 "
    试想一个场景，Python大家应该都不陌生，我们使用python的时候，python其实是有版本号的，比如Python3.5，我们输入Python3.5来运行python程序，当Python3升级到3.9的时候，我们需要运行Python3.9来运行程序。如果我们创建软连接python3.5---python3，我们就可以运行python3来运行python3.5了，当python升级后，我们删除原有链接，创建新的链接，并链接到最新的版本即可，用户不用关心是什么版本，但也可以查询到，**这就是软链接**。

```shell
(base) λ -c test → touch hello.c && echo "this is a C source file" > hello.c
(base) λ -c test → cat hello.c
this is a C source file                      
(base) λ -c test → ln hello.c hard
(base) λ -c test → ls -li        
total 8
       271134447 -rw-rw-r--. 2 george george 24 Dec 12 08:31 hard
       271134447 -rw-rw-r--. 2 george george 24 Dec 12 08:31 hello.c 
(base) λ -c test → vi hard #修改hard文件
(base) λ -c test → cat hard   
this is a C source file
now I write something to hard file
                                                         %
(base) λ -c test → cat hello.c
this is a C source file 
now I write something to hard file→  
(base) λ -c test → ln -s hello.c soft
(base) λ -c test → ls -li
total 8
       271134447 -rw-rw-r--. 2 george george 59 Dec 12 08:35 hard
       271134447 -rw-rw-r--. 2 george george 59 Dec 12 08:35 hello.c
       271134448 lrwxrwxrwx. 1 george george 7 Dec 12 08:44 soft -> hello.c   

(base) λ -c test → rm hello.c
(base) λ -c test → cat soft   
cat: soft: No such file or directory
(base) λ -c test → cat hard   
this is a C source file
now I write something to hard file
now I writ something to soft file  

(base) λ -c test → echo "rewrite somethin" >> soft
(base) λ -c test → ls
hard  hello.c  soft
```
* 在上述例子中，我们建立hello.c文件，再建立了硬链接，两个文件的inode值都为271134447 说明两者指向同一块区块。随后修改hard，hello.c的文件也随之修改。
* 随后我们建立了软链接，它们的inode不一致，指向不同的区块.
* 我们随即删除了源文件hello.c，发现软链接已经找不到源文件了。原因是因为软链接的inode所指向的内容保存了一个绝对路径，当用户访问这个文件的时候，自动替换成所指文件的路径，如果这个文件（hello.c）被删除了，自然无法找到。
* 当我们在软链接中写入信息时，hello.c又自动出现了，说明修改软链接，系统自动将其路径替换为其所代表的的绝对路径，并直接访问那个路径了。


**总结**
* 硬链接：与普通文件相同，inode与源文件完全相同，指向同一区块，删除源文件对hard文件不影响；
* 软链接：inode与源文件不同，实际上存储的是所链接文件的绝对地址，删除源文件后，soft则不存在，如果增加soft内容，源文件又会出现。

!!! question "如何查看一个目录的大小 ?"

    ```shell
        du -sh dir
        (base) λ -c ~ → du -sh oracel  2.7G    oracel
    ```

## 引用来源 {#references .no-underline}

[^1]: [The Linux Command Line: A Complete Introduction](http://linuxclass.heinz.cmu.edu/doc/tlcl.pdf)  
[^2]: [https://101.lug.ustc.edu.cn/](https://101.lug.ustc.edu.cn/)

