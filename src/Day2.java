import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    File file;
    Scanner myReader;

    public void solveDay2(){
        long start2 = System.currentTimeMillis();
        new Day2().Day2Part1("day2.txt");
        new Day2().Day2Part2("day2.txt");
        long end2 = System.currentTimeMillis();
        System.out.println(", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    private void setFile(String iFile) {
        this.file = new File("./input/day2/" + iFile);
    }

    private void Day2Part1(String iFile) {
        {
            Pattern pattern_blue = Pattern.compile("(\\d+) blue");
            Pattern pattern_green = Pattern.compile("(\\d+) green");
            Pattern pattern_red = Pattern.compile("(\\d+) red");
            Matcher m_b;
            Matcher m_g;
            Matcher m_r;
            int sumID = 0;
            this.setFile(iFile);
            try {
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    boolean illegalCubeNum = false;
                    String data = myReader.nextLine();
                    //int count = (int) data.chars().filter(ch -> ch == ';').count() + 1;
                    //Blue cubes, max 14
                    m_b = pattern_blue.matcher(data);
                    m_g = pattern_green.matcher(data);
                    m_r = pattern_red.matcher(data);
                    while (m_b.find()) {
                        String digits = m_b.group(1);
                        int number = Integer.parseInt(digits);
                        if (number > 14)
                            illegalCubeNum = true;
                        //System.out.print(number + "b ");
                    }
                    //Green cubes, max 13
                    while (m_g.find()) {
                        String digits = m_g.group(1);
                        int number = Integer.parseInt(digits);
                        if (number > 13)
                            illegalCubeNum = true;
                        //System.out.print(number + "g ");
                    }
                    //Red cubes, max 12
                    while (m_r.find()) {
                        String digits = m_r.group(1);
                        int number = Integer.parseInt(digits);
                        if (number > 12)
                            illegalCubeNum = true;
                        //System.out.print(number + "r ");
                    }
                    //System.out.println();
                    if (!illegalCubeNum) {
                        int gamenum = Integer.parseInt(data.substring(data.indexOf('e') + 2, data.indexOf(':')));
                        sumID += gamenum;
                    }
                }

                System.out.print("Day 2 Part 1: " + sumID);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void Day2Part2(String iFile) {
        {
            Pattern pattern_blue = Pattern.compile("(\\d+) blue");
            Pattern pattern_green = Pattern.compile("(\\d+) green");
            Pattern pattern_red = Pattern.compile("(\\d+) red");
            Matcher m_b;
            Matcher m_g;
            Matcher m_r;
            int sumID = 0;
            this.setFile(iFile);
            try {
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    //int count = (int) data.chars().filter(ch -> ch == ';').count() + 1;
                    //Blue cubes, max 14
                    m_b = pattern_blue.matcher(data);
                    m_g = pattern_green.matcher(data);
                    m_r = pattern_red.matcher(data);
                    int max_b = 0, max_g = 0, max_r = 0;
                    while (m_b.find()) {
                        String digits = m_b.group(1);
                        int number = Integer.parseInt(digits);
                        max_b = Math.max(number, max_b);
                    }
                    //Green cubes, max 13
                    while (m_g.find()) {
                        String digits = m_g.group(1);
                        int number = Integer.parseInt(digits);
                        max_g = Math.max(number, max_g);
                    }
                    //Red cubes, max 12
                    while (m_r.find()) {
                        String digits = m_r.group(1);
                        int number = Integer.parseInt(digits);
                        max_r = Math.max(number, max_r);
                    }
                    //System.out.println();
                    int powerOfCubes = max_b*max_g*max_r;
                    //System.out.println(powerOfCubes);
                    sumID += powerOfCubes;
                }

                System.out.print(", Day 2 Part 2: " + sumID);
                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

