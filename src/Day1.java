import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Day1 {
    File file = new File("./input/day1/day1.txt");
    Scanner myReader;

    public void solveDay1(){
        long start2 = System.currentTimeMillis();
        new Day1().Day1Part1();
        new Day1().Day1Part2("day1.txt");
        long end2 = System.currentTimeMillis();
        System.out.println(", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    private void Day1Part1() {
        {
            int num = 0;
            try {
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    String clean = data.replaceAll("\\D+","");
                    num += (clean.charAt(0)-'0')*10+clean.charAt(clean.length()-1)-'0';
                    //System.out.println(data + " " + num);
                }
                System.out.print("Day 1 Part 1: " + num);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void Day1Part2(String inputfile) {
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
                            int index;
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
                    //System.out.println("Uncleaned String: " + data + " Cleaned String: " + clean + " Value: " + num);
                }
                System.out.print(", Day 1 Part 2: " + num);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
