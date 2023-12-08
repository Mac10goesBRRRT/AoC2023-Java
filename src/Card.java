import java.util.*;
import java.util.stream.Collectors;

public class Card implements Comparable<Card>{
    private String value;

    public int getPower() {
        return power;
    }

    private int power;
    public Card(String value, int power){
        this.value = value;
        this.power=power;
    }
    public String getValue(){
        return value;
    }
    @Override
    public int compareTo(Card o) {
        int thisScore = calculateScore(this.value);
        int otherScore = calculateScore(o.getValue());
        int returnValue = Integer.compare(thisScore, otherScore);
        if(returnValue == 0)
            returnValue = stringSort(this.value, o.getValue());
        return returnValue;
    }

    private int calculateScore(String value) {
        Map<String, Long> result = Arrays.stream(value.split("")).map(String::toLowerCase).collect(Collectors.groupingBy(s->s, LinkedHashMap::new, Collectors.counting()));
        List<Map.Entry<String, Long>> sortedEntries = result.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .toList();
        //System.out.println(result);
        int retValue = Math.toIntExact(sortedEntries.get(0).getValue());
        if(retValue > 3)
             return retValue + 1;
        if(retValue==3 && sortedEntries.get(1).getValue()==2)
            return 4;
        else if(retValue==3)
            return 3;
        else if(retValue==2 && sortedEntries.get(1).getValue()==2)
            return 2;
        else if(retValue==2)
            return 1;
        return 0;
    }

    private int stringSort (String s1, String s2){
        String customOrder = "23456789TJQKA";
        //String customOrder = "J23456789TQKA";
        int value = 0;
        int i = 0;
        do{
            int index1 = customOrder.indexOf(s1.charAt(i));
            int index2 = customOrder.indexOf(s2.charAt(i));
            value = Integer.compare(index1, index2);
            i++;
        }while(value == 0 && i < s1.length());

        return value;
    }

}
