import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day4 {
    File file;
    Scanner myReader;

    public void setFile(String iFile) {
        this.file = new File("./input/day4/" + iFile);
    }
    public void solveDay4() {
        long start2 = System.currentTimeMillis();
        setFile("day4.txt");
        inputObj iObj = Day4Input("day4.txt");
        int sum1 = Part1(iObj);
        int sum2 = Part2(iObj);
        long end2 = System.currentTimeMillis();
        System.out.println("Day 4 Part 1: " + sum1 + ", Day 4 Part 2: " + sum2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    public inputObj Day4Input(String iFile) {
        inputObj iObj = new inputObj();
        List<int[]> input = new ArrayList<int[]>();
        try {
            this.setFile(iFile);
            myReader = new Scanner(file);
            int gameCounter = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] gameData = data.split(":");
                String[] result = gameData[1].split("\\|");
                result[0] = result[0].trim();
                result[1] = result[1].trim();
                String[] winners = result[0].split("\\s+");
                String[] tries = result[1].split("\\s+");
                //Making the Array longer to include car value and number of card copies
                int[] arr = new int[3 + winners.length + tries.length];
                arr[0] = gameCounter;
                //For part 2
                arr[1] = 1;
                gameCounter++;
                for(int i = 0 ; i < winners.length; i++){
                    arr[i + 3] = Integer.parseInt(winners[i]);
                }
                for(int i = 0 ; i < tries.length; i++){
                    arr[i + 3 + winners.length] = Integer.parseInt(tries[i]);
                }
                input.add(arr);
                iObj.setNumWin(winners.length);
                iObj.setNumTries(tries.length);
            }
            iObj.setInput(input);

            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return iObj;
    }

    private int Part1(inputObj input){
        List<int[]> list = input.getInput();
        int wins = input.getNumWin();
        int sum = 0;
        for (int[] ints : list) {
            int num = getNumEquals(ints, wins);
            sum += num;
        }
        return sum;
    }

    private int Part2(inputObj input){
        List<int[]> list = input.getInput();
        int entries = list.size();
        int[] key = new int[entries];
        Arrays.fill(key,1);
        for(int[] ints : list){
            int times = key[ints[0]-1];
            for(int i = 0; i < ints[2]; i++){
                if(i + ints[0] < key.length) {
                    //Idea: add the cards the number of times the get copied
                    key[i + ints[0]] += times;
                }
            }
        }
        return Arrays.stream(key).sum();
    }

    private int getNumEquals(int[] ints, int divIndex) {
        int count = 0;
        int valueOfSet = 0;
        for(int i = 3; i < divIndex + 3; i++){
            for(int j = divIndex + 3; j < ints.length; j++){
                if(ints[i]==ints[j]) {
                    count++;
                }
            }
        }
        if(count>0)
            valueOfSet = 1 << count-1;
        ints[2] = count;
        //System.out.println(Arrays.toString(ints));
        return valueOfSet;
    }


    private static class inputObj {
        int numWin;
        int numTries;
        List<int[]> input;
        public void setNumTries(int numTries) {
            this.numTries = numTries;
        }
        public void setNumWin(int num){
            numWin=num;
        }
        public void setInput(List<int[]> list){
            input = list;
        }

        public int getNumWin() {
            return numWin;
        }

        public int getNumTries() {
            return numTries;
        }

        public List<int[]> getInput() {
            return input;
        }
    }
}
