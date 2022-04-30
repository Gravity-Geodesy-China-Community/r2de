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
