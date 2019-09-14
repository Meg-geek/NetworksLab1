import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AppsInfo {
    private Map<String, AppRecord> appsInfo = new HashMap<>();
    private int MAX_MILSEC_DELAY;
    private View view;

    public AppsInfo(int delay){
        MAX_MILSEC_DELAY = delay;
        view = new SwingView();
    }

    public void add(String ip, String msg){
        if(appsInfo.containsKey(ip)){
            appsInfo.get(ip).renewRecvDate();
        } else {
            appsInfo.put(ip, new AppRecord(msg));
        }
    }

    public void updateInfo(){
        Date nowDate = new Date();
        for(Map.Entry<String, AppRecord> record : appsInfo.entrySet()){
            if(record.getValue().getRecvDate().getTime() - nowDate.getTime() > MAX_MILSEC_DELAY){
                appsInfo.remove(record.getKey());
            }
        }
        view.update(appsInfo.entrySet());
    }
}


class AppRecord {
    private String msg;
    private Date recvDate;

    public AppRecord(String msg){
        this.msg = msg;
        this.recvDate = new Date();
    }

    public String getMsg(){
        return msg;
    }

    public Date getRecvDate(){
        return recvDate;
    }

    public void renewRecvDate(){
        recvDate = new Date();
    }
}