import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day3 {
    File file;
    Path path;
    Scanner myReader;
    int colum;
    int rows;

    public void setFile(String ifile) {
        this.file = new File("./input/day3/" + ifile);
        this.path = Path.of("./input/day3/" + ifile);
    }

    public void solveDay3(){
        long start2 = System.currentTimeMillis();
        Day3 d3 = new Day3();
        char[][] c = d3.Day3Input("day3.txt");
        int rows = d3.rows;
        int column = d3.colum;
        int[][] lut = d3.makeLutPart1(c);
        int[][] lut2 = d3.makeLutPart2(c);
        int sum1 = d3.sumNum(c,lut);
        int sum2 = d3.calculateRatio(c, lut2);
        long end2 = System.currentTimeMillis();
        System.out.println("Day 3 Part 1: " + sum1 + ", Day 3 Part 1: " + sum2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    public char[][] Day3Input(String ifile) {
        char[][] c = null;
        try {
            this.setFile(ifile);
            myReader = new Scanner(file);
            int lineCount;
            try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                lineCount = (int) stream.count() + 2;
            }
            this.colum = lineCount;
            int streamlen = -1;
            int j = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (streamlen == -1) {
                    streamlen = data.length() + 2;
                    this.rows = streamlen;
                    c = new char[lineCount][streamlen];
                    Arrays.fill(c[0], '.');
                    Arrays.fill(c[lineCount - 1], '.');
                }
                data = "." + data + ".";
                c[j] = Arrays.copyOf(data.toCharArray(), data.length());
                j++;
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public int[][] makeLutPart1(char[][] input) {
        int[][] lut = new int[rows][colum];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < colum - 1; j++) {
                if (input[i][j] >= '0' && input[i][j] <= '9')
                    lut[i][j] = checkAdjacent(input, i, j) ? 1 : 0;
            }
        }
        return lut;
    }

    public int[][] makeLutPart2(char[][] input) {
        int[][] lut = new int[rows][colum];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < colum - 1; j++) {
                if (input[i][j] == '*')
                    lut[i][j] = checkAdjGears(input, i, j);
            }
        }
        return lut;
    }

    private int checkAdjGears(char[][] input, int i, int j) {
        //Compass is possible, but just produces tons of code
        int num = 0;
        if (numCheck(input[i][j - 1])) num++;
        if (numCheck(input[i][j + 1])) num++;
        if (numCheck(input[i - 1][j])) num++;
        else {
            if (numCheck(input[i - 1][j - 1])) num++;
            if (numCheck(input[i - 1][j + 1])) num++;
        }
        if (numCheck(input[i + 1][j])) num++;
        else {
            if (numCheck(input[i + 1][j - 1])) num++;
            if (numCheck(input[i + 1][j + 1])) num++;
        }
        return num;
    }

    public int calculateRatio(char[][] input, int[][] lut) {
        int sum = 0;
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < rows - 1; j++) {
                if (lut[i][j] == 2) {
                    int prod = 1;
                    if (numCheck(input[i][j - 1])) prod *= findNum(input[i], j - 1);
                    if (numCheck(input[i][j + 1])) prod *= findNum(input[i], j + 1);
                    if (numCheck(input[i - 1][j])) prod *= findNum(input[i - 1], j);
                    else {
                        if (numCheck(input[i - 1][j - 1])) prod *= findNum(input[i - 1], j - 1);
                        if (numCheck(input[i - 1][j + 1])) prod *= findNum(input[i - 1], j + 1);
                    }
                    if (numCheck(input[i + 1][j])) prod *= findNum(input[i + 1], j);
                    else {
                        if (numCheck(input[i + 1][j - 1])) prod *= findNum(input[i + 1], j - 1);
                        if (numCheck(input[i + 1][j + 1])) prod *= findNum(input[i + 1], j + 1);
                    }
                    sum += prod;
                }
            }
        }
        return sum;
    }

    private int findNum(char[] input, int index) {
        int firstIndex = index;
        int lastIndex = index;
        //go to the end of the Number
        while (numCheck(input[lastIndex + 1]))
            lastIndex++;
        //go to the start of the number
        while (numCheck(input[firstIndex - 1]))
            firstIndex--;
        String str = new String(input);
        int value = Integer.parseInt(str.substring(firstIndex, lastIndex + 1));
        return value;
    }


    private boolean numCheck(char input) {
        return input >= '0' && input <= '9';
    }

    public int sumNum(char[][] input, int[][] lut) {
        int num = 0;
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < colum - 1; j++) {
                int firstIndex;
                int lastIndex;
                boolean adj = false;
                if (numCheck(input[i][j])) {
                    firstIndex = j;
                    lastIndex = j;
                    while (numCheck(input[i][lastIndex + 1])) {
                        lastIndex++;
                    }
                    for (int k = firstIndex; k <= lastIndex; k++) {
                        if (lut[i][k] == 1) {
                            adj = true;
                            break;
                        }
                    }
                    if (adj) {
                        String str = new String(input[i]);
                        int value = Integer.parseInt(str.substring(firstIndex, lastIndex + 1));
                        num += value;
                    }
                    j = lastIndex + 1;
                }
            }
        }
        return num;
    }

    private boolean checkAdjacent(char[][] input, int rowCord, int columCord) {
        return checkSymbol(input, rowCord - 1, columCord - 1) || checkSymbol(input, rowCord - 1, columCord) || checkSymbol(input, rowCord - 1, columCord + 1) ||
                checkSymbol(input, rowCord, columCord - 1) || checkSymbol(input, rowCord, columCord + 1) ||
                checkSymbol(input, rowCord + 1, columCord - 1) || checkSymbol(input, rowCord + 1, columCord) || checkSymbol(input, rowCord + 1, columCord + 1);
    }

    private boolean checkSymbol(char[][] input, int rowCord, int columCord) {
        return input[rowCord][columCord] == '*' || input[rowCord][columCord] == '#' || input[rowCord][columCord] == '$' || input[rowCord][columCord] == '+' || input[rowCord][columCord] == '@'
                || input[rowCord][columCord] == '/' || input[rowCord][columCord] == '%' || input[rowCord][columCord] == '=' || input[rowCord][columCord] == '-' || input[rowCord][columCord] == '&';
    }
}
