
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

    /*
    class Subfinal extends FinalDemo{
        public final void display(){  //Idea says : 'display()' cannot override 'display()' in 'fundamental.finalkeyword.Main.FinalDemo'; overridden method is final
        System.out.println("The final method is overridden");
        }
    }
    */
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
    /*
            class Sub extends FinalClass { //Idea says: Cannot inherit from final 'fundamental.finalkeyword.Main.FinalClass'
        public  void display() {
            System.out.println("The final method is overridden.");
        }
    */
    public void main(String[] args) {
        Main myObj = new Main();
        //myObj.x = 25; // will generate an error: cannot assign a value to a final variable
        System.out.println(myObj.x);
    }
    }
}

