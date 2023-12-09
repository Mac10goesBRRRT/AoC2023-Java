import java.util.*;
import java.util.stream.Collectors;

public record Card(String value, int power) implements Comparable<Card> {

    @Override
    public int compareTo(Card o) {
        int thisScore = calculateScore(this.value);
        int otherScore = calculateScore(o.value());
        int returnValue = Integer.compare(thisScore, otherScore);
        if (returnValue == 0)
            returnValue = stringSort(this.value, o.value());
        return returnValue;
    }

    public int calculateScore(String value) {
        Map<String, Long> result = Arrays.stream(value.split("")).map(String::toLowerCase).collect(Collectors.groupingBy(s -> s, LinkedHashMap::new, Collectors.counting()));
        List<Map.Entry<String, Long>> sortedEntries = result.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .toList();
        //System.out.print(result);
        int retValue = Math.toIntExact(sortedEntries.get(0).getValue());
        int numJokers = 0;
        int indexJ = -1;
        for (int i = 0; i < sortedEntries.size(); i++) {
            if (sortedEntries.get(i).getKey().equals("j")) {
                indexJ = i;
                break;
            }
        }
        if (indexJ > -1)
            numJokers = Math.toIntExact(sortedEntries.get(indexJ).getValue());
        if (retValue == 5 || (retValue == 3 && numJokers == 2) || numJokers == 4 || (retValue == 4 && numJokers == 1) || (numJokers == 3 && sortedEntries.get(1).getValue() == 2)) //5, cant upgrade
            return 6;
        if (retValue == 4 || (retValue == 3 && numJokers == 1) || numJokers == 3 || checkQuad(sortedEntries)) //4, can be upgraded to 5 with any joker, and we simply add the number of Jokers (1 or none)
            return 5;
        if ((retValue == 3 && sortedEntries.get(1).getValue() == 2) || (retValue == 2 && numJokers == 1 && sortedEntries.get(1).getValue() == 2)) //Full House, if we have a Triple and a Pair. Or we have  2 + 2 (so a pair of 2 and 2 jokers)
            return 4;
        else if (retValue == 3 || numJokers == 2 || (retValue == 2 && numJokers == 1)) //Triple, or 2 Jokers and 1 random card
            return 3;
        else if ((retValue == 2 && sortedEntries.get(1).getValue() == 2)) //2 Pairs !!! 1 Pair and 1 joker makes a Triple, 1 Pair and 2 Jokers make 4
            return 2;
        else if (retValue == 2 || numJokers == 1) //1 Pair, or 1 Joker and any other card.
            return 1;
        return 0;
    }

    private boolean checkQuad(List<Map.Entry<String, Long>> sortedEntries) {
        if (sortedEntries.get(0).getValue() == 2 && sortedEntries.get(1).getValue() == 2)
            return (sortedEntries.get(0).getKey().equals("j") || sortedEntries.get(1).getKey().equals("j"));
        return false;
    }

    private int stringSort(String s1, String s2) {
        //String customOrder = "23456789TJQKA";
        String customOrder = "J23456789TQKA";
        int value;
        int i = 0;
        do {
            int index1 = customOrder.indexOf(s1.charAt(i));
            int index2 = customOrder.indexOf(s2.charAt(i));
            value = Integer.compare(index1, index2);
            i++;
        } while (value == 0 && i < s1.length());

        return value;
    }

}
