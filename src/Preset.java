import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Preset {
    File file;
    Scanner myReader;
    public void setFile(String ifile){
        this.file = new File("./input/day2" + ifile);
    }
    public void Day2Part1(String ifile) {
        {
            try {
                this.setFile(ifile);
                myReader = new Scanner(file);
                while (myReader.hasNextLine()) {
                    String data = myReader.nextLine();
                    System.out.println(data);

                }


                myReader.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
