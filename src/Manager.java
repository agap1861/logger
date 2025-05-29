

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Manager {
    private File fileLogs;
    private List<Log> logs = new ArrayList<>();
    private Map<String, Integer> pages = new HashMap<>();
    private Map<String, Integer> ips = new HashMap<>();

    Manager(File fileLogs) {
        this.fileLogs = fileLogs;

    }


    public void readLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileLogs.getPath()))) {
            String line = "";
            while (reader.ready()) {
                line = reader.readLine();
                String[] split = line.split(" ");
                String data = split[0];
                String time = split[1];
                String ip = split[2];
                String page = split[3];
                Log log = new Log(data, time, ip, page);
                logs.add(log);
                addNewPageInMap(page);
                addNewIPInMap(ip);

            }
        } catch (IOException e) {
            throw new RuntimeException();

        }
    }

    private void addNewPageInMap(String page) {
        if (pages.containsKey(page)) {
            int oldValue = pages.get(page);
            Integer newValue = oldValue + 1;
            pages.replace(page, oldValue, newValue);

        } else {
            pages.put(page, 1);
        }


    }

    private void addNewIPInMap(String ip) {
        if (ips.containsKey(ip)) {
            int oldValue = ips.get(ip);
            Integer newValue = oldValue + 1;
            ips.replace(ip, oldValue, newValue);

        } else {
            ips.put(ip, 1);
        }
    }

    public void showStat() {
        if (logs.isEmpty()) {
            System.out.println("Сначала загрузи логи потом, будем считать");
            return;
        }
        System.out.println("Общее количество посещений: " + logs.size());

        System.out.println("Самая популярная страница: " + getThePopularPage().getName() + " ("
                + getThePopularPage().getMaxCount() + " посещения)");
        System.out.println("Уникальных IP: " + getUniqueIp());
        System.out.println("IP с наибольшим числом запросов: " + getThePopularIP().getName() + " (" +
                getThePopularIP().getMaxCount() + " запроса)");


    }

    private String totalNumberOfVisit() {//Метод для получения статистики, а не вывода в консоль
        return "Общее количество посещений: " + logs.size();
    }

    private String mostPopularPAge() {//Метод для получения статистики, а не вывода в консоль
        return "Самая популярная страница: " + getThePopularPage().getName() + " ("
                + getThePopularPage().getMaxCount() + " посещения)";
    }

    private String uniqueIp() {//Метод для получения статистики, а не вывода в консоль
        return "Уникальных IP: " + getUniqueIp();
    }

    private String ipMostRequests() {//Метод для получения статистики, а не вывода в консоль
        return "IP с наибольшим числом запросов: " + getThePopularIP().getName() + " (" +
                getThePopularIP().getMaxCount() + " запроса)";
    }

    private List<String> gelAllStat() {

        List<String> stat = new ArrayList<>();
        stat.add(totalNumberOfVisit());
        stat.add(mostPopularPAge());
        stat.add(uniqueIp());
        stat.add(ipMostRequests());
        return stat;
    }


    public void extraFunctions() {
        Map<String, Integer> group = groupLogsByDate();
        for (String date : group.keySet()) {
            System.out.println(date + ": " + group.get(date) + " запроса");
        }
        Map<String, Integer> hours = countRequestsInHour();
        for (String hour : hours.keySet()) {
            System.out.println(hour + ":00 - " + hours.get(hour) + " запросов");
        }

    }

    private int getUniqueIp() {
        List<String> uniqueIp = new ArrayList<>();
        int unique = 0;
        for (Log log : logs) {
            String ip = log.getIp();
            if (uniqueIp.contains(ip)) {
                unique++;
            } else {
                uniqueIp.add(ip);
            }
        }
        return unique;
    }

    private StatisticMax getThePopularPage() {
        int maxCount = 0;
        String maxPage = "";
        for (String name : pages.keySet()) {
            if (pages.get(name) > maxCount) {
                maxCount = pages.get(name);
                maxPage = name;
            }
        }
        return new StatisticMax(maxPage, maxCount);

    }

    private StatisticMax getThePopularIP() {
        int maxCount = 0;
        String maxIP = "";
        for (String name : ips.keySet()) {
            if (ips.get(name) > maxCount) {
                maxCount = ips.get(name);
                maxIP = name;
            }
        }
        return new StatisticMax(maxIP, maxCount);

    }

    public void getRequestsByIP(String ip) {
        System.out.println("Запросы с IP " + ip + ":");
        for (Log log : logs)
            if (log.getIp().equals(ip)) {

                System.out.println(log.getDate() + " " + log.getTime() + " " + log.getPage());
            }
    }

    private Map<String, Integer> groupLogsByDate() {
        Map<String, Integer> group = new HashMap<>();
        for (Log log : logs) {
            if (group.containsKey(log.getDate())) {
                int oldValue = group.get(log.getDate());
                int newValue = oldValue + 1;
                group.replace(log.getDate(), oldValue, newValue);
            } else {
                group.put(log.getDate(), 1);
            }
        }
        return group;
    }

    private Map<String, Integer> countRequestsInHour() {
        Map<String, Integer> hours = new HashMap<>();
        for (Log log : logs) {
            if (hours.containsKey(log.extractHours())) {
                int oldValue = hours.get(log.extractHours());
                int newValue = oldValue + 1;
                hours.replace(log.extractHours(), oldValue, newValue);
            } else {
                hours.put(log.extractHours(), 1);
            }
        }
        return hours;
    }

    public List<Log> filterDatesByRange(String dateStart, String dateEnd) {
        TimeComparator timeComparator = new TimeComparator();
        List<Log> range = new ArrayList<>();
        for (Log log : logs) {
            if (log.getDate().compareTo(dateStart) >= 0 && log.getDate().compareTo(dateEnd) <= 0) {
                range.add(log);
            }
        }
        range.sort(timeComparator);
        return range;
    }

    public void saveStat() {


        try (BufferedWriter writer = new BufferedWriter(new FileWriter(initFileStat().getPath()))) {


            List<String> stats = gelAllStat();
            for (String stat : stats) {
                String line = stat;
                writer.write(line);
                writer.newLine();
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File initFileStat() {
        String nameOfFile = "stat";
        File fileStat = null;
        try {
            fileStat = File.createTempFile(nameOfFile, ".txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!fileStat.exists()) {
            try {
                fileStat.createNewFile();

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
        return fileStat;
    }


}

class StatisticMax {
    private String name;
    private int maxCount;

    public StatisticMax(String name, int maxCount) {
        this.name = name;
        this.maxCount = maxCount;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
