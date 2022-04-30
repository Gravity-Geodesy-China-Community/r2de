package fundamental;

public class StringTest {
    public static void main(String[] args) {
        /*
        abstract class AbstractStringBuilder implements Appendable, CharSequence {
        The value is used for character storage.
        char[] value;
         */
        // the code comment in upper is the source code of class AbstractStringBuilder, this class is the parent
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
