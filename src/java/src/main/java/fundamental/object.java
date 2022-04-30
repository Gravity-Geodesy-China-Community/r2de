package fundamental;
import java.lang.Override;

public class object {


@Override
    protected void finalize()
    {
        System.out.println("finalize method called");
    }
    public static void main(String[] args)
    {
        object t = new object();
        System.out.println(t.hashCode());
        t = null;

        // calling garbage collector
        System.gc();

        System.out.println("end");
    }


}
