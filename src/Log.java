

public class Log {
    private String date;
    private String time;
    private String ip;
    private String page;

    public Log(String date, String time, String ip, String page) {
        this.date = date;
        this.time = time;
        this.ip = ip;
        this.page = page;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
