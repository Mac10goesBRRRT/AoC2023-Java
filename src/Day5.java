import java.io.File;
import java.io.FileNotFoundException;

import java.math.MathContext;
import java.sql.SQLOutput;
import java.util.*;

public class Day5 {
    //dest start, source start, length
    //Everything not mapped stays the same
    List<long[]> map;
    public void solveDay5() {
        System.out.print("!!!---Consider Commenting this out, it may take a while! ");
        long start2 = System.currentTimeMillis();
        boolean solved = false;
        map = inputReader("day5.txt");
        long[] border = map.get(0);
        border[0] += 2;
        for(int i = 1; i < border.length; i++)
            border[i] = border[i] + border[i-1];
        long[] seed = map.get(1);
        map = listSorter(map,1);
        long part1 = part1Solve(map, 0, 0);
        System.out.print("Day 5 Part 1: " + part1);
        //Preparation for part 2
        long lastSeed = 0;
        long lastLoc = 0;
        //Preparing Range:
        long[] SeedRange = map.get(1);
        for(int i = 1; i < SeedRange.length; i+=2){
            SeedRange[i] += SeedRange[i-1] - 1;
        }
        map = listSorter(map,0);
        for(long currentLoc = 0;  currentLoc<=3543571814L;){ //3543571814L
            long[] lowerRange = new long[7];
            long[] upperRange = new long[7];
            long reverseSeed = rangeLogic(currentLoc, (int) border[5], (int) border[6], map, lowerRange, upperRange, 0);
            for(int i = 5; i > 0; i--){
                reverseSeed = rangeLogic(reverseSeed, (int) border[i-1], (int) border[i], map, lowerRange, upperRange, 5-i);
            }
            reverseSeed = rangeLogic(reverseSeed, 2, (int) border[0], map, lowerRange, upperRange, 6);
            Arrays.sort(lowerRange);
            Arrays.sort(upperRange);
            if(reverseSeed == -1) {
                break;
            }
            //find the shortest current range
            int lowerRangeIndex = 0;
            while(lowerRange[lowerRangeIndex]==0 && lowerRangeIndex < lowerRange.length)
                lowerRangeIndex++;
            int upperRangeIndex = 0;
            while(upperRange[upperRangeIndex]==0) {
                upperRangeIndex++;
                if(upperRangeIndex==7){
                    upperRangeIndex=0;
                    currentLoc++;
                    break;
                }
            }
            //if we have the shortest ranges, we can calculate if the seed range was hit
            for(int i = 1; i < SeedRange.length; i+=2){
                if((reverseSeed >= SeedRange[i-1] && reverseSeed <= SeedRange[i]) || (reverseSeed + upperRange[upperRangeIndex] >= SeedRange[i-1] && reverseSeed + upperRange[upperRangeIndex] <= SeedRange[i])){
                    map = listSorter(map,1);
                    System.out.println("possible seed: " + reverseSeed + " @loc: " + currentLoc + " minimum: " + (currentLoc-lowerRange[lowerRangeIndex]) + " maximum: " + (currentLoc + upperRange[upperRangeIndex]));
                    long part2 = part1Solve(map, SeedRange[i-1], SeedRange[i]);
                    long end2 = System.currentTimeMillis();
                    System.out.println(", Day 5 Part 2: " + part2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
                    return;
                }
            }
            currentLoc += upperRange[upperRangeIndex];
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

    private List<long[]> listSorter(List<long[]> map, int index){
        long[] key = map.get(0);
        int toIndex = 2;
        int fromIndex = 2;
        for(int i = 0; i < key.length; i++){
            toIndex = (int)key[i];
            List<long[]> sublist = map.subList(fromIndex, toIndex);
            sublist.sort(Comparator.comparingLong(arr -> arr[index]));
            for(int j = fromIndex, k = 0; j < toIndex; j++,k++){
                map.set(j, sublist.get(k));
            }
            fromIndex = toIndex;
        }
        return map;
    }

    private long rangeLogic(long input, int rangeFrom, int rangeTo, List<long[]> map, long[] lowerRange,long[] upperRange, int rangePos){
        List<long[]> sublist = map.subList(rangeFrom, rangeTo);
        if(input < sublist.get(0)[0]) {
            lowerRange[rangePos] = input;
            upperRange[rangePos] = sublist.get(0)[0] - input;
            return input;
        }
        if(input > sublist.get(rangeTo-rangeFrom-1)[0]+sublist.get(rangeTo-rangeFrom-1)[2] - 1) {
            lowerRange[rangePos] = sublist.get(rangeTo-rangeFrom-1)[1]+sublist.get(rangeTo-rangeFrom-1)[2]; // may be 1 or 2
            upperRange[rangePos] = Long.MAX_VALUE;
            return input;
        }
        for(int i = 0;i<sublist.size();i++){
            long[] listEntry = sublist.get(i);
            if(listEntry[0] <= input && listEntry[0]+listEntry[2]-1 >= input){
                lowerRange[rangePos] = input - listEntry[0];
                upperRange[rangePos] = listEntry[2] - (input - listEntry[0]);
                return input - listEntry[0] + listEntry[1];
            }
        }
        return input; //if not in any list, it should stay the same
    }
    private long forwardRangeLogic(long input, int rangeFrom, int rangeTo, List<long[]> map){
        List<long[]> sublist = map.subList(rangeFrom, rangeTo);
        if(input < sublist.get(0)[1])
            return input;
        if(input > sublist.get(rangeTo-rangeFrom-1)[1]+sublist.get(rangeTo-rangeFrom-1)[2] - 1)
            return input;
        for(int i = 0;i<sublist.size();i++){
            long[] listEntry = sublist.get(i);
            if(listEntry[1] <= input && listEntry[1]+listEntry[2]-1 >= input){
                return (input - listEntry[1]) + listEntry[0];
            }
        }
        return input;
    }
    private long part1Solve(List<long[]> map, long start, long fin){
        long[] border = map.get(0);
        long[] seed = map.get(1);
        long lowLoc = Long.MAX_VALUE;
        long lowSeed = Long.MAX_VALUE;
        boolean arrMode = false;
        long currseed;
        if(start == fin && start == 0){
            arrMode = true;
            fin = seed.length;
        }
        for(long s = start; s < fin; s++){
            if(arrMode)
                currseed = seed[(int) s];
            else
                currseed = s;
            for(int i = 0; i < 7; i++){
                if(i-1 == -1)
                    currseed = forwardRangeLogic(currseed, 2, (int) border[i], map);
                else
                    currseed = forwardRangeLogic(currseed, (int) border[i-1], (int) border[i], map);
            }
            lowSeed = (currseed<lowLoc)? s : lowSeed;
            lowLoc = Math.min(currseed, lowLoc);
        }
        return lowLoc;
    }
}
