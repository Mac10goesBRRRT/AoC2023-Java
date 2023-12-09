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
                int[] numInt = new int[numString.length+1];
                for(int i = 0; i < numString.length; i++)
                    numInt[i] = Integer.parseInt(numString[i]);
                sum1 += calcDiff(numInt)[numInt.length-1];
            }
            myReader = new Scanner(file);
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(sum1);
    }
    private int[] calcDiff(int[] arr){
        int[] temp = new int[arr.length-1];
        for(int i = 0 ; i < arr.length-2; i++){
            temp[i] = arr[i+1] - arr[i];
        }
        if(!isZero(temp))
            calcDiff(temp);
        else
            arr[arr.length-1] = arr[arr.length-2] + temp[temp.length-1];
        arr[arr.length-1] = arr[arr.length-2] + temp[temp.length-1];
        return arr;
    }

    private boolean isZero(int[] temp) {
        for(int i = 0; i < temp.length - 1; i++)
            if(temp[i]!=0)
                return false;
        return true;
    }
}
