package filestream;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFile {
    public void process() throws IOException {
        File file = new File("/Users/droykarmakar/Documents/dhrubajrk-code/src/filestream/input-numbers.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())  {
            String s = sc.nextLine();
            System.out.print("Min: " + Arrays.stream(s.split(" "))
                    .map(Integer::parseInt)
                    .max(Comparator.comparingInt(o -> -o))
                    .get());
            System.out.print(" Sum: " + (Integer) Arrays.stream(s.split(" "))
                    .map(Integer::parseInt).mapToInt(value -> value).sum());
            System.out.println(" ");
        }
    }

    public static void main (String[] args) throws IOException {
        new ReadFile().process();
    }
}
