import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day5 {
    //dest start, source start, length
    //Everything not mapped stays the same
    //long[] seed = new long[]{28965817L,302170009L,1752849261L,48290258L,804904201L,243492043L,2150339939L,385349830L,1267802202L,350474859L,2566296746L,17565716L,3543571814L,291402104L,447111316L,279196488L,3227221259L,47952959L,1828835733L,9607836L};
    long[] seed = {79,14,55,13};
    List<long[]> map;
    public void solveDay5() {
        map = inputReader();
        int h= 5*5;
    }

    private List<long[]> inputReader(){
        List<long[]> map = new ArrayList<>();
        File file = new File("./input/day5/sampleInput.txt");
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
