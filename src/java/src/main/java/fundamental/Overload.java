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
    }
}