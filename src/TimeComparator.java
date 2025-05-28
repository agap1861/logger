import java.util.Comparator;

public class TimeComparator implements Comparator<Log> {

    @Override
    public int compare(Log log_1, Log log_2) {
        int diff = log_1.getDate().compareTo(log_2.getDate());
        if (diff!=0){
            return diff;
        }
        return log_1.getTime().compareTo(log_2.getTime());


    }
}
