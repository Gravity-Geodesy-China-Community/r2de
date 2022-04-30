# Java 基础{#first-class}

!!! abstract "导言"

   本节介绍 Java 的基础知识，这是开展 Java 开发的准备知识。

## Java基础 {#fundamental}

### 1、Java 中的重载、重写的区别？

Java中，**重载** (overload) 意味着[^1]：  

1）**发生在同一个类中**  
2）函数名相同  
3）参数类型、个数不同 、返回值可以不同
4）发生在编译时

**函数重载例子**：
```java
package fundamental;

public class Overload {
    /* example of overload
    this two function have same name but for different number of arguments
     */
    static int addition(int num1,int num2){return num1+num2;}
    static int addition(int num1,int num2,int num3){return num1+num2+num3;}
    static double addition(double num1, double num2){return num1+num2;}  //different type parameter(double versus int)
    public static void main(String args[]) {
        System.out.println(addition(1,1)); //method overloading
        System.out.println(addition(1,1,1)); //method overloading, we are calling same methods but for different number of arguments.
        System.out.println(addition(1.0,2.0));
        /*
        output : 2
                 3
                3.0
         */

         /*
         Overloading in java is basically a “compile-time   
         polymMethod Overloading”. Compile-time   
         polymorphism in java is also called as “Static method   
         Dispatch” or “Early binding”. So what do I mean by that   
         jargon?
         */

    }
}
```

**重写**（Override）：
1）**父类必须有同名的方法**  
2）与父类方法的参数列表必须相同，返回值范围小于父类，抛出异常范围小于父类
3）必须有继承关系
4）如果父类方法是 `private、final、static`，则不能够被重载
5）运行时的多态


**函数重写例子**

```java
package fundamental;
class Parent {
    public void display() {
        System.out.println("Hello, I am from parent class");
    }
}
//Child or subclass
class Sub extends Parent {
    //Below method overrides the Parent display() method
// @override
    public void display() {
        System.out.println("Hello, I am from child class");
    }
}

public class Override {

    //Driver class
        public static void main(String args[])
        {
            Parent superObject = new Parent ();
            superObject.display(); // Super class method is called
            Parent subObject = new Sub();
            subObject.display(); //Child class method is called by a parent type reference: this is functionality of method overriding
            Sub subObject2 = new Sub(); //Child class method is called by a child type reference
            subObject2.display();
            /*
            output:
             Hello, I am from parent class
             Hello, I am from child class
             Hello, I am from child class
             */
        }
    }

```

<center>
    <img style="border-radius: 0.3125em;
    box-shadow: 0 2px 4px 0 rgba(34,36,38,.12),0 2px 10px 0 rgba(34,36,38,.08);" 
    src="https://gitee.com/georgegou/gravitychina/raw/picture/%E9%87%8D%E8%BD%BD%E4%B8%8E%E9%87%8D%E5%86%99.png">
    <br>
    <div style="color:orange; border-bottom: 1px solid #d9d9d9;
    display: inline-block;
    color: #999;
    padding: 2px;">重载与重写示意图</div>
</center>

### 2、Java 的String、StringBuffer、StringBulider的区别？

**String 为什么不可变？**

类似于Python中的字符串对象，其值一但定义是不可变的！ 底层使用` private 
final char value[] `来实现的，知道为什么不变了吗，final修饰
Java提供了 String、StringBuffer、StringBuilder来表达字符串。其中 StringBuffer和StringBuilder（JDK1.5）是可变的。

**StringBuffer、StringBuilder的区别对比**？

| No | StringBuffer | StringBuilder |
| :-----| ----: | :----: |
| 1 | 线程安全 | 线程不安全 |
| 2 | 效率比StringBuilder差 | 效率比StringBuffer好 |
| 3 | since JDK1.0 |since JDK1.5 |

**实例**:
```java
package fundamental;

public class StringTest {
    public static void main(String[] args) {
        /*
        abstract class AbstractStringBuilder implements Appendable, 
        CharSequence {
        The value is used for character storage.
        char[] value;
         */
        // the code comment in upper is the source code of class AbstractStringBuilder, 
        // this class is the parent
        // of StringBuffer and StringBuilder
        StringBuffer buffer = new StringBuffer("StringBuffer \n");
        buffer.append("I am a java Programmer");
        System.out.println(buffer);
        /*
        output:
            StringBuffer
            I am a java Programmer
         */
        char[] ch={'j','a','v','a','t','p','o','i','n','t'};
        String s =new String(ch);
        System.out.println(s.charAt(0));  // j
        System.out.println(s); // javatpoint
        /*
        String class source code:
        public final class String
        implements java.io.Serializable, Comparable<String>, CharSequence {
        The value is used for character storage.
        private final char value[]; // final means immutable
         */
        StringBuilder builder=new StringBuilder("StringBuilder");
        builder.append("in java");
        System.out.println(builder); // StringBuilder in java

        /*
        Test the performance of StringBuffer and StringBuilder
         */
        long startTime = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer("Java");
        for (int i=0; i<10000; i++){
            sb.append("Tpoint");
        }
        System.out.println("Time taken by StringBuffer: " + (System.currentTimeMillis() - startTime) + "ms");
        startTime = System.currentTimeMillis();
        StringBuilder sb2 = new StringBuilder("Java");
        for (int i=0; i<10000; i++){
            sb2.append("Tpoint");
        }
        System.out.println("Time taken by StringBuilder: " + (System.currentTimeMillis() - startTime) + "ms");
        /*
        output:
        Time taken by StringBuffer: 9ms
        Time taken by StringBuilder: 6ms
         */
        /*
         if we take the loop times as 10000 for example, the output is :
         Time taken by StringBuffer: 1ms
         Time taken by StringBuilder: 1ms
         */
        // It means the StringBuffer is slower than StringBuilder, When can usually use StringBuffer to avoid
        // thread unsafe
    }
}
    }
```
### 3、为什么StringBuilder线程不安全（实际代码例子）

```java
package fundamental;
/*
 this file contains the example to explain why StringBuffer is thread safe
 */
/*
why we implement Runnable Interface?
this is one way to realise multithreading, another way is to inherit the thread class,
but java is Single inheritance, which the derived class inherits only one base class
 */

public class StringBuffer_Multithreading {
    public static void main(String args[]) throws InterruptedException {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                for(int j = 0; j < 1000; j++){
                    sb.append("java StringBuilder is thread unsafe"); //35 length long character
                }
            }).start();
        }

        Thread.sleep(1000);
        System.out.println(sb.length());  // 221970, not 350000

        StringBuffer sf = new StringBuffer();
        for(int i = 0; i < 10; i++){
            new Thread(() -> {
                for(int j = 0; j < 1000; j++){
                    sf.append("java StringBuilder is thread unsafe"); //35 length long character
                }
            }).start();
        }

        Thread.sleep(1000);
        System.out.println(sf.length());  // 350000
        /* output for j < 1000
        Exception in thread "Thread-3" Exception in thread "Thread-4" java.lang.ArrayIndexOutOfBoundsException
	    at java.lang.System.arraycopy(Native Method)
        at java.lang.String.getChars(String.java:826)
        at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:449)
        at java.lang.StringBuilder.append(StringBuilder.java:142)
        at fundamental.StringBuffer_Multithreading.lambda$main$0(StringBuffer_Multithreading.java:17)
        at java.lang.Thread.run(Thread.java:750)
    java.lang.ArrayIndexOutOfBoundsException
        at java.lang.System.arraycopy(Native Method)
        at java.lang.String.getChars(String.java:826)
        at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:449)
        at java.lang.StringBuilder.append(StringBuilder.java:142)
        at fundamental.StringBuffer_Multithreading.lambda$main$0(StringBuffer_Multithreading.java:17)
        at java.lang.Thread.run(Thread.java:750)
        275100
        Process finished with exit code 0
         */

        // the output is less than 35*1000*10 = 350000

        /*
         the reason why things we mentioned above happened?
         look back the reasize of StringBuilder in JDK source

         lang.ArrayIndexOutOfBoundsException means is we access
         the upper bounds in one array or string

         public StringBuilder() { super(16);} in StringBuilder.java means the length
         of StringBuilder is initalized by statement

         public StringBuilder() {super(16);}
         in class StringBuilter.java

         and when we call the append method in AbstractStringBuilder.java
         it will extend the length by just add the initialized value 16 + length(str_to_be_added) by the statement
         "count  += len"
         which is thread unsafe. because if two thread run simultaneously and get the value of count = 5
         it will become 5+35 = 40, the next thread will get 40 not 75 as expected, this is why the output
         is 275100 not 350000(True answer)
         */
    }
}
```
上述例子中[^2]，我们编写了一个简单的多线程程序，就是一个线程拼接字符串`java StringBuilder is thread unsafe`
1000次，加上空格总共35个字符。如果10个线程同时工作，拼接字符长度应该是35*1000*10 = 350000，但是实际返回却是
275100，还抛出`Exception in thread "Thread-3" Exception in thread "Thread-4" java.lang.ArrayIndexOutOfBoundsException`
异常，表示类似于数组越界的意思。**这就是线程不安全导致的结果**。但是使用 StringBuffer 类就不存在上述问题。

**原因分析：**

本文从源码的角度来分析这个问题：
```java
public final class StringBuilder
    extends AbstractStringBuilder
 // StringBuilder 构造函数
    public StringBuilder() {
        super(16);
    } 

   // this code in StringBuilder.java
```
```java
abstract class AbstractStringBuilder implements Appendable, CharSequence {
    /**
     * The value is used for character storage.
     */
    char[] value;

    /**
     * The count is the number of characters used.
     */
    int count;

    //this code in AbstractStringBuilder.java
```
从构造函数看出，实际上 new StringBuilder 对象的时候初识化了一个长度为16。  
不难看出，每次拼接字符串的时候，就需要扩充对象的长度。 
```java
    public AbstractStringBuilder append(Object obj) {
        return append(String.valueOf(obj));
    }

    /**
     * Appends the specified string to this character sequence.
     * <p>
     * The characters of the {@code String} argument are appended, in
     * order, increasing the length of this sequence by the length of the
     * argument. If {@code str} is {@code null}, then the four
     * characters {@code "null"} are appended.
     * <p>
     * Let <i>n</i> be the length of this character sequence just prior to
     * execution of the {@code append} method. Then the character at
     * index <i>k</i> in the new character sequence is equal to the character
     * at index <i>k</i> in the old character sequence, if <i>k</i> is less
     * than <i>n</i>; otherwise, it is equal to the character at index
     * <i>k-n</i> in the argument {@code str}.
     *
     * @param   str   a string.
     * @return  a reference to this object.
     */
    public AbstractStringBuilder append(String str) {
        if (str == null)
            return appendNull();
        int len = str.length();
        ensureCapacityInternal(count + len);
        str.getChars(0, len, value, count);
        count += len;   // 这一步就是对字符串长度count的扩充，len就是传入字符串的长度
        return this;
    }
```
 `count += len;`这句代码造成了线程不安全，从报错中`Exception in thread "Thread-1" Exception in thread "Thread-2" Exception in thread "Thread-8" java.lang.ArrayIndexOutOfBoundsException`可以看出， 
 多个线程出现了访问越界的情况。实际上是这样的，如果两个线程同时访问`count = 5` 的话，第一个线程执行代码 `count += len;`，得到
 `count = 40`，第二个线程拿到的`count`也是等于`5`，意味着得到的更新的`count`也等于`40`，正常情况下应该是`75`。

 抛出异常的代码为：
 ```java
     public void getChars(int srcBegin, int srcEnd, char dst[], int dstBegin) {
        if (srcBegin < 0) {
            throw new StringIndexOutOfBoundsException(srcBegin);
        }
        if (srcEnd > value.length) {
            throw new StringIndexOutOfBoundsException(srcEnd); //这可能是抛出异常的语句
        }
        if (srcBegin > srcEnd) {
            throw new StringIndexOutOfBoundsException(srcEnd - srcBegin);
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }
 ```
上文分析到字符串长度本应该为`75`，但是线程不安全的原因，取到的还是`40`，这里访问的下标就大于`40`，抛出异常。

**为什么Stringbuffer线程就安全？**
```java
    public synchronized StringBuffer append(CharSequence s) {
        toStringCache = null;
        super.append(s);
        return this;
    }
```
上面是 `Javabuffer` 类文件中的 `append` 函数实现，发现其用了 `synchronized` 关键字修饰，保证了线程安全性。

**上述三者在实际开发中的使用经验**

1、操作少量的数据 String
2、多线程操作字符串 StringBuffer
3、单线程操作大量字符串 StringBuilder


### 4、自动装箱与拆箱

!!! note 英文原文解释
    **Autoboxing**[^3] in Java is a process of converting a primitive data type into an object of its corresponding wrapper class.
    For example, converting int to Integer class, long to Long class or double to Double class, etc.

    **Unboxing** in Java is an automatic conversion of an object of a wrapper class to the value of its respective primitive data type by the compiler.

自动装箱与拆箱（Autoboxing and Unboxing）是 Java 非常重要的特性：

**装箱**：将基本类型（int、long、double）用其对应的引用类型（integer、Long、Double）包装起来
**拆箱**：将包装类型转换为基本类型



**示例代码**：
```java
package fundamental;
import java.util.ArrayList;

public class outboxing {
    public static void main(String[] args)
    {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0,2);
        int num = arrayList.get(0);
// unboxing because get method returns an Integer object
        System.out.println(num); //2

    }
}
```

### 5、== 与 equals 区别

!!! note Difference between == and .equals() method in Java
    The major difference[^4] between the == operator and .equals() method is that one is an operator, and the other is the method. Both these == operators and equals() are used to compare objects to mark equality. Let’s analyze the difference between the == and .equals() method in Java.


| No | == 操作符| equals 方法|
| :-----| :----: | :----: |
| 1 | ==是操作符 | equal是方法 |
| 2 | 用来比较对象（地址）与基本数据类型（值） | 判断对象实际内容 |
| 3 | 可用于原始对象比较 |不能用于原始对象（int） |
| 4 | 不能被重写|可以被重写 |

**对于上文第4点的例子**

`String`中的`equals`方法是被重写过的，`Object`中的`equal`方法是比较对象的内存地址，
而`String`类中的地址是比较的`String`对象的值。

```java
package fundamental;

public class equal {
        public static void main(String[] args) {
            String a = new String("ab"); // a 为一个引用
            String b = new String("ab"); // b 为另一个引用,对象的内容一样
            String aa = "java_euqal_is_a_method"; // 放在常量池中
            String bb = "java_==_campare_value_or_object"; // 从常量池中查找
            if (aa == bb) // false
                System.out.println("aa==bb");
            if (a == b) // false，非同一对象
                System.out.println("a==b");
            if (a.equals(b)) // true
                System.out.println("aEQb");
            if (42 == 42.0) { // true
                System.out.println("true");
            }
        }
}
```
### 6、Java 中 final 关键字有什么用？

1、final 修饰的变量初始化后就不能再更改。看下面的例子：

2、final 修饰一个类时
```java
package fundamental;

package fundamental;
/*
final keyword in front of method, which can't be overridden
*/
    class FinalDemo {
        // create a final method
        public final void display() {
            System.out.println("This is a final method.");
        }
    }

    class Subfinal extends FinalDemo{
        public final void display(){  //Idea says : 'display()' cannot override 'display()' in 'fundamental.finalkeyword.Main.FinalDemo'; overridden method is final
            System.out.println("The final method is overridden");
        }
    }
    /*
    final keyword in front of variable,which can't be changed
    */
    public class finalkeyword {
        public class Main {
            final int x = 10;

     /*
     final keyword in front of variable,which can't be changed
     */
     // create a final class
     final class FinalClass {
         public void display() {
             System.out.println("This is a final method.");
         }
     }

    // try to extend the final class, which can't not be inherited
    class Sub extends FinalClass { //Idea says: Cannot inherit from final 'fundamental.finalkeyword.Main.FinalClass'
        public  void display() {
            System.out.println("The final method is overridden.");
        }

    public void main(String[] args) {
        Main myObj = new Main();
        myObj.x = 25; // will generate an error: cannot assign a value to a final variable
        System.out.println(myObj.x);
    }
    }
}

```

### 7、谈谈什么是`Object`类

`Object`是所有类的父类，其实现了一系列面向对象程序设计的常用方法，如下表所示：

| 方法| 描述|
| :----:    | :-----------: |
| public final native Class<?> getClass()  | 用于返回当前运行时对象的 Class 对象 |
|    public native int hashCode() | 返回对象的hashCode |
|   public boolean equals(Object obj)|判断对象的地址是否相同 |
| protected native Object clone() |创建当前对象的一份拷贝，内存地址不同 |
| public String toString()|返回当前对象的字符串表达|
| public final native void notify() |唤醒一个对象监视器上等待的单线程
| public final native void notifyAll() |唤醒对象监视器上的所有线程 |
| public final native void wait(long timeout)|暂停线程的执行，除非用notify唤醒|、
|public final void wait(long timeout, int nanos)|上述方法增加了额外时间|
|public final void wait() |一直等待，没有超过多少时间的概念|
|protected void finalize()|实例被垃圾回收的时候触发的操作|

### 8、Java中的异常处理

??? info "JDK Throwable.java 源码注释"
    The Throwable class is the superclass of all errors and exceptions in the Java language. Only objects that are instances of this class (or one of its subclasses) are thrown by the Java Virtual Machine or can be thrown by the Java throw statement. Similarly, only this class or one of its subclasses can be the argument type in a catch clause. For the purposes of compile-time checking of exceptions, Throwable and any subclass of Throwable that is not also a subclass of either RuntimeException or Error are regarded as checked exceptions.
    Instances of two subclasses, Error and Exception, are conventionally used to indicate that exceptional situations have occurred. Typically, these instances are freshly created in the context of the exceptional situation so as to include relevant information (such as stack trace data).
    A throwable contains a snapshot of the execution stack of its thread at the time it was created. It can also contain a message string that gives more information about the error. Over time, a throwable can suppress other throwables from being propagated. Finally, the throwable can also contain a cause: another throwable that caused this throwable to be constructed. The recording of this causal information is referred to as the chained exception facility, as the cause can, itself, have a cause, and so on, leading to a "chain" of exceptions, each caused by another.
    One reason that a throwable may have a cause is that the class that throws it is built atop a lower layered abstraction, and an operation on the upper layer fails due to a failure in the lower layer. It would be bad design to let the throwable thrown by the lower layer propagate outward, as it is generally unrelated to the abstraction provided by the upper layer. Further, doing so would tie the API of the upper layer to the details of its implementation, assuming the lower layer's exception was a checked exception. Throwing a "wrapped exception" (i.e., an exception containing a cause) allows the upper layer to communicate the details of the failure to its caller without incurring either of these shortcomings. It preserves the flexibility to change the implementation of the upper layer without changing its API (in particular, the set of exceptions thrown by its methods).
    A second reason that a throwable may have a cause is that the method that throws it must conform to a general-purpose interface that does not permit the method to throw the cause directly. For example, suppose a persistent collection conforms to the Collection interface, and that its persistence is implemented atop java.io. Suppose the internals of the add method can throw an IOException. The implementation can communicate the details of the IOException to its caller while conforming to the Collection interface by wrapping the IOException in an appropriate unchecked exception. (The specification for the persistent collection should indicate that it is capable of throwing such exceptions.)
    A cause can be associated with a throwable in two ways: via a constructor that takes the cause as an argument, or via the initCause(Throwable) method. New throwable classes that wish to allow causes to be associated with them should provide constructors that take a cause and delegate (perhaps indirectly) to one of the Throwable constructors that takes a cause. Because the initCause method is public, it allows a cause to be associated with any throwable, even a "legacy throwable" whose implementation predates the addition of the exception chaining mechanism to Throwable.
    By convention, class Throwable and its subclasses have two constructors, one that takes no arguments and one that takes a String argument that can be used to produce a detail message. Further, those subclasses that might likely have a cause associated with them should have two more constructors, one that takes a Throwable (the cause), and one that takes a String (the detail message) and a Throwable (the cause).
    Since:
    JDK1.0
    Author:
    unascribed, Josh Bloch (Added exception chaining and programmatic access to stack trace in 1.4.)+

**Java.lang** 包中有一个专门做错误和异常处理的类就是`Throwable`类。

Java 程序错误和异常是不同的概念：类似于`Virtual MachineError`、`OutOfMemoryErro`、`NoClassDefFoundError`等错误
**错误**发生于虚拟机本身，代码上不可查。**异常**是程序本身可以处理的异常，比如其重要子类`RuntimeException`
常见的异常：`。NullPointerException`，空指针异常，要访问的变量没有引用任何对象;`ArithmeticException`，算术异常，类似于除以0这中操作。

**如果在程序中做好异常处理**

一般来说，为了实现程序的**高可用性**，必须对各种极端或边界条件进行测试。这时候就需要对一些情况做异常判断，针对异常
情况再做相关的处理，增加程序的鲁棒性。。
```java
import java.io.*;

class ListOfNumbers {

  // create an integer array
  private int[] list = {5, 6, 8, 9, 2};

  // method to write data from array to a fila
  public void writeList() {
    PrintWriter out = null;

    try {
      System.out.println("Entering try statement");

      // creating a new file OutputFile.txt
      out = new PrintWriter(new FileWriter("OutputFile.txt"));

      // writing values from list array to Output.txt
      for (int i = 0; i < 7; i++) {
        out.println("Value at: " + i + " = " + list[i]);
      }
    }
    
    catch (Exception e) {
      System.out.println("Exception => " + e.getMessage());
    }
    
    finally {
      // checking if PrintWriter has been opened
      if (out != null) {
        System.out.println("Closing PrintWriter");
        // close PrintWriter
        out.close();
      }
      
      else {
        System.out.println("PrintWriter not open");
      }
    }

  }
}

class Main {
  public static void main(String[] args) {
    ListOfNumbers list = new ListOfNumbers();
    list.writeList();
  }
}
/*
Entering try statement
Exception => Index 5 out of bounds for length 5
Closing PrintWriter
*/
```
**Finally**可能不被执行的情况：  
1) Finally语句有异常;  
2) 前面有代码System.exit()语句，退出程序
3）程序再线程中死亡
4）关闭 CPU

### 9、Java中通过键盘获取输入的方法

1、通过`Scanner`:
2、通过`BufferReader`:
```java
package fundamental;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class BufferReader
{
    public static void main(String[] args) throws IOException
    {
        
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(isr);//Creating a BufferedReader
        System.out.println("Enter Something for bufferReader");
        String line = bufferedReader.readLine();
        System.out.print("bufferReader Entered: " + line + "\n");
        bufferedReader.close();//Closing the stream
         /*
         Enter Something for bufferReader
         bufferReader
         bufferReader Entered: bufferReader
         */
        System.out.println("Enter Something for scanner");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        System.out.println("Scanner input " + s);
        input.close();
        /*
        Enter Something for scanner
        Scanner
        Scanner input Scanner
         */

    }
}
```

### 10、接口与抽象类的区别？

??? question "什么时抽象类"


| 方法| 描述|
| :----:    | :-----------: |
| 一个类只能实现一个抽象类  | 一个类可以实现多个接口 |
|    public native int hashCode() | 返回对象的hashCode |
|   public boolean equals(Object obj)|判断对象的地址是否相同 |
| protected native Object clone() |创建当前对象的一份拷贝，内存地址不同 |
| public String toString()|返回当前对象的字符串表达|
| public final native void notify() |唤醒一个对象监视器上等待的单线程
| public final native void notifyAll() |唤醒对象监视器上的所有线程 |
| public final native void wait(long timeout)|暂停线程的执行，除非用notify唤醒|、
|public final void wait(long timeout, int nanos)|上述方法增加了额外时间|
|public final void wait() |一直等待，没有超过多少时间的概念|
|protected void finalize()|实例被垃圾回收的时候触发的操作|




## 引用来源 {#references .no-underline}
[^1]: [overloading-and-overriding-in-java/](https://www.educba.com/overloading-and-overriding-in-java/)  
[^2]: [为什么？为什么StringBuilder是线程不安全的？](https://blog.csdn.net/m0_57286743/article/details/117891582?utm_medium=distribute.pc_relevant.none-task-blog-2~default~baidujs_baidulandingword~default-1.pc_relevant_default&spm=1001.2101.3001.4242.2&utm_relevant_index=4)
[^3]:[autoboxing-and-unboxing](https://www.javatpoint.com/autoboxing-and-unboxing)
[^4]:[Difference between == and .equals() method in Java](https://byjus.com/gate/difference-between-operator-and-equals-method-in-java/#:~:text=Difference%20between%20%3D%3D%20and%20.equals%20%28%29%20method%20in,compare%20conflic%20...%20%201%20more%20rows%20)
[^5]:[https://www.geeksforgeeks.org/object-class-in-java/](https://www.geeksforgeeks.org/object-class-in-java/)
[^6]:[Object class in Java](https://www.javatpoint.com/object-class#:~:text=Object%20class%20in%20Java%20The%20Object%20class%20is,refer%20the%20child%20class%20object%2C%20know%20as%20upcasting.)