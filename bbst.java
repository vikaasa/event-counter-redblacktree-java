import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Created by Vikaasa on 3/14/2016.
 */
public class bbst {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// reading file with test input
        FileReader inputFile = new FileReader(args[0]);
        BufferedReader bufferReader = new BufferedReader(inputFile);
        int size = Integer.parseInt(bufferReader.readLine());
		// storing test input data in an array
        int[][] a = new int[size][2];
       

        String line;
        int count = 0;
        while ((line = bufferReader.readLine()) != null) {
            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
             while (stringTokenizer.hasMoreElements()) {
                Integer key = Integer.parseInt(stringTokenizer.nextElement().toString());
                Integer value = Integer.parseInt(stringTokenizer.nextElement().toString());  
			    int i = 0;
                a[count][0] = key;
				a[count][1] = value;
				count++;
			}
        }
        // initializing new Red Black Tree
		RedBlackTree tree = new RedBlackTree();
        tree.callInitialize(a, 0, size - 1, size);
        inputFile.close();
        bufferReader.close();
		
		// reading commands from input stream
        while ((line = br.readLine()) != null&&!line.equals("quit")) {
            if(line.isEmpty())
                continue;
            StringTokenizer stringTokenizer = new StringTokenizer(line, " ");
            while (stringTokenizer.hasMoreElements()) {
				Integer x,y;
                String command = stringTokenizer.nextElement().toString();
				switch (command.toString()) {
                    case "increase":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        y = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.increase(x, y);
                        break;
                    case "reduce":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        y = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.reduce(x,y);
                        break;
                    case "count":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.count(x);
                        break;
                    case "inrange":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        y = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.callInRange(x, y);
                        break;
                    case "next":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.next(x);
                        break;
                    case "previous":
                        x = Integer.parseInt(stringTokenizer.nextElement().toString());
                        tree.previous(x);
                        break;
                    default:
                        System.out.println("Incorrect command");
                        break;	
                }
            }
           
        }  
	br.close();
    }
}