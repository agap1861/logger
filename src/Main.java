
import java.io.File;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Test test = new Test();
        test.logger();
//        Scanner scanner = new Scanner(System.in);
//        Manager manager = new Manager();
//        String path = "C:/Users/danil/Desktop/logs.txt";
//        File file = new File(path);

      /*  while (true) {
            print();
            int cmd = scanner.nextInt();
            switch (cmd) {
                case 1:
                    manager.readLogs(file);
                    System.out.println("Все логи на месте!");
                    continue;

                case 2:
                    manager.showStat();
                    continue;
                case 3:
                    System.out.println("Введите IP адрес: ");
                    String ip = scanner.next();
                    manager.getRequestsByIP(ip);
                    continue;
                case 4:
                    return;
            }

        }*/

    }
    public static void print() {
        System.out.println("1. Загрузить логи");
        System.out.println("2. Показать статистику");
        System.out.println("3. Найти все запросы по IP");
        System.out.println("4. Выйти");
    }
}
class Test {
    public void logger() {

        String path = "C:/Users/danil/Desktop/logs.txt";
        File file = new File(path);
        Manager manager = new Manager(file);
        manager.readLogs();
        System.out.println(manager.gelAllStat());


       String dateStar = "2025-03-22";
       String dateEnd = "2025-03-24";
      var list = manager.filterDatesByRange(dateStar,dateEnd);
      for(Log log:list){
          System.out.println(log);
      }
        manager.saveStat();

    }


}


