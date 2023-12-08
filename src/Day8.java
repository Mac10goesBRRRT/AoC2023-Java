import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Day8 {
    String directions;
    HashMap<String,Node> map = new HashMap<>();
    ArrayList<String> startLoc = new ArrayList<>();

    public void solveDay8(){
        getInput();
        long start2 = System.currentTimeMillis();
        nodeConnect();
        int sum1 = solveSingleMaze("AAA");
        long sum2 = solveMultiMaze();
        long end2 = System.currentTimeMillis();
        System.out.println("Day 4 Part 1: " + sum1 + ", Day 4 Part 2: " + sum2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    private long solveMultiMaze(){
        ArrayList<Integer> nodeCounts = new ArrayList<>();
        for(String s : startLoc){
            nodeCounts.add(solveSingleMaze(s));
        }
        System.out.println(nodeCounts);
        long lcm = nodeCounts.get(0);
        /*
        for(int i = 1; i < nodeCounts.size(); i++){
            lcm = lcm(lcm, nodeCounts.get(i));
        }*/

        return lcm;
    }

    private long lcm(long a, long b){
        return (a*b)/gcd(a,b);
    }
    private long gcd(long a, long b){
        while(a != b){
            if(a > b)
                a -= b;
            else
                b -= a;
        }
        return a;
    }
    private void nodeConnect(){
        for (Map.Entry<String, Node> entry : map.entrySet()) {
            Node n = entry.getValue();
            String left = n.leftVal, right = n.rightVal;
            n.left = map.get(left);
            n.right = map.get(right);
        }
        System.out.println("Map linked");
    }

    private int solveSingleMaze(String sLoc){
        Node entry = map.get(sLoc);
        int nodesVisited = 0;
        int stringLoc = 0;
        while(entry.ident.charAt(2)!='Z'){
            char c = directions.charAt(stringLoc%directions.length());
            stringLoc++;
            if(c=='L')
                entry = entry.left;
            else
                entry = entry.right;
            nodesVisited++;
        }
        return nodesVisited;
    }

    private void getInput(){
        try {
            File file = new File("./input/day8/day8.txt");
            Scanner myReader = new Scanner(file);
            directions = myReader.nextLine();
            myReader.nextLine();
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if(data.charAt(2) == 'A')
                    startLoc.add(data.substring(0,3));
                Node newNode = new Node(data.substring(0, 3), data.substring(7, 10), data.substring(12, 15));
                //System.out.println(data.substring(0,3)+data.substring(7,10)+data.substring(12,15));
                map.put(data.substring(0,3), newNode);
            }
            myReader = new Scanner(file);
            myReader.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    private static class Node{
        private final String ident;
        Node left;
        String leftVal;
        Node right;
        String rightVal;

        private Node(String ident, String leftVal, String rightVal){
            this.ident = ident;
            this.leftVal = leftVal;
            this.rightVal = rightVal;
        }
    }
}
