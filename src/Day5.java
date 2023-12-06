import java.io.File;
import java.io.FileNotFoundException;

import java.math.MathContext;
import java.util.*;

public class Day5 {
    //dest start, source start, length
    //Everything not mapped stays the same
    List<long[]> map;
    public void solveDay5() {
        boolean solved = false;
        map = inputReader("sampleInput.txt");
        map = listSorter(map);
        long[] border = map.get(0);
        border[0] += 2;
        for(int i = 1; i < border.length; i++)
            border[i] = border[i] + border[i-1];
        System.out.println(Arrays.toString(border));
        long[] seed = map.get(1);
        System.out.println("locating seeds: " + Arrays.toString(seed));
        long lastSeed = 0;
        long lastLoc = 0;
        for(long currentLoc = 1; currentLoc<=100;){ //3543571814L
            long[] ranges = new long[7];
            long reverseSeed = rangeLogic(currentLoc, (int) border[5], (int) border[6], map, ranges, 0);
            reverseSeed = rangeLogic(reverseSeed, (int) border[4], (int) border[5], map, ranges, 1);
            reverseSeed = rangeLogic(reverseSeed, (int) border[3], (int) border[4], map, ranges, 2);
            reverseSeed = rangeLogic(reverseSeed, (int) border[2], (int) border[3], map, ranges, 3);
            reverseSeed = rangeLogic(reverseSeed, (int) border[1], (int) border[2], map, ranges, 4);
            reverseSeed = rangeLogic(reverseSeed, (int) border[0], (int) border[1], map, ranges, 5);
            reverseSeed = rangeLogic(reverseSeed, 2, (int) border[0], map, ranges, 6);
            System.out.println("For location: " + currentLoc + ", seed: " + reverseSeed);
            Arrays.sort(ranges);
            if(reverseSeed == -1) {
                System.out.println("For location: " + currentLoc + ", seed: " + reverseSeed);
                break;
            }
            int k = 0;
            while(ranges[k]==0) {
                k++;
                if(k==7){
                    k=0;
                    currentLoc++;
                    break;
                }
            }
            for(long s : seed){
                //logik anpassen, der seed muss zwischen dem Letzen seed und der jetzigen starting loc sein
                long upperRangeLimit = currentLoc - 1;
                if(lastSeed != 0 && lastSeed <= s && reverseSeed >= s){
                    System.out.println("Found location between " + lastLoc + " and " + upperRangeLimit + " seed " + s);
                    solved = true;
                }
            }
            //if(solved)
            //    break;
            lastLoc = currentLoc;
            currentLoc += ranges[k];
            //currentLoc++;
            lastSeed = reverseSeed;
        }
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

    private List<long[]> listSorter(List<long[]> map){
        long[] key = map.get(0);
        int toIndex = 2;
        int fromIndex = 2;
        for(int i = 0; i < key.length; i++){
            toIndex += (int)key[i];
            //System.out.println("Sort from: " + fromIndex + " to: " + toIndex);
            List<long[]> sublist = map.subList(fromIndex, toIndex);
            sublist.sort(Comparator.comparingLong(arr -> arr[0]));
            for(int j = fromIndex, k = 0; j < toIndex; j++,k++){
                map.set(j, sublist.get(k));
            }
            fromIndex = toIndex;
        }
        return map;
    }

    private long rangeLogic(long input, int rangeFrom, int rangeTo, List<long[]> map, long[] ranges, int rangePos){
        List<long[]> sublist = map.subList(rangeFrom, rangeTo);
        if(input < sublist.get(0)[0])
            return input;
        if(input > sublist.get(rangeTo-rangeFrom-1)[0]+sublist.get(rangeTo-rangeFrom-1)[2] - 1)
            return input;
        for(int i = 0;i<sublist.size();i++){
            long[] listEntry = sublist.get(i);
            if(listEntry[0] <= input && listEntry[0]+listEntry[2]-1 >= input){
                ranges[rangePos] = listEntry[2] - (input - listEntry[0]);
                return input - listEntry[0] + listEntry[1];
            }
        }
        return input; //if not in any list, it should stay the same
    }
}
