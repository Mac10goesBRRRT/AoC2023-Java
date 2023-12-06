public class Day6 {
    int[] time1 = {60,94,78,82};
    long time2 = 60947882;
    int[] distance1 = {475,2138,1015,1650};
    long distance2 = 475213810151650L;

    int p1Prod = 1;


    public void solveDay6(){
        long start2 = System.currentTimeMillis();
        int  result1 = day6part1();
        long result2 = day6part2();
        long end2 = System.currentTimeMillis();
        System.out.println("Day 4 Part 1: " + result1 + ", Day 4 Part 2: " + result2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
        p1Prod = 1;
        start2 = System.currentTimeMillis();
        result2 = solveBoth();
        end2 = System.currentTimeMillis();
        System.out.println("Day 4 Part 1: " + p1Prod + ", Day 4 Part 2: " + result2 + ", Elapsed Time in milli seconds: " + (end2-start2) + "ms");
    }

    private int day6part1() {
        for(int race = 0; race < time1.length; race++){
            int success = 0;
            for(int i = 1; i < time1[race]-1; i++){
                if(i*(time1[race]-i) > distance1[race]){
                    success += 1;
                    //System.out.println("Race " + race + ": " + i*(time[race]-i));
                }
            }
            //System.out.println("Race " + race + " Results: " + success);
            p1Prod *= success;
        }
        return p1Prod;
    }
    private long day6part2() {
        long success = 0;
        for(long i = 7797052; i < time2 - 1 ; i++){
            if(i*(time2-i) > distance2)
                success++;
            //if(i%1000 == 0)
            //    System.out.println("iteration: " + i + ", successes: " + success);
        }
        return success;
    }
    private long day6QuadSolve(long time, long record){
        //i ist the speed or time held -i^2+time*i-d = 0 so the exact distance, d is unknown
        double sqrt = Math.sqrt(time * time + (-4) * record);
        double x1 = (-time + sqrt)/(-2);
        double x2 = (-time - sqrt)/(-2);
        return (long) (Math.floor(x2) - Math.floor(x1));
    }

    private long solveBoth(){
        for(int i = 0; i < time1.length; i++){
            p1Prod *= (int) day6QuadSolve((long)time1[i], (long)distance1[i]);
        }
        return day6QuadSolve(time2, distance2);
    }
}
