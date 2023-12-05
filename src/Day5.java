import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    //dest start, source start, length
    //Everything not mapped stays the same
    List<long[]> map;
    public void solveDay5() {
        map = inputReader("sampleInput.txt");
        int h= 5*5;
    }

    /**
     * Reads the Day 5 Input
     * @param filename filename in folder
     * @return ArrayList of the File, [0] is the cut of points for maps, [1] is the seeds. Rest is input-dependant
     */
    private List<long[]> inputReader(String filename){
        List<long[]> map = new ArrayList<>();
        File file = new File("./input/day5/" + filename);
        map.add(new long[7]);
        try {
        Scanner myReader = new Scanner(file);
        int lineCount = 0;
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            lineCount++;
            if(data.startsWith("seeds:")){
                String[] str = data.split(": ");
                str = str[1].split(" ");
                long[] a = new long[str.length];
                for(int i = 0; i < str.length; i++){
                    a[i] = Long.parseLong(str[i]);
                }
                map.add(a);
            } else if(data.startsWith("seed-")){
                lineCount=-2;
            } else if(data.startsWith("soil-")){
                long[] a = map.get(0);
                a[0] = lineCount;
                lineCount=-2;
            } else if(data.startsWith("ferti")){
                long[] a = map.get(0);
                a[1] = lineCount;
                lineCount=-2;
            } else if(data.startsWith("water")){
                long[] a = map.get(0);
                a[2] = lineCount;
                lineCount=-2;
            } else if(data.startsWith("light")){
                long[] a = map.get(0);
                a[3] = lineCount;
                lineCount=-2;
            } else if(data.startsWith("tempe")){
                long[] a = map.get(0);
                a[4] = lineCount;
                lineCount=-2;
            } else if(data.startsWith("humid")){
                long[] a = map.get(0);
                a[5] = lineCount;
                lineCount=-2;
            } else if(!data.isEmpty()){
                String[] str = data.split(" ");
                long[] num = new long[str.length];
                for(int i = 0; i < str.length; i++) {
                    num[i] = Long.parseLong(str[i]);
                }
                map.add(num);
            }
        }
        long[] a = map.get(0);
        a[6] = lineCount+2;
        myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
