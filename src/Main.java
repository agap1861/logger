
import java.io.File;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        test.logger();

    }

}
class Test {
    public void logger() {

        String path = "C:/Users/danil/Desktop/logs.txt";
        File file = new File(path);
        LogSource filesourse = Manager.getSourceFile(file);
        filesourse.readLogs();
        System.out.println(filesourse.gelAllStat());

       String dateStar = "2025-03-22";
       String dateEnd = "2025-03-24";
      var list = filesourse.filterDatesByRange(dateStar,dateEnd);
      for(Log log:list){
          System.out.println(log);
      }
        filesourse.saveStat();

    }


}


