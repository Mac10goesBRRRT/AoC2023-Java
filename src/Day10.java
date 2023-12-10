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

    int colums, rows;
    int xStart, yStart;
    private char[][] getInput() {
        char[][] c = new char[0][];
        try {
            Path path = Path.of("./input/day10/sampleInput.txt");
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
                data = data.replaceAll("\\.", " ")
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
        System.out.println("done");
        for(int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (lut[i][j] > -1) {
                    if(map[i][j]=='S')
                        System.out.print(ANSI_RED + map[i][j] + ANSI_RESET);
                    else
                        System.out.print(ANSI_GREEN + map[i][j] + ANSI_RESET);
                }else
                    System.out.print(map[i][j]);
            }
            System.out.println();
        }
        double sum1 = lookUp(lut);
        System.out.println(Math.ceil(sum1/2));
    }

    private int lookUp(int[][] lut) {
        int res = -1;
        res = Math.max(lut[yStart - 1][xStart], res);
        res = Math.max(lut[yStart + 1][xStart], res);
        res = Math.max(lut[yStart][xStart - 1], res);
        res = Math.max(lut[yStart][xStart + 1], res);
        return res;
    }

    private void loopStart(char[][]map, int[][]lut){
        lut[yStart][xStart] = 0;
        depthFirstSearch(map,lut,yStart,xStart);
    }

    private void pathfindW(char[][] map, int[][] lut, int y, int x, int i) {
        lut[y][x] = i;
        if(map[y][x] == '═' && lut[y][x+1]== -1)
            pathfindW(map, lut, y, x+1, i+1);
        else if(map[y][x] == '╗' && lut[y+1][x]== -1)
            pathfindN(map, lut, y+1, x, i+1);
        else if(map[y][x] == '╝' && lut[y-1][x]== -1)
            pathfindS(map, lut, y-1, x, i+1);
        else return;
    }

    private void pathfindE(char[][] map, int[][] lut, int y, int x, int i) {
        lut[y][x] = i;
        if(map[y][x] == '═' && lut[y][x-1]== -1)
            pathfindE(map, lut, y, x-1, i+1);
        else if(map[y][x] == '╔' && lut[y+1][x]== -1)
            pathfindN(map, lut, y+1, x, i+1);
        else if(map[y][x] == '╚' && lut[y-1][x]== -1)
            pathfindS(map, lut, y-1, x, i+1);
        else return;
    }

    private void pathfindN(char[][] map, int[][] lut, int y, int x, int i) {
        lut[y][x] = i;
        if(map[y][x] == '║' && lut[y+1][x]== -1)
            pathfindN(map, lut, y+1, x, i+1);
        else if(map[y][x] == '╝' && lut[y][x-1]== -1)
            pathfindE(map, lut, y, x-1, i+1);
        else if(map[y][x] == '╚' && lut[y][x+1]== -1)
            pathfindW(map, lut, y, x+1, i+1);
        else return;
    }

    private void pathfindS(char[][] map, int[][] lut, int y, int x, int i) {
        lut[y][x] = i;
        if(map[y][x] == '║' && lut[y-1][x] == -1)
            pathfindS(map, lut, y-1, x, i+1);
        else if(map[y][x] == '╗' && lut[y][x-1] == -1)
            pathfindE(map, lut, y, x-1, i+1);
        else if(map[y][x] == '╔' && lut[y][x+1] == -1)
            pathfindW(map, lut, y, x+1, 1+1);
        else return;
    }

    private void depthFirstSearch(char[][] map, int[][] lut, int startY, int startX){
        Deque<int[]> stack = new ArrayDeque<>();
        //logic to find the 2 starting pipes
        if((yStart-1) >-1 && (map[yStart-1][xStart]=='║'||map[yStart-1][xStart]=='╗'||map[yStart-1][xStart]=='╔')) {
            stack.push(new int[]{yStart-1, xStart, 1});
        }
        else if(map[yStart+1][xStart]=='║'||map[yStart+1][xStart]=='╝'||map[yStart+1][xStart]=='╚') {
            stack.push(new int[]{yStart+1, xStart, 1});
        }
        else if(map[yStart][xStart-1]=='═'||map[yStart][xStart-1]=='╔'||map[yStart][xStart-1]=='╚') {
            stack.push(new int[]{yStart, xStart-1, 1});
        }
        else if(map[yStart][xStart+1]=='═'||map[yStart][xStart+1]=='╗'||map[yStart][xStart+1]=='╝') {
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
                if((map[y-1][x] == '║' || map[y-1][x] == '╗' || map[y-1][x] == '╔') && lut[y-1][x] == -1)
                    stack.push(new int[]{y-1, x, i+1});
            }
            if(map[y][x]=='║'|| map[y][x]=='╗'|| map[y][x]=='╔') {
                if((map[y+1][x] == '║' || map[y+1][x] == '╝' || map[y+1][x] == '╚')&& lut[y+1][x]== -1)
                    stack.push(new int[]{y+1, x, i+1});
            }
            if(map[y][x]=='═'||map[y][x]=='╗'||map[y][x]=='╝') {
                if((map[y][x-1] == '═' || map[y][x-1] == '╔' || map[y][x-1] == '╚')&& lut[y][x-1]== -1)
                    stack.push(new int[]{y, x-1, i+1});
            }
            if(map[y][x]=='═'||map[y][x]=='╔'||map[y][x]=='╚') {
                if((map[y][x+1] == '═' || map[y][x+1] == '╗' || map[y][x+1] == '╝')&& lut[y][x+1]== -1)
                    stack.push(new int[]{y, x+1, i+1});
            }
        }
    }
}
