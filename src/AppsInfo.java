import java.util.*;

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
        List<String> keyToDel = new ArrayList<>();
        for(Map.Entry<String, AppRecord> record : appsInfo.entrySet()){
            if(nowDate.getTime() - record.getValue().getRecvDate().getTime() > MAX_MILSEC_DELAY){
                keyToDel.add(record.getKey());
            }
        }
        for(String keyDel: keyToDel){
            appsInfo.remove(keyDel);
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