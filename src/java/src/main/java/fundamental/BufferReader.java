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