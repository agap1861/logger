import java.util.List;

public interface LogSource {
    void readLogs();

    List<String> gelAllStat();

    void extraFunctions();

    void getRequestsByIP(String ip);

    List<Log> filterDatesByRange(String dateStart, String dateEnd);

    void saveStat();
}
