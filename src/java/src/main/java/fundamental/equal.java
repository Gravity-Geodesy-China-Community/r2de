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
