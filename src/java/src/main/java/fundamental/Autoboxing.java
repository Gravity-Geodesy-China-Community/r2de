package fundamental;
import java.util.ArrayList;

public class Autoboxing {
    public static void main(String[] args)
    {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        //Autoboxing: int primitive to Integer
        arrayList.add(11); //passed int (primitive type), but compiler automatically converts it to the Integer object.
        arrayList.add(22);
        System.out.println(arrayList);
    }
    // output: [11, 22]
}
