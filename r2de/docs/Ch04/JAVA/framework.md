
!!! question "Java 基本概念"

    **类（Class）**：

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


!!! question "如何查看目录的大小"

    ```shell
        du -sh dir
        (base) λ -c ~ → du -sh oracel  2.7G    oracel
    ```
