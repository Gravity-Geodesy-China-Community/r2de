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
