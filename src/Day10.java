import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;
import java.util.stream.Stream;

public class Day10 {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_BLUE = "\u001B[34m";

    char startPipe = '╗';

    int colums, rows;
    int xStart, yStart;
    private char[][] getInput() {
        char[][] c = new char[0][];
        try {
            Path path = Path.of("./input/day10/day10.txt");
            Scanner myReader = new Scanner(path);
            int lineCount;
            try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
                lineCount = (int) stream.count();
            }
            this.colums = lineCount;
            int streamLen = -1;
            int j = 0;
            boolean startFound = false;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (streamLen == -1) {
                    streamLen = data.length() + 2;
                    this.rows = streamLen;
                    c = new char[lineCount][streamLen];
                }
                data = data.replaceAll("\\.", ".")
                        .replaceAll("\\|", "║")
                        .replaceAll("-", "═")
                        .replaceAll("L", "╚")
                        .replaceAll("J", "╝")
                        .replaceAll("7", "╗")
                        .replaceAll("F", "╔");
                if(!startFound) {
                    xStart = data.indexOf('S');
                    if (xStart != -1) {
                        yStart = j;
                        startFound = true;
                    }
                }
                c[j] = Arrays.copyOf(data.toCharArray(), data.length());
                j++;
            }
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public void solveDay10() {
        char[][] map = getInput();
        //Create and fill lut
        int[][] lut = new int[colums][rows];
        for(int[] l : lut)
            Arrays.fill(l,-1);
        loopStart(map, lut);
        double sum1 = lookUp(lut);
        char[][]big = supersizeArray(map,lut);
        //printCharArray(big);
        floodFill(big);
        //printCharArray(big);
        shrinkArray(big, lut);/*
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (lut[i][j] > -1) {
                    if(map[i][j]=='S')
                        System.out.print(ANSI_RED + map[i][j] + ANSI_RESET);
                    else
                        System.out.print(ANSI_GREEN + map[i][j] + ANSI_RESET);
                }else {
                    if(lut[i][j]==-2)
                        System.out.print(ANSI_BLUE + map[i][j] + ANSI_RESET);
                    else
                        System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }*/
        System.out.println(Math.ceil(sum1/2));
        System.out.println(countMinus1(lut));
    }

    private void shrinkArray(char[][] big, int[][] lut) {
        for(int y = 0; y < lut.length; y++){
            for(int x = 0; x < lut[0].length; x++){
                if(big[y*3+1][x*3+1]=='o')
                    lut[y][x] = -2;
            }
        }
    }

    private static void printCharArray(char[][] big) {
        for(char[] c : big){
            for(char d : c){
                if(d=='x')
                    System.out.print(ANSI_RED + d + ANSI_RESET);
                else
                    System.out.print(d);
            }
            System.out.println();
        }
    }

    private int lookUp(int[][] lut) {
        int res = -1;
        if(yStart-1 > -1)
            res = Math.max(lut[yStart - 1][xStart], res);
        if(yStart+1 < lut[0].length)
            res = Math.max(lut[yStart + 1][xStart], res);
        if(xStart-1 > -1)
            res = Math.max(lut[yStart][xStart - 1], res);
        if(xStart+1 < lut[0].length)
            res = Math.max(lut[yStart][xStart + 1], res);
        return res;
    }

    private void loopStart(char[][]map, int[][]lut){
        lut[yStart][xStart] = 0;
        depthFirstSearch(map,lut,yStart,xStart);
    }

    private void depthFirstSearch(char[][] map, int[][] lut, int startY, int startX){
        Deque<int[]> stack = new ArrayDeque<>();
        //logic to find the 2 starting pipes
        if((yStart-1) >-1 && (map[yStart-1][xStart]=='║'||map[yStart-1][xStart]=='╗'||map[yStart-1][xStart]=='╔')) {
            stack.push(new int[]{yStart-1, xStart, 1});
        }
        else if((yStart+1) < map[0].length &&(map[yStart+1][xStart]=='║'||map[yStart+1][xStart]=='╝'||map[yStart+1][xStart]=='╚')) {
            stack.push(new int[]{yStart+1, xStart, 1});
        }
        else if((xStart-1) >-1 && (map[yStart][xStart-1]=='═'||map[yStart][xStart-1]=='╔'||map[yStart][xStart-1]=='╚')) {
            stack.push(new int[]{yStart, xStart-1, 1});
        }
        else if((xStart +1) < map[0].length && (map[yStart][xStart+1]=='═'||map[yStart][xStart+1]=='╗'||map[yStart][xStart+1]=='╝')) {
            stack.push(new int[]{yStart, xStart+1, 1});
        }
        //here comes the overflow
        while(!stack.isEmpty()){
            int[] current = stack.pop();
            int y = current[0];
            int x = current[1];
            int i = current[2];

            lut[y][x] = i;
            //char c = map[y][x];
            //System.out.println("Currently@: " + c + " lut: " + lut[y][x]);
            //need to look at where we currently are
            if(map[y][x]=='║'||map[y][x]=='╝'||map[y][x]=='╚') {
                if((y-1) >-1 && ((map[y-1][x] == '║' || map[y-1][x] == '╗' || map[y-1][x] == '╔')) && lut[y-1][x] == -1)
                    stack.push(new int[]{y-1, x, i+1});
            }
            if(map[y][x]=='║'|| map[y][x]=='╗'|| map[y][x]=='╔') {
                if((map[y+1][x] == '║' || map[y+1][x] == '╝' || map[y+1][x] == '╚')&& lut[y+1][x]== -1)
                    stack.push(new int[]{y+1, x, i+1});
            }
            if(map[y][x]=='═'||map[y][x]=='╗'||map[y][x]=='╝') {
                if((x-1) >-1 && ((map[y][x-1] == '═' || map[y][x-1] == '╔' || map[y][x-1] == '╚')) && lut[y][x-1]== -1)
                    stack.push(new int[]{y, x-1, i+1});
            }
            if(map[y][x]=='═'||map[y][x]=='╔'||map[y][x]=='╚') {
                if((map[y][x+1] == '═' || map[y][x+1] == '╗' || map[y][x+1] == '╝')&& lut[y][x+1]== -1)
                    stack.push(new int[]{y, x+1, i+1});
            }
        }
    }
    private void floodFill(char[][] big){
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0,0});
        big[0][0] = 'o';
        while(!stack.isEmpty()){
            int[] current = stack.pop();
            int y = current[0];
            int x = current[1];
            big[y][x] = 'o';
            if(y-1 > -1 && big[y-1][x] == 'i')
                stack.push(new int[]{y-1,x});
            if(y+1 < big.length && big[y+1][x] == 'i')
                stack.push(new int[]{y+1,x});
            if(x-1 > -1 && big[y][x-1] == 'i')
                stack.push(new int[]{y,x-1});
            if(x+1 < big[0].length && big[y][x+1] == 'i')
                stack.push(new int[]{y,x+1});
        }
    }
    private int countMinus1(int[][]lut){
        int count = 0;
        for(int[] l : lut){
            for(int i : l){
                if(i == -1)
                    count++;
            }
        }
        return count;
    }

    private char[][] supersizeArray(char[][] map, int[][] lut){
        char[][] big = new char[lut.length*3][lut[0].length*3];
        for(char[] c : big){
            Arrays.fill(c,'i');
        }
        for(int y = 0; y < lut.length; y++){
            for(int x = 0; x <lut[0].length; x++){
                if(lut[y][x]!=-1){
                    //now we fill
                    char c = map[y][x];
                    if(c == 'S')
                        c=startPipe;
                    switch (c) {
                        case '║':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'x';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'i';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'i';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'x';big[y*3+2][x*3+2] = 'i';
                                    break;
                        case '═':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'i';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'x';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'x';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'i';big[y*3+2][x*3+2] = 'i';
                                    break;
                        case '╚':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'x';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'i';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'x';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'i';big[y*3+2][x*3+2] = 'i';
                                    break;
                        case '╝':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'x';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'x';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'i';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'i';big[y*3+2][x*3+2] = 'i';
                                    break;
                        case '╗':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'i';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'x';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'i';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'x';big[y*3+2][x*3+2] = 'i';
                                    break;
                        case '╔':   big[y*3][x*3] = 'i';big[y*3][x*3+1] = 'i';big[y*3][x*3+2] = 'i';
                                    big[y*3+1][x*3] = 'i';big[y*3+1][x*3+1] = 'x';big[y*3+1][x*3+2] = 'x';
                                    big[y*3+2][x*3] = 'i';big[y*3+2][x*3+1] = 'x';big[y*3+2][x*3+2] = 'i';
                                    break;
                    }
                }
            }
        }

        return big;
    }
}
