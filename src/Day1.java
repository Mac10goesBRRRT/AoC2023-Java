import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Queue;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Day1 {
    File file = new File("./input/day1/day1.txt");
    Scanner myReader;


    public void Day1Part1() {
        {
            int num = 0;
            try {
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String clean = data.replaceAll("\\D+","");
                    num += (clean.charAt(0)-'0')*10+clean.charAt(clean.length()-1)-'0';
                    System.out.println(data + " " + num);
                }
                System.out.println(num);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void Day1Part2(String inputfile) {
        {
            File file = new File("./input/day1/"+inputfile);
            int num = 0;
            char[] c = {'1','2','3','4','5','6','7','8','9'};
            try {
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String[] numbers = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
                    int j = 0;
                    do {
                        for (int i = 0; i < 9; i++) {
                            int index = -1;
                            if ((index = data.indexOf(numbers[i])) != -1) {
                                StringBuilder newData = new StringBuilder(data);
                                newData.setCharAt(index + 1, c[i]);
                                data = String.valueOf(newData);
                            }
                        }
                        j++;
                    }while(j < 3);
                    String clean = data.replaceAll("\\D+","");
                    num += (clean.charAt(0)-'0')*10+clean.charAt(clean.length()-1)-'0';
                    System.out.println("Uncleaned String: " + data + " Cleaned String: " + clean + " Value: " + num);
                }
                System.out.println("Value at the end: " + num);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
