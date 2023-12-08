import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    public void solveDay7(){
        List<Card> cardList = getInput();
        cardList.sort(null);
        for(Card c : cardList)
            System.out.println(c.getValue() + " " + c.getPower());
        int sum1 = 0;
        for(int i = 0 ; i < cardList.size(); i++){
            sum1 += cardList.get(i).getPower() * (i+1);
        }
        System.out.println(sum1);

    }

    private List<Card> getInput(){
        List<Card> cardList = new ArrayList<>();
        Scanner myReader;
        try {
            myReader = new Scanner(new File("./input/day7/sampleInput.txt"));
            int gameCounter = 1;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String s = data.substring(0,5);
                int value = Integer.parseInt(data.substring(5).trim());
                cardList.add(new Card(s,value));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return cardList;
    }
}
