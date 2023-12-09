import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day9 {
    int sum1, sum2;
    public void solveDay9() {
        try {
            File file = new File("./input/day9/day9.txt");
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] numString = data.split(" ");
                int[] numInt = new int[numString.length+2];
                for(int i = 0; i < numString.length; i++)
                    numInt[i+1] = Integer.parseInt(numString[i]);
                int[] result = calcDiff(numInt);
                sum1 += result[result.length-1];
                sum2 += result[0];
            }
            myReader = new Scanner(file);
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Part 1: " + sum1 + " Part 2: " + sum2);
    }
    private int[] calcDiff(int[] arr){
        int[] temp = new int[arr.length-1];
        for(int i = 1 ; i < arr.length-2; i++){
            temp[i] = arr[i+1] - arr[i];
        }
        if(!isZero(temp))
            calcDiff(temp);
        else {
            arr[arr.length - 1] = arr[arr.length - 2] + temp[temp.length - 1];
            arr[0] = arr[1] - temp[1];
        }
        arr[arr.length-1] = arr[arr.length-2] + temp[temp.length-1];
        arr[0] = arr[1] - temp[0];
        return arr;
    }

    private boolean isZero(int[] temp) {
        for(int i = 0; i < temp.length - 1; i++)
            if(temp[i]!=0)
                return false;
        return true;
    }
}
