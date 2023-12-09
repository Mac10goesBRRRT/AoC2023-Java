import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day7 {
    // 246591849, 246539389, 246628634 too low
    //NOT 246688347
    public void solveDay7(){
        List<Card> cardList = getInput();
        cardList.sort(null);
        //for(Card c : cardList)
            //System.out.println(c.getValue() + " " + c.getPower() + " " + (c.calculateScore(c.getValue())));
        int sum1 = 0;
        for(int i = 0 ; i < cardList.size(); i++){
            sum1 += cardList.get(i).power() * (i+1);
        }
        System.out.println(sum1);
    }

    private List<Card> getInput(){
        List<Card> cardList = new ArrayList<>();
        Scanner myReader;
        try {
            myReader = new Scanner(new File("./input/day7/day7.txt"));
            //int gameCounter = 1;
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
