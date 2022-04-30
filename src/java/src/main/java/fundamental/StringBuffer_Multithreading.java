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
